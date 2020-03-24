package cn.net.colin.controller.test;

import cn.net.colin.common.helper.RedisUtil;
import cn.net.colin.mapper.sysManage.SysUserMapper;
import cn.net.colin.model.sysManage.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: cn.net.colin.controller.test
 * @Author: sxf
 * @Date: 2020-3-24
 * @Description:
 */
@RestController
@RequestMapping("/redis")
public class TestRedisComtroller {
//    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping(value = "setKey",method = RequestMethod.GET)
    public String setKey(String key,String value){
        redisUtil.set(key,value);
        Map<String,Object>  map = new HashMap<>();
        map.put("name","邵晓方");
        redisUtil.hset("testkey","testitem",map);
        Map<String,Object>  resultmap = (Map<String,Object>)redisUtil.hget("testkey","testitem");
        return "setKey:key:"+key+" value"+value;
    }

    @RequestMapping(value = "getValue",method = RequestMethod.GET)
    public String getValue(String key){
        String value=redisUtil.get(key)+"";
        return "getValue:key:"+key+" value"+value;
    }

    @RequestMapping(value = "testCache")
    @Cacheable(cacheNames = "my-redis-cache2",keyGenerator ="keyGenerator")
    public SysUser testCache(String id){
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.parseLong(id));
//        redisUtil.sSet("sysuser",sysUser);
        return sysUser;
    }



}
