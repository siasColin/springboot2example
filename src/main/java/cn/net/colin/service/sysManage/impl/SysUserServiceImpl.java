package cn.net.colin.service.sysManage.impl;

import cn.net.colin.common.exception.BusinessRuntimeException;
import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.util.SQLUtil;
import cn.net.colin.mapper.sysManage.*;
import cn.net.colin.model.common.Role;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.*;
import cn.net.colin.service.sysManage.ISysOrgService;
import cn.net.colin.service.sysManage.ISysUserService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysOperatetypeMapper sysOperatetypeMapper;
    @Autowired
    private ISysOrgService sysOrgService;

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    public SysUser selectByPrimaryKey(Long id) {
        return this.sysUserMapper.selectByPrimaryKey(id);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.sysUserMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysUser record) {
        return this.sysUserMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysUser record) {
        return this.sysUserMapper.updateByPrimaryKey(record);
    }

    public int insert(SysUser record) {
        return this.sysUserMapper.insert(record);
    }

    public int insertSelective(SysUser record) {
        return this.sysUserMapper.insertSelective(record);
    }

    public int insertBatch(List<SysUser> sysUserList) {
        return this.sysUserMapper.insertBatch(sysUserList);
    }

    public int insertBatchSelective(List<SysUser> sysUserList) {
        return this.sysUserMapper.insertBatchSelective(sysUserList);
    }

    public int updateBatchByPrimaryKey(List<SysUser> sysUserList) {
        return this.sysUserMapper.updateBatchByPrimaryKey(sysUserList);
    }

    public int updateBatchByPrimaryKeySelective(List<SysUser> sysUserList) {
        return this.sysUserMapper.updateBatchByPrimaryKeySelective(sysUserList);
    }

    /**
     * 实现了SpringSecurity >> UserDetailsService 的接口方法
     * 用于登录认证
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = new SysUser();
        user.setLoginName(username);
        List<SysUser> userList = sysUserMapper.selective(user);
        if(userList != null && userList.size() > 0){
            SysUser sysUser = userList.get(0);
            //根据用户所在机构编码查询机构信息
            String orgCode = sysUser.getOrgCode();
            SysOrg sysOrg = sysOrgMapper.selectByOrgCode(orgCode);
            //根据机构所属地区编码查询地区信息
            String areaCode = sysOrg.getAreaCode();
            SysArea sysArea = sysAreaMapper.selectByAreaCode(areaCode);
            sysOrg.setSysArea(sysArea);
            sysUser.setSysOrg(sysOrg);

            /**
             * 角色和权限类似。
             * 	主要区别在于，角色具有特殊的语义-从Spring Security 4开始，“ ROLE_ ”前缀将通过任何与角色相关的方法自动添加（。
             * 	因此hasAuthority（'ROLE_ADMIN'）与hasRole（'ADMIN'）类似，因为会自动添加' ROLE_ '前缀。
             * 	但是使用授权的好处是我们完全不必使用ROLE_前缀。
             */
            //查询用户的系统角色
            List<SysRole> sysRoleList = sysRoleMapper.selectByUserId(sysUser.getId());
            //根据系统角色，查询出角色对应的权限
            List<SysOperatetype> sysOperatetypeList = sysOperatetypeMapper.selectOperatetypeListByRoleList(sysRoleList);
            //封装springsecurity 的角色对象
            if((sysRoleList != null && sysRoleList.size() > 0) ||
                    (sysOperatetypeList != null && sysOperatetypeList.size() > 0)){
                List<Role> roles = new ArrayList<Role>();
                //遍历系统角色
                for (SysRole sysRole : sysRoleList) {
                    Role role = new Role();
                    role.setId(sysRole.getId());
                    //和springsecurity保持一致系统角色拼接上ROLE_前缀
                    role.setRoleName("ROLE_"+sysRole.getRoleCode());
                    role.setRoleDesc(sysRole.getRoleName());
                    roles.add(role);
                }
                //遍历权限
                for (SysOperatetype sysOperatetype : sysOperatetypeList) {
                    Role role = new Role();
                    role.setId(sysOperatetype.getId());
                    role.setRoleName(sysOperatetype.getOperateCode());
                    role.setRoleDesc(sysOperatetype.getOperateName());
                    roles.add(role);
                }
                sysUser.setRoles(roles);
            }
            return sysUser;
        }else{//认证失败
            throw new BusinessRuntimeException(ResultCode.LOGIN_ERROR);
        }
    }

    @Override
    public List<SysUser> selectByParams(Map<String, Object> paramMap) {
        //如果没有传入orgCode，则返回登录用户可维护的所有机构下的用户。如果传入了orgCode，说明是要查询指定机构的用户
        if(paramMap != null && (paramMap.get("orgCode") == null ||
                (paramMap.get("orgCode") != null && paramMap.get("orgCode").toString().trim().equals("")))){
            Map<String,Object> orgParams = new HashMap<String,Object>();
            //首先获取当前登录用户可维护的机构
            List<TreeNode> treeNodeList = sysOrgService.selectOrgTreeNodes(orgParams);
            if(treeNodeList != null && treeNodeList.size() > 0){
                List<List<TreeNode>> orgList = SQLUtil.getSumArrayList(treeNodeList);
                paramMap.put("orgList",orgList);
            }
        }
        int pageNum = paramMap.get("page") == null ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int pageSize = paramMap.get("limit") == null ? 10 : Integer.parseInt(paramMap.get("limit").toString());
        PageHelper.startPage(pageNum,pageSize);
        return this.sysUserMapper.selectByParams(paramMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveUserAndRoles(SysUser user, String[] roles) {
        int saveNum = this.sysUserMapper.insertSelective(user);
        if(roles != null && roles.length > 0){
            long userId = user.getId();
            List<Map<String,Object>> userAndRoleList = new ArrayList<Map<String,Object>>();
            for (String roleId: roles) {
                Map<String,Object> userAndRoleMap = new HashMap<String,Object>(2);
                userAndRoleMap.put("roleId",Long.parseLong(roleId));
                userAndRoleMap.put("userId",userId);
                userAndRoleList.add(userAndRoleMap);
            }
            this.sysRoleMapper.saveUserAndRoleList(userAndRoleList);
        }
        return saveNum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserAndRoles(SysUser user, String[] roleIds) {
        int updateNum = this.sysUserMapper.updateByPrimaryKeySelective(user);
        //先删除用户和角色的关联关系
        Long [] userIds = {user.getId()};
        this.sysRoleMapper.deleteUserAndRoleByUserId(userIds);
        //再保存
        if(roleIds != null && roleIds.length > 0){
            long userId = user.getId();
            List<Map<String,Object>> userAndRoleList = new ArrayList<Map<String,Object>>();
            for (String roleId: roleIds) {
                Map<String,Object> userAndRoleMap = new HashMap<String,Object>(2);
                userAndRoleMap.put("roleId",Long.parseLong(roleId));
                userAndRoleMap.put("userId",userId);
                userAndRoleList.add(userAndRoleMap);
            }
            this.sysRoleMapper.saveUserAndRoleList(userAndRoleList);
        }
        return updateNum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatchByPrimaryKey(Long[] ids) {
        //1.删除用户
        int deleteNum = this.sysUserMapper.deleteBatchByPrimaryKey(ids);
        //2.删除用户角色关联关系
        this.sysRoleMapper.deleteUserAndRoleByUserId(ids);
        return deleteNum;
    }

    @Override
    public int updatePwdByUserIds(String password, String[] userIds) {
        int updateNum = this.sysUserMapper.updatePwdByUserIds(password,userIds);
        return updateNum;
    }

    @Override
    public List<SysUser> selectUserListByRoleId(String roleId) {
        return sysUserMapper.selectUserListByRoleId(roleId);
    }
}