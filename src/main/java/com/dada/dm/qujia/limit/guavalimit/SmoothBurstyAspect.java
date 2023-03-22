package com.dada.dm.qujia.limit.guavalimit;


import com.dada.dm.qujia.exception.DwException;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author jt.Qu
 * @description 接口限流切面
 * @program: kafka-demo
 * @date 2023-03-22 15:27
 */
@Aspect
@Component
@Slf4j
public class SmoothBurstyAspect {

    private static final Map<String, EfRateLimiter> EF_RATE_LIMITER_MAP = new ConcurrentHashMap<>();

    @Pointcut(value = "@annotation(com.dada.dm.qujia.limit.guavalimit.RateLimit)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        String rateLimitName = className + "-" + methodName;

        EfRateLimiter rateLimiter = this.getRateLimiter(rateLimitName, rateLimit);
        boolean success = rateLimiter.tryAcquire();
        Object[] args = joinPoint.getArgs();
        if (success) {
            return joinPoint.proceed(args);
        } else {
            log.error("className > {}.{}(), rate limiting, parameters[{}]", className, methodName, args);
            throw new DwException(-1, "接口访问太过频繁，请稍候再试");
        }
    }

    private EfRateLimiter getRateLimiter(String key, RateLimit rateLimit) {
        EfRateLimiter efRateLimiter = EF_RATE_LIMITER_MAP.get(key);
        if (efRateLimiter == null) {
            synchronized (this) {
                if ((efRateLimiter = EF_RATE_LIMITER_MAP.get(key)) == null) {
                    efRateLimiter = new EfRateLimiter();
                    RateLimiter rateLimiter = RateLimiter.create(Double.valueOf(rateLimit.permitsPerSecond()));
                    String timeoutStr = rateLimit.timeout();
                    long timeout = 0L;
                    if (StringUtils.isNotEmpty(timeoutStr)) {
                        timeout = Math.max(Integer.valueOf(timeoutStr), 0L);
                    }
                    TimeUnit timeUnit = rateLimit.timeUnit();

                    efRateLimiter.setRateLimiter(rateLimiter);
                    efRateLimiter.setTimeout(timeout);
                    efRateLimiter.setTimeUnit(timeUnit);
                    EF_RATE_LIMITER_MAP.putIfAbsent(key, efRateLimiter);
                }
            }
        }

        return efRateLimiter;
    }
}
