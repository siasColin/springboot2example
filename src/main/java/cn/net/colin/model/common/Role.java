package cn.net.colin.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Package: cn.net.colin.model.common
 * @Author: sxf
 * @Date: 2020-3-5
 * @Description: 角色实体，SysRole偏重于绑定系统菜单。而实际上SysRole所拥有的实际权限角色则是Role。
 * 一个SysRole可能对应多个Role
 * 这里我们整合了SpringSecurity，所以实现GrantedAuthority
 */
public class Role implements GrantedAuthority {

    private Long id;
    private String roleName;
    private String roleDesc;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
