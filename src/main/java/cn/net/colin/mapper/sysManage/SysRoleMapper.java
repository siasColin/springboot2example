package cn.net.colin.mapper.sysManage;

import cn.net.colin.model.sysManage.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}