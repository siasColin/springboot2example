package cn.net.colin.service.sysManage.impl;

import cn.net.colin.mapper.sysManage.SysUserMapper;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    public SysUser selectByPrimaryKey(Long id) {
        return this.sysUserMapper.selectByPrimaryKey(id);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.sysUserMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysUser record) {
        return this.sysUserMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysUser record) {
        return this.sysUserMapper.updateByPrimaryKey(record);
    }

    public int insert(SysUser record) {
        return this.sysUserMapper.insert(record);
    }

    public int insertSelective(SysUser record) {
        return this.sysUserMapper.insertSelective(record);
    }

    public int insertBatch(List<SysUser> sysUserList) {
        return this.sysUserMapper.insertBatch(sysUserList);
    }

    public int insertBatchSelective(List<SysUser> sysUserList) {
        return this.sysUserMapper.insertBatchSelective(sysUserList);
    }

    public int updateBatchByPrimaryKey(List<SysUser> sysUserList) {
        return this.sysUserMapper.updateBatchByPrimaryKey(sysUserList);
    }

    public int updateBatchByPrimaryKeySelective(List<SysUser> sysUserList) {
        return this.sysUserMapper.updateBatchByPrimaryKeySelective(sysUserList);
    }
}