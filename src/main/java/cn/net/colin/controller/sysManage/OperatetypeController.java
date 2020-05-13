package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.model.sysManage.SysOperatetype;
import cn.net.colin.service.sysManage.ISysOperatetypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.controller.sysManage
 * @Author: sxf
 * @Date: 2020-3-22
 * @Description:
 */
@Controller
@RequestMapping("/operatetypeManage")
public class OperatetypeController {
    Logger logger = LoggerFactory.getLogger(OperatetypeController.class);

    @Autowired
    private ISysOperatetypeService sysOperatetypeService;
    /**
     * 返回系统权限集合
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/operatetypeList")
    @ResponseBody
    public ResultInfo operatetypeList() throws IOException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<SysOperatetype> operatetypeList = sysOperatetypeService.selectByParams(paramMap);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,operatetypeList,operatetypeList.size());
    }

    /**
     * 返回系统权限id集合
     * @return ResultInfo 自定义结果返回实体类
     * @param roleid 角色id
     * @throws IOException
     */
    @GetMapping("/operatetypeList/{roleid}")
    @ResponseBody
    public ResultInfo operatetypeList(@PathVariable("roleid") String roleid) throws IOException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("roleid",Long.parseLong(roleid));
        List<Long> operatetypeidList = sysOperatetypeService.selectOperatetypeidListByRoleid(paramMap);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,operatetypeidList,operatetypeidList.size());
    }
}
