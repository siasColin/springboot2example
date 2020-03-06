package cn.net.colin.service.sysManage;

import cn.net.colin.model.sysManage.SysModulelist;
import java.util.List;

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
}