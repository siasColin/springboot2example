package cn.net.colin.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Package: cn.net.colin
 * @Author: sxf
 * @Date: 2020-8-19
 * @Description: 雪花算法生成全局唯一ID，取消dataCenterId ，workerId占10位即范围（0 ~ 1023）
 */
public class IdWorker {

    private volatile static IdWorker idWorker = null;

    /**
     * 开始时间 (2020-05-03)
     */
    private final long twepoch = 1588435200000L;

    /**
     * 机器ID占用的位数
     */
    private final long workerIdBits = 10L;

    /**
     *支持的最大机器ID，结果为1023（此移位算法可以快速计算出最大的小数
     *可以用几个二进制数字表示的数字）
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 序列在id中所占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID左12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 时间向左截取22位（10 + 12）
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits;

    /**
     * 生成序列掩码
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 机器ID（0〜1023）
     */
    private long workerId;

    /**
     * 序列以毫秒为单位（0〜4095）
     */
    private long sequence = 0L;

    /**
     * 上次ID生成的时间
     */
    private long lastTimestamp = -1L;

    /** id池，可一定程度上减小时间回拨造成的影响 */
    private static ConcurrentLinkedQueue<Long> idQueue;

    /**
     * Constructor
     * @param workerId
     *            Job ID (0 ~ 1023)
     */
    public IdWorker(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    /**
     * 获取下一个ID（该方法是线程安全的）
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
    }

    /**
     * 阻塞直到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp
     *            上次ID生成的时间
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回当前时间（以毫秒为单位）
     *
     * @return Current time (ms)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static IdWorker getInstance() {
        if (idWorker == null) {
            synchronized (IdWorker.class) {
                if (idWorker == null) {
                    init(initWorkerId());
                }
            }
        }
        return idWorker;
    }

    public static long initWorkerId() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!",e);
        }
        byte[] ipAddressByteArray = address.getAddress();
        return ((ipAddressByteArray[ipAddressByteArray.length - 2] & 0B11) << Byte.SIZE) + (ipAddressByteArray[ipAddressByteArray.length - 1] & 0xFF);
    }

    public static void init(Long serverNodeId) {
        System.out.println("serverNodeId:"+serverNodeId);
        if (idWorker == null) {
            synchronized (IdWorker.class) {
                if (idWorker == null) {
                    idWorker = new IdWorker(serverNodeId);
                    idQueue = new ConcurrentLinkedQueue<Long>();
                    for (int i = 0; i < 100000; i++) {// id池 初始化放入10W id,可以根据业务需求适当调整这个数值以减小时间回拨造成的影响
                        idQueue.offer(idWorker.nextId());
                    }
                }
            }
        }
    }
    public Long generateId(){
        try {
            idQueue.offer(idWorker.nextId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idQueue.poll();
    }

    public static void main(String[] args) {
        IdWorker instance = IdWorker.getInstance();
        for (int i = 0;i<10000;i++){
            System.out.println(instance.nextId());
        }
    }
}
