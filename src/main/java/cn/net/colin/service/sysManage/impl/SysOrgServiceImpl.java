package cn.net.colin.service.sysManage.impl;

import cn.net.colin.common.util.RecursiveChildUtil;
import cn.net.colin.common.util.SQLUtil;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.mapper.sysManage.SysAreaMapper;
import cn.net.colin.mapper.sysManage.SysOrgMapper;
import cn.net.colin.mapper.sysManage.SysRoleMapper;
import cn.net.colin.mapper.sysManage.SysUserMapper;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.model.sysManage.SysOrg;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysOrgService;
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
public class SysOrgServiceImpl implements ISysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

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

    @Override
    public SysOrg selectByOrgCode(String orgcode) {
        return sysOrgMapper.selectByOrgCode(orgcode);
    }

    @Override
    public List<SysOrg> selectByParentCode(String parentCode) {
        return sysOrgMapper.selectByParentCode(parentCode);
    }

    @Override
    public Map<String, Object> orgRelation(String orgCode) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("isQuote",false);
        //1.查询该机构编码下是否存在子机构，如果存在则证明被引用
        int childNum = this.sysOrgMapper.selectChildNumByOrgCode(orgCode);
        if(childNum > 0){
            resultMap.put("isQuote",true);
            resultMap.put("msg","存在子机构，不允许更新/删除！");
        }
        //2.查询该机构编码是否被用户表引用
        int userNum = this.sysUserMapper.selectUserNumByOrgCode(orgCode);
        if(userNum > 0){
            resultMap.put("isQuote",true);
            resultMap.put("msg","用户表中存在外键引用，不允许更新/删除！");
        }
        //3.查询该地区编码是否被角色表引用
        int roleNum = this.sysRoleMapper.selectRoleNumByOrgCode(orgCode);
        if(roleNum > 0){
            resultMap.put("isQuote",true);
            resultMap.put("msg","角色表中存在外键引用，不允许更新/删除！");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatOrgWithFK(SysOrg sysOrg, String orgCode) {
        //1.更新机构表中parent_code等于指定orgCode的parent_code值
        int childNum = this.sysOrgMapper.updateParentCodeByOrgCode(orgCode,sysOrg.getOrgCode());
        //2.更新角色表中的 org_code
        int roleNum = this.sysRoleMapper.updateOrgCode(orgCode,sysOrg.getOrgCode());

        //最后更新机构信息
        return this.sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
    }
}