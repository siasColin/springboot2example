package cn.net.colin.service.sysManage.impl;

import cn.net.colin.common.util.RecursiveChildUtil;
import cn.net.colin.common.util.SQLUtil;
import cn.net.colin.common.util.SpringSecurityUtil;
import cn.net.colin.mapper.sysManage.SysAreaMapper;
import cn.net.colin.mapper.sysManage.SysRoleMapper;
import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.model.sysManage.SysRole;
import cn.net.colin.model.sysManage.SysUser;
import cn.net.colin.service.sysManage.ISysRoleService;
import com.github.pagehelper.PageHelper;
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
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;

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

    @Override
    public List<SysRole> selectByParams(Map<String, Object> paramMap) {
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        /**
         * 判断是否拥有管理员权限
         *      有则返回当前地区及子地区所有角色信息；
         *      无则只返回当前登录地区的共享角色信息，以及本机构的私有角色信息
         */
        if(sysUser != null && !SpringSecurityUtil.hasRole("ADMIN_AUTH")){
            if(paramMap.get("orgCode") == null){
                paramMap.put("orgCode",sysUser.getSysOrg().getOrgCode());
            }
        }
        //如果参数中有地区编码，说明查询指定地区的角色信息，否则进行当前地区及子地区过滤
        if(!(paramMap != null && paramMap.get("areaCode") != null)){
            List<TreeNode> areaList = new ArrayList<TreeNode>();
            if(sysUser != null){
                SysArea sysArea = sysUser.getSysOrg().getSysArea();
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
                    areaList.add(pTreeNode);
                    RecursiveChildUtil.areaTreeChildRecursive(sysArea.getAreaCode(),treeNodeList,areaList);
                }else{
                    areaList.add(pTreeNode);
                }
            }
            List<List<TreeNode>> sumAreaList = SQLUtil.getSumArrayList(areaList);
            paramMap.put("areaList",sumAreaList);
        }
        int pageNum = paramMap.get("page") == null ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int pageSize = paramMap.get("limit") == null ? 10 : Integer.parseInt(paramMap.get("limit").toString());
        PageHelper.startPage(pageNum,pageSize);
        return this.sysRoleMapper.selectByParams(paramMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveRoleAndPermissions(SysRole sysRole, String[] systemPermissions) {
        int num = this.sysRoleMapper.insertSelective(sysRole);
        if(systemPermissions != null && systemPermissions.length > 0){
            long roleId = sysRole.getId();
            List<Map<String,Object>> roleOperatetypeList = new ArrayList<Map<String,Object>>();
            for (String operateTypeId: systemPermissions) {
                Map<String,Object> roleOperatetypeMap = new HashMap<String,Object>(2);
                roleOperatetypeMap.put("operateTypeId",Long.parseLong(operateTypeId));
                roleOperatetypeMap.put("roleId",roleId);
                roleOperatetypeList.add(roleOperatetypeMap);
            }
            this.sysRoleMapper.saveRoleOperatetypeList(roleOperatetypeList);
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRoleAndPermissions(SysRole sysRole, String[] systemPermissions) {
        long roleId = sysRole.getId();
        //更新角色信息
        int num = this.sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        //先删除角色和权限关联关系
        int deleteNum = this.sysRoleMapper.deleteRoleOperatetypeByRoleid(roleId);
        //再重新保存
        if(systemPermissions != null && systemPermissions.length > 0){
            List<Map<String,Object>> roleOperatetypeList = new ArrayList<Map<String,Object>>();
            for (String operateTypeId: systemPermissions) {
                Map<String,Object> roleOperatetypeMap = new HashMap<String,Object>(2);
                roleOperatetypeMap.put("operateTypeId",Long.parseLong(operateTypeId));
                roleOperatetypeMap.put("roleId",roleId);
                roleOperatetypeList.add(roleOperatetypeMap);
            }
            this.sysRoleMapper.saveRoleOperatetypeList(roleOperatetypeList);
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatchByPrimaryKey(Long[] ids) {
        //1.删除角色
        int num = this.sysRoleMapper.deleteBatchByPrimaryKey(ids);
        //2.删除角色权限关联关系
        this.sysRoleMapper.deleteRoleAndPermissions(ids);
        //3.删除角色菜单关联关系
        this.sysRoleMapper.deleteRoleModulelistByRoleIds(ids);
        //4.删除角色用户关联关系
        this.sysRoleMapper.deleteRoleAndUserByRoleIds(ids);
        return num;
    }

    @Override
    public List<TreeNode> roleWithAreaListTree(Map<String, Object> paramMap) {
        SysUser sysUser = SpringSecurityUtil.getPrincipal();
        /**
         * 判断是否拥有管理员权限
         *      有则返回，当前地区及子地区所有角色信息；
         *      无则只返回当前登录地区的共享角色信息，以及本机构的私有角色信息
         */
        if(sysUser != null && !SpringSecurityUtil.hasRole("ADMIN_AUTH")){
            paramMap.put("orgCode",sysUser.getSysOrg().getOrgCode());
        }
        List<TreeNode> areaList = new ArrayList<TreeNode>();
        if(sysUser != null){
            SysArea sysArea = sysUser.getSysOrg().getSysArea();
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
                areaList.add(pTreeNode);
                RecursiveChildUtil.areaTreeChildRecursive(sysArea.getAreaCode(),treeNodeList,areaList);
            }else{
                areaList.add(pTreeNode);
            }
        }
        List<List<TreeNode>> sumAreaList = SQLUtil.getSumArrayList(areaList);
        paramMap.put("areaList",sumAreaList);
        List<SysRole> roleList = this.sysRoleMapper.selectByParams(paramMap);
        List<TreeNode> resultList = new ArrayList<TreeNode>();
        if(areaList != null && areaList.size() > 0 && roleList != null && roleList.size() > 0){
            for(int i=0;i<areaList.size();i++){
                TreeNode areaNode = areaList.get(i);
                areaNode.setChkDisabled("true");
                areaNode.setId("area_"+areaNode.getId());
                areaNode.setPId("area_"+areaNode.getPId());
                resultList.add(areaNode);
            }
            for(int i=0;i<roleList.size();i++){
                SysRole sysRole = roleList.get(i);
                TreeNode roleNode = new TreeNode();
                roleNode.setName(sysRole.getRoleName());
                roleNode.setId(sysRole.getId()+"");
                roleNode.setPId("area_"+sysRole.getAreaCode());
                resultList.add(roleNode);
            }
        }

        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveRoleAndMenu(Map<String, Object> params) {
        int saveNum = 0;
        String checkedRole = params.get("checkedRole") == null ? null : params.get("checkedRole").toString();
        String checkedMenus = params.get("checkedMenus") == null ? null : params.get("checkedMenus").toString();
        if(checkedRole != null && !checkedRole.trim().equals("")){
            //先删除选定角色和菜单的关联关系
            Long [] roleArr = {Long.parseLong(checkedRole)};
            this.sysRoleMapper.deleteRoleModulelistByRoleIds(roleArr);
            if(checkedMenus != null && !checkedMenus.trim().equals("")){
                String [] checkedMenuArr = checkedMenus.split(",");
                //关联菜单不为空，保存
                List<Map<String,Long>> roleAndMenuList = new ArrayList<Map<String,Long>>(checkedMenuArr.length);
                long roleid = Long.parseLong(checkedRole);
                for (int i = 0;i<checkedMenuArr.length;i++){
                    Map<String,Long> roleAndMenu = new HashMap<String,Long>();
                    roleAndMenu.put("roleId",roleid);
                    roleAndMenu.put("moduleListId",Long.parseLong(checkedMenuArr[i]));
                    roleAndMenuList.add(roleAndMenu);
                }
                saveNum = this.sysRoleMapper.insertRoleMenuBatch(roleAndMenuList);
            }
        }
        return saveNum;
    }

    @Override
    public List<String> selectMenuIdsByRoleId(Long roleId) {
        return this.sysRoleMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public List<Long> selectRoleIdListByUserId(Map<String, Object> paramMap) {
        List<Long> relustList = new ArrayList<Long>();
        if(paramMap != null && paramMap.get("userId") != null){
            //查询用户的系统角色
            List<SysRole> sysRoleList = sysRoleMapper.selectByUserId(Long.parseLong(paramMap.get("userId").toString()));
            for (SysRole role : sysRoleList) {
                relustList.add(role.getId());
            }
        }
        return relustList;
    }

    @Override
    public int saveRoleAndUsers(String roleId, String[] users) {
        int saveNum = 0;
        if(roleId != null && !roleId.trim().equals("") && users != null && users.length > 0){
            List<Map<String,Object>> userAndRoleList = new ArrayList<Map<String,Object>>();
            for (String userId: users) {
                Map<String,Object> userAndRoleMap = new HashMap<String,Object>(2);
                userAndRoleMap.put("roleId",Long.parseLong(roleId));
                userAndRoleMap.put("userId",Long.parseLong(userId));
                userAndRoleList.add(userAndRoleMap);
            }
            saveNum = this.sysRoleMapper.saveUserAndRoleList(userAndRoleList);
        }
        return saveNum;
    }

    @Override
    public int deleteRoleAndUser(String roleId, Long [] users) {
        int deleteNum = 0;
        if(roleId != null && !roleId.trim().equals("") && users != null && users.length > 0){
            deleteNum = this.sysRoleMapper.deleteUserAndRoleByRoleIdAndUserIds(Long.parseLong(roleId),users);
        }
        return deleteNum;
    }
}