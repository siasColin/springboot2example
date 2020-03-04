package cn.net.colin.service.sysManage.impl;

import cn.net.colin.mapper.sysManage.SysRoleMapper;
import cn.net.colin.model.sysManage.SysRole;
import cn.net.colin.service.sysManage.ISysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    public SysRole selectByPrimaryKey(Long id) {
        return this.sysRoleMapper.selectByPrimaryKey(id);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.sysRoleMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysRole record) {
        return this.sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysRole record) {
        return this.sysRoleMapper.updateByPrimaryKey(record);
    }

    public int insert(SysRole record) {
        return this.sysRoleMapper.insert(record);
    }

    public int insertSelective(SysRole record) {
        return this.sysRoleMapper.insertSelective(record);
    }

    public int insertBatch(List<SysRole> sysRoleList) {
        return this.sysRoleMapper.insertBatch(sysRoleList);
    }

    public int insertBatchSelective(List<SysRole> sysRoleList) {
        return this.sysRoleMapper.insertBatchSelective(sysRoleList);
    }

    public int updateBatchByPrimaryKey(List<SysRole> sysRoleList) {
        return this.sysRoleMapper.updateBatchByPrimaryKey(sysRoleList);
    }

    public int updateBatchByPrimaryKeySelective(List<SysRole> sysRoleList) {
        return this.sysRoleMapper.updateBatchByPrimaryKeySelective(sysRoleList);
    }
}