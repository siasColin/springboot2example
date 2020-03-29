package cn.net.colin.quartz;
import cn.net.colin.quartz.util.QuartzManager;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Component
public class InitTask implements ApplicationRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(InitTask.class);

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments var) throws Exception{
        ThreadPoolExecutor sendThreadPool = new ThreadPoolExecutor(20, 30,
                200, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        String params = "{\"name\":\"sxf\"}";
        //一个任务添加失败，不影响其他任务
        //QuartzManager.addJob(scheduler, "HelloJob", Class.forName("cn.net.colin.quartz.job.HelloJob"), "0/30 * * * * ?",params);
    }

}
