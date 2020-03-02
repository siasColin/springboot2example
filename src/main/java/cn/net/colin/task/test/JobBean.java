package cn.net.colin.task.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Package: cn.net.colin.task.common
 * @Author: sxf
 * @Date: 2020-3-2
 * @Description:
 */
@Component
public class JobBean {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("sentinelSimpleAsync")
    public void dojob(String name) throws InterruptedException {
        long timestamp = System.currentTimeMillis();
        Thread thread = Thread.currentThread();
        Thread.sleep(3000);
        logger.info("doAuditCollect...run, timestamp={}, threadId={}, threadName={},name={}", timestamp, thread.getId(), thread.getName(),name);
    }
}
