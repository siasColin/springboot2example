package cn.net.colin.controller.common;

import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.exception.entity.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认的/error处理会在springboot中的ErrorMvcAutoConfiguration>>BasicErrorController中进行
 * 这里我们用自定义的
 * Created by colin on 2020-2-27.
 */
@Controller
public class MyErrorController implements ErrorController {
    Logger Logger = LoggerFactory.getLogger(MyErrorController.class);
    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * 处理页面请求异常
     * @param request
     * @return
     */
    @RequestMapping(value="/error",produces="text/html")
    public String handleErrorHtml(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        ResultInfo result = null;
        if(statusCode == 400) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_400);
        }else if(statusCode == 403) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_403);
        }else if(statusCode == 404) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_404);
        }else if(statusCode == 405) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_405);
        }else if(statusCode == 500) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_500);
        }else{
            result = request.getAttribute("ext") == null ? ResultInfo.of(ResultCode.UNKNOWN_ERROR) :
                    (ResultInfo) request.getAttribute("ext");
        }
        request.setAttribute("returnCode",result.getReturnCode());
        request.setAttribute("returnMessage",result.getReturnMessage());
        if(result.getTotal() != null){
            request.setAttribute("total",result.getTotal());
        }
        if(result.getData() != null){
            request.setAttribute("data",result.getData());
        }
        return "error/error";
    }

    /**
     * 处理非页面异常
     * @param request
     * @return
     */
    @RequestMapping("/error")
    @ResponseBody
    public ResultInfo handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        ResultInfo result = null;
        if(statusCode == 400) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_400);
        }else if(statusCode == 403) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_403);
        }else if(statusCode == 404) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_404);
        }else if(statusCode == 405) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_405);
        }else if(statusCode == 500) {
            result = ResultInfo.of(ResultCode.STATUS_CODE_500);
        }else{
            result = request.getAttribute("ext") == null ? ResultInfo.of(ResultCode.UNKNOWN_ERROR) :
                    (ResultInfo) request.getAttribute("ext");
        }
        return result;
    }

    @RequestMapping(value="/authException",produces="text/html")
    public String authExceptionHtml(HttpServletRequest request) {
        ResultInfo resultInfo = ResultInfo.of(ResultCode.STATUS_CODE_403);
        request.setAttribute("returnCode",resultInfo.getReturnCode());
        request.setAttribute("returnMessage",resultInfo.getReturnMessage());
        return "error/authException";
    }

    @RequestMapping("/authException")
    @ResponseBody
    public ResultInfo authException(HttpServletRequest request) {
        return ResultInfo.of(ResultCode.STATUS_CODE_403);
    }

}
