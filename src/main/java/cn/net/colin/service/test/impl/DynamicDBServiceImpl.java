package cn.net.colin.service.test.impl;

import cn.net.colin.common.aop.DataSourceAnnotation;
import cn.net.colin.common.util.DynamicDataSourceSwitcher;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.mapper.sysManage.SysAreaMapper;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.service.test.IDynamicDBService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxf on 2020-3-1.
 */
@Service
public class DynamicDBServiceImpl implements IDynamicDBService {
    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Override
    public Object findUserListOnDB1() {
        return sysAreaMapper.selectAll();
    }
    @Override
    @DataSourceAnnotation(DynamicDataSourceSwitcher.db2)
    public Object findUserListOnDB2Auto() {
        return sysAreaMapper.selectAll();
    }

    @Override
    public Object findUserListOnDB2() {
        return sysAreaMapper.selectAll();
    }

//    @Transactional(rollbackFor = Exception.class)
    public void testTransactional_db1() {
        SysArea sysArea = new SysArea();
        sysArea.setId(SnowflakeIdWorker.generateId());
        sysArea.setAreaCode(sysArea.getId()+"");
        sysArea.setAreaName(sysArea.getId()+"name");
        sysArea.setParentCode("0");
        sysArea.setAreaLevel(0);
        sysAreaMapper.insert(sysArea);
        int i = 1/0;
    }

//    @Transactional(rollbackFor = Exception.class)
    @DataSourceAnnotation(DynamicDataSourceSwitcher.db2)
    public void testTransactional_db2() {
        SysArea sysArea = new SysArea();
        sysArea.setId(SnowflakeIdWorker.generateId());
        sysArea.setAreaCode(sysArea.getId()+"");
        sysArea.setAreaName(sysArea.getId()+"name");
        sysArea.setParentCode("0");
        sysArea.setAreaLevel(0);
        sysAreaMapper.insert(sysArea);
        int i = 1/0;
    }
}
