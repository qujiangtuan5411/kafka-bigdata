package com.dada.dm.qujia.limit.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author jt.Qu
 * @description 计数器限流注解
 * @program: kafka-demo
 * @date 2023-03-13 20:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CounterLimit {
    /**
     * 调用方唯一key的名字
     *
     * @return
     */
    String name();

    /**
     * 限制访问次数
     *
     * @return
     */
    int limitTimes() default 5;

    /**
     * 限制时长，也就是计数器的过期时间
     *
     * @return
     */
    long timeout() default 60;;

    /**
     * 限制时长单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
