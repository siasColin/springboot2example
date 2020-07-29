package cn.net.colin.quartz;
import cn.net.colin.model.quartzManage.SysQuartz;
import cn.net.colin.quartz.util.QuartzManager;
import cn.net.colin.service.quartzManage.ISysQuzrtzService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: cn.net.colin.quartz
 * @Author: sxf
 * @Date: 2020-3-28
 * @Description: 项目启动时加载quartz的定时任务
 */
@Component
public class InitTask implements ApplicationRunner {
    private final static Logger logger = LoggerFactory.getLogger(InitTask.class);

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ISysQuzrtzService sysQuzrtzService;
    @Value("${quartzWorkID}")
    private String quartzWorkID;

    @Override
    public void run(ApplicationArguments var) throws Exception{
        Map<String,Object> quartzParams = new HashMap<String,Object>();
        quartzParams.put("running","1");//查询状态为“启动”的任务
        quartzParams.put("quartzWorkID",quartzWorkID);//只查询当前服务的任务
        List<SysQuartz> sysQuartzList = sysQuzrtzService.selectByParamsWithBlobs(quartzParams);
        if(sysQuartzList != null && sysQuartzList.size() > 0){
            for(int i=0;i<sysQuartzList.size();i++){
                SysQuartz sysQuzrtz = sysQuartzList.get(i);
                try{ //一个任务添加失败，不影响其他任务
                    QuartzManager.addJob(scheduler, sysQuzrtz.getQuartzname()+"_"+sysQuzrtz.getId(), Class.forName(sysQuzrtz.getClazzname()), sysQuzrtz.getCron(),sysQuzrtz.getParams());
                }catch (Exception e){
                    logger.error("添加任务失败："+sysQuzrtz.toString());
                    e.printStackTrace();
                }
            }
        }
    }

}
