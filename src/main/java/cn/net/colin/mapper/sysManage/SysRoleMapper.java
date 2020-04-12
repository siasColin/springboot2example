package cn.net.colin.mapper.sysManage;

import cn.net.colin.model.sysManage.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sxf
 * date:2020/03/04 17:48
 */
public interface SysRoleMapper {
    /** 
     * 根据ID删除
     * @param id 主键ID
     * @return 返回删除成功的数量
     */
    int deleteByPrimaryKey(Long id);

    /** 
     * 添加对象所有字段
     * @param record 插入字段对象(必须含ID）
     * @return 返回添加成功的数量
     */
    int insert(SysRole record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     * @return 返回添加成功的数量
     */
    int insertSelective(SysRole record);

    /** 
     * 根据ID查询
     * @param id 主键ID
     * @return 返回查询的结果
     */
    SysRole selectByPrimaryKey(Long id);

    /** 
     * 根据ID修改对应字段
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKeySelective(SysRole record);

    /** 
     * 根据ID修改所有字段(必须含ID）
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKey(SysRole record);

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
     * 根据用户id，查询出用户关联的角色
     * @param id
     * @return
     */
    List<SysRole> selectByUserId(@Param("userid") Long id);

    /**
     * 查询指定地区编码下角色数量
     * @param areaCode
     * @return
     */
    int selectRoleNumByAreaCode(String areaCode);

    /**
     * 更新角色表中的area_code字段
     * @param areaCode  原始地区编码
     * @param newCode   更新后的地区编码
     * @return
     */
    int updateAreaCode(String areaCode, String newCode);

    /**
     * 查询该地区编码是否被角色表引用
     * @param orgCode
     * @return
     */
    int selectRoleNumByOrgCode(String orgCode);

    /**
     *
     * @param orgCode 原始机构编码
     * @param newCode 更新后的机构编码
     * @return
     */
    int updateOrgCode(@Param("orgCode") String orgCode, @Param("newCode") String newCode);

    /**
     * 根据传入参数，查询角色信息
     * @param paramMap
     * @return
     */
    List<SysRole> selectByParams(Map<String, Object> paramMap);

    /**
     * 保存角色和权限关联关系
     * @param roleOperatetypeList
     */
    void saveRoleOperatetypeList(List<Map<String, Object>> roleOperatetypeList);

    /**
     * 根据角色id，删除角色和权限关联关系
     * @param id
     * @return
     */
    int deleteRoleOperatetypeByRoleid(@Param("roleid") Long id);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    int deleteBatchByPrimaryKey(Long[] ids);

    /**
     * 删除角色和权限关联关系
     * @param ids
     */
    void deleteRoleAndPermissions(Long[] ids);

    /**
     * 根据菜单id，删除角色和菜单关联关系
     * @param id
     */
    void deleteRoleModulelistByModuleListid(@Param("moduleListId") Long id);

    /**
     * 删除角色和菜单关联关系
     * @param ids
     */
    void deleteRoleModulelistByRoleIds(Long[] ids);

    /**
     * 删除角色和用户表关联关系
     * @param ids
     */
    void deleteRoleAndUserByRoleIds(Long[] ids);

    /**
     * 批量保存角色和菜单关联关系
     * @param roleAndMenuList
     * @return
     */
    int insertRoleMenuBatch(List<Map<String, Long>> roleAndMenuList);

    /**
     * 根据角色id，查询角色关联的菜单
     * @param roleId
     * @return
     */
    List<String> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 批量保存用户角色信息
     * @param userAndRoleList
     */
    int saveUserAndRoleList(List<Map<String, Object>> userAndRoleList);

    /**
     * 根据用户id，批量删除用户和角色的关联关系
     * @param userIds
     */
    void deleteUserAndRoleByUserId(Long [] userIds);

    /**
     * 解除角色用户绑定关系
     * @param roleId
     * @param users
     * @return
     */
    int deleteUserAndRoleByRoleIdAndUserIds(@Param("roleid") Long roleId, Long[] users);
}