package cn.net.colin.common.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Package: cn.net.colin.common.helper
 * @Author: sxf
 * @Date: 2020-3-6
 * @Description: 使用jackjson 对象转json时，将Long型数据转为字符串类型
 * 解决数据传到前端时，js中number类型存储的长度小于long，丢失精度的问题
 */
public class LongJsonSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String text = (value == null ? null : String.valueOf(value));
        if (text != null) {
            jsonGenerator.writeString(text);
        }
    }
}
