package cn.net.colin.controller.articleManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.articleManage.ArticleType;
import cn.net.colin.model.articleManage.ArticleTypeCriteria;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.articleManage.IArticleTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.controller.articleManage
 * @Author: sxf
 * @Date: 2020-5-6
 * @Description: 文章分类管理
 */
@Controller
@RequestMapping("/articleTypeManage")
public class ArticleTypeManageController {
    Logger logger = LoggerFactory.getLogger(ArticleTypeManageController.class);

    @Autowired
    private IArticleTypeService articleTypeService;

    @GetMapping("/articleTypeList")
    public String articleTypeList(){
        return "articleManage/articleTypeList";
    }

    /**
     * 返回文章分类列表
     * @param paramMap
     *      typeName 分类名（模糊查询）
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/articleTypeListData")
    @ResponseBody
    public ResultInfo articleTypeListData(@RequestParam Map<String,Object> paramMap) throws IOException {
        int pageNum = paramMap.get("page") == null ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int pageSize = paramMap.get("limit") == null ? 10 : Integer.parseInt(paramMap.get("limit").toString());
        ArticleTypeCriteria articleTypeCriteria = new ArticleTypeCriteria();
        articleTypeCriteria.setOrderByClause("id desc");
        ArticleTypeCriteria.Criteria criteria = articleTypeCriteria.createCriteria();
        if(paramMap.get("typeName") != null){
            criteria.andTypeNameLike("%" + paramMap.get("typeName").toString().trim() + "%");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<ArticleType> articleTypeList = articleTypeService.selectByExample(articleTypeCriteria);
        PageInfo<ArticleType> result = new PageInfo(articleTypeList);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,articleTypeList,result.getTotal());
    }

    /**
     * 跳转到文章分类添加页面
     * @return
     */
    @GetMapping("/articleType")
    public String articleType(){
        return "articleManage/saveOrUpdateArticleType";
    }

    /**
     * 添加文章分类
     * @param articleType
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','INSERT_AUTH')")
    @PostMapping("/articleType")
    @ResponseBody
    public ResultInfo saveUser(ArticleType articleType){
        articleType.setId(SnowflakeIdWorker.generateId());
        articleType.setCreateTime(new Date());
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        if(sysUser != null){
            articleType.setCreateUser(sysUser.getUserName());
        }
        int num = articleTypeService.insertSelective(articleType);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,articleType);
        }
        return resultInfo;
    }

    /**
     * 跳转到文章类型编辑页面
     * @return
     */
    @GetMapping("/articleType/{articleTypeId}")
    public String user(@PathVariable("articleTypeId") long articleTypeId, Map<String,Object> modelMap){
        ArticleType articleType = articleTypeService.selectByPrimaryKey(articleTypeId);
        modelMap.put("articleType",articleType);
        return "articleManage/saveOrUpdateArticleType";
    }

    /**
     * 更新文章类型信息
     * @param articleType
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/articleType")
    @ResponseBody
    public ResultInfo updateArticleType(ArticleType articleType){
        int num = articleTypeService.updateByPrimaryKeySelective(articleType);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,articleType);
        }
        return resultInfo;
    }

    /**
     * 根据id集合，删除文章分类
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/articleType/{articleTypeIds}")
    @ResponseBody
    public ResultInfo deleteArticleType(@PathVariable("articleTypeIds") Long [] articleTypeIds){
        ArticleTypeCriteria articleTypeCriteria = new ArticleTypeCriteria();
        ArticleTypeCriteria.Criteria criteria = articleTypeCriteria.createCriteria();
        criteria.andIdIn(Arrays.asList(articleTypeIds));
        int num = articleTypeService.deleteByExample(articleTypeCriteria);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        }
        return resultInfo;
    }


    /**
     * 返回文章类型列表
     * @param paramMap
     *      typeName 类型名（模糊查询）
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/allArticleTypeListData")
    @ResponseBody
    public ResultInfo allArticleTypeListData(@RequestParam Map<String,Object> paramMap) throws IOException {
        ArticleTypeCriteria articleTypeCriteria = new ArticleTypeCriteria();
        articleTypeCriteria.setOrderByClause("id desc");
        List<ArticleType> articleTypeList = articleTypeService.selectByExample(articleTypeCriteria);
        return ResultInfo.ofData(ResultCode.SUCCESS,articleTypeList);
    }

}
