package cn.net.colin.common.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Package: cn.net.colin.common.helper
 * @Author: sxf
 * @Date: 2020-4-18
 * @Description: 使用
 *                 boolean lock = redisLock.lock("SellTicktLock",5,10000);
 *                 try{
 *                      if(lock){
 *                          try{
 *                              //获取锁成功，执行业务处理
 *                          }finally {
 *                               //只有获取锁成功才执行释放锁，如果释放锁的操作放到if(lock)之外，可能会造成if(lock)内的业务处理没有执行完;
 *                               //而另外一个线程获取锁失败执行了释放锁操作，然后再有一个线程又获取到了锁，进入if(lock) 此时if(lock)内有两个线程。
 *                               //当然，如果if(lock)内部业务处理时间超过了锁的失效时间（expireMsecs），也会出现if(lock)内有多个线程；所以要根据实际情况设置合理的失效时间以及获取锁超时时间。
 *                              redisLock.unlock("SellTicktLock");
 *                          }
 *                      }else{
 *                          //获取锁失败
 *                      }
 *                 }
 */
@Component
public class RedisLock {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 重试时间
     */
    private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 100;
    /**
     * 锁对应的值，无意义，写为1
     */
    private static final String value = "1";
    /**
     * 锁的后缀
     */
    private static final String LOCK_SUFFIX = "_redis_lock";


    /**
     * 获取锁
     * @param key 锁的键
     * @param expireMsecs 锁失效时间，防止线程在入锁以后没有释放锁，阻塞后面的线程无法获取锁
     * @param timeoutMsecs 线程获取锁的等待时间，超过等待时间则获取锁失败
     * @return
     */
    public boolean lock(String key,int expireMsecs,long timeoutMsecs) {
        key = key + LOCK_SUFFIX;
        long timeout = timeoutMsecs;
        while (timeout > 0){
            // 尝试获取锁
            Boolean boo = redisTemplate.opsForValue().setIfAbsent(key, value, expireMsecs, TimeUnit.SECONDS);
            // 判断结果
            if(boo != null && boo){
                return  true;
            }else{
                timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
                // 延时
                try {
                    Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * @param key 锁的key
     * 释放获取到的锁
     */
    public void unlock(String key) {
        key = key + LOCK_SUFFIX;
        redisTemplate.delete(key);
    }



}
