package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysAreaService;
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
 * @Date: 2020-3-6
 * @Description: 地区管理
 */
@Controller
@RequestMapping("/areaManage")
public class AreaManageController {
    Logger logger = LoggerFactory.getLogger(AreaManageController.class);

    @Autowired
    private ISysAreaService sysAreaService;

    @GetMapping("/arealist")
    public String arealist(){
        return "sysManage/areaManage/areaManageList";
    }

    /**
     * 跳转到地区树页面
     * @param type
     * @return
     */
    @GetMapping("/areatree/{type}")
    public String arealist(@PathVariable("type") String type){
        /**
         * type:
         *      none 普通ztree页面
         *      radio 单选框ztree页面
         *      check 复选框ztree页面
         */
        String targetPage = "sysManage/areaManage/areatree";
        if(type.equals("radio")){//跳转到普通ztree页面
            targetPage = "sysManage/areaManage/areatreeRadio";
        }else if(type.equals("check")){
            targetPage = "sysManage/areaManage/areatreeCheck";
        }
        return targetPage;
    }

    /**
     * 跳转到当前登录地区及子地区树页面
     * @param type
     * @return
     */
    @GetMapping("/childsAreatree/{type}")
    public String childsArealist(@PathVariable("type") String type){
        /**
         * type:
         *      none 普通ztree页面
         *      radio 单选框ztree页面
         *      check 复选框ztree页面
         */
        String targetPage = "sysManage/areaManage/childsAreatree";
        if(type.equals("radio")){//跳转到普通ztree页面
            targetPage = "sysManage/areaManage/childsAreatreeRadio";
        }else if(type.equals("check")){
            targetPage = "sysManage/areaManage/childsAreatreeCheck";
        }
        return targetPage;
    }

    /**
     * 返回满足zTree结构的地区信息
     * @param areaName 地区名字（模糊查询）
     * @param areaLevel 地区行政级别（0 国家 1 省 2 直辖市 3 地级市 4 县 5 乡/镇 6 村）
     * @param minAreaLevel 最小行政级别（例如：参数为4 则查询县级及以上行政级别的地区）
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/areaListTree")
    @ResponseBody
    public ResultInfo areaListTree(String areaName,String areaLevel,String minAreaLevel) throws IOException {
//        List<SysArea> sysAreas = sysAreaService.selectAll();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(areaName != null && !areaName.equals("")){
            paramMap.put("areaName",areaName);
        }
        if(areaLevel != null && !areaLevel.equals("")){
            paramMap.put("areaLevel",Integer.parseInt(areaLevel));
        }
        if(minAreaLevel != null && !minAreaLevel.equals("")){
            paramMap.put("minAreaLevel",Integer.parseInt(minAreaLevel));
        }
        List<TreeNode> treeNodeList = sysAreaService.selectAreaTreeNodes(paramMap);
        return ResultInfo.ofData(ResultCode.SUCCESS,treeNodeList);
    }

    /**
     * 返回当前登录地区以及子地区信息
     * @param minAreaLevel 最小行政级别（例如：参数为4 则查询县级及以上行政级别的地区）
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/childsAreaListTree/{minAreaLevel}")
    @ResponseBody
    public ResultInfo childsAreaListTree(@PathVariable("minAreaLevel") String minAreaLevel) throws IOException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(minAreaLevel != null && !minAreaLevel.equals("")){
            paramMap.put("minAreaLevel",Integer.parseInt(minAreaLevel));
        }
        List<TreeNode> treeNodeList = sysAreaService.selectChildsAreaListTree(paramMap);
        return ResultInfo.ofData(ResultCode.SUCCESS,treeNodeList);
    }

    @GetMapping("/area/{areaid}")
    @ResponseBody
    public ResultInfo area(@PathVariable("areaid") String areaid){
        SysArea sysArea = sysAreaService.selectByPrimaryKey(Long.parseLong(areaid));
        return ResultInfo.ofData(ResultCode.SUCCESS,sysArea);
    }

    @GetMapping("/areaOnCode/{areacode}")
    @ResponseBody
    public ResultInfo areaOnCode(@PathVariable("areacode") String areacode){
        SysArea sysArea = sysAreaService.selectByAreaCode(areacode);
        return ResultInfo.ofData(ResultCode.SUCCESS,sysArea);
    }

    /**
     * 保存地区信息
     * @param sysArea
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','INSERT_AUTH')")
    @PostMapping("/area")
    @ResponseBody
    public ResultInfo saveArea(SysArea sysArea){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        //父级ID为空，默认取当前登录用户所属地区的父级地区
        if(sysArea.getParentCode() == null && (sysArea.getParentCode() != null && sysArea.getParentCode().trim().equals(""))){
            sysArea.setParentCode(sysUser.getSysOrg().getSysArea().getParentCode());
        }
        sysArea.setId(SnowflakeIdWorker.generateId());
        sysArea.setCreateTime(new Date());
        sysArea.setCreateUser(sysUser.getLoginName());
        int num = sysAreaService.insertSelective(sysArea);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysArea);
        }
        return resultInfo;
    }

    /**
     * 更新地区信息
     * @param sysArea
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/area")
    @ResponseBody
    public ResultInfo updateArea(SysArea sysArea){
        //父级ID为空，默认去当前登录用户所属地区的父级地区
        if(sysArea.getParentCode() == null && (sysArea.getParentCode() != null && sysArea.getParentCode().trim().equals(""))){
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            sysArea.setParentCode(sysUser.getSysOrg().getSysArea().getParentCode());
        }
        int num = sysAreaService.updateByPrimaryKeySelective(sysArea);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysArea);
        }
        return resultInfo;
    }
    /**
     * 更新地区信息，同时更新关联表中AreaCode信息
     * @param sysArea
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/area/{areaCode}")
    @ResponseBody
    public ResultInfo updateAreaWithFK(SysArea sysArea,@PathVariable("areaCode") String areaCode){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        //父级ID为空，默认去当前登录用户所属地区的父级地区
        if(sysArea.getParentCode() == null && (sysArea.getParentCode() != null && sysArea.getParentCode().trim().equals(""))){
            sysArea.setParentCode(sysUser.getSysOrg().getSysArea().getParentCode());
        }
        int num = sysAreaService.updatAreaWithFK(sysArea,areaCode);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysArea);
        }
        /**
         * 相等，则更新的是当前登录人所属地区信息。
         *      提示登录人重新登录
         *      或者更新securitycontext中的用户信息
         */
        if(sysUser.getSysOrg().getSysArea().getAreaCode().equals(areaCode)){
            //提示用户重新登录
//            resultInfo = ResultInfo.ofData(ResultCode.RELOGIN,sysArea);
            //更新用户信息中的地区信息，重新放入securitycontext中
            sysUser.getSysOrg().setSysArea(sysArea);
            SpringSecurityUtil.setAuthentication(sysUser);
        }
        return resultInfo;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/area/{id}")
    @ResponseBody
    public ResultInfo deleteArea(@PathVariable("id") Long id){
        int num = sysAreaService.deleteByPrimaryKey(id);
         ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        }
        return resultInfo;
    }

    /**
     * 验证一个地区编码是否被其他表引用
     * @param areaCode
     * @return
     */
    @GetMapping("/areaRelation/{areaCode}")
    @ResponseBody
    public ResultInfo areaRelation(@PathVariable("areaCode") String areaCode){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        Map<String,Object> resultMap = sysAreaService.areaRelation(areaCode);
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

}
