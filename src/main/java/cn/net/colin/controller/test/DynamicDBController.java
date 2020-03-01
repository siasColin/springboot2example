package cn.net.colin.controller.test;

import cn.net.colin.common.util.DynamicDataSourceSwitcher;
import cn.net.colin.service.test.IDynamicDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sxf on 2020-3-1.
 */
@Controller
public class DynamicDBController {

    @Autowired
    private IDynamicDBService dynamicDBService;

    /**
     * 测试多数据源配置，获取默认数据源(db1)数据
     * @return
     */
    @GetMapping("/db1")
    @ResponseBody
    public Object db1(){
        return dynamicDBService.findUserListOnDB1();
    }

    /**
     * 测试多数据源配置，获取db2数据源数据(service层使用自动以注解)
     * @return
     */
    @GetMapping("/db2Auto")
    @ResponseBody
    public Object db2Auto(){
        return dynamicDBService.findUserListOnDB2Auto();
    }

    /**
     * 测试多数据源配置，获取db2数据源数据(手动切换)
     * @return
     */
    @GetMapping("/db2")
    @ResponseBody
    public Object db2(){
        DynamicDataSourceSwitcher.setDataSource(DynamicDataSourceSwitcher.db2);
        Object list = dynamicDBService.findUserListOnDB2();
        DynamicDataSourceSwitcher.cleanDataSource();
        return list;
    }
}
