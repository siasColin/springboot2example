package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysOrg;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * @Package: cn.net.colin.controller.sysManage
 * @Author: sxf
 * @Date: 2020-3-15
 * @Description: 机构管理
 */
@Controller
@RequestMapping("/orgManage")
public class OrgManageController {
    Logger logger = LoggerFactory.getLogger(OrgManageController.class);

    @Autowired
    private ISysOrgService sysOrgService;

    @GetMapping("/orglist")
    public String orglist(){
        return "sysManage/orgManage/orgManageList";
    }


    /**
     * 返回满足zTree结构的机构信息
     * @param orgName 机构名字（模糊查询）
     * @param areaCode 地区编码
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/orgListTree")
    @ResponseBody
    public ResultInfo orgListTree(String orgName, String areaCode) throws IOException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(orgName != null && !orgName.equals("")){
            paramMap.put("orgName",orgName);
        }
        if(areaCode != null && !areaCode.equals("")){
            paramMap.put("areaCode",areaCode);
        }
        List<TreeNode> treeNodeList = sysOrgService.selectOrgTreeNodes(paramMap);
        return ResultInfo.ofData(ResultCode.SUCCESS,treeNodeList);
    }


    /**
     * 跳转到机构树页面
     * @param type
     * @return
     */
    @GetMapping("/orgtree/{type}")
    public String orglist(@PathVariable("type") String type){
        /**
         * type:
         *      none 普通ztree页面
         *      radio 单选框ztree页面
         *      check 复选框ztree页面
         */
        String targetPage = "sysManage/orgManage/orgtree";
        if(type.equals("radio")){//跳转到普通ztree页面
            targetPage = "sysManage/orgManage/orgtreeRadio";
        }else if(type.equals("check")){
            targetPage = "sysManage/orgManage/orgtreeCheck";
        }
        return targetPage;
    }

    /**
     * 根据机构编码查询机构信息
     * @param orgcode
     * @return
     */
    @GetMapping("/orgOnCode/{orgcode}")
    @ResponseBody
    public ResultInfo orgOnCode(@PathVariable("orgcode") String orgcode){
        SysOrg sysOrg = sysOrgService.selectByOrgCode(orgcode);
        return ResultInfo.ofData(ResultCode.SUCCESS,sysOrg);
    }

    /**
     * 保存机构信息
     * @param sysOrg
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','INSERT_AUTH')")
    @PostMapping("/org")
    @ResponseBody
    public ResultInfo saveOrg(SysOrg sysOrg){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        //父级ID为空，查询parentcode=-1的记录，默认parentcode=-1为根节点。如果没有记录那么新增节点作为根节点
        if(sysOrg.getParentCode() == null && (sysOrg.getParentCode() != null && sysOrg.getParentCode().trim().equals(""))){
            List<SysOrg> orgList = sysOrgService.selectByParentCode("-1");
            if(orgList != null && orgList.size() > 0){
                sysOrg.setParentCode(orgList.get(0).getOrgCode());
            }else{
                sysOrg.setParentCode("-1");
            }
        }
        sysOrg.setId(SnowflakeIdWorker.generateId());
        sysOrg.setCreateTime(new Date());
        sysOrg.setCreateUser(sysUser.getLoginName());
        int num = sysOrgService.insertSelective(sysOrg);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysOrg);
        }
        return resultInfo;
    }

    /**
     * 根据机构id查询机构信息
     * @param orgid
     * @return
     */
    @GetMapping("/org/{orgid}")
    @ResponseBody
    public ResultInfo org(@PathVariable("orgid") String orgid){
        SysOrg sysOrg = sysOrgService.selectByPrimaryKey(Long.parseLong(orgid));
        return ResultInfo.ofData(ResultCode.SUCCESS,sysOrg);
    }

    /**
     * 验证一个机构编码是否被其他表引用
     * @param orgCode
     * @return
     */
    @GetMapping("/orgRelation/{orgCode}")
    @ResponseBody
    public ResultInfo orgRelation(@PathVariable("orgCode") String orgCode){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        Map<String,Object> resultMap = sysOrgService.orgRelation(orgCode);
        //是否被引用
        boolean isQuote = false;
        if(resultMap.get("isQuote") != null){
            isQuote = (boolean) resultMap.get("isQuote");
        }
        if(isQuote){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,resultMap);
        }
        return  resultInfo;
    }

    /**
     * 更新地区信息
     * @param sysOrg
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/org")
    @ResponseBody
    public ResultInfo updateOrg(SysOrg sysOrg){
        //父级ID为空，查询parentcode=-1的记录，默认parentcode=-1为根节点。如果没有记录那么新增节点作为根节点
        if(sysOrg.getParentCode() == null && (sysOrg.getParentCode() != null && sysOrg.getParentCode().trim().equals(""))){
            List<SysOrg> orgList = sysOrgService.selectByParentCode("-1");
            if(orgList != null && orgList.size() > 0){
                sysOrg.setParentCode(orgList.get(0).getOrgCode());
            }else{
                sysOrg.setParentCode("-1");
            }
        }
        int num = sysOrgService.updateByPrimaryKeySelective(sysOrg);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysOrg);
        }
        return resultInfo;
    }

    /**
     * 更新机构信息，同时更新关联表中orgCode信息
     * @param sysOrg
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/org/{orgCode}")
    @ResponseBody
    public ResultInfo updateAreaWithFK(SysOrg sysOrg,@PathVariable("orgCode") String orgCode){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        //父级ID为空，查询parentcode=-1的记录，默认parentcode=-1为根节点。如果没有记录那么新增节点作为根节点
        if(sysOrg.getParentCode() == null && (sysOrg.getParentCode() != null && sysOrg.getParentCode().trim().equals(""))){
            List<SysOrg> orgList = sysOrgService.selectByParentCode("-1");
            if(orgList != null && orgList.size() > 0){
                sysOrg.setParentCode(orgList.get(0).getOrgCode());
            }else{
                sysOrg.setParentCode("-1");
            }
        }
        int num = sysOrgService.updatOrgWithFK(sysOrg,orgCode);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysOrg);
        }
        /**
         * 相等，则更新的是当前登录人所属机构信息。
         *      提示登录人重新登录
         *      或者更新securitycontext中的用户信息
         */
        if(sysUser.getSysOrg().getOrgCode().equals(orgCode)){
            //提示用户重新登录
//            resultInfo = ResultInfo.ofData(ResultCode.RELOGIN,sysArea);
            //更新用户信息中的地区信息，重新放入securitycontext中
            sysOrg.setSysArea(sysUser.getSysOrg().getSysArea());
            sysUser.setSysOrg(sysOrg);
            SpringSecurityUtil.setAuthentication(sysUser);
        }
        return resultInfo;
    }

    /**
     * 根据机构id删除机构记录
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/org/{id}")
    @ResponseBody
    public ResultInfo deleteOrg(@PathVariable("id") Long id){
        int num = sysOrgService.deleteByPrimaryKey(id);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        }
        return resultInfo;
    }
}
