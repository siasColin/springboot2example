package cn.net.colin.quartz.job;

import cn.net.colin.model.sysManage.SysArea;
import cn.net.colin.service.sysManage.ISysAreaService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Package: cn.net.colin.quartz.job
 * @Author: sxf
 * @Date: 2020-3-28
 * @Description: Job 的实例要到该执行它们的时候才会实例化出来。每次 Job 被执行，一个新的 Job 实例会被创建。
 * 其中暗含的意思就是你的 Job 不必担心线程安全性，因为同一时刻仅有一个线程去执行给定 Job 类的实例，甚至是并发执行同一 Job 也是如此。
 * @DisallowConcurrentExecution 保证上一个任务执行完后，再去执行下一个任务
 */
@DisallowConcurrentExecution
public class HelloJobTest implements Job, Serializable {

    private static Logger logger = LoggerFactory.getLogger(HelloJobTest.class);
    @Autowired
    private ISysAreaService sysAreaService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String jobName = context.getJobDetail().getKey().getName();
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            logger.info("【HelloJobTest】"+jobName+"执行时间: " + format.format(new Date())+"  name:"+dataMap.get("name"));
            List<SysArea> areaList =  sysAreaService.selectAll();
//            logger.info(JSON.toJSONString(areaList));
            Thread.sleep(1000 * 60);
            logger.info("【HelloJobTest】"+jobName+"================执行完成========================" + format.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
