package cn.net.colin.controller.sysManage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Package: cn.net.colin.controller.sysManage
 * @Author: sxf
 * @Date: 2020-3-6
 * @Description: 地区管理
 */
@Controller
@RequestMapping("/areaManage")
public class AreaManageController {


    @RequestMapping("/arealist")
    public String arealist(){
        return "sysManage/areaManage/areaManageList";
    }
}
