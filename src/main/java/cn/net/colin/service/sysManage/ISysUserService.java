package cn.net.colin.service.sysManage;

import cn.net.colin.model.sysManage.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface ISysUserService extends UserDetailsService {

    SysUser selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int insert(SysUser record);

    int insertSelective(SysUser record);

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
     * 返回用户信息列表
     * @param paramMap
     * @return
     */
    List<SysUser> selectByParams(Map<String, Object> paramMap);

    /**
     * 保存用户以及用户关联角色信息
     * @param user
     * @param roles
     * @return
     */
    int saveUserAndRoles(SysUser user, String[] roles);

    /**
     * 更新用户信息,以及用户和角色关联关系
     * @param user
     * @param roleIds
     * @return
     */
    int updateUserAndRoles(SysUser user, String[] roleIds);

    /**
     * 根据id集合，删除用户
     * @param ids
     * @return
     */
    int deleteBatchByPrimaryKey(Long[] ids);

    /**
     * 重置用户密码
     * @param password
     * @param userIds
     * @return
     */
    int updatePwdByUserIds(String password, String[] userIds);

    /**
     * 据角色id，查询角色关联的用户集合
     * @param roleId
     * @return 系统用户集合
     */
    List<SysUser> selectUserListByRoleId(String roleId);
}