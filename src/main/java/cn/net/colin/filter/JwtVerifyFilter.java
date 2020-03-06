package cn.net.colin.filter;

import cn.net.colin.common.config.RsaKeyProperties;
import cn.net.colin.common.exception.BusinessRuntimeException;
import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.JwtUtils;
import cn.net.colin.model.common.Payload;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysUserService;
import com.alibaba.fastjson.JSON;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package: cn.net.colin.filter
 * @Author: sxf
 * @Date: 2020-3-6
 * @Description:
 */
public class JwtVerifyFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtVerifyFilter.class);
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private RsaKeyProperties prop;

    private List<String> doUrls = new ArrayList<String>(Arrays.asList("/hello"));

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取访问的url
        boolean doFlag = false;
        for(String oneallowUrl:doUrls){//判断是否拦截
            Pattern mpattern = Pattern.compile(oneallowUrl);
            Matcher mmatcher = mpattern.matcher(uri);
            if(mmatcher.find()){
                doFlag = true;
                break;
            }
        }
        if(doFlag){
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                //如果携带错误的token，则给用户提示请登录！
//                chain.doFilter(request, response);
                response(response,ResultCode.TOKEN_NOTFOUND);
            } else {
                //如果携带了正确格式的token要先得到token
                String token = header.replace("Bearer ", "");
                //验证tken是否正确
                try{
                    Payload<SysUser> payload = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), SysUser.class);
                    SysUser user = payload.getUserInfo();
                    if(user!=null){//验证通过
                        UserDetails userDetails = sysUserService.loadUserByUsername(user.getLoginName());
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }else{
                        response(response,ResultCode.TOKEN_ERROR);
                    }
                }catch (Exception e){
                    response(response,ResultCode.TOKEN_ERROR);
                }
            }
        }else{
            chain.doFilter(request, response);
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
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out = response.getWriter();
            ResultInfo resultInfo = ResultInfo.of(resultCode);
            out.write(JSON.toJSONString(resultInfo));
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
