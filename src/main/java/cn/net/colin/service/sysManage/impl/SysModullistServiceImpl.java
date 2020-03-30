package cn.net.colin.service.sysManage.impl;

import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.mapper.sysManage.SysModullistMapper;
import cn.net.colin.mapper.sysManage.SysRoleMapper;
import cn.net.colin.model.common.Role;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysModulelist;
import cn.net.colin.model.sysManage.SysRole;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysModullistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysModullistServiceImpl implements ISysModullistService {
    @Autowired
    private SysModullistMapper sysModullistMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysModullistServiceImpl.class);


    public SysModulelist selectByPrimaryKey(Long id) {
        return this.sysModullistMapper.selectByPrimaryKey(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Long id) {
        int deleteNum = this.sysModullistMapper.deleteByPrimaryKey(id);
        //同时删除，菜单和角色关联关系
        sysRoleMapper.deleteRoleModulelistByModuleListid(id);
        return deleteNum;
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

    @Override
    public List<SysModulelist> selectFirstMenu() {
        List<SysModulelist> firstMuneList = new ArrayList<SysModulelist>();
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        if(sysUser != null && sysUser.getId() != null){
            //查询用户的系统角色
            List<SysRole> sysRoleList = this.sysRoleMapper.selectByUserId(sysUser.getId());
            Map<String,Object> roleParams = new HashMap<String,Object>();
            roleParams.put("sysRoleList",sysRoleList);
            roleParams.put("pid",1);
            firstMuneList = this.sysModullistMapper.selectMenu(roleParams);
        }
        return firstMuneList;
    }
}