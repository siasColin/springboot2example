package cn.net.colin.service.sysManage.impl;

import cn.net.colin.mapper.sysManage.SysAreaMapper;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.service.sysManage.ISysAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAreaServiceImpl implements ISysAreaService {
    @Autowired
    private SysAreaMapper sysAreaMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysAreaServiceImpl.class);

    public SysArea selectByPrimaryKey(Long id) {
        return this.sysAreaMapper.selectByPrimaryKey(id);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.sysAreaMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysArea record) {
        return this.sysAreaMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysArea record) {
        return this.sysAreaMapper.updateByPrimaryKey(record);
    }

    public int insert(SysArea record) {
        return this.sysAreaMapper.insert(record);
    }

    public int insertSelective(SysArea record) {
        return this.sysAreaMapper.insertSelective(record);
    }

    public int insertBatch(List<SysArea> areaList) {
        return this.sysAreaMapper.insertBatch(areaList);
    }

    public int insertBatchSelective(List<SysArea> areaList) {
        return this.sysAreaMapper.insertBatchSelective(areaList);
    }

    public int updateBatchByPrimaryKey(List<SysArea> areaList) {
        return this.sysAreaMapper.updateBatchByPrimaryKey(areaList);
    }

    public int updateBatchByPrimaryKeySelective(List<SysArea> areaList) {
        return this.sysAreaMapper.updateBatchByPrimaryKeySelective(areaList);
    }

    @Override
    public List<SysArea> selectAll() {
        return  this.sysAreaMapper.selectAll();
    }
}