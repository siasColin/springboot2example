package cn.net.colin.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Package: cn.net.colin.filter
 * @Author: sxf
 * @Date: 2020-3-8
 * @Description: 自定义Filter实现请求地址与角色判断
 */
public class RequestURIFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtVerifyFilter.class);
    /**
     * 忽略拦截的URI集合
     */
    private List<String> ignoreURIs = new ArrayList<String>(Arrays.asList("/hello/\\S*","/",
            "/login","/logout","/auth/login","/common/uploadSingle","/common/uploadMany"));

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String uri = httpServletRequest.getRequestURI();//获取访问的url
        boolean ignoreFlag = false;
        for(String oneallowURI:ignoreURIs){//判断是否拦截
            if(Pattern.matches(oneallowURI,uri)){
                ignoreFlag = true;
                break;
            }
        }
        if(ignoreFlag){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else{
            /**
             * 验证是否有权限请求该资源
             */
            if(SecurityContextHolder.getContext().getAuthentication() != null){
                /**
                 * 可以在登录的时候将用户所有角色拥有的菜单地址放入用户对象中，
                 * 这里便可以取出来对比是否是该用户可以访问的地址
                 * 是则放行，不是提醒用户没有权限
                 */
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(true){//如果验证通过则放行
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                }else{
                    httpServletResponse.sendRedirect("/authException");
                }

            }else{
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
    }
}
