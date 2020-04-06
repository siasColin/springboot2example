package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * @Date: 2020-3-29
 * @Description: 用户管理
 */
@Controller
@RequestMapping("/userManage")
public class UserManageController {
    Logger logger = LoggerFactory.getLogger(UserManageController.class);

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/userManageList")
    public String userManageList(){
        return "sysManage/userManage/userManageList";
    }

    /**
     * 返回用户信息列表
     * @param paramMap
     *      userName 用户名字（模糊查询）
     *      orgCode 机构编码
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/userList")
    @ResponseBody
    public ResultInfo userList(@RequestParam Map<String,Object> paramMap) throws IOException {
        int pageNum = paramMap.get("page") == null ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int pageSize = paramMap.get("limit") == null ? 10 : Integer.parseInt(paramMap.get("limit").toString());
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> userList = sysUserService.selectByParams(paramMap);
        PageInfo<SysUser> result = new PageInfo(userList);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,userList,result.getTotal());
    }

    /**
     * 跳转到用户添加页面
     * @return
     */
    @GetMapping("/user")
    public String user(){
        return "sysManage/userManage/saveOrUpdateUser";
    }

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','INSERT_AUTH')")
    @PostMapping("/user")
    @ResponseBody
    public ResultInfo saveUser(SysUser user,String [] roleIds){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        user.setId(SnowflakeIdWorker.generateId());
        user.setCreateTime(new Date());
        if(sysUser != null){
            user.setCreateUser(sysUser.getLoginName());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        int num = sysUserService.saveUserAndRoles(user,roleIds);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,user);
        }
        return resultInfo;
    }

    /**
     * 跳转到用户编辑页面
     * @return
     */
    @GetMapping("/user/{id}")
    public String user(@PathVariable("id") String id, Map<String,Object> modelMap){
        SysUser sysUser = sysUserService.selectByPrimaryKey(Long.parseLong(id));
        modelMap.put("sysUser",sysUser);
        return "sysManage/userManage/saveOrUpdateUser";
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/user")
    @ResponseBody
    public ResultInfo updateUser(SysUser user,String [] roleIds){
        int num = sysUserService.updateUserAndRoles(user,roleIds);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,user);
        }
        return resultInfo;
    }

    /**
     * 根据id集合，删除用户
     * @param ids
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/user")
    @ResponseBody
    public ResultInfo deleteUser(Long [] ids){
        int num = this.sysUserService.deleteBatchByPrimaryKey(ids);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        }
        return resultInfo;
    }

    /**
     * 跳转到用户编辑页面
     * @return
     */
    @GetMapping("/resetpwd/{ids}")
    public String resetpwd(@PathVariable("ids")String ids, Map<String,Object> modelMap){
        modelMap.put("ids",ids);
        return "sysManage/userManage/resetPwd";
    }

    /**
     * 重置用户密码
     * @param password 新密码
     * @param loginPassword 当前登录用户密码
     * @param ids 要重置密码的用户id数组
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/resetPwd")
    @ResponseBody
    public ResultInfo resetPwd(String password,String loginPassword,String [] ids){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        if (password != null && !password.trim().equals("")
                && loginPassword != null && !loginPassword.trim().equals("") && ids != null && ids.length >0){
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            if(!bCryptPasswordEncoder.matches(loginPassword,sysUser.getPassword())){
                return ResultInfo.of(ResultCode.PWD_ERROR);
            }
            password =  bCryptPasswordEncoder.encode(password);
            int updateNum = this.sysUserService.updatePwdByUserIds(password,ids);
        }
        return resultInfo;
    }

}
