package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.model.sysManage.SysModulelist;
import cn.net.colin.model.sysManage.SysOrg;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysModullistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.controller.sysManage
 * @Author: sxf
 * @Date: 2020-3-6
 * @Description: 菜单管理
 */
@Controller
@RequestMapping("/menuManage")
public class MenuManageController {
    Logger logger = LoggerFactory.getLogger(MenuManageController.class);

    @Autowired
    private ISysModullistService sysModullistService;

    @GetMapping("/menulist")
    public String arealist(){
        return "sysManage/menuManage/menuManageList";
    }

    /**
     * 返回满足zTree结构的菜单信息
     * @param menuName 菜单名字（模糊查询）
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/menuListTree")
    @ResponseBody
    public ResultInfo menuListTree(String menuName) throws IOException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(menuName != null && !menuName.equals("")){
            paramMap.put("moduleName",menuName);
        }
        List<TreeNode> treeNodeList = sysModullistService.selectMenuTreeNodes(paramMap);
        return ResultInfo.ofData(ResultCode.SUCCESS,treeNodeList);
    }

    /**
     * 跳转到菜单树页面
     * @param type
     * @return
     */
    @GetMapping("/menutree/{type}")
    public String arealist(@PathVariable("type") String type){
        /**
         * type:
         *      none 普通ztree页面
         *      radio 单选框ztree页面
         *      check 复选框ztree页面
         */
        String targetPage = "sysManage/menuManage/menutree";
        if(type.equals("radio")){//跳转到普通ztree页面
            targetPage = "sysManage/menuManage/menutreeRadio";
        }else if(type.equals("check")){
            targetPage = "sysManage/menuManage/menutreeCheck";
        }
        return targetPage;
    }

    /**
     * 保存菜单信息
     * @param sysModulelist
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','INSERT_AUTH')")
    @PostMapping("/menu")
    @ResponseBody
    public ResultInfo saveArea(SysModulelist sysModulelist){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        //父级ID为空，查询pid=-1的记录，默认pid=-1为根节点。如果没有记录那么新增节点作为根节点
        if(sysModulelist.getPid() == null){
            List<SysModulelist> moduleLists = sysModullistService.selectByPid(-1l);
            if(moduleLists != null && moduleLists.size() > 0){
                sysModulelist.setPid(moduleLists.get(0).getId());
            }else{
                sysModulelist.setPid(-1l);
            }
        }
        sysModulelist.setId(SnowflakeIdWorker.generateId());
        sysModulelist.setCreateTime(new Date());
        sysModulelist.setCreateUser(sysUser.getLoginName());
        int num = sysModullistService.insertSelective(sysModulelist);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysModulelist);
        }
        return resultInfo;
    }

    /**
     * 根据菜单id，查询菜单信息
     * @param id
     * @return
     */
    @GetMapping("/menu/{id}")
    @ResponseBody
    public ResultInfo area(@PathVariable("id") String id){
        SysModulelist sysModulelist = sysModullistService.selectByPrimaryKey(Long.parseLong(id));
        return ResultInfo.ofData(ResultCode.SUCCESS,sysModulelist);
    }

    /**
     * 更新菜单信息
     * @param sysModulelist
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/menu")
    @ResponseBody
    public ResultInfo updateMenu(SysModulelist sysModulelist){
        //父级ID为空，查询pid=-1的记录，默认pid=-1为根节点。如果没有记录那么新增节点作为根节点
        if(sysModulelist.getPid() == null){
            List<SysModulelist> moduleLists = sysModullistService.selectByPid(-1l);
            if(moduleLists != null && moduleLists.size() > 0){
                sysModulelist.setPid(moduleLists.get(0).getId());
            }else{
                sysModulelist.setPid(-1l);
            }
        }
        int num = sysModullistService.updateByPrimaryKeySelective(sysModulelist);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysModulelist);
        }
        return resultInfo;
    }

    /**
     * 根据id，删除菜单
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/menu/{id}")
    @ResponseBody
    public ResultInfo deleteArea(@PathVariable("id") Long id){
        int num = sysModullistService.deleteByPrimaryKey(id);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        }
        return resultInfo;
    }
}