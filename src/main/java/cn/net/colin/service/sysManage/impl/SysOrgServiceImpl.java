package cn.net.colin.service.sysManage.impl;

import cn.net.colin.mapper.sysManage.SysOrgMapper;
import cn.net.colin.model.sysManage.SysOrg;
import cn.net.colin.service.sysManage.ISysOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOrgServiceImpl implements ISysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

    public SysOrg selectByPrimaryKey(Long id) {
        return this.sysOrgMapper.selectByPrimaryKey(id);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.sysOrgMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysOrg record) {
        return this.sysOrgMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysOrg record) {
        return this.sysOrgMapper.updateByPrimaryKey(record);
    }

    public int insert(SysOrg record) {
        return this.sysOrgMapper.insert(record);
    }

    public int insertSelective(SysOrg record) {
        return this.sysOrgMapper.insertSelective(record);
    }

    public int insertBatch(List<SysOrg> sysOrgList) {
        return this.sysOrgMapper.insertBatch(sysOrgList);
    }

    public int insertBatchSelective(List<SysOrg> sysOrgList) {
        return this.sysOrgMapper.insertBatchSelective(sysOrgList);
    }

    public int updateBatchByPrimaryKey(List<SysOrg> sysOrgList) {
        return this.sysOrgMapper.updateBatchByPrimaryKey(sysOrgList);
    }

    public int updateBatchByPrimaryKeySelective(List<SysOrg> sysOrgList) {
        return this.sysOrgMapper.updateBatchByPrimaryKeySelective(sysOrgList);
    }
}