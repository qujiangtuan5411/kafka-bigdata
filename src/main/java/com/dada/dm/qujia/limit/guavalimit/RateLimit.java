package com.dada.dm.qujia.limit.guavalimit;


import org.springframework.beans.factory.annotation.Required;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author jt.Qu
 * @description 自定义注解，用于接口的限流
 * @program: kafka-demo
 * @date 2023-03-22 15:24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    /**
     * 限流器名称，如果不设置，默认是类名加方法名。如果多个接口设置了同一个名称，那么使用同一个限流器
     *
     * @return
     */
    String name() default "";

    /**
     * 一秒内允许通过的请求数QPS
     *
     * @return
     */
//    @Required
    String permitsPerSecond();

    /**
     * 获取令牌超时时间
     *
     * @return
     */
    String timeout() default "0";

    /**
     * 获取令牌超时时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
