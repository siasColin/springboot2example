package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.model.sysManage.SysOperatetype;
import cn.net.colin.model.sysManage.SysRole;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
 * @Date: 2020-3-22
 * @Description: 角色管理
 */
@Controller
@RequestMapping("/roleManage")
public class RoleManageController {
    Logger logger = LoggerFactory.getLogger(AreaManageController.class);

    @Autowired
    private ISysRoleService sysRoleService;

    @GetMapping("/roleManageList")
    public String arealist(){
        return "sysManage/roleManage/roleManageList";
    }

    /**
     * 返回角色信息列表
     * @param roleName 角色名字（模糊查询）
     * @param areaCode 地区编码
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/roleList")
    @ResponseBody
    public ResultInfo areaListTree(String roleName, String areaCode) throws IOException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(roleName != null && !roleName.equals("")){
            paramMap.put("roleName",roleName);
        }
        if(areaCode != null && !areaCode.equals("")){
            paramMap.put("areaCode",areaCode);
        }
        PageHelper.startPage(1,10);
        List<SysRole> roleList = sysRoleService.selectByParams(paramMap);
        PageInfo<SysRole> result = new PageInfo(roleList);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,roleList,result.getTotal());
    }

    /**
     * 跳转到角色添加页面
     * @return
     */
    @GetMapping("/role")
    public String role(){
        return "sysManage/roleManage/saveOrUpdateRole";
    }

    /**
     * 保存角色信息
     * @param sysRole
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','INSERT_AUTH')")
    @PostMapping("/role")
    @ResponseBody
    public ResultInfo saveRole(SysRole sysRole,String [] systemPermissions){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        sysRole.setId(SnowflakeIdWorker.generateId());
        sysRole.setCreateTime(new Date());
        if(sysUser != null){
            sysRole.setCreateUser(sysUser.getLoginName());
            if(sysRole.getRoleAttr() == 1){//私有
                sysRole.setOrgCode(sysUser.getOrgCode());
            }
        }
        int num = sysRoleService.saveRoleAndPermissions(sysRole,systemPermissions);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysRole);
        }
        return resultInfo;
    }

    /**
     * 跳转到角色编辑页面
     * @return
     */
    @GetMapping("/role/{id}")
    public String role(@PathVariable("id") String id,Map<String,Object> modelMap){
        SysRole sysRole = sysRoleService.selectByPrimaryKey(Long.parseLong(id));
        modelMap.put("sysRole",sysRole);
        return "sysManage/roleManage/saveOrUpdateRole";
    }

    /**
     * 更新角色信息
     * @param sysRole
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','INSERT_AUTH')")
    @PutMapping("/role")
    @ResponseBody
    public ResultInfo updateRole(SysRole sysRole,String [] systemPermissions){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        if(sysUser != null){
            if(sysRole.getRoleAttr() == 1){//私有
                SysRole role = sysRoleService.selectByPrimaryKey(sysRole.getId());
                if(role.getRoleAttr() != 1){//共享改私有
                    sysRole.setOrgCode(sysUser.getOrgCode());
                }
            }
        }
        int num = sysRoleService.updateRoleAndPermissions(sysRole,systemPermissions);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysRole);
        }
        return resultInfo;
    }

    /**
     * 根据id集合，删除角色
     * @param ids
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/role")
    @ResponseBody
    public ResultInfo deleteRole(Long [] ids){
        int num = sysRoleService.deleteBatchByPrimaryKey(ids);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        }
        return resultInfo;
    }
}
