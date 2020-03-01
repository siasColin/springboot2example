package cn.net.colin.controller.test;

import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.util.DynamicDataSourceSwitcher;
import cn.net.colin.mapper.test.UserMapper;
import cn.net.colin.model.test.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by colin on 2020-2-27.
 */

@Controller
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

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
}
