package com.dada.dm.qujia.limit.guavalimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author jt.Qu
 * @description 接口限流器
 * @program: kafka-demo
 * @date 2023-03-22 15:25
 */
public class EfRateLimiter {
    private RateLimiter rateLimiter;
    private long timeout;
    private TimeUnit timeUnit;

    public RateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public void setRateLimiter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public boolean tryAcquire() {
        return rateLimiter.tryAcquire(timeout, timeUnit);
    }

    public boolean tryAcquire(int permits) {
        return rateLimiter.tryAcquire(permits, timeout, timeUnit);
    }

}
