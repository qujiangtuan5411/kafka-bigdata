//package com.dada.dm.qujia.limit.redis;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.BoundValueOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.messaging.handler.HandlerMethod;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Objects;
//
///**
// * @author jt.Qu
// * @description 注解实现拦截器
// * @program: kafka-demo
// * @date 2023-03-13 20:30
// */
//@Component
//@Slf4j
//public class CounterLimiterHandlerInterceptor implements HandlerInterceptor {
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            // 判断方法是否包含CounterLimit，有这个注解就需要进行限速操作
//            if (handlerMethod.hasMethodAnnotation(CounterLimit.class)) {
//                CounterLimit annotation = handlerMethod.getMethod().getAnnotation(CounterLimit.class);
//                JSONObject result = new JSONObject();
//                String token = request.getParameter(annotation.name());
//                response.setContentType("text/json;charset=utf-8");
//                result.put("timestamp", System.currentTimeMillis());
//                BoundValueOperations<String, Integer> boundGeoOperations = redisTemplate.boundValueOps(token);
//                // 如果用户身份唯一key为空，直接返回错误
//                if (StringUtils.isEmpty(token)) {
//                    result.put("result", "token is invalid");
//                    response.getWriter().print(JSON.toJSONString(result));
//                    // 如果限速校验通过，则将请求放行
//                } else if (checkLimiter(token, annotation)) {
//                    result.put("result", "请求成功");
//                    Long expire = boundGeoOperations.getExpire();
//                    log.info("result：{}, expire: {}", result, expire);
//                    return true;
//                    // 否则告知调用方达到限速上线
//                } else {
//                    result.put("result", "达到访问次数限制，禁止访问");
//                    Long expire = boundGeoOperations.getExpire();
//                    log.info("result：{}, expire: {}", result, expire);
//                    response.getWriter().print(JSON.toJSONString(result));
//                }
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 限速校验
//     */
//    private Boolean checkLimiter(String token, CounterLimit annotation) {
//        BoundValueOperations<String, Integer> boundGeoOperations = redisTemplate.boundValueOps(token);
//        Integer count = boundGeoOperations.get();
//        if (Objects.isNull(count)) {
//            redisTemplate.boundValueOps(token).set(1, annotation.timeout(), annotation.timeUnit());
//        } else if (count >= annotation.limitTimes()) {
//            return Boolean.FALSE;
//        } else {
//            redisTemplate.boundValueOps(token).set(count + 1, boundGeoOperations.getExpire(), annotation.timeUnit());
//        }
//        return Boolean.TRUE;
//    }
//
//
//}
