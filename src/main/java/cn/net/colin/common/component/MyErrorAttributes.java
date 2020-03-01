package cn.net.colin.common.component;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

//给容器中加入我们自己定义的ErrorAttributes
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        if(webRequest.getAttribute("ext", 0) != null){
            //我们的异常处理器携带的数据
            Map<String,Object> ext = (Map<String, Object>) webRequest.getAttribute("ext", 0);
            ext.put("company","ssd");
            return ext;
        }else{
            return map;
        }
    }
}
