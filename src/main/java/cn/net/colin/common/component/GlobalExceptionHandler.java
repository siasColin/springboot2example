package cn.net.colin.common.component;

import cn.net.colin.common.exception.BusinessRuntimeException;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.exception.entity.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request){
        e.printStackTrace();
        ResultInfo result = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if (e instanceof BusinessRuntimeException) {
            BusinessRuntimeException bex = (BusinessRuntimeException)e;
            result = ResultInfo.of(bex.getResultCode());
        }else{
            if(e instanceof AccessDeniedException){//没有权限
                result = ResultInfo.of(ResultCode.STATUS_CODE_406);
            }else{
                if(e instanceof InvocationTargetException && e.getMessage() == null){
                    result.setReturnMessage(((InvocationTargetException) e).getTargetException().getMessage());
                }else {
                    result.setReturnMessage(e.toString());
                }
            }
        }
        /*result.setTotal(5l);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name","colin");
        map.put("age","27");
        list.add(map);
        result.setData(list);*/
        request.setAttribute("ext",result);
        return "forward:/error";
    }
}
