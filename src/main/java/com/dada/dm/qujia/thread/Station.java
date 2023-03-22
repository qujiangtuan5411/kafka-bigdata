package com.dada.dm.qujia.thread;

import com.dada.dm.qujia.distributedlock.RedisLock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author jt.Qu
 * @description 买票
 * @program: kafka-demo
 * @date 2023-03-16 11:15
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station extends Thread{

    private RedisTemplate redisTemplate;

    static int tick = 20;
    static Object ob = new Object();

    public Station(String name) {
        super(name);
    }

    public Station(String name,RedisTemplate redisTemplate) {
        super(name);
        this.redisTemplate = redisTemplate;
    }

//    @Override
//    public void run(){
//        while (tick > 0){
//            if(tick >0 ){
//                log.info("{}卖出了第{} 张票",getName(),tick);
//                tick--;
//            }else{
//                log.info("票卖卖完了");
//            }
//            try {
//                //休息一秒
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @Override
//    public void run(){
//        while (tick > 0){
//            if(tick >0 ){
//                synchronized (ob){
//                    log.info("{}卖出了第{} 张票",getName(),tick);
//                    tick--;
//                }
//            }else{
//                log.info("票卖卖完了");
//            }
//            try {
//                //休息一秒
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @Override
    public void run(){
        while (tick > 0){
            if(tick >0 ){
                try (RedisLock redisLock = new RedisLock(redisTemplate,"redisKey",30)){
                    if (redisLock.getLock()) {
                        log.info("{}进入了锁！卖出了第{} 张票",getName(),tick);
                        tick--;
                        redisLock.unLock();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                log.info("票卖卖完了");
            }
            try {
                //休息一秒
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Station("线程1").start();
        new Station("线程2").start();
        new Station("线程3").start();
        new Station("线程4").start();
    }



}
