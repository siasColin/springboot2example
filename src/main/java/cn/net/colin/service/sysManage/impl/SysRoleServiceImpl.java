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
         *      有则返回，当前地区及子地区所有角色信息；
         *      无则只返回当前登录地区下以及子地区的共享角色信息，以及本机构的私有角色信息
         */
        if(sysUser != null && !SpringSecurityUtil.hasRole("ADMIN_AUTH")){
            paramMap.put("orgCode",sysUser.getSysOrg().getOrgCode());
        }
        //如果参数中有地区编码，说明查询指定地区的角色信息，否则进行当前地区及子地区过滤
        if(!(paramMap != null && paramMap.get("areaCode") != null)){
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

        //4.删除角色用户关联关系

        return num;
    }
}