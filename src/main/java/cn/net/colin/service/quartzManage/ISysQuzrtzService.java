package cn.net.colin.service.quartzManage;

import cn.net.colin.model.quartzManage.SysQuzrtz;

import java.util.List;
import java.util.Map;

public interface ISysQuzrtzService {

    SysQuzrtz selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysQuzrtz record);

    int updateByPrimaryKey(SysQuzrtz record);

    int insert(SysQuzrtz record);

    int insertSelective(SysQuzrtz record);

    /**
     * 返回任务信息列表
     * @param paramMap
     *  quartzname 任务名称（模糊查询）
     *  running 任务状态（1 启用，0禁用）
     * @return
     */
    List<SysQuzrtz> selectByParams(Map<String, Object> paramMap);

    /**
     * 返回任务信息列表(带任务参数)
     * @param quartzParams
     *  quartzname 任务名称（模糊查询）
     *  running 任务状态（1 启用，0禁用）
     * @return
     */
    List<SysQuzrtz> selectByParamsWithBlobs(Map<String, Object> quartzParams);
}