package com.dada.dm.qujia.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author jt.Qu
 * @description 谷歌guava限流 : 令牌桶算法
 * @program: kafka-demo
 * @date 2023-03-13 20:04
 */
public class GuavaLimit {

    public static void main(String[] args) throws InterruptedException {
        // qps 2
        RateLimiter rateLimiter = RateLimiter.create(2);
        for (int i = 0; i < 10; i++) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
            System.out.println(time + ":" + rateLimiter.tryAcquire());
            Thread.sleep(250);
        }
    }

//    void resync(long nowMicros) { // 当前微秒时间
//        // 当前时间是否大于下一个令牌生成时间
//        if (nowMicros > this.nextFreeTicketMicros) {
//            // 可生成的令牌数 newPermits = （当前时间 - 下一个令牌生成时间）/ 令牌生成时间间隔。
//            // 如果 QPS 为2，这里的 coolDownIntervalMicros 就是 500000.0 微秒(500ms)
//            double newPermits = (double)(nowMicros - this.nextFreeTicketMicros) / this.coolDownIntervalMicros();
//            // 更新令牌库存 storedPermits。
//            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + newPermits);
//            // 更新下一个令牌生成时间 nextFreeTicketMicros
//            this.nextFreeTicketMicros = nowMicros;
//        }
//    }

}
