package cn.net.colin.common.component;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Package: cn.net.colin.common.component
 * @Author: sxf
 * @Date: 2020-3-6
 * @Description: 处理匿名用户访问无权限资源时的异常
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletRequest.getRequestDispatcher("/authException").forward(httpServletRequest,httpServletResponse);
    }
}
