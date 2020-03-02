package cn.net.colin.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Package: cn.net.colin.common.config
 * @Author: sxf
 * @Date: 2020-3-2
 * @Description: 异步任务配置类，例如某个方法标注了 @Async("sentinelSimpleAsync")
 */
@Configuration
@EnableAsync
public class ExecutorConfiguration {
    @Value("${executor.pool.core.size}")
    private int corePoolSize;
    @Value("${executor.pool.max.size}")
    private int maxPoolSize;
    @Value("${executor.queue.capacity}")
    private int queueCapacity;

    @Bean
    public Executor sentinelSimpleAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("SentinelSimpleExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor sentinelAsync() {
        /**
         * 当一个任务通过execute(Runnable)方法欲添加到线程池时：
         * 1、 如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
         * 2、 如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
         * 3、如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
         * 4、 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程 maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
         * 5、 当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数
         */
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程池维护线程的最少数量
        executor.setCorePoolSize(corePoolSize);
        //线程池维护线程的最大数量
        executor.setMaxPoolSize(maxPoolSize);
        //线程池所使用的缓冲队列
        executor.setQueueCapacity(queueCapacity);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(30000);
        executor.setThreadNamePrefix("SentinelSwapExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
