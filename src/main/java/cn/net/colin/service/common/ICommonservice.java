package cn.net.colin.service.common;

import java.util.Map;

/**
 * @Package: cn.net.colin.service.common
 * @Author: sxf
 * @Date: 2020-3-29
 * @Description: 公用service
 */
public interface ICommonservice {
    boolean fromVerifyByCode(Map<String,Object> map);
}
