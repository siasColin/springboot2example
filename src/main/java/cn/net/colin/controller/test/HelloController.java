package cn.net.colin.controller.test;

import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.service.sysManage.ISysAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by colin on 2020-2-27.
 * ClassPathResource classPathResource = new ClassPathResource("static/model/pztjmodel.xls");
 *             InputStream inputStream = classPathResource.getInputStream();
 */

@Controller
@RequestMapping("/hello")
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Autowired
    ISysAreaService sysAreaService;

    @RequestMapping("/hello")
    @ResponseBody
    public Object hello(){
        logger.info(dataSource.getClass().getName());
        return "HelloWorld!";
    }

    @RequestMapping("/helloResult")
    @ResponseBody
    public ResultInfo helloResult(){
        return ResultInfo.of(ResultCode.SUCCESS);
    }

    @RequestMapping("/date")
    @ResponseBody
    public Object hello(Date date){
        return date;
    }

    @RequestMapping("/insertBatch")
    @ResponseBody
    public Object insertBatch(){
        List<SysArea> areaList = new ArrayList<SysArea>();
        for (int i= 0;i<2;i++) {
            SysArea sysArea = new SysArea();
            sysArea.setId(SnowflakeIdWorker.generateId());
            sysArea.setAreaCode(sysArea.getId()+"");
            sysArea.setAreaName(sysArea.getId()+"name");
            sysArea.setParentCode("0");
            sysArea.setAreaLevel(0);
            areaList.add(sysArea);
        }
        int num = sysAreaService.insertBatch(areaList);
        return num;
    }

    @RequestMapping("/helloSecurity")
    @ResponseBody
    public Object helloSecurity(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        return principal;
    }
}
