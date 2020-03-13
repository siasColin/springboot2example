package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/areaListTree")
    @ResponseBody
    public ResultInfo areaListTree(Area area, HttpServletRequest request) throws IOException {
        String areaName = request.getParameter("areaName") == null ? "" : request.getParameter("areaName").toString();
//        List<SysArea> sysAreas = sysAreaService.selectAll();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(!areaName.equals("")){
            paramMap.put("areaName",areaName);
        }
        List<TreeNode> treeNodeList = sysAreaService.selectAreaTreeNodes(paramMap);
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

}
