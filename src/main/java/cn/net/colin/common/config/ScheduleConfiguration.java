package cn.net.colin.common.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Package: cn.net.colin.common.config
 * @Author: sxf
 * @Date: 2020-3-2
 * @Description: 调度任务配置类，这里主要是设置线程池的大小，实现定时调度的并行化。
 */
@Configuration
@EnableScheduling
public class ScheduleConfiguration implements SchedulingConfigurer, AsyncConfigurer {

    /** 异步处理 */
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar){
        TaskScheduler taskScheduler = taskScheduler();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    /** 定时任务多线程处理 */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //设置线程池容量
        scheduler.setPoolSize(20);
        //线程名前缀
        scheduler.setThreadNamePrefix("task-");
        //当调度器shutdown被调用时等待当前被调度的任务完成
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        //等待时常
        scheduler.setAwaitTerminationSeconds(60);
        return scheduler;
    }

    /** 异步处理 */
    public Executor getAsyncExecutor(){
        Executor executor = taskScheduler();
        return executor;
    }

    /** 异步处理 异常 */
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}

