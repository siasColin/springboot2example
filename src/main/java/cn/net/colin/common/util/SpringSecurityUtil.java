package cn.net.colin.common.util;

import cn.net.colin.model.common.Role;
import cn.net.colin.model.sysManage.SysUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @Package: cn.net.colin.common.util
 * @Author: sxf
 * @Date: 2020-3-14
 * @Description: springsecurity 工具类
 */
public class SpringSecurityUtil {

    /**
     * 获取SecurityContext中存放的用户信息
     * @return
     */
    public static SysUser getPrincipal(){
        try {
            return (SysUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param rolename
     * @return
     */
    public static boolean hasRole(String rolename){
        boolean hasRole = false;
        try {
            SysUser sysUser = getPrincipal();
            if(sysUser != null){
                List<Role> roleList = sysUser.getRoles();
                if(roleList != null && roleList.size() > 0){
                    for (Role role : roleList) {
                        if(rolename.equals(role.getRoleName())){
                            hasRole = true;
                            break;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return hasRole;
    }

    /**
     * 将用户信息存入SecurityContext中
     *      可用于用户信息的更新
     * @param userDetails
     */
    public static void setAuthentication(UserDetails userDetails){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
