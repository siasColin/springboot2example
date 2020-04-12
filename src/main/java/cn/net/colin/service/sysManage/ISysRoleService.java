package cn.net.colin.service.sysManage;

import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysRole;

import java.util.List;
import java.util.Map;

public interface ISysRoleService {

    SysRole selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    /**
     * 根据ID修改所有字段(必须含ID）
     * @param sysRoleList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatch(List<SysRole> sysRoleList);

    /**
     * 批量插入（字段可选）
     * @param sysRoleList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatchSelective(List<SysRole> sysRoleList);

    /**
     * 批量修改
     * @param sysRoleList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKey(List<SysRole> sysRoleList);

    /**
     * 批量修改(字段可选)
     * @param sysRoleList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKeySelective(List<SysRole> sysRoleList);

    /**
     * 根据传入参数，查询角色信息
     * @param paramMap
     * @return
     */
    List<SysRole> selectByParams(Map<String, Object> paramMap);

    /**
     * 保存角色，并保存角色和系统权限关联关系
     * @param sysRole
     * @param systemPermissions
     * @return
     */
    int saveRoleAndPermissions(SysRole sysRole, String[] systemPermissions);

    /**
     * 更新角色信息，同时更新角色和系统权限关联关系
     * @param sysRole
     * @param systemPermissions
     * @return
     */
    int updateRoleAndPermissions(SysRole sysRole, String[] systemPermissions);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    int deleteBatchByPrimaryKey(Long[] ids);

    /**
     * 获取以地区为父节点的角色ztree树结构数据
     * @param paramMap
     * @return
     */
    List<TreeNode> roleWithAreaListTree(Map<String, Object> paramMap);

    /**
     * 保存角色菜单信息
     * @param params
     * @return
     */
    int saveRoleAndMenu(Map<String, Object> params);

    /**
     * 根据角色id，查询角色关联的菜单
     * @param roleId
     * @return
     */
    List<String> selectMenuIdsByRoleId(Long roleId);

    /**
     * 返回指定用户关联的角色id集合
     * @param paramMap
     * @return
     */
    List<Long> selectRoleIdListByUserId(Map<String, Object> paramMap);

    /**
     * 角色绑定用户
     * @param roleId
     * @param users
     * @return
     */
    int saveRoleAndUsers(String roleId, String[] users);

    /**
     * 解除角色用户绑定关系
     * @param roleId
     * @param users
     * @return
     */
    int deleteRoleAndUser(String roleId, Long [] users);
}