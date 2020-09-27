package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.IdWorker;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
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
@ApiSort(value = 5)
@Api(tags = "用户管理",description = "用户管理相关API")
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
     *      roleIdNotBind 角色id，如果传入该字段则查询未绑定该角色的用户集合
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/userList")
    @ResponseBody
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取用户信息列表", notes = "返回用户信息列表信息",
            consumes = "application/x-www-form-urlencoded",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="页码",required=true,example = "1",paramType="query"),
            @ApiImplicitParam(name="limit",value="每页记录数",required=true,example = "10",paramType="query"),
            @ApiImplicitParam(name="userName",value="用户名字",required=false,paramType="query"),
            @ApiImplicitParam(name="orgCode",value="机构编码",required=false,paramType="query"),
            @ApiImplicitParam(name="roleIdNotBind",value="角色id（查询未绑定该角色的用户集合）",required=false,example = "1",paramType="query",dataType = "long")
    })
    public ResultInfo userList(@ApiIgnore @RequestParam Map<String,Object> paramMap) throws IOException {
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
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "保存用户信息",
            consumes = "application/x-www-form-urlencoded",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="loginName",value="账号",required=true,paramType="query"),
            @ApiImplicitParam(name="password",value="密码",required=true,paramType="query"),
            @ApiImplicitParam(name="roleIds",value="角色ID（逗号分隔）",required=false,paramType="query"),
    })
    public ResultInfo saveUser(SysUser user,String [] roleIds){
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        user.setId(IdWorker.getInstance().generateId());
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
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "更新用户信息",
            consumes = "application/x-www-form-urlencoded",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="用户ID",required=true,paramType="query",example = "0"),
            @ApiImplicitParam(name="userName",value="姓名",required=false,paramType="query"),
            @ApiImplicitParam(name="userGender",value="性别(0 男，1 女)",required=false,paramType="query"),
            @ApiImplicitParam(name="phoneNumber",value="电话号码",required=false,paramType="query"),
            @ApiImplicitParam(name="orgCode",value="所属机构编码",required=false,paramType="query"),
            @ApiImplicitParam(name="userStatus",value="用户状态(0 正常，2 禁用， 3 过期， 4 锁定)",required=false,paramType="query"),
            @ApiImplicitParam(name="roleIds",value="角色ID（逗号分隔）",required=false,paramType="query")
    })
    public ResultInfo updateUser(SysUser user,String [] roleIds){
        String password = user.getPassword();
        if(password != null && !password.trim().equals("")){
            //这里不允许更新密码
            user.setPassword(null);
        }
        int num = sysUserService.updateUserAndRoles(user,roleIds);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,user);
        }
        return resultInfo;
    }

    /**
     * 更新用户头像
     * @param headImg
     * @return
     */
    @PutMapping("/userHeadImg")
    @ResponseBody
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新当前用户头像",
            consumes = "application/x-www-form-urlencoded",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="headImg",value="头像",required=true,paramType="query"),
    })
    public ResultInfo updateHeadImg(String headImg){
        SysUser user = new SysUser();
        user.setHeadImg(headImg);
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        if (sysUser != null){
            user.setId(sysUser.getId());
        }else{
            return ResultInfo.of(ResultCode.STATUS_CODE_406);
        }
        int num = sysUserService.updateByPrimaryKeySelective(user);
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
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "用户批量删除",
            consumes = "application/x-www-form-urlencoded",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="ids",value="用户id（多个逗号分隔）",dataType = "String",required=true,paramType="query"),
    })
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
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "批量重置用户密码",
            consumes = "application/x-www-form-urlencoded",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="ids",value="用户id（多个逗号分隔）",dataType = "String",required=true,paramType="query"),
            @ApiImplicitParam(name="loginPassword",value="当前用户密码",dataType = "String",required=true,paramType="query"),
            @ApiImplicitParam(name="password",value="新密码",dataType = "String",required=true,paramType="query"),
    })
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

    /**
     * 根据角色id，查询角色关联的用户集合
     * @param roleId
     * @return
     */
    @GetMapping("/roleAndUserList/{roleId}")
    @ResponseBody
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "根据角色id，查询角色关联的用户集合",
            consumes = "application/x-www-form-urlencoded",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="页码",required=true,example = "1",paramType="query"),
            @ApiImplicitParam(name="limit",value="每页记录数",required=true,example = "10",paramType="query"),
            @ApiImplicitParam(name="roleId",value="角色ID",dataType = "String",required=true,paramType="path")
    })
    public ResultInfo roleAndUserList(@PathVariable("roleId") String roleId, HttpServletRequest request){
        int pageNum = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page").toString());
        int pageSize = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit").toString());
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> roleList = sysUserService.selectUserListByRoleId(roleId);
        PageInfo<SysUser> result = new PageInfo(roleList);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,roleList,result.getTotal());
    }

    /**
     * 跳转到角色用户绑定时，在指定角色下新增用户绑定的用户选择页面
     * @return
     */
    @GetMapping("/userListByRoleIdAndNotBind")
    public String userListByRoleIdAndNotBind(){
        return "sysManage/userManage/userListByRoleIdAndNotBind";
    }

}
