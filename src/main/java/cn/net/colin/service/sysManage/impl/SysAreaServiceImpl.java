package cn.net.colin.service.sysManage.impl;

import cn.net.colin.common.util.RecursiveChildUtil;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.mapper.sysManage.SysAreaMapper;
import cn.net.colin.mapper.sysManage.SysOrgMapper;
import cn.net.colin.mapper.sysManage.SysRoleMapper;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysAreaService;
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
public class SysAreaServiceImpl implements ISysAreaService {
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

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

    @Override
    public List<TreeNode> selectAreaTreeNodes(Map<String, Object> paramMap) {
        List<TreeNode> treeNodeList = this.sysAreaMapper.selectAreaTreeNodes(paramMap);
        /**
         * 判断是否拥有管理员权限
         *      有则不做过滤，返回全部地区信息；
         *      无则只返回当前登录用户及其子地区信息
         */
        if(!SpringSecurityUtil.hasRole("ADMIN_AUTH")){
            SysUser sysUser = SpringSecurityUtil.getPrincipal();
            SysArea sysArea = sysUser.getSysOrg().getSysArea();
            if(sysUser != null && sysArea != null){
                List<TreeNode> childList = new ArrayList<TreeNode>();
                TreeNode pTreeNode = new TreeNode();
                pTreeNode.setId(sysArea.getAreaCode());
                pTreeNode.setName(sysArea.getAreaName());
                pTreeNode.setPId(sysArea.getParentCode());
                pTreeNode.setIsParent("true");
                pTreeNode.setOpen("true");
                childList.add(pTreeNode);
                RecursiveChildUtil.areaTreeChildRecursive(sysArea.getAreaCode(),treeNodeList,childList);
                return childList;
            }
        }
        return treeNodeList;
    }

    @Override
    public SysArea selectByAreaCode(String areacode) {
        return this.sysAreaMapper.selectByAreaCode(areacode);
    }

    @Override
    public Map<String,Object> areaRelation(String areaCode) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("isQuote",false);
        //1.查询该地区编码下是否存在子地区，如果存在则证明被引用
        int childNum = this.sysAreaMapper.selectChildNumByAreaCode(areaCode);
        if(childNum > 0){
            resultMap.put("isQuote",true);
            resultMap.put("msg","存在子地区，不允许更新/删除！");
        }
        //2.查询该地区编码是否被机构表引用
        int orgNum = this.sysOrgMapper.selectOrgNumByAreaCode(areaCode);
        if(orgNum > 0){
            resultMap.put("isQuote",true);
            resultMap.put("msg","机构表中存在外键引用，不允许更新/删除！");
        }
        //3.查询该地区编码是否被角色表引用
        int roleNum = this.sysRoleMapper.selectRoleNumByAreaCode(areaCode);
        if(roleNum > 0){
            resultMap.put("isQuote",true);
            resultMap.put("msg","角色表中存在外键引用，不允许更新/删除！");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatAreaWithFK(SysArea sysArea,String areaCode) {
        //1.更新地区表中parent_code等于指定areacode的parent_code值
        int childNum = this.sysAreaMapper.updateParentCodeByAreaCode(areaCode,sysArea.getAreaCode());
        //2.更新机构表中的area_code字段；由于外键设置了级联更新，这里不再主动更新
//        int orgNum = this.sysOrgMapper.updateAreaCode(areaCode,sysArea.getAreaCode());
        //3.更新角色表中的area_code字段；由于外键设置了级联更新，这里不再主动更新
//        int roleNum = this.sysRoleMapper.updateAreaCode(areaCode,sysArea.getAreaCode());


        //最后更新地区信息
        return this.sysAreaMapper.updateByPrimaryKeySelective(sysArea);
    }

    @Override
    public List<TreeNode> selectChildsAreaListTree(Map<String, Object> paramMap) {
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        SysArea sysArea = sysUser.getSysOrg().getSysArea();
        List<TreeNode> childList = new ArrayList<TreeNode>();
        if(sysUser != null && sysArea != null){
            TreeNode pTreeNode = new TreeNode();
            pTreeNode.setId(sysArea.getAreaCode());
            pTreeNode.setName(sysArea.getAreaName());
            pTreeNode.setPId(sysArea.getParentCode());
            pTreeNode.setIsParent("false");
            pTreeNode.setOpen("false");
            if(SpringSecurityUtil.hasRole("ADMIN_AUTH")){//管理员
                List<TreeNode> treeNodeList = this.sysAreaMapper.selectAreaTreeNodes(paramMap);
                if(treeNodeList != null && treeNodeList.size() >0){
                    pTreeNode.setIsParent("true");
                    pTreeNode.setOpen("true");
                }
                childList.add(pTreeNode);
                RecursiveChildUtil.areaTreeChildRecursive(sysArea.getAreaCode(),treeNodeList,childList);
            }else{
                childList.add(pTreeNode);
            }
        }
        return childList;
    }
}