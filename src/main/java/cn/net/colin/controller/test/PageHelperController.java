package cn.net.colin.controller.test;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.service.sysManage.ISysAreaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sxf on 2020-3-1.
 */
@Controller
public class PageHelperController {

    @Autowired
    ISysAreaService sysAreaService;

    @PostMapping("/pageHelper")
    @ResponseBody
    public Object pageHelper(){
        PageHelper.startPage(1,10);
        List<SysArea> list = sysAreaService.selectAll();
        PageInfo<SysArea> result = new PageInfo(list);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,list,result.getTotal());
    }
}
