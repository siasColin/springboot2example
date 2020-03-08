package cn.net.colin.controller.test;

import cn.net.colin.common.util.GetServerRealPathUnit;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Package: cn.net.colin.controller.test
 * @Author: sxf
 * @Date: 2020-3-7
 * @Description:
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @GetMapping("/getpath")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_ADMIN_AUTH')")
    public String getPath(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return GetServerRealPathUnit.getPath("uploadfile");
    }

    @GetMapping("/getpath2")
    @ResponseBody
    @PreAuthorize("hasAuthority('INSERT_AUTH')")
    public String getpath2(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return GetServerRealPathUnit.getPath("uploadfile");
    }
}
