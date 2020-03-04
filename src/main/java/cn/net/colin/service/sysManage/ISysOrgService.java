package cn.net.colin.service.sysManage;

import cn.net.colin.model.sysManage.SysOrg;

import java.util.List;

public interface ISysOrgService {

    SysOrg selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOrg record);

    int updateByPrimaryKey(SysOrg record);

    int insert(SysOrg record);

    int insertSelective(SysOrg record);

    /**
     * 根据ID修改所有字段(必须含ID）
     * @param sysOrgList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatch(List<SysOrg> sysOrgList);

    /**
     * 批量插入（字段可选）
     * @param sysOrgList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatchSelective(List<SysOrg> sysOrgList);

    /**
     * 批量修改
     * @param sysOrgList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKey(List<SysOrg> sysOrgList);

    /**
     * 批量修改(字段可选)
     * @param sysOrgList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKeySelective(List<SysOrg> sysOrgList);
}