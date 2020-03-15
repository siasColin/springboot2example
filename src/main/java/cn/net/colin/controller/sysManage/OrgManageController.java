package cn.net.colin.controller.sysManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.service.sysManage.ISysOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.controller.sysManage
 * @Author: sxf
 * @Date: 2020-3-15
 * @Description: 机构管理
 */
@Controller
@RequestMapping("/orgManage")
public class OrgManageController {
    Logger logger = LoggerFactory.getLogger(OrgManageController.class);

    @Autowired
    private ISysOrgService sysOrgService;

    @GetMapping("/orglist")
    public String arealist(){
        return "sysManage/orgManage/orgManageList";
    }


    /**
     * 返回满足zTree结构的机构信息
     * @param orgName 机构名字（模糊查询）
     * @param areaCode 地区编码
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/orgListTree")
    @ResponseBody
    public ResultInfo orgListTree(String orgName, String areaCode) throws IOException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(orgName != null && !orgName.equals("")){
            paramMap.put("orgName",orgName);
        }
        if(areaCode != null && !areaCode.equals("")){
            paramMap.put("areaCode",areaCode);
        }
        List<TreeNode> treeNodeList = sysOrgService.selectOrgTreeNodes(paramMap);
        return ResultInfo.ofData(ResultCode.SUCCESS,treeNodeList);
    }


    /**
     * 跳转到机构树页面
     * @param type
     * @return
     */
    @GetMapping("/orgtree/{type}")
    public String orglist(@PathVariable("type") String type){
        /**
         * type:
         *      none 普通ztree页面
         *      radio 单选框ztree页面
         *      check 复选框ztree页面
         */
        String targetPage = "sysManage/orgManage/orgtree";
        if(type.equals("radio")){//跳转到普通ztree页面
            targetPage = "sysManage/orgManage/orgtreeRadio";
        }else if(type.equals("check")){
            targetPage = "sysManage/orgManage/orgtreeCheck";
        }
        return targetPage;
    }
}
