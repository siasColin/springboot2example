package cn.net.colin.controller.quartzManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.controller.sysManage.AreaManageController;
import cn.net.colin.model.quartzManage.SysQuzrtz;
import cn.net.colin.service.quartzManage.ISysQuzrtzService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.controller.quartzManage
 * @Author: sxf
 * @Date: 2020-4-12
 * @Description: 可视化定时任务管理页面控制器
 */
@Controller
@RequestMapping("/quartzManage")
public class QuartzManageController {
    Logger logger = LoggerFactory.getLogger(QuartzManageController.class);
    @Autowired
    private ISysQuzrtzService sysQuzrtzService;

    @GetMapping("/quzrtzManagelist")
    public String quzrtzManagelist(){
        return "quartzManage/quartzList";
    }

    /**
     * 返回任务信息列表
     * @param paramMap
     *  quartzname 任务名称（模糊查询）
     *  page 页码
     *  limit 每页数据量
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/quartzList")
    @ResponseBody
    public ResultInfo quartzList(@RequestParam Map<String,Object> paramMap) throws IOException {
        int pageNum = paramMap.get("page") == null ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int pageSize = paramMap.get("limit") == null ? 10 : Integer.parseInt(paramMap.get("limit").toString());
        PageHelper.startPage(pageNum,pageSize);
        List<SysQuzrtz> roleList = sysQuzrtzService.selectByParams(paramMap);
        PageInfo<SysQuzrtz> result = new PageInfo(roleList);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,roleList,result.getTotal());
    }

}
