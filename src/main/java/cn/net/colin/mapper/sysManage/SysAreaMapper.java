package cn.net.colin.mapper.sysManage;

import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sxf
 * date:2020/03/04 17:48
 */
public interface SysAreaMapper {
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
    int insert(SysArea record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     * @return 返回添加成功的数量
     */
    int insertSelective(SysArea record);

    /** 
     * 根据ID查询
     * @param id 主键ID
     * @return 返回查询的结果
     */
    SysArea selectByPrimaryKey(Long id);

    /** 
     * 根据ID修改对应字段
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKeySelective(SysArea record);

    /** 
     * 根据ID修改所有字段(必须含ID）
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKey(SysArea record);

    /**
     * 批量插入
     * @param areaList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatch(List<SysArea> areaList);

    /**
     * 批量插入（字段可选）
     * @param areaList 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatchSelective(List<SysArea> areaList);

    /**
     * 批量修改
     * @param areaList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKey(List<SysArea> areaList);

    /**
     * 批量修改(字段可选)
     * @param areaList 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKeySelective(List<SysArea> areaList);

    /**
     * 查询所有记录
     * @return 返回所有记录集合
     */
    List<SysArea> selectAll();

    /**
     * 根据地区编码查询地区信息
     * @param areaCode
     */
    SysArea selectByAreaCode(@Param("areaCode") String areaCode);

    /**
     * 查询满足zTree数据结构的地区信息集合
     * @param paramMap
     * @return 地区信息集合
     */
    List<TreeNode> selectAreaTreeNodes(Map<String, Object> paramMap);

    /**
     * 查询子地区数量
     * @param areaCode
     * @return
     */
    int selectChildNumByAreaCode(@Param("areaCode") String areaCode);

    /**
     * 更新地区表中parent_code等于指定areacode的parent_code值
     * @param areaCode  原始地区编码
     * @param newCode   更新后的地区编码
     * @return
     */
    int updateParentCodeByAreaCode(@Param("areaCode") String areaCode, @Param("newCode") String newCode);
}