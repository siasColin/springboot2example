package cn.net.colin.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by colin on 2020-2-27.
 */
@Controller
public class LoginController {

    @RequestMapping("/loginerror")
    public String loginerror(Map<String,Object> modelMap){
        modelMap.put("msg","认证失败，请检查用户名或密码是否正确！");
        return  "login";
    }

    @RequestMapping("/login")
    public String login(Map<String,Object> modelMap){
        return  "login";
    }

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> modelMap,
                        HttpSession session){
        if("123456".equals(password)){
            //登陆成功，防止表单重复提交，可以重定向到主页
            session.setAttribute("loginUser",username);
            return "redirect:/main";
        }else{
            //登陆失败
            modelMap.put("msg","用户名密码错误");
            return  "login";
        }
    }
}
