package cn.net.colin.mapper.sysManage;

import cn.net.colin.model.sysManage.SysOperatetype;
import cn.net.colin.model.sysManage.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sxf
 * date:2020/03/04 17:48
 */
public interface SysOperatetypeMapper {
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
    int insert(SysOperatetype record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     * @return 返回添加成功的数量
     */
    int insertSelective(SysOperatetype record);

    /** 
     * 根据ID查询
     * @param id 主键ID
     * @return 返回查询的结果
     */
    SysOperatetype selectByPrimaryKey(Long id);

    /** 
     * 根据ID修改对应字段
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKeySelective(SysOperatetype record);

    /** 
     * 根据ID修改所有字段(必须含ID）
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKey(SysOperatetype record);

    /**
     * 根据ID修改所有字段(必须含ID）
     * @param operatetypeList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatch(List<SysOperatetype> operatetypeList);

    /**
     * 批量插入（字段可选）
     * @param operatetypeList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatchSelective(List<SysOperatetype> operatetypeList);

    /**
     * 批量修改
     * @param operatetypeList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKey(List<SysOperatetype> operatetypeList);

    /**
     * 批量修改(字段可选)
     * @param operatetypeList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKeySelective(List<SysOperatetype> operatetypeList);

    /**
     * 根据系统角色集合查询出对应的系统权限集合
     * @param sysRoleList
     * @return
     */
    List<SysOperatetype> selectOperatetypeListByRoleList(@Param("list") List<SysRole> sysRoleList);

    /**
     * 返回系统权限集合
     * @param paramMap
     * @return
     */
    List<SysOperatetype> selectByParams(Map<String, Object> paramMap);

    /**
     * 根据角色id，查询角色对应的系统权限id集合
     * @param paramMap
     * @return
     */
    List<Long> selectOperatetypeidListByRoleid(Map<String, Object> paramMap);
}