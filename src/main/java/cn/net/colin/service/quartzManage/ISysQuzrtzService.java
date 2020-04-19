package cn.net.colin.service.quartzManage;

import cn.net.colin.model.quartzManage.SysQuartz;

import java.util.List;
import java.util.Map;

public interface ISysQuzrtzService {

    SysQuartz selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysQuartz record);

    int updateByPrimaryKey(SysQuartz record);

    int insert(SysQuartz record);

    int insertSelective(SysQuartz record);

    /**
     * 返回任务信息列表
     * @param paramMap
     *  quartzname 任务名称（模糊查询）
     *  running 任务状态（1 启用，0禁用）
     * @return
     */
    List<SysQuartz> selectByParams(Map<String, Object> paramMap);

    /**
     * 返回任务信息列表(带任务参数)
     * @param quartzParams
     *  quartzname 任务名称（模糊查询）
     *  running 任务状态（1 启用，0禁用）
     * @return
     */
    List<SysQuartz> selectByParamsWithBlobs(Map<String, Object> quartzParams);

    /**
     * 批量删除任务
     * @param ids
     * @return
     */
    int deleteBatchByPrimaryKey(Long[] ids);

    /**
     * 根据id数组，查询任务集合
     * @param ids
     * @return
     */
    List<SysQuartz> selectByPrimaryKeys(Long[] ids);
}