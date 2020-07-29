package cn.net.colin.service.quartzManage.impl;

import cn.net.colin.mapper.quartzManage.SysQuzrtzMapper;

import cn.net.colin.model.quartzManage.SysQuartz;
import cn.net.colin.service.quartzManage.ISysQuzrtzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysQuzrtzServiceImpl implements ISysQuzrtzService {
    @Autowired
    private SysQuzrtzMapper sysQuzrtzMapper;
    @Value("${quartzWorkID}")
    private String quartzWorkID;

    private static final Logger logger = LoggerFactory.getLogger(SysQuzrtzServiceImpl.class);


    public SysQuartz selectByPrimaryKey(Long id) {
        return this.sysQuzrtzMapper.selectByPrimaryKey(id);
    }


    public int deleteByPrimaryKey(Long id) {
        return this.sysQuzrtzMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysQuartz record) {
        return this.sysQuzrtzMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysQuartz record) {
        return this.sysQuzrtzMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int insert(SysQuartz record) {
        return this.sysQuzrtzMapper.insert(record);
    }

    public int insertSelective(SysQuartz record) {
        record.setExp3(quartzWorkID);
        return this.sysQuzrtzMapper.insertSelective(record);
    }

    @Override
    public List<SysQuartz> selectByParams(Map<String, Object> paramMap) {
        List<SysQuartz> resultList = this.sysQuzrtzMapper.selectByParams(paramMap);
        resultList.parallelStream().forEach(sq -> sq.setExp4(quartzWorkID));
        return resultList;
    }

    @Override
    public List<SysQuartz> selectByParamsWithBlobs(Map<String, Object> quartzParams) {
        return this.sysQuzrtzMapper.selectByParamsWithBlobs(quartzParams);
    }

    @Override
    public int deleteBatchByPrimaryKey(Long[] ids) {
        return this.sysQuzrtzMapper.deleteBatchByPrimaryKey(ids);
    }

    @Override
    public List<SysQuartz> selectByPrimaryKeys(Long[] ids) {
        return this.sysQuzrtzMapper.selectByPrimaryKeys(ids);
    }
}