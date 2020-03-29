package cn.net.colin.mapper.common;

import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.mapper.common
 * @Author: sxf
 * @Date: 2020-3-29
 * @Description:
 */
public interface CommonMapper {
    List<Map<String,Object>> fromVerifyByCode(Map<String,Object> map);
}
