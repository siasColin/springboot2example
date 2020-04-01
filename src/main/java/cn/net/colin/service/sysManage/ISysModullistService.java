package cn.net.colin.service.sysManage;

import cn.net.colin.model.common.TreeNode;
import cn.net.colin.model.sysManage.SysModulelist;
import java.util.List;
import java.util.Map;

public interface ISysModullistService {

    SysModulelist selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysModulelist record);

    int updateByPrimaryKey(SysModulelist record);

    int insert(SysModulelist record);

    int insertSelective(SysModulelist record);

    /**
     * 根据ID修改所有字段(必须含ID）
     * @param modulelists 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatch(List<SysModulelist> modulelists);

    /**
     * 批量插入（字段可选）
     * @param modulelists 插入对象(必须含ID）集合
     * @return 返回插入成功的数量
     */
    int insertBatchSelective(List<SysModulelist> modulelists);

    /**
     * 批量修改
     * @param modulelists 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKey(List<SysModulelist> modulelists);

    /**
     * 批量修改(字段可选)
     * @param modulelists 修改对象(必须含ID）集合
     * @return 返回修改成功的数量
     */
    int updateBatchByPrimaryKeySelective(List<SysModulelist> modulelists);

    /**
     * 查询满足zTree数据结构的菜单信息集合
     * @param paramMap
     * @return 菜单信息集合
     */
    List<TreeNode> selectMenuTreeNodes(Map<String, Object> paramMap);

    /**
     * 查询指定pid的子菜单
     * @param pid
     * @return
     */
    List<SysModulelist> selectByPid(long pid);

    /**
     * 获取当前登录用户已授权的一级菜单
     * @return
     */
    List<SysModulelist> selectFirstMenu();

    /**
     * 根据一级菜单id，查询子菜单
     * @param moduleId
     * @return
     */
    Map<String, Object> selectChildMenu(String moduleId);
}