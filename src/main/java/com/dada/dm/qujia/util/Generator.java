package com.dada.dm.qujia.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * twitter snowflake分布式id的java实现<br>
 */
public class Generator {

    /**
     * TWEPOCH
     */
    private static final long TWEPOCH = 1546272000000L;
    /**
     * 生成器标识位数;可支持同一网段(0-255)的所有机器同时集群
     */
    private static final long GENERATOR_ID_BITS = 8L;
    /**
     * 生成器ID最大值
     */
    private static final long MAX_GENERATOR_ID = -1L ^ -1L << GENERATOR_ID_BITS;
    /**
     * 毫秒内自增位
     */
    private static final long SEQUENCE_BITS = 10L;
    /**
     * 生成器ID偏左移10位
     */
    private static final long GENERATOR_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 时间毫秒左移18位
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + GENERATOR_ID_BITS;
    /**
     * SEQUENCE_MASK
     */
    private static final long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;
    //注意申请时为类属性
    private Lock lock = new ReentrantLock();
    /**
     * 生成器ID
     */
    private long generatorId;

    /**
     * sequence
     */
    private long sequence = 0L;

    /**
     * lastTimestamp
     */
    private long lastTimestamp = -1L;

    /**
     * 默认构造函数
     */
    private Generator() {

    }

    /**
     * 构造函数
     *
     * @param generatorId 生成器ID
     */
    public Generator(long generatorId) {
        if (generatorId > MAX_GENERATOR_ID || generatorId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_GENERATOR_ID));
        }
        this.generatorId = generatorId;
    }

    /**
     * 获取下一个id值
     *
     * @return 下一个id值
     */
    public synchronized long nextId() {
        lock.lock();
        try {
            long timestamp = this.timeGen();
            if (this.lastTimestamp == timestamp) {
                this.sequence = (this.sequence + 1) & SEQUENCE_MASK;
                if (this.sequence == 0) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0;
            }
            if (timestamp < this.lastTimestamp) {
                try {
                    throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            this.lastTimestamp = timestamp;
            long nextId = ((timestamp - TWEPOCH << TIMESTAMP_LEFT_SHIFT)) | (this.generatorId << GENERATOR_ID_SHIFT) | (this.sequence);
            return nextId;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 等待下一个毫秒的到来
     *
     * @param lastTimestamp lastTimestamp
     * @return long
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

}
