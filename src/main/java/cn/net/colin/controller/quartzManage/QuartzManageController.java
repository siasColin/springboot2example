package cn.net.colin.controller.quartzManage;

import cn.net.colin.common.exception.entity.ResultCode;
import cn.net.colin.common.exception.entity.ResultInfo;
import cn.net.colin.common.util.JsonUtils;
import cn.net.colin.common.util.SnowflakeIdWorker;
import cn.net.colin.model.quartzManage.SysQuartz;
import cn.net.colin.quartz.util.QuartzManager;
import cn.net.colin.service.quartzManage.ISysQuzrtzService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.controller.quartzManage
 * @Author: sxf
 * @Date: 2020-4-12
 * @Description: 可视化定时任务管理页面控制器
 */
@Controller
@RequestMapping("/quartzManage")
public class QuartzManageController {
    Logger logger = LoggerFactory.getLogger(QuartzManageController.class);
    @Autowired
    private ISysQuzrtzService sysQuzrtzService;
    @Autowired
    private Scheduler scheduler;
    @Value("${quartzWorkID}")
    private String quartzWorkID;

    @GetMapping("/quzrtzManagelist")
    public String quzrtzManagelist(){
        return "quartzManage/quartzList";
    }

    /**
     * 返回任务信息列表
     * @param paramMap
     *  quartzname 任务名称（模糊查询）
     *  page 页码
     *  limit 每页数据量
     * @return ResultInfo 自定义结果返回实体类
     * @throws IOException
     */
    @GetMapping("/quartzList")
    @ResponseBody
    public ResultInfo quartzList(@RequestParam Map<String,Object> paramMap) throws IOException {
        int pageNum = paramMap.get("page") == null ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int pageSize = paramMap.get("limit") == null ? 10 : Integer.parseInt(paramMap.get("limit").toString());
        PageHelper.startPage(pageNum,pageSize);
        List<SysQuartz> sysQuartzList = sysQuzrtzService.selectByParams(paramMap);
        PageInfo<SysQuartz> result = new PageInfo(sysQuartzList);
        return ResultInfo.ofDataAndTotal(ResultCode.SUCCESS,sysQuartzList,result.getTotal());
    }

    /**
     * 跳转到添加任务页面
     * @return
     */
    @GetMapping("/quartz")
    public String quartz(){
        return "quartzManage/saveOrUpdateQuartz";
    }

    /**
     * 保存任务信息
     * @param sysQuartz
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','INSERT_AUTH')")
    @PostMapping("/quartz")
    @ResponseBody
    public ResultInfo saveQuartz(SysQuartz sysQuartz){
        sysQuartz.setRunning(1);
        sysQuartz.setId(SnowflakeIdWorker.generateId());
        if(sysQuartz.getParams() != null && !sysQuartz.getParams().trim().equals("")){//格式化json字符串
            sysQuartz.setParams(JsonUtils.jsonPretty(sysQuartz.getParams()));
        }
        int num = sysQuzrtzService.insertSelective(sysQuartz);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            //添加成功，启动任务
            try {
                QuartzManager.addJob(scheduler, sysQuartz.getQuartzname()+"_"+ sysQuartz.getId(), Class.forName(sysQuartz.getClazzname()), sysQuartz.getCron(), sysQuartz.getParams());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS, sysQuartz);
        }
        return resultInfo;
    }
    /**
     * 跳转到任务编辑页面
     * @return
     */
    @GetMapping("/quartz/{id}")
    public String quartz(@PathVariable("id") String id,Map<String,Object> modelMap){
        SysQuartz sysQuartz = sysQuzrtzService.selectByPrimaryKey(Long.parseLong(id));
        modelMap.put("sysQuartz", sysQuartz);
        return "quartzManage/saveOrUpdateQuartz";
    }

    /**
     * 更新任务信息
     * @param sysQuartz
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/quartz")
    @ResponseBody
    public ResultInfo updateQuartz(SysQuartz sysQuartz){
        SysQuartz oldSysQuartz = sysQuzrtzService.selectByPrimaryKey(sysQuartz.getId());
        if(sysQuartz.getParams() != null && !sysQuartz.getParams().trim().equals("")){//格式化json字符串
            sysQuartz.setParams(JsonUtils.jsonPretty(sysQuartz.getParams()));
        }
        int num = sysQuzrtzService.updateByPrimaryKeySelective(sysQuartz);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            if(oldSysQuartz.getRunning() == 1){//状态为启动的任务
                QuartzManager.modifyJobTimebyparams(scheduler, oldSysQuartz.getQuartzname()+"_"+oldSysQuartz.getId(), sysQuartz.getQuartzname()+"_"+sysQuartz.getId(), sysQuartz.getCron(), sysQuartz.getParams());
            }
            resultInfo = ResultInfo.ofData(ResultCode.SUCCESS,sysQuartz);
        }
        return resultInfo;
    }

    /**
     * 根据id集合，删除角色
     * @param ids
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','DELETE_AUTH')")
    @DeleteMapping("/quartz")
    @ResponseBody
    public ResultInfo deleteQuartz(Long [] ids){
        if(ids ==  null || (ids != null && ids.length == 0)){
            return ResultInfo.of(ResultCode.SUCCESS);
        }
        //先查询要删除的任务集合，用于删除成功后从quartz定时任务中移除
        List<SysQuartz> sysQuartzList = sysQuzrtzService.selectByPrimaryKeys(ids);
        int num = sysQuzrtzService.deleteBatchByPrimaryKey(ids);
        ResultInfo resultInfo = ResultInfo.of(ResultCode.UNKNOWN_ERROR);
        if(num > 0){
            for (SysQuartz quartz : sysQuartzList) {
                //删除成功，移除定时器。
                QuartzManager.removeJob(scheduler, quartz.getQuartzname()+"_"+quartz.getId());
            }
            resultInfo = ResultInfo.of(ResultCode.SUCCESS);
        }
        return resultInfo;
    }

    /**
     * 切换任务状态
     * @param id 任务id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/quartz/{id}")
    @ResponseBody
    public ResultInfo updateQuartzStatus(@PathVariable("id") String id){
        try{
            SysQuartz sysQuartz = this.sysQuzrtzService.selectByPrimaryKey(Long.parseLong(id));
            if(sysQuartz.getRunning() == 0){//当前是禁用状态，改为启用
                sysQuartz.setRunning(1);
                int num = this.sysQuzrtzService.updateByPrimaryKeySelective(sysQuartz);
                if( num > 0){
                    QuartzManager.addJob(scheduler, sysQuartz.getQuartzname()+"_"+id, Class.forName(sysQuartz.getClazzname()), sysQuartz.getCron(),sysQuartz.getParams());
                }
                return ResultInfo.ofData(ResultCode.SUCCESS,1);
            }else {
                sysQuartz.setRunning(0);
                int num = this.sysQuzrtzService.updateByPrimaryKeySelective(sysQuartz);
                if( num > 0){
                    QuartzManager.removeJob(scheduler, sysQuartz.getQuartzname()+"_"+sysQuartz.getId());
                }
                return ResultInfo.ofData(ResultCode.SUCCESS,0);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  ResultInfo.of(ResultCode.FAILED);
        }
    }
    /**
     * 修改任务执行时间
     * @param id 任务id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTH','UPDATE_AUTH')")
    @PutMapping("/quartzCron/{id}")
    @ResponseBody
    public ResultInfo updateQuartzCron(@PathVariable("id") String id,String cron) {
        try{
            if (!CronExpression.isValidExpression(cron)) {//校验cron表达式
                return  ResultInfo.of(ResultCode.CRON_ERROR);
            }else{
                SysQuartz sysQuartz = this.sysQuzrtzService.selectByPrimaryKey(Long.parseLong(id));
                sysQuartz.setCron(cron);
                int num = this.sysQuzrtzService.updateByPrimaryKeySelective(sysQuartz);
                if( num > 0 && sysQuartz.getRunning() == 1){//任务状态是启动则修改执行时间
                    QuartzManager.modifyJobTime(scheduler, sysQuartz.getQuartzname()+"_"+sysQuartz.getId(), null, cron);
                }
                return  ResultInfo.of(ResultCode.SUCCESS);
            }

        }catch (Exception e){
            e.printStackTrace();
            return  ResultInfo.of(ResultCode.FAILED);
        }
    }

}
