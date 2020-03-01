package cn.net.colin.controller.test;

import cn.net.colin.mapper.test.UserMapper;
import cn.net.colin.model.test.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sxf on 2020-3-1.
 */
@Controller
public class PageHelperController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/pageHelper")
    @ResponseBody
    public Object pageHelper(){
        PageHelper.startPage(1,5);
        List<User> list = userMapper.findUserList();
        PageInfo result = new PageInfo(list);
        return result;
    }
}
