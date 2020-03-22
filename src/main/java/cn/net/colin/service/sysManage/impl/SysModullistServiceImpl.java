package cn.net.colin.service.sysManage.impl;

import cn.net.colin.mapper.sysManage.SysModullistMapper;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysModulelist;
import cn.net.colin.service.sysManage.ISysModullistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysModullistServiceImpl implements ISysModullistService {
    @Autowired
    private SysModullistMapper sysModullistMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysModullistServiceImpl.class);


    public SysModulelist selectByPrimaryKey(Long id) {
        return this.sysModullistMapper.selectByPrimaryKey(id);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.sysModullistMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysModulelist record) {
        return this.sysModullistMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysModulelist record) {
        return this.sysModullistMapper.updateByPrimaryKey(record);
    }

    public int insert(SysModulelist record) {
        return this.sysModullistMapper.insert(record);
    }

    public int insertSelective(SysModulelist record) {
        return this.sysModullistMapper.insertSelective(record);
    }

    public int insertBatch(List<SysModulelist> modulelists) {
        return this.sysModullistMapper.insertBatch(modulelists);
    }

    public int insertBatchSelective(List<SysModulelist> modulelists) {
        return this.sysModullistMapper.insertBatchSelective(modulelists);
    }

    public int updateBatchByPrimaryKey(List<SysModulelist> modulelists) {
        return this.sysModullistMapper.updateBatchByPrimaryKey(modulelists);
    }

    public int updateBatchByPrimaryKeySelective(List<SysModulelist> modulelists) {
        return this.sysModullistMapper.updateBatchByPrimaryKeySelective(modulelists);
    }

    @Override
    public List<TreeNode> selectMenuTreeNodes(Map<String, Object> paramMap) {
        return this.sysModullistMapper.selectMenuTreeNodes(paramMap);
    }

    @Override
    public List<SysModulelist> selectByPid(long pid) {
        return this.sysModullistMapper.selectByPid(pid);
    }
}