package cn.net.colin.service.common;

import cn.net.colin.mapper.common.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.service.common
 * @Author: sxf
 * @Date: 2020-3-29
 * @Description:
 */
@Service
public class CommonServiceImpl implements ICommonservice{

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public boolean fromVerifyByCode(Map<String, Object> map) {
        List<Map<String,Object>> listCode = commonMapper.fromVerifyByCode(map);
        if(listCode.size()>0){
            return false;
        }
        return true;
    }
}
