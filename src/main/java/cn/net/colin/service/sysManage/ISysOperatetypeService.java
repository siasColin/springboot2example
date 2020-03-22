package cn.net.colin.service.sysManage;

import cn.net.colin.model.sysManage.SysOperatetype;

import java.util.List;
import java.util.Map;

public interface ISysOperatetypeService {

    SysOperatetype selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOperatetype record);

    int updateByPrimaryKey(SysOperatetype record);

    int insert(SysOperatetype record);

    int insertSelective(SysOperatetype record);

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