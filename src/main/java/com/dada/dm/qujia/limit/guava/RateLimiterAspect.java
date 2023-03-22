//package com.dada.dm.qujia.limit.guava;
//
//import com.google.common.base.Strings;
//import com.google.common.util.concurrent.RateLimiter;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.function.Function;
//
///**
// * @author jt.Qu
// * @description Guava 限流切面
// * @program: kafka-demo
// * @date 2023-03-22 10:19
// */
//@Aspect
//@Component
//@Slf4j
//public class RateLimiterAspect {
//
//    public interface KeyFactory {
//        String createKey(JoinPoint jp, RateLimit limit);
//    }
//
//    private static final KeyFactory DEFAULT_KEY_FACTORY = (jp, limit) -> JoinPointToStringHelper.toString(jp);
//
//    private final ConcurrentHashMap<String, RateLimiter> limiters;
//    private final KeyFactory keyFactory;
//
//    @Autowired
//    public RateLimiterAspect(Optional<KeyFactory> keyFactory) {
//        this.limiters = new ConcurrentHashMap<>();
//        this.keyFactory = keyFactory.orElse(DEFAULT_KEY_FACTORY);
//    }
//
//    @Before("@annotation(limit)")
//    public void rateLimit(JoinPoint jp, RateLimit limit) {
//        String key = createKey(jp, limit);
//        RateLimiter limiter = limiters.computeIfAbsent(key, createLimiter(limit));
//        double delay = limiter.acquire();
//        log.info("Acquired rate limit permission ({} qps) for {} in {} seconds", limiter.getRate(), key, delay);
//    }
//
//    private Function<String, RateLimiter> createLimiter(RateLimit limit) {
//        return name -> RateLimiter.create(limit.value());
//    }
//
//    private String createKey(JoinPoint jp, RateLimit limit) {
//        return Optional.ofNullable(Strings.emptyToNull(limit.key()))
//                .orElseGet(() -> keyFactory.createKey(jp, limit));
//    }
//}
