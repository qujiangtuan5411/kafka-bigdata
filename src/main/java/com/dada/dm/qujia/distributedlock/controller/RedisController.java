package com.dada.dm.qujia.distributedlock.controller;

import com.dada.dm.qujia.distributedlock.RedisLock;
import com.dada.dm.qujia.thread.Station;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jt.Qu
 * @description redis 分布式锁
 * @program: kafka-demo
 * @date 2023-03-15 11:20
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * redis 分布式锁测试
     * @return
     */
    @RequestMapping("redisLock")
    public String redisLock(){
        log.info("我进入了方法！");
        try (RedisLock redisLock = new RedisLock(redisTemplate,"redisKey",30)){
            if (redisLock.getLock()) {
                log.info("我进入了锁！！");
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "方法执行完成";
    }

    /**
     * Redis分布式锁（setnx+lua）自动释放锁 卖票案例
     * @return
     */
    @RequestMapping("threadRedisLock")
    public String threadRedisLock(){
        for(int i =1 ;i<=5;i++){
            new Station("线程"+i,redisTemplate).start();
        }
        return "方法执行完成";
    }







}
