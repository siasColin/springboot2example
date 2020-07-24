package cn.net.colin.filter;

import cn.net.colin.common.config.RsaKeyProperties;
import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.JsonUtils;
import cn.net.colin.common.util.JwtUtils;
import cn.net.colin.model.common.Role;
import cn.net.colin.model.sysManage.SysUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private RsaKeyProperties prop;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
        super.setFilterProcessesUrl("/auth/login");

    }

    /**
     * 接收并解析用户凭证，出現错误时抛出自定义异常，给前台返回错误提示。
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType() != null &&(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE))) {
            UsernamePasswordAuthenticationToken authRequest = null;
            try{
                Map<String,String> usermap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                String username = usermap.get("username");
                String password = usermap.get("password");
                if (checkNull(username) || checkNull(password)){
                    response(response,ResultInfo.of(ResultCode.LOGIN_ERROR));
                }else{
                    authRequest = new UsernamePasswordAuthenticationToken(username, password);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return authenticationManager.authenticate(authRequest);
        }else{
            response(response,ResultInfo.of(ResultCode.CONTENT_TYPE_ERROR));
            return null;
        }
    }

    /**
     * 登录成功后，生成token,并且返回json数据给客户端
     */
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser user = new SysUser();
        //添加需要放入token中的信息，登录名必须，其他按需添加
        user.setLoginName(authResult.getName());
//        user.setRoles((List<Role>) authResult.getAuthorities());
        //生成token，有效期10分钟
        String token = JwtUtils.generateTokenExpireInMinutes(user, prop.getPrivateKey(), 10);
        //再生成一个refresh_token，用于刷新token,有效期2小时
        //重置登录账号，防止使用Refresh_token当做Authorization，Refresh_token 登录账号以 refreshToken_ 开头
        SysUser refreshTokenUser = new SysUser();
        refreshTokenUser.setLoginName("refreshToken_"+user.getLoginName());
        String refresh_token = JwtUtils.generateTokenExpireInMinutes(refreshTokenUser, prop.getPrivateKey(), 2*60);
        //将两个Token写入response头信息中
        response.addHeader("Authorization", "Bearer "+token);
        response.addHeader("Refresh_token", refresh_token);
        SysUser pSysUser = (SysUser) authResult.getPrincipal();
        user.setUserName(pSysUser.getUserName());
        user.setHeadImg(pSysUser.getHeadImg());
        user.setRoles(null);
        response(response,ResultInfo.ofData(ResultCode.SUCCESS,user));
    }

    /**
     * 登录失败时返回给客户端提示信息
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResultCode resultCode = ResultCode.USER_LOGINFAIL;
        if (failed instanceof UsernameNotFoundException || failed instanceof BadCredentialsException) {
            resultCode = ResultCode.LOGIN_ERROR;
        } else if (failed instanceof DisabledException) {
            resultCode = ResultCode.USER_DISABLED;
        } else if (failed instanceof LockedException) {
            resultCode = ResultCode.USER_LOCKED;
        } else if (failed instanceof AccountExpiredException) {
            resultCode = ResultCode.USER_ACCOUNTEXPIRED;
        } else if (failed instanceof CredentialsExpiredException) {
            resultCode = ResultCode.USER_CREDENTIALSEXPIRED;
        }
        response(response,ResultInfo.of(resultCode));
    }


    /**
     * 给客户端返回提示数据
     * @param response
     * @param resultInfo
     */
    private void response(HttpServletResponse response,ResultInfo resultInfo){
        PrintWriter out = null;
        try{
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            out = response.getWriter();
//            ResultInfo resultInfo = ResultInfo.of(resultCode);
            out.write(JsonUtils.toString(resultInfo));
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out != null){
                out.close();
                out = null;
            }
        }

    }

    private boolean checkNull(String value){
        return value == null || (value != null &&("".equals(value.trim()) || "null".equalsIgnoreCase(value.trim())));
    }

}
