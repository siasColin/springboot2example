package cn.net.colin.task.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class TestScheduled {
    Logger logger = LoggerFactory.getLogger(TestScheduled.class);

    @Autowired
    private JobBean jobBean;

    @Scheduled(cron = "0/3 * * * * ?")
    public void cronTask() {
        long timestamp = System.currentTimeMillis();
        try {
            Thread thread = Thread.currentThread();
            Thread.sleep(6000 * 10);
            logger.info("cron任务开始, timestamp={}, threadId={}, threadName={}", timestamp, thread.getId(), thread.getName());
            jobBean.dojob(thread.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Scheduled(fixedRate = 1000)
    public void rateTask() {
        long timestamp = System.currentTimeMillis();
        try {
            Thread thread = Thread.currentThread();
            logger.info("fixedRate任务开始, timestamp={}, threadId={}, threadName={}", timestamp, thread.getId(), thread.getName());
            jobBean.dojob(thread.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
