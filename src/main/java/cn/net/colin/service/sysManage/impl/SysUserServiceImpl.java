package cn.net.colin.service.sysManage.impl;

import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.mapper.sysManage.SysUserMapper;
import cn.net.colin.model.common.Role;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

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
       /* List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        //admin/123456  ;$2a$10$Jw923tUCmRkkvZ/tv2YdYO3UKLN934VHz1ssADyxyPSM43sUWeAR6
        UserDetails userDetails = new User("admin","$2a$10$Jw923tUCmRkkvZ/tv2YdYO3UKLN934VHz1ssADyxyPSM43sUWeAR6",authorities);
        return userDetails;*/
        /**
         * 1.根据username（loginName）从数据库中查出用户对象
         * 2.查出用户拥有的角色集合，放到用户对象中。（因为在该系统设计中角色信息需要业务转换，所以单独提取出来）
         */
        //先不走数据库
        SysUser user = new SysUser();
        user.setId(SnowflakeIdWorker.generateId());
        user.setUserName("管理员");
        user.setLoginName("admin");
        user.setPassword("$2a$10$Jw923tUCmRkkvZ/tv2YdYO3UKLN934VHz1ssADyxyPSM43sUWeAR6");//123456经过加密后的字符
        List<Role> roles = new ArrayList<Role>();
        //这里我们可以放一些增、删、改等角色信息
        Role role = new Role();
        role.setId(SnowflakeIdWorker.generateId());
        role.setRoleName("INSERT_ROLE");
        role.setRoleDesc("拥有新增数据权限");
        roles.add(role);
        user.setRoles(roles);
        return user;
    }
}