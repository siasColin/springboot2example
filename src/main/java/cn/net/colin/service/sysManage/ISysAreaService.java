package cn.net.colin.service.sysManage;

import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysArea;

import java.util.List;
import java.util.Map;


public interface ISysAreaService {

    SysArea selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysArea record);

    int updateByPrimaryKey(SysArea record);

    int insert(SysArea record);

    int insertSelective(SysArea record);

    /**
     * 根据ID修改所有字段(必须含ID）
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
     * 查询满足zTree数据结构的地区信息集合
     * @param paramMap
     * @return 地区信息集合
     */
    List<TreeNode> selectAreaTreeNodes(Map<String, Object> paramMap);

    /**
     * 根据地区编码查询地区信息
     * @param areacode
     * @return 地区信息对象
     */
    SysArea selectByAreaCode(String areacode);

    /**
     * 验证一个地区编码是否被引用
     * @param areaCode
     * @return
     */
    Map<String,Object> areaRelation(String areaCode);

    /**
     * 更新地区信息，同时更新关联表中AreaCode信息
     * @param sysArea 待更新的地区信息
     * @param areaCode 更新之前的areaCode
     * @return
     */
    int updatAreaWithFK(SysArea sysArea,String areaCode);

    /**
     * 管理员，返回当前登录地区以及子地区信息
     * 非管理员，返回当前登录地区
     * @param paramMap
     * @return
     */
    List<TreeNode> selectChildsAreaListTree(Map<String, Object> paramMap);
}