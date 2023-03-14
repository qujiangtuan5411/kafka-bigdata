//package com.dada.dm.qujia.interceptor;
//
//import com.dada.dm.qujia.limit.redis.CounterLimiterHandlerInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author jt.Qu
// * @description
// * @program: kafka-demo
// * @date 2023-03-13 20:33
// */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Autowired
//    private CounterLimiterHandlerInterceptor counterLimiterHandlerInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 计数器限速
//        registry.addInterceptor(counterLimiterHandlerInterceptor).addPathPatterns("/**");
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
//}
