package cn.net.colin.quartz.util;

import cn.net.colin.common.util.JsonUtils;
import org.quartz.*;

import java.util.Map;
import java.util.Set;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Quartz调度管理器  
 * @author sxf
 *
 */
public class QuartzManager {  
    private static String JOB_GROUP_NAME = "JOB_GROUP_SYSTEM";  
    private static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_SYSTEM";  
  
    /** 
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
     *  
     * @param sched 
     *            调度器 
     *  
     * @param jobName 
     *            任务名 
     * @param cls 
     *            任务 
     * @param time 
     *            时间设置，参考quartz说明文档 
     *  
     * @Title: QuartzManager.java 
     */  
    public static void addJob(Scheduler sched, String jobName,
                              Class cls, String time, String params) {
        try {  
            JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);// 任务名，任务组，任务执行类
            JobDetail jobDetail = newJob(cls).withIdentity(jobKey).build();
            if (params != null && !params.equals("")) {
                Map<String,Object> paramsMap = JsonUtils.toMap(params,String.class,Object.class);
				Set<String> keyset = paramsMap.keySet();
				JobDataMap datamap = jobDetail.getJobDataMap();
				for (String key : keyset) {
					datamap.put(key, paramsMap.get(key));
				}
			}
            TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);// 触发器
            Trigger trigger = newTrigger().withIdentity(triggerKey)
                    .withSchedule(cronSchedule(time)).build();// 触发器时间设定  
            sched.scheduleJob(jobDetail, trigger);  
            if (!sched.isShutdown()) {  
                sched.start();// 启动  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * @Description: 添加一个定时任务 
     *  
     * @param sched 
     *            调度器 
     *  
     * @param jobName 
     *            任务名 
     * @param jobGroupName 
     *            任务组名 
     * @param triggerName 
     *            触发器名 
     * @param triggerGroupName 
     *            触发器组名 
     * @param jobClass 
     *            任务 
     * @param time 
     *            时间设置，参考quartz说明文档 
     *  
     * @Title: QuartzManager.java 
     */  
    public static void addJob(Scheduler sched, String jobName,
                              String jobGroupName, String triggerName, String triggerGroupName,
                              Class jobClass, String time, String params) {
        try {  
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobKey).build();
            if (params != null && !params.equals("")) {
                Map<String,Object> paramsMap = JsonUtils.toMap(params,String.class,Object.class);
                Set<String> keyset = paramsMap.keySet();
                JobDataMap datamap = jobDetail.getJobDataMap();
                for (String key : keyset) {
                    datamap.put(key, paramsMap.get(key));
                }
			}
            // 触发器  
            TriggerKey triggerKey = new TriggerKey(triggerName,
                    triggerGroupName);  
            Trigger trigger = newTrigger().withIdentity(triggerKey)
                    .withSchedule(cronSchedule(time)).build();  
            sched.scheduleJob(jobDetail, trigger);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
     *  
     * @param sched 
     *            调度器 
     * @param oldjobName
     * @param newjobname
     * @param time
     *  
     * @Title: QuartzManager.java 
     */  
    public static void modifyJobTimebyparams(Scheduler sched, String oldjobName, String newjobname,
                                             String time, String params) {
        try {  
            TriggerKey triggerKey = new TriggerKey(oldjobName, TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {  
                return;  
            }  
//            String oldTime = trigger.getCronExpression();  
            JobKey jobKey = new JobKey(oldjobName, JOB_GROUP_NAME);
            JobDetail jobDetail = sched.getJobDetail(jobKey);
            Class objJobClass = jobDetail.getJobClass();  
            removeJob(sched, oldjobName);  
            addJob(sched, newjobname, objJobClass, time,params);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * @Description: 修改一个任务的触发时间 
     *  
     * @param sched 
     *            调度器 * 
     * @param sched 
     *            调度器 
     * @param triggerName 
     * @param triggerGroupName 
     * @param time 
     *  
     * @Title: QuartzManager.java 
     */  
    public static void modifyJobTime(Scheduler sched, String triggerName,
                                     String triggerGroupName, String time) {
    	if (triggerGroupName == null ||(triggerGroupName != null && triggerGroupName.trim().equals(""))) {
    		triggerGroupName = TRIGGER_GROUP_NAME;
		}
        try {  
            TriggerKey triggerKey = new TriggerKey(triggerName,
                    triggerGroupName);  
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(time)) {  
                // 修改时间  
            	trigger = trigger.getTriggerBuilder().withSchedule(cronSchedule(time)).build(); 
                // 重启触发器  
//                sched.resumeTrigger(triggerKey);  
            	sched.rescheduleJob(triggerKey, trigger);
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     *  
     * @param sched 
     *            调度器 
     * @param jobName 
     *  
     * @Title: QuartzManager.java 
     */  
    public static void removeJob(Scheduler sched, String jobName) {
        try {  
            TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
            sched.pauseTrigger(triggerKey);// 停止触发器  
            sched.unscheduleJob(triggerKey);// 移除触发器  
            JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
            sched.deleteJob(jobKey);// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * @Description: 移除一个任务 
     *  
     * @param sched 
     *            调度器 
     * @param jobName 
     * @param jobGroupName 
     * @param triggerName 
     * @param triggerGroupName 
     *  
     * @Title: QuartzManager.java 
     */  
    public static void removeJob(Scheduler sched, String jobName,
                                 String jobGroupName, String triggerName, String triggerGroupName) {
        try {  
            TriggerKey triggerKey = new TriggerKey(triggerName,
                    triggerGroupName);  
            sched.pauseTrigger(triggerKey);// 停止触发器  
            sched.unscheduleJob(triggerKey);// 移除触发器  
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            sched.deleteJob(jobKey);// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * @Description:启动所有定时任务 
     *  
     * @param sched 
     *            调度器 
     *  
     * @Title: QuartzManager.java 
     */  
    public static void startJobs(Scheduler sched) {
        try {  
            sched.start();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * @Description:关闭所有定时任务 
     *  
     * @param sched 
     *            调度器 
     *  
     */  
    public static void shutdownJobs(Scheduler sched) {
        try {  
            if (!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
}  
