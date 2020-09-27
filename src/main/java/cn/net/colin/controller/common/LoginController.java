package cn.net.colin.controller.common;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.controller.sysManage.MenuManageController;
import cn.net.colin.model.sysManage.SysModulelist;
import cn.net.colin.service.sysManage.ISysModullistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by colin on 2020-2-27.
 */
@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ISysModullistService sysModullistService;

    @RequestMapping("/loginerror")
    public String loginerror(HttpServletRequest request, Map<String,Object> modelMap){
        AuthenticationException authenticationException =
                (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (authenticationException instanceof UsernameNotFoundException || authenticationException instanceof BadCredentialsException) {
            modelMap.put("msg","用户名或密码错误");
        } else if (authenticationException instanceof DisabledException) {
            modelMap.put("msg","用户已被禁用");
        } else if (authenticationException instanceof LockedException) {
            modelMap.put("msg","账户被锁定");
        } else if (authenticationException instanceof AccountExpiredException) {
            modelMap.put("msg","账户过期");
        } else if (authenticationException instanceof CredentialsExpiredException) {
            modelMap.put("msg","证书过期");
        } else {
            modelMap.put("msg","登录失败");
        }
        return  "login";
    }

    @RequestMapping("/login")
    public String login(Map<String,Object> modelMap){
        return  "login";
    }

    /**
     * 登录成功后跳转首页
     * @param modelMap
     * @return
     */
    @RequestMapping("/main")
    public String main(Map<String,Object> modelMap){
        List<SysModulelist> firstMenu = this.sysModullistService.selectFirstMenu();
        modelMap.put("firstMenu",firstMenu);
        return  "index";
    }

    /**
     * 根据一级菜单id，查询子菜单
     * @param moduleId
     * @return
     */
    @GetMapping("/childMenu/{moduleId}")
    @ResponseBody
    public ResultInfo childMenu(@PathVariable("moduleId") String moduleId){
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        Map<String,Object> resultMap = this.sysModullistService.selectChildMenu(moduleId);
        resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,resultMap);
        return resultInfo;
    }
}
