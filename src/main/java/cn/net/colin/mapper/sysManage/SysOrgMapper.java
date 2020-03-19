package cn.net.colin.mapper.sysManage;

import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sxf
 * date:2020/03/04 17:48
 */
public interface SysOrgMapper {
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
    int insert(SysOrg record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     * @return 返回添加成功的数量
     */
    int insertSelective(SysOrg record);

    /** 
     * 根据ID查询
     * @param id 主键ID
     * @return 返回查询的结果
     */
    SysOrg selectByPrimaryKey(Long id);

    /** 
     * 根据ID修改对应字段
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKeySelective(SysOrg record);

    /** 
     * 根据ID修改所有字段(必须含ID）
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKey(SysOrg record);

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
     * 根据机构编码查询机构信息
     * @param orgCode
     * @return
     */
    SysOrg selectByOrgCode(@Param("orgCode") String orgCode);

    /**
     * 查询指定地区编码下机构数量
     * @param areaCode
     * @return
     */
    int selectOrgNumByAreaCode(@Param("areaCode") String areaCode);

    /**
     * 更新机构表中的area_code字段
     * @param areaCode  原始地区编码
     * @param newCode   更新后的地区编码
     * @return
     */
    int updateAreaCode(@Param("areaCode") String areaCode, @Param("newCode") String newCode);

    /**
     * 查询ztree结构的机构信息
     * @param paramMap
     * @return
     */
    List<TreeNode> selectOrgTreeNodes(Map<String, Object> paramMap);

    /**
     * 根据父级机构编码，查询子机构
     * @param parentCode 父机构编码
     * @return
     */
    List<SysOrg> selectByParentCode(@Param("parentCode") String parentCode);

    /**
     * 验证一个机构编码是否被其他表引用
     * @param orgCode
     * @return
     */
    int selectChildNumByOrgCode(@Param("orgCode") String orgCode);

    /**
     * 更新机构表中parent_code等于指定orgCode的parent_code值
     * @param orgCode 原始机构编码
     * @param newCode 更新后的机构编码
     * @return
     */
    int updateParentCodeByOrgCode(@Param("orgCode") String orgCode, @Param("newCode") String newCode);
}