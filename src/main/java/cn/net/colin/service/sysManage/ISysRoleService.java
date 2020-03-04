package cn.net.colin.service.sysManage;

import cn.net.colin.model.sysManage.SysRole;

import java.util.List;

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
}