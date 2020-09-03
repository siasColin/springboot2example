package cn.net.colin.filter;

import cn.net.colin.common.config.RsaKeyProperties;
import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.JsonUtils;
import cn.net.colin.common.util.JwtUtils;
import cn.net.colin.model.common.Payload;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysUserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Package: cn.net.colin.filter
 * @Author: sxf
 * @Date: 2020-3-6
 * @Description: 判断请求头中是否存在Token，如果存在则设置通过springsecurity认证，并将用户信息存入上下文
 */
public class LoginFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private RsaKeyProperties prop;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) { //如果没有携带有效token，则直接放行执行过滤器链中下一个过滤器
            chain.doFilter(request, response);
        } else {//如果携带了token，则进行token校验
            //如果携带了正确格式的token要先得到token
            String token = header.replace("Bearer ", "");
            //验证tken是否正确
            try{
                Payload<SysUser> payload = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), SysUser.class);
                SysUser user = payload.getUserInfo();
                //refreshToken_ 开头的是用于刷新的token
                if(user!=null && !user.getLoginName().startsWith("refreshToken_")){//验证通过
                    UserDetails userDetails = sysUserService.loadUserByUsername(user.getLoginName());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(request, response);
                }else{
                    response(response,ResultCode.TOKEN_ERROR);
                }
            }catch (ExpiredJwtException e){//token过期
                //判断用于刷新的token是否过期
                String refresh_token = request.getHeader("Refresh_token");
                try{
                    Payload<SysUser> payload = JwtUtils.getInfoFromToken(refresh_token, prop.getPublicKey(), SysUser.class);
                    SysUser user = payload.getUserInfo();
                    if(user!=null && user.getLoginName().startsWith("refreshToken_")){//验证通过
                        String loginName = user.getLoginName().replace("refreshToken_","");
                        UserDetails userDetails = sysUserService.loadUserByUsername(loginName);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        //生成新的token
                        String newToken = JwtUtils.generateTokenExpireInMinutes(user, prop.getPrivateKey(), 10);
                        //再生成一个refresh_token，用于刷新token,有效期2小时
                        String newRefresh_token = JwtUtils.generateTokenExpireInMinutes(user, prop.getPrivateKey(), 2*60);
                        //将两个Token写入response头信息中
                        response.addHeader("Authorization", "Bearer "+newToken);
                        response.addHeader("Refresh_token", newRefresh_token);
                        chain.doFilter(request, response);
                    }else{
                        response(response,ResultCode.TOKEN_ERROR);
                    }
                    //尽可能让老的refresh_token失效，可以配合redis完成
                }catch (ExpiredJwtException ex){//refresh_token过期
                    response(response,ResultCode.TOKEN_ERROR);
                }catch (Exception exc){
                    response(response,ResultCode.TOKEN_ERROR);
                }
            }catch (Exception e){
                response(response,ResultCode.TOKEN_ERROR);
            }
        }
    }

    /**
     * 给客户端返回提示数据
     * @param response
     * @param resultCode
     */
    private void response(HttpServletResponse response,ResultCode resultCode){
        PrintWriter out = null;
        try{
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            out = response.getWriter();
            ResultInfo resultInfo = ResultInfo.of(resultCode);
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
}
