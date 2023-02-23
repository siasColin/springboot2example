package cn.net.colin.common.aspect;

import cn.net.colin.mapper.quartzManage.SysQuzrtzMapper;
import cn.net.colin.model.quartzManage.SysQuartz;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author SXF
 * @version 1.0
 * @description: TODO
 * @date 2023/2/23 13:49
 */
@Component
@Aspect
public class QuartzJobAspect {
    @Resource
    private SysQuzrtzMapper sysQuzrtzMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* cn.net.colin.quartz.job..*(..))")
    public void aspect() {
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        JobExecutionContext context = (JobExecutionContext) args[0];
        String jobName = context.getJobDetail().getKey().getName();
        /**更新数据库，记录任务开始时间 start*/
        String jobId = jobName.split("_")[1];
        SysQuartz sysQuartz = new SysQuartz();
        sysQuartz.setId(Long.parseLong(jobId));
        sysQuartz.setExp1(formatter.format(LocalDateTime.now()));
        sysQuartz.setExp2("");
        sysQuzrtzMapper.updateByPrimaryKeySelective(sysQuartz);
        /**更新数据库，记录任务开始时间 end*/
        Object result = pjp.proceed();
        /**更新数据库，记录任务完成时间 start*/
        sysQuartz.setExp2(formatter.format(LocalDateTime.now()));
        sysQuzrtzMapper.updateByPrimaryKeySelective(sysQuartz);
        /**更新数据库，记录任务完成时间 end*/
        return result;
    }
}
