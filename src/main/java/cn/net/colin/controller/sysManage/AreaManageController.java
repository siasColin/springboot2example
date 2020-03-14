package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysAreaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.geom.Area;
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
    ISysAreaService sysAreaService;

    @GetMapping("/arealist")
    public String arealist(){
        return "sysManage/areaManage/areaManageList";
    }

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
        /**
         * 判断是否拥有管理员权限
         *      有则不做过滤，返回全部地区信息；
         *      无则只返回当前登录用户及其子地区信息
         */
        if(!SpringSecurityUtil.hasRole("ADMIN_AUTH")){
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            SysArea sysArea = sysUser.getSysOrg().getSysArea();
            if(sysUser != null && sysArea != null){
                List<TreeNode> childList = new ArrayList<TreeNode>();
                TreeNode pTreeNode = new TreeNode();
                pTreeNode.setId(sysArea.getAreaCode());
                pTreeNode.setName(sysArea.getAreaName());
                pTreeNode.setPId(sysArea.getParentCode());
                pTreeNode.setIsParent("true");
                pTreeNode.setOpen("true");
                childList.add(pTreeNode);
                areaTreeChildRecursive(sysArea.getAreaCode(),treeNodeList,childList);
                return ResultInfo.ofData(ResultCode.SUCCESS,childList);
            }
        }
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
        SysUser sysUser = (SysUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        int num = sysAreaService.updateByPrimaryKeySelective(sysArea);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysArea);
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
     * 递归筛选所有子地区
     * @param pAreaCode 父级地区编码
     * @param treeNodeList 所有地区节点集合
     * @param childList 子地区集合
     * @return
     */
    private void areaTreeChildRecursive(String pAreaCode, List<TreeNode> treeNodeList, List<TreeNode> childList) {
        for (TreeNode treeNode : treeNodeList) {
            if(treeNode.getPId().equals(pAreaCode)){
                childList.add(treeNode);
                areaTreeChildRecursive(treeNode.getId(),treeNodeList,childList);
            }
        }
    }

}
