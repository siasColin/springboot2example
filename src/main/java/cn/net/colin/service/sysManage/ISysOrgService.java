package cn.net.colin.service.sysManage;

import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysOrg;

import java.util.List;
import java.util.Map;

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

    /**
     *  获取ztree结构的机构信息
     * @param paramMap
     * @return
     */
    List<TreeNode> selectOrgTreeNodes(Map<String, Object> paramMap);

    /**
     * 根据机构编码查询机构信息
     * @param orgcode 机构编码
     * @return
     */
    SysOrg selectByOrgCode(String orgcode);

    /**
     * 根据父级机构编码，查询子机构
     * @param parentCode 父机构编码
     * @return
     */
    List<SysOrg> selectByParentCode(String parentCode);
}