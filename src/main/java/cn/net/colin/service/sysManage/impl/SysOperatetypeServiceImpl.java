package cn.net.colin.service.sysManage.impl;

import cn.net.colin.mapper.sysManage.SysOperatetypeMapper;
import cn.net.colin.model.sysManage.SysOperatetype;
import cn.net.colin.service.sysManage.ISysOperatetypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysOperatetypeServiceImpl implements ISysOperatetypeService {
    @Autowired
    private SysOperatetypeMapper sysOperatetypeMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysOperatetypeServiceImpl.class);

    public SysOperatetype selectByPrimaryKey(Long id) {
        return this.sysOperatetypeMapper.selectByPrimaryKey(id);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.sysOperatetypeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysOperatetype record) {
        return this.sysOperatetypeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysOperatetype record) {
        return this.sysOperatetypeMapper.updateByPrimaryKey(record);
    }

    public int insert(SysOperatetype record) {
        return this.sysOperatetypeMapper.insert(record);
    }

    public int insertSelective(SysOperatetype record) {
        return this.sysOperatetypeMapper.insertSelective(record);
    }

    public int insertBatch(List<SysOperatetype> operatetypeList) {
        return this.sysOperatetypeMapper.insertBatch(operatetypeList);
    }

    public int insertBatchSelective(List<SysOperatetype> operatetypeList) {
        return this.sysOperatetypeMapper.insertBatchSelective(operatetypeList);
    }

    public int updateBatchByPrimaryKey(List<SysOperatetype> operatetypeList) {
        return this.sysOperatetypeMapper.updateBatchByPrimaryKey(operatetypeList);
    }

    public int updateBatchByPrimaryKeySelective(List<SysOperatetype> operatetypeList) {
        return this.sysOperatetypeMapper.updateBatchByPrimaryKeySelective(operatetypeList);
    }

    @Override
    public List<SysOperatetype> selectByParams(Map<String, Object> paramMap) {
        return this.sysOperatetypeMapper.selectByParams(paramMap);
    }

    @Override
    public List<Long> selectOperatetypeidListByRoleid(Map<String, Object> paramMap) {
        return this.sysOperatetypeMapper.selectOperatetypeidListByRoleid(paramMap);
    }
}