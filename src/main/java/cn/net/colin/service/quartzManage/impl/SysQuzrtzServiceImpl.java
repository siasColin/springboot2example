package cn.net.colin.service.quartzManage.impl;

import cn.net.colin.mapper.quartzManage.SysQuzrtzMapper;

import cn.net.colin.model.quartzManage.SysQuzrtz;
import cn.net.colin.service.quartzManage.ISysQuzrtzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysQuzrtzServiceImpl implements ISysQuzrtzService {
    @Autowired
    private SysQuzrtzMapper sysQuzrtzMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysQuzrtzServiceImpl.class);


    public SysQuzrtz selectByPrimaryKey(Long id) {
        return this.sysQuzrtzMapper.selectByPrimaryKey(id);
    }


    public int deleteByPrimaryKey(Long id) {
        return this.sysQuzrtzMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysQuzrtz record) {
        return this.sysQuzrtzMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysQuzrtz record) {
        return this.sysQuzrtzMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int insert(SysQuzrtz record) {
        return this.sysQuzrtzMapper.insert(record);
    }

    public int insertSelective(SysQuzrtz record) {
        return this.sysQuzrtzMapper.insertSelective(record);
    }

    @Override
    public List<SysQuzrtz> selectByParams(Map<String, Object> paramMap) {
        return this.sysQuzrtzMapper.selectByParams(paramMap);
    }

    @Override
    public List<SysQuzrtz> selectByParamsWithBlobs(Map<String, Object> quartzParams) {
        return this.sysQuzrtzMapper.selectByParamsWithBlobs(quartzParams);
    }
}