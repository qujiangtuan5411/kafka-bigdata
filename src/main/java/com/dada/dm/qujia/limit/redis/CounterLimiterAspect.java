package com.dada.dm.qujia.limit.redis;

import com.alibaba.fastjson.JSON;
import com.dada.dm.qujia.exception.DwException;
import com.dada.dm.qujia.exception.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jt.Qu
 * @description 注解实现拦截器
 * @program: kafka-demo
 * @date 2023-03-13 20:30
 */
@Slf4j
@Aspect
@Component
public class CounterLimiterAspect {

    @Autowired
    private RedisTemplate redisTemplate;


    @Pointcut(value = "@annotation(com.dada.dm.qujia.limit.redis.CounterLimit)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CounterLimit counterLimit = method.getAnnotation(CounterLimit.class);
        String token = counterLimit.name();
        long timeout = counterLimit.timeout();
        if (StringUtils.isEmpty(token)) {
            Object target = joinPoint.getTarget();
            Object[] args = joinPoint.getArgs();
            //获取登陆用户
//            Method getCurrentUserId = target.getClass().getSuperclass().getDeclaredMethod("getCurrentUserId");
//            getCurrentUserId.setAccessible(true);
//            Object userId = getCurrentUserId.invoke(target, null);
            Long userId = 12945L;
            String paramStr = Arrays.stream(args).filter(e -> !(e instanceof HttpServletResponse)).map(JSON::toJSONString).collect(Collectors.joining());
            String md5 = DigestUtils.md5Hex(paramStr);
            String classAndMethodName = target.getClass().getSimpleName() + "-" + signature.getName();
//        TestController-test01-f02862ff3b8d6fdca0a659bbd9ef5a36-11366
            token = classAndMethodName + "-" + md5 + "-" + userId;
        }
        // 判断方法是否包含CounterLimit，有这个注解就需要进行限速操作
        BoundValueOperations<String, Integer> boundGeoOperations = redisTemplate.boundValueOps(token);
        // 如果用户身份唯一key为空，直接返回错误
        if (checkLimiter(token, counterLimit, timeout)) {
            Long expire = boundGeoOperations.getExpire();
            log.info("expire: {}", expire);
            log.info("正常访问达到访问");
            // 否则告知调用方达到限速上线
        } else {
            Long expire = boundGeoOperations.getExpire();
            log.info("expire: {}", expire);
            log.info("达到访问次数限制，禁止访问");
            throw new DwException(ResponseCodeEnum.ACCESS_ERROR);
        }
    }


    /**
     * 限速校验
     */
    private Boolean checkLimiter(String token, CounterLimit annotation, long timeout) {
        BoundValueOperations<String, Integer> boundGeoOperations = redisTemplate.boundValueOps(token);
        Integer count = boundGeoOperations.get();
        if (Objects.isNull(count)) {
            redisTemplate.boundValueOps(token).set(1, annotation.timeout(), annotation.timeUnit());
        } else if (count >= annotation.limitTimes()) {
            return Boolean.FALSE;
        } else if (boundGeoOperations.getExpire() <= 0) {
            redisTemplate.boundValueOps(token).set(count + 1, timeout, annotation.timeUnit());
        } else {
            redisTemplate.boundValueOps(token).set(count + 1, boundGeoOperations.getExpire(), annotation.timeUnit());
        }
        return Boolean.TRUE;
    }


}
