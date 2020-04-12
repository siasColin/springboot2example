package cn.net.colin.mapper.quartzManage;

import cn.net.colin.model.quartzManage.SysQuzrtz;

import java.util.List;
import java.util.Map;

/**
 * @author sxf code generator
 * date:2020/04/12 16:15
 */
public interface SysQuzrtzMapper {

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
    int insert(SysQuzrtz record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     * @return 返回添加成功的数量
     */
    int insertSelective(SysQuzrtz record);

    /**
     * 根据ID查询
     * @param id 主键ID
     * @return 返回查询的结果
     */
    SysQuzrtz selectByPrimaryKey(Long id);

    /**
     * 根据ID修改对应字段
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKeySelective(SysQuzrtz record);

    /** 
     * 根据ID修改字段（包含二进制大对象）
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKeyWithBLOBs(SysQuzrtz record);

    /** 
     * 根据ID修改所有字段(必须含ID）
     * @param record 修改字段对象(必须含ID）
     * @return 返回更新成功的数量
     */
    int updateByPrimaryKey(SysQuzrtz record);

    /**
     * 返回任务信息列表
     * @param paramMap
     *  quartzname 任务名称（模糊查询）
     * @return
     */
    List<SysQuzrtz> selectByParams(Map<String, Object> paramMap);

    /**
     * 返回任务信息列表（带任务参数）
     * @param quartzParams
     *  quartzname 任务名称（模糊查询）
     *  running 任务状态（1 启用，0禁用）
     * @return
     */
    List<SysQuzrtz> selectByParamsWithBlobs(Map<String, Object> quartzParams);
}