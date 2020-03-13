package cn.net.colin.common.component;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Package: cn.net.colin.common.component
 * @Author: sxf
 * @Date: 2020-3-6
 * @Description: 处理认证过的用户访问无权限资源时的异常
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        String uri = httpServletRequest.getRequestURI();
        if(uri != null && uri.equals("/logout")){
            httpServletResponse.sendRedirect("/");
        }else{
            httpServletResponse.sendRedirect("/authException");
        }
    }
}
