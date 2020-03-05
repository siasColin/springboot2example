package cn.net.colin.service.sysManage;

import cn.net.colin.model.sysManage.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

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
}