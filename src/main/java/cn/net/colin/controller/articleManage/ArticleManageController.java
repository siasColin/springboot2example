package cn.net.colin.controller.articleManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.articleManage.*;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.articleManage.IArticleCommentService;
import cn.net.colin.service.articleManage.IArticleInfoService;
import cn.net.colin.service.articleManage.IArticleLikesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * @Package: cn.net.colin.controller.articleManage
 * @Author: sxf
 * @Date: 2020-5-7
 * @Description: 文章管理
 */
@Controller
@RequestMapping("/articleManage")
public class ArticleManageController {
    Logger logger = LoggerFactory.getLogger(ArticleManageController.class);

    @Autowired
    private IArticleInfoService articleInfoService;

    @Autowired
    private IArticleCommentService articleCommentService;

    @Autowired
    private IArticleLikesService articleLikesService;

    /**
     * 跳转到文章发布页面
     * @return
     */
    @GetMapping("/article")
    public String article(){
        return "articleManage/article";
    }

    /**
     * 添加文章
     * @param articleInfo
     * @return
     */
    @PostMapping("/article")
    @ResponseBody
    public ResultInfo saveArticle(ArticleInfo articleInfo){
        articleInfo.setId(SnowflakeIdWorker.generateId());
        articleInfo.setCreateTime(new Date());
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        if(sysUser != null){
            articleInfo.setUserId(sysUser.getId());
        }
        int num = articleInfoService.insertSelective(articleInfo);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,articleInfo);
        }
        return resultInfo;
    }

    /**
     * 跳转到已发布文章列表页面
     * @return
     */
    @GetMapping("/myArticleList")
    public String myArticleList(){
        return "articleManage/myArticleList";
    }

    /**
     * 返回文章列表
     * @param paramMap
     *      typeId 分类id
     *      infoTitle 标题（模糊查询）
     * @param own 1 返回自己发布的， 0 表示管理员登录，返回所有
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/articleListData/{own}")
    @ResponseBody
    public ResultInfo articleListData(@RequestParam Map<String,Object> paramMap,@PathVariable("own") int own) throws IOException {
        int pageNum = paramMap.get("page") == null ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int pageSize = paramMap.get("limit") == null ? 10 : Integer.parseInt(paramMap.get("limit").toString());
        ArticleInfoCriteria articleInfoCriteria = new ArticleInfoCriteria();
        articleInfoCriteria.setOrderByClause("id desc");
        ArticleInfoCriteria.Criteria criteria = articleInfoCriteria.createCriteria();
        if(paramMap.get("typeId") != null){
            criteria.andTypeIdEqualTo(Long.parseLong((paramMap.get("typeId").toString().trim())));
        }
        if(paramMap.get("infoTitle") != null){
            criteria.andInfoTitleLike("%"+paramMap.get("infoTitle").toString().trim()+"%");
        }
        if(own == 1){//查询自己发布的
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            if(sysUser != null && sysUser.getId() != null){
                criteria.andUserIdEqualTo(sysUser.getId());
            }else{
                return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,null,0l);//没有登录，则返回空数据
            }
        }else{
            if(!SpringSecurityUtil.hasRole("ADMIN_AUTH")) {//非管理员，没有获取权限
                return  ResultInfo.of(ResultCode.STATUS_CODE_403);
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        List<ArticleInfo> articleList = articleInfoService.selectByExample(articleInfoCriteria);
        PageInfo<ArticleType> result = new PageInfo(articleList);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,articleList,result.getTotal());
    }

    /**
     * 跳转到文章管理页面
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH')")
    @GetMapping("/articleList")
    public String articleList(){
        return "articleManage/articleList";
    }

    /**
     * 跳转到文章更新页面
     * @return
     */
    @GetMapping("/article/{articleId}")
    public String article(@PathVariable("articleId") long articleId, Map<String,Object> modelMap){
        ArticleInfo articleInfo = articleInfoService.selectByPrimaryKey(articleId);
        modelMap.put("articleInfo",articleInfo);
        return "articleManage/article";
    }


    /**
     * 更新文章
     * @param articleInfo
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/article")
    @ResponseBody
    public ResultInfo updateArticle(ArticleInfo articleInfo){
        int num = articleInfoService.updateByPrimaryKeySelective(articleInfo);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,articleInfo);
        }
        return resultInfo;
    }

    /**
     * 根据id集合，删除文章分类
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/article/{articleIds}")
    @ResponseBody
    public ResultInfo deleteArticle(@PathVariable("articleIds") Long [] articleIds){
        ArticleInfoCriteria articleInfoCriteria = new ArticleInfoCriteria();
        ArticleInfoCriteria.Criteria criteria = articleInfoCriteria.createCriteria();
        criteria.andIdIn(Arrays.asList(articleIds));
        int num = articleInfoService.deleteByExample(articleInfoCriteria);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        }
        return resultInfo;
    }

    /**
     * 跳转到文章浏览页面
     * @return
     */
    @GetMapping("/articleView/{articleId}")
    public String articleView(@PathVariable("articleId") long articleId, Map<String,Object> modelMap){
        ArticleInfo articleInfo = articleInfoService.selectByPrimaryKey(articleId);
        modelMap.put("articleInfo",articleInfo);
        int isLike = 0;//当前登录用户是否已点赞，0 未点赞 1 已点赞
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        if(sysUser != null && sysUser.getId() != null){
            ArticleLikesCriteria articleLikesCriteria = new ArticleLikesCriteria();
            ArticleLikesCriteria.Criteria criteria = articleLikesCriteria.createCriteria();
            criteria.andUserIdEqualTo(sysUser.getId());
            criteria.andInfoIdEqualTo(articleId);
            long count = articleLikesService.countByExample(articleLikesCriteria);
            if(count > 0){
                isLike = 1;
            }
        }
        modelMap.put("isLike",isLike);
        //阅读量+1
        ArticleInfo updateArticle = new ArticleInfo();
        updateArticle.setId(articleId);
        updateArticle.setInfoAmount(articleInfo.getInfoAmount()+1);
        articleInfoService.updateByPrimaryKeySelective(updateArticle);

        //查询评论信息
        /*ArticleCommentCriteria articleCommentCriteria = new ArticleCommentCriteria();
        articleCommentCriteria.setOrderByClause("id asc");
        ArticleCommentCriteria.Criteria criteria = articleCommentCriteria.createCriteria();
        criteria.andInfoIdEqualTo(articleInfo.getId());
        criteria.andParentIdEqualTo(-1l);
        List<ArticleComment> listComment = articleCommentService.selectByExample(articleCommentCriteria);
        modelMap.put("listComment",listComment);*/
        return "articleManage/articleView";
    }

    /**
     * 获取评论集合
     * @param articleId
     * @return
     */
    @GetMapping("/comment/{articleId}")
    @ResponseBody
    public ResultInfo saveComment(@PathVariable("articleId") long articleId,int page){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        try{
            //查询评论信息
            ArticleCommentCriteria articleCommentCriteria = new ArticleCommentCriteria();
            articleCommentCriteria.setOrderByClause("id desc");
            ArticleCommentCriteria.Criteria criteria = articleCommentCriteria.createCriteria();
            criteria.andInfoIdEqualTo(articleId);
            criteria.andParentIdEqualTo(-1l);
            PageHelper.startPage(page,10);
            List<ArticleComment> listComment = articleCommentService.selectByExample(articleCommentCriteria);
            PageInfo<ArticleComment> result = new PageInfo(listComment);
            long total = result.getTotal();
            int totalPage = 0;
            if(total % 10 == 0l){
                totalPage = new Long(total/10).intValue();
            }else{
                totalPage = new Long(total/10).intValue()+1;
            }
            resultInfo = ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,listComment,totalPage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 评论
     * @param articleComment
     * @return
     */
    @PostMapping("/comment")
    @ResponseBody
    public ResultInfo saveComment(ArticleComment articleComment){
        articleComment.setId(SnowflakeIdWorker.generateId());
        articleComment.setCommentTime(new Date());
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        if(sysUser != null){
            articleComment.setFromUserId(sysUser.getId());
        }
        int num = articleCommentService.insertSelective(articleComment);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            if(articleComment.getParentId() != null && articleComment.getParentId() != -1l){
                //查询子评论信息
                ArticleCommentCriteria articleCommentCriteria = new ArticleCommentCriteria();
                articleCommentCriteria.setOrderByClause("id asc");
                ArticleCommentCriteria.Criteria criteria = articleCommentCriteria.createCriteria();
                criteria.andInfoIdEqualTo(articleComment.getInfoId());
                criteria.andParentIdEqualTo(articleComment.getParentId());

                List<ArticleComment> listComment = articleCommentService.selectByExample(articleCommentCriteria);
                resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,listComment);
            }else{
                resultInfo = ResultInfo.of(ResultCode.SUCCESS);
            }
        }
        return resultInfo;
    }

    /**
     * 根据id集合，删除文章分类
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/comment/{id}/{pid}")
    @ResponseBody
    public ResultInfo deleteArticle(@PathVariable("id") Long id,@PathVariable("pid") Long pid){
        ArticleComment articleComment = articleCommentService.selectByPrimaryKey(id);
        ArticleCommentCriteria articleCommentCriteria = new ArticleCommentCriteria();
        ArticleCommentCriteria.Criteria criteria = articleCommentCriteria.createCriteria();
        criteria.andIdEqualTo(id);
        articleCommentCriteria.or().andParentIdEqualTo(id);
        int num = articleCommentService.deleteByExample(articleCommentCriteria);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            if(pid == -1){
                /*ArticleCommentCriteria articleCommentCriteria_Query = new ArticleCommentCriteria();
                articleCommentCriteria_Query.setOrderByClause("id asc");
                ArticleCommentCriteria.Criteria criteria_Query = articleCommentCriteria_Query.createCriteria();
                criteria_Query.andInfoIdEqualTo(articleComment.getInfoId());
                if(articleComment.getParentId() == null){
                    criteria_Query.andParentIdEqualTo(-1l);
                }else{
                    criteria_Query.andParentIdEqualTo(articleComment.getParentId());
                }

                List<ArticleComment> listComment = articleCommentService.selectByExample(articleCommentCriteria_Query);
                resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,listComment);*/
                resultInfo = ResultInfo.of(ResultCode.SUCCESS);
            }else{
                //查询子评论信息
                ArticleCommentCriteria commentCriteria = new ArticleCommentCriteria();
                commentCriteria.setOrderByClause("id asc");
                ArticleCommentCriteria.Criteria criteriaP = commentCriteria.createCriteria();
                criteriaP.andParentIdEqualTo(pid);
                List<ArticleComment> listComment = articleCommentService.selectByExample(commentCriteria);
                resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,listComment);
            }
        }
        return resultInfo;
    }

    /**
     * 跳转到消息中心页面
     * @return
     */
    @GetMapping("/msgbox")
    public String msgbox(Map<String,Object> modelMap){
        //查询当前登录人，收到的评论或者回复
        return "articleManage/msgbox";
    }

    /**
     * 消息中心
     * @param page
     * @return
     */
    @GetMapping("/msgboxdata")
    @ResponseBody
    public ResultInfo msgboxdata(int page){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        resultInfo.setTotal(0l);
        try{
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            List<ArticleComment> listComment = new ArrayList<ArticleComment>();
            if(sysUser != null){
                ArticleCommentCriteria articleCommentCriteria = new ArticleCommentCriteria();
                articleCommentCriteria.setOrderByClause("id desc");
                ArticleCommentCriteria.Criteria criteria = articleCommentCriteria.createCriteria();
                criteria.andToUserIdEqualTo(sysUser.getId());
                criteria.andFromUserIdNotEqualTo(sysUser.getId());
                criteria.andReadStatusEqualTo(0);//未阅
                PageHelper.startPage(page,10);
                listComment = articleCommentService.selectByExample(articleCommentCriteria);
                PageInfo<ArticleComment> result = new PageInfo(listComment);
                long total = result.getTotal();
                int totalPage = 0;
                if(total % 10 == 0l){
                    totalPage = new Long(total/10).intValue();
                }else{
                    totalPage = new Long(total/10).intValue()+1;
                }
                //更新状态,为已读
                if(listComment != null && listComment.size() > 0){
                    List<Long> commentIds = new ArrayList<Long>();
                    for(int i=0;i<listComment.size();i++){
                        commentIds.add(listComment.get(i).getId());
                    }
                    ArticleCommentCriteria articleCommentCriteria_update = new ArticleCommentCriteria();
                    ArticleCommentCriteria.Criteria criteria_update = articleCommentCriteria_update.createCriteria();
                    criteria_update.andIdIn(commentIds);
                    ArticleComment articleComment_update = new ArticleComment();
                    articleComment_update.setReadStatus(1);
                    int updateNum = articleCommentService.updateByExampleSelective(articleComment_update,articleCommentCriteria_update);
                }
                resultInfo = ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,listComment,totalPage);
            }
        }catch (Exception e){
            e.printStackTrace();;
        }
        return resultInfo;
    }

    /**
     * 消息中心消息数量
     * @return
     */
    @GetMapping("/msgboxcount")
    @ResponseBody
    public ResultInfo msgboxcount(){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        Map<String,Long> countMap = new HashMap<String,Long>();
        try{
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            List<ArticleComment> listComment = new ArrayList<ArticleComment>();
            if(sysUser != null) {
                ArticleCommentCriteria articleCommentCriteria = new ArticleCommentCriteria();
                ArticleCommentCriteria.Criteria criteria = articleCommentCriteria.createCriteria();
                criteria.andToUserIdEqualTo(sysUser.getId());
                criteria.andFromUserIdNotEqualTo(sysUser.getId());
                criteria.andReadStatusEqualTo(0);//未阅
                long count = articleCommentService.countByExample(articleCommentCriteria);
                countMap.put("count",count);
                resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,countMap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 点赞
     * @param articleLikes
     * @return
     */
    @PostMapping("/likes")
    @ResponseBody
    public ResultInfo likes(ArticleLikes articleLikes){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        try{
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            if(sysUser != null && sysUser.getId() != null){
                articleLikes.setId(SnowflakeIdWorker.generateId());
                articleLikes.setCreateTime(new Date());
                articleLikes.setUserId(sysUser.getId());
                int num = articleLikesService.insertSelective(articleLikes);
                if(num > 0){
                    resultInfo = ResultInfo.of(ResultCode.SUCCESS);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 取消点赞
     * @param infoId
     * @return
     */
    @DeleteMapping("/likes/{infoId}")
    @ResponseBody
    public ResultInfo deleteLikes(@PathVariable("infoId") Long infoId){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        try{
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            if(sysUser != null && sysUser.getId() != null){
                ArticleLikesCriteria articleLikesCriteria = new ArticleLikesCriteria();
                ArticleLikesCriteria.Criteria criteria = articleLikesCriteria.createCriteria();
                criteria.andUserIdEqualTo(sysUser.getId());
                criteria.andInfoIdEqualTo(infoId);
                int num = articleLikesService.deleteByExample(articleLikesCriteria);
                resultInfo = ResultInfo.of(ResultCode.SUCCESS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 消息中心消息数量
     * @return
     */
    @GetMapping("/likescount/{infoId}")
    @ResponseBody
    public ResultInfo likescount(@PathVariable("infoId") Long infoId) {
        ResultInfo resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        Map<String, Long> countMap = new HashMap<String, Long>();
        try{
            ArticleLikesCriteria articleLikesCriteria = new ArticleLikesCriteria();
            ArticleLikesCriteria.Criteria criteria = articleLikesCriteria.createCriteria();
            criteria.andInfoIdEqualTo(infoId);
            long count = articleLikesService.countByExample(articleLikesCriteria);
            countMap.put("count",count);
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,countMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }


}
