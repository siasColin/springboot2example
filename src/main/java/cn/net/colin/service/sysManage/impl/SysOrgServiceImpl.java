package cn.net.colin.service.sysManage.impl;

import cn.net.colin.common.util.RecursiveChildUtil;
import cn.net.colin.common.util.SQLUtil;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.mapper.sysManage.SysAreaMapper;
import cn.net.colin.mapper.sysManage.SysOrgMapper;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.model.sysManage.SysOrg;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysOrgServiceImpl implements ISysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;

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

    @Override
    public List<TreeNode> selectOrgTreeNodes(Map<String, Object> paramMap) {
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        /**
         * 判断是否拥有管理员权限
         *      有则返回本地区以及所有子地区下机构；
         *      无则只返回当前登录用户所属机构及子机构
         */
        if(sysUser != null && SpringSecurityUtil.hasRole("ADMIN_AUTH")){
            SysArea sysArea = sysUser.getSysOrg().getSysArea();
            //是管理员，首选查询本地区以及所有子地区
            Map<String,Object> areaParams = new HashMap<String,Object>();
            areaParams.put("maxAreaLevel",sysArea.getAreaLevel());
            //机构只查询到县
            areaParams.put("minAreaLevel",4);
            List<TreeNode> areaNodeList = sysAreaMapper.selectAreaTreeNodes(areaParams);
            List<TreeNode> childAreaList = new ArrayList<TreeNode>();
            TreeNode pTreeNode = new TreeNode();
            pTreeNode.setId(sysArea.getAreaCode());
            pTreeNode.setName(sysArea.getAreaName());
            pTreeNode.setPId(sysArea.getParentCode());
            childAreaList.add(pTreeNode);
            RecursiveChildUtil.areaTreeChildRecursive(sysArea.getAreaCode(),areaNodeList,childAreaList);
            List<List<TreeNode>> areaList = SQLUtil.getSumArrayList(childAreaList);
            paramMap.put("areaList",areaList);
        }
        List<TreeNode> orgNodeList = this.sysOrgMapper.selectOrgTreeNodes(paramMap);
        if(orgNodeList != null && orgNodeList.size() > 0 &&
                sysUser != null && !SpringSecurityUtil.hasRole("ADMIN_AUTH")){
            //如果不是管理员，只返回当前机构以及子机构信息
            SysOrg sysOrg = sysUser.getSysOrg();
            List<TreeNode> childOrgList = new ArrayList<TreeNode>();
            TreeNode pTreeNode = new TreeNode();
            pTreeNode.setId(sysOrg.getOrgCode());
            pTreeNode.setName(sysOrg.getOrgName());
            pTreeNode.setPId(sysOrg.getParentCode());
            childOrgList.add(pTreeNode);
            RecursiveChildUtil.orgTreeChildRecursive(sysOrg.getOrgCode(),orgNodeList,childOrgList);
            return childOrgList;
        }
        return orgNodeList;
    }
}