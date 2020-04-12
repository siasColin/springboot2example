package cn.net.colin.mapper.sysManage;

import cn.net.colin.model.sysManage.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sxf
 * date:2020/03/04 17:48
 */
public interface SysUserMapper {
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
    int insert(SysUser record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     * @return 返回添加成功的数量
     */
    int insertSelective(SysUser record);

    /** 
     * 根据ID查询
     * @param id 主键ID
     * @return 返回查询的结果
     */
    SysUser selectByPrimaryKey(Long id);

    /** 
     * 根据ID修改对应字段
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKeySelective(SysUser record);

    /** 
     * 根据ID修改所有字段(必须含ID）
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKey(SysUser record);

    /**
     * 根据ID修改所有字段(必须含ID）
     * @param sysUserList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatch(List<SysUser> sysUserList);

    /**
     * 批量插入（字段可选）
     * @param sysUserList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatchSelective(List<SysUser> sysUserList);

    /**
     * 批量修改
     * @param sysUserList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKey(List<SysUser> sysUserList);

    /**
     * 批量修改(字段可选)
     * @param sysUserList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKeySelective(List<SysUser> sysUserList);

    /**
     * 根据条件查询用户信息
     * @param user
     * @return 返回用户集合
     */
    List<SysUser> selective(SysUser user);

    /**
     * 查询该机构编码是否被用户表引用
     * @param orgCode
     * @return
     */
    int selectUserNumByOrgCode(@Param("orgCode") String orgCode);

    /**
     * 返回用户信息列表
     * @param paramMap
     * @return
     */
    List<SysUser> selectByParams(Map<String, Object> paramMap);

    /**
     * 根据id集合，删除用户
     * @param ids
     */
    int deleteBatchByPrimaryKey(Long[] ids);

    /**
     * 重置用户密码
     * @param password
     * @param userIds
     * @return
     */
    int updatePwdByUserIds(@Param("password")String password, @Param("userIds")String[] userIds);

    /**
     * 据角色id，查询角色关联的用户集合
     * @param roleId
     * @return 系统用户集合
     */
    List<SysUser> selectUserListByRoleId(@Param("roleId") String roleId);
}