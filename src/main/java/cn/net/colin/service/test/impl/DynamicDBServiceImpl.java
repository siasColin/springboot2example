package cn.net.colin.service.test.impl;

import cn.net.colin.common.aop.MyDataSource;
import cn.net.colin.common.util.DynamicDataSourceSwitcher;
import cn.net.colin.mapper.test.UserMapper;
import cn.net.colin.service.test.IDynamicDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxf on 2020-3-1.
 */
@Service
public class DynamicDBServiceImpl implements IDynamicDBService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Object findUserListOnDB1() {
        return userMapper.findUserList();
    }
    @Override
    @MyDataSource(DynamicDataSourceSwitcher.db2)
    public Object findUserListOnDB2Auto() {
        return userMapper.findUserList();
    }

    @Override
    public Object findUserListOnDB2() {
        return userMapper.findUserList();
    }

//    @Transactional(rollbackFor = Exception.class)
    public void testTransactional_db1() {
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("loginId","sxf");
        userMap.put("loginPwd","123");
        userMap.put("userName","colin");
        userMapper.addUser(userMap);
        int i = 1/0;
    }

//    @Transactional(rollbackFor = Exception.class)
    @MyDataSource(DynamicDataSourceSwitcher.db2)
    public void testTransactional_db2() {
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("loginId","sxf");
        userMap.put("loginPwd","123");
        userMap.put("userName","colin");
        userMapper.addUser(userMap);
        int i = 1/0;
    }
}
