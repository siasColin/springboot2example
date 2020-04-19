package cn.net.colin.controller.common;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.DateUtils;
import cn.net.colin.common.util.GetServerRealPathUnit;
import cn.net.colin.common.util.JsonUtils;
import cn.net.colin.controller.sysManage.AreaManageController;
import cn.net.colin.service.common.ICommonservice;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.controller.common
 * @Author: sxf
 * @Date: 2020-3-7
 * @Description: 公用处理器
 */
@Controller
@RequestMapping("/common")
public class CommonController {
    Logger logger = LoggerFactory.getLogger(AreaManageController.class);

    @Autowired
    private ICommonservice commonservice;

    /**
     * 单文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadSingle")
    @ResponseBody
    public ResultInfo uploadSingle(@RequestParam("file") MultipartFile file,String prefixPath)
            throws IOException {
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        try{
            String separator = File.separator;
            String basepath = GetServerRealPathUnit.getPath("static"+separator+"uploadfile"+separator+prefixPath);
            File upload = new File(basepath);
            if (!upload.exists()) upload.mkdirs();
            logger.info("[文件地址] - [{}]",upload.getAbsolutePath());
            logger.info("[文件类型] - [{}]", file.getContentType());
            logger.info("[文件名称] - [{}]", file.getOriginalFilename());
            logger.info("[文件大小] - [{}]", file.getSize());
            Map<String, String> result = new HashMap<>(16);
            result.put("contentType", file.getContentType());
            result.put("fileName", file.getOriginalFilename());
            result.put("fileSize", file.getSize() + "");
            result.put("fileUrl", "uploadfile/"+prefixPath+"/"+file.getOriginalFilename());
            File targetFile = new File(upload.getAbsolutePath() +separator+ file.getOriginalFilename());
            if(targetFile.exists()) {//如果文件存在，重命名文件
                String fileName =file.getOriginalFilename();
                fileName = fileName.substring(0,fileName.lastIndexOf("."))+
                        "_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+
                        fileName.substring(fileName.lastIndexOf("."));
                targetFile = new File(upload.getAbsolutePath() +separator+ fileName);
            }
            // TODO Spring Mvc 提供的写入方式
            file.transferTo(targetFile);
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultInfo;
    }

    /**
     * 多文件上传
     * @param files
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadMany")
    @ResponseBody
    public ResultInfo uploadMany(@RequestParam("files") MultipartFile[] files,String prefixPath)
            throws IOException {
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        try{
            if (files == null || files.length == 0) {
                return ResultInfo.of(ResultCode.FILE_NOTFOUND);
            }
            String separator = File.separator;
            String basepath = GetServerRealPathUnit.getPath("static"+separator+"uploadfile"+separator+prefixPath);
            File upload = new File(basepath);
            if (!upload.exists()) upload.mkdirs();
            List<Map<String, String>> results = new ArrayList<>();
            for (MultipartFile file : files) {
                Map<String, String> map = new HashMap<>(16);
                map.put("contentType", file.getContentType());
                map.put("fileName", file.getOriginalFilename());
                map.put("fileSize", file.getSize() + "");
                map.put("fileUrl", "uploadfile/"+prefixPath+"/"+file.getOriginalFilename());
                // TODO Spring Mvc 提供的写入方式
                File targetFile = new File(upload.getAbsolutePath() +separator+ file.getOriginalFilename());
                if(targetFile.exists()) {//如果文件存在，重命名文件
                    String fileName =file.getOriginalFilename();
                    fileName = fileName.substring(0,fileName.lastIndexOf("."))+
                            "_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+
                            fileName.substring(fileName.lastIndexOf("."));
                    targetFile = new File(upload.getAbsolutePath() +separator+ fileName);
                }
                file.transferTo(targetFile);
                results.add(map);
            }
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,results);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 文件下载,写入response流
     * @param request
     * @param response
     * @param filepath
     * @return
     */
    @ResponseBody
    @RequestMapping("/downFile")
    public Object downFile(HttpServletRequest request, HttpServletResponse response, String filepath)  {
        try {
            filepath = java.net.URLDecoder.decode(filepath,"UTF-8");
            //兼容windows,linux分隔符
            String fileSeperator = File.separator;
            //物理路径地址头
            String basePath = request.getServletContext().getRealPath("/");// 获取tomcat跟路径
            String path=basePath+fileSeperator+filepath;
            File file=new File(path);
            String fileName = file.getName();
            //解决乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            // 设置文件名称
            response.setHeader("content-disposition", "attachment;fileName=\""+fileName+"\"");
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            FileInputStream in = new FileInputStream(path);
            int len = 0;
            byte buffer[]=new byte[1024];
            OutputStream out = response.getOutputStream();
            while((len = in.read(buffer))>0){
                out.write(buffer, 0, len);
            }
            in.close();
            return null;//如果方法带返回值，一定要return null，否则会出现文件损坏
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 录入页面数据不重复校验
     * @param request id:区分新增和更新,val:校验值, code:校验字段, tableName:校验数据库表名
     * @return
     */
    @RequestMapping("fromVerifyCode")
    @ResponseBody
    public Object fromVerifyCode(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("id",request.getParameter("id"));
        map.put("val",request.getParameter("val"));
        map.put("code",request.getParameter("code"));
        map.put("tableName",request.getParameter("tableName"));
        boolean flag = commonservice.fromVerifyByCode(map);
        if(flag){
            return ResultInfo.of(ResultCode.SUCCESS);
        }
        return ResultInfo.of(ResultCode.FAILED);
    }

    /**
     * 校验传入的值是否是Cron表达式
     * @return
     */
    @RequestMapping("cronCheck")
    @ResponseBody
    public ResultInfo cronCheck(String cronVal){
        try{
            if (CronExpression.isValidExpression(cronVal)) {
                return ResultInfo.of(ResultCode.SUCCESS);
            }else{
                return ResultInfo.of(ResultCode.FAILED);
            }
        }catch (Exception e){
            return ResultInfo.of(ResultCode.FAILED);
        }
    }

    /**
     * 校验传入的值是否是Json格式
     * @return
     */
    @RequestMapping("jsonCheck")
    @ResponseBody
    public ResultInfo jsonCheck(String jsonVal){
        try{
            Map<String,Object> map = JsonUtils.toMap(jsonVal,String.class,Object.class);
            if(map != null){
                return ResultInfo.of(ResultCode.SUCCESS);
            }else{
                return ResultInfo.of(ResultCode.FAILED);
            }
        }catch (Exception e){
            return ResultInfo.of(ResultCode.FAILED);
        }
    }

    /**
     * 处理session超时
     * @param request
     * @return
     */
    @RequestMapping("/sessionInvalid")
    @ResponseBody
    public ResultInfo handleError(HttpServletRequest request) {
        ResultInfo result = ResultInfo.of(ResultCode.SESSIONINVALID);
        return result;
    }
    /**
     * 处理session超时
     * @param request
     * @return
     */
    @RequestMapping(value="/sessionInvalid",produces="text/html")
    public String handleError(HttpServletRequest request,Map<String,Object> modelMap) {
        modelMap.put("msg","登录状态已过期，请重新登录！");
        return  "login";
    }
}
