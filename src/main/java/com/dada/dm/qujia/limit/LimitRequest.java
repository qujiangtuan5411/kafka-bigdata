package com.dada.dm.qujia.limit;


import redis.clients.jedis.Jedis;

/**
 * @author jt.Qu
 * @description
 * @program: kafka-demo
 * @date 2023-03-13 18:19
 */
public class LimitRequest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("172.16.25.58", 6000);
        jedis.del("PayRequest");
        //模拟发5个请求
        int cnt = 5;
        for (int i = 0; i < cnt; i++) {
            LimitUtil.canVisit(jedis, "PayRequest", 100, 10);
        }
    }
}

class LimitUtil {
    //判断是否需要限流
    public static void canVisit(Jedis jedis, String requestType, int limitTime, int limitNum) {
        long currentTime = System.currentTimeMillis();
        //把请求放入zset
        jedis.zadd(requestType, currentTime, Long.valueOf(currentTime).toString());
        //去掉时间范围外（超时）的请求
        jedis.zremrangeByScore(requestType, 0, currentTime - limitTime * 1000);
        //统计时间范围内总数
        Long count = jedis.zcard(requestType);
        //设置所有请求的超时时间
        jedis.expire(requestType, limitTime + 1);
        boolean flag = limitNum >= count;
        if (flag) {
            System.out.println("Can visit");
        } else {
            System.out.println("Can not visit");
        }
    }
}