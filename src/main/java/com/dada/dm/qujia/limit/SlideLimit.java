//package com.dada.dm.qujia.limit;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import redis.clients.jedis.Jedis;
//
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * @author jt.Qu
// * @description 滑动窗口限流
// * @program: kafka-demo
// * @date 2023-03-13 19:04
// */
//@Slf4j
//public class SlideLimit {
//
//    private static final Long SYSTEM_MAX_COUNT = 10L;
//
//    public static void main(String[] args) {
//        Jedis jedis = new Jedis("172.16.25.58", 6000);
//
//        ReentrantLock reentrantLock = new ReentrantLock();
//        for (int i = 1; i < 15; i++) {
//            //模拟一个时间窗口 60s ，分为 5个格子，每个格子 12秒
//            new Thread(new SildeThread(jedis, "slide_test", 12 * 1000L, reentrantLock)).start();
//        }
//    }
//
//    @Data
//    static class SildeThread implements Runnable {
//
//        private final Jedis jedis;
//        private final String key;
//        private final Long gridSize;
//        private final ReentrantLock lock;
//
//        public SildeThread(Jedis jedis, String key, Long gridSize, ReentrantLock lock) {
//            this.jedis = jedis;
//            this.key = key;
//            this.gridSize = gridSize;
//            this.lock = lock;
//        }
//
//
//        @Override
//        public void run() {
//            lock.lock();
//            try {
//                long nowTime = System.currentTimeMillis();
//                //清空当前时间窗口中的请求数据（相当于每次向右移动一个格子）
//                jedis.zremrangeByScore(key, 0, nowTime - gridSize);
//
//                //当前时间窗口请求数量
//                Long currentCount = jedis.zcard(key);
//                if (currentCount > SYSTEM_MAX_COUNT) {
//                    System.out.println(Thread.currentThread().getName() + "\t 超出最大系统负载，执行限流");
//                }
//
//                jedis.zadd(key, nowTime, String.valueOf(nowTime));
//                System.out.println(Thread.currentThread().getName() + "\t正常执行");
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("error ->{}", e.getMessage());
//            } finally {
//                lock.unlock();
//            }
//
//        }
//    }
//
//}
