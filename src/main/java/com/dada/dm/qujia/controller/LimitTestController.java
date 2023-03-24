package com.dada.dm.qujia.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONObject;
import com.dada.dm.qujia.constant.ResourceConstant;
import com.dada.dm.qujia.exception.ResponseCodeEnum;
import com.dada.dm.qujia.limit.guavalimit.RateLimit;
import com.dada.dm.qujia.limit.redis.CounterLimit;
import com.dada.dm.qujia.model.Response;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author jt.Qu
 * @description
 * @program: kafka-demo
 * @date 2023-03-13 20:35
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class LimitTestController {

    /**
     * redis 限流
     *
     * @param name
     * @param token
     * @return
     */
    @CounterLimit(name = "token", limitTimes = 3, timeout = 2, timeUnit = TimeUnit.SECONDS)
//    @CounterLimit(name = "token")
    @GetMapping("/limit/count-test")
    public Response<?> counterLimiter(String name, String token) {
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("token", token);
        return new Response<>(result, ResponseCodeEnum.SUCCESS);
    }

    /**
     * 单机 guava 限流
     *
     * @return
     */
    @RateLimit(permitsPerSecond = "1")
    @GetMapping("/limit/rate-test")
    public Response<?> rateLimiter(String key) {
        JSONObject result = new JSONObject();
        result.put("key", key);
        return new Response<>(result, ResponseCodeEnum.SUCCESS);
    }

    /**
     * 分布式 sentinel 限流
     *
     * @return
     */
//    @GetMapping("/limit/sentinel-test")
//    public Response<?> sentinelLimiter(String key){
//        try (Entry entry = SphU.entry(ResourceConstant.SENTINEL_TEST)) {
//            log.info("user key :{}", key);
//        } catch (BlockException e) {
//            log.info("--接口访问太过频繁，请稍候再试!!!");
//            return new Response<>(ResponseCodeEnum.ACCESS_ERROR);
//        } finally {
//
//        }
//        JSONObject result = new JSONObject();
//        result.put("key", key);
//        return new Response<>(result, ResponseCodeEnum.SUCCESS);
//    }


    /**
     * 分布式 sentinel 限流
     *
     * @return
     */
    @GetMapping("/limit/sentinel-test")
    @SentinelResource(value = ResourceConstant.SENTINEL_TEST, blockHandler = "flowBlockHandler")
    public Response<?> sentinelLimiter(String key) throws InterruptedException {
        log.info("user key :{}", key);
        Thread.sleep(50);

        JSONObject result = new JSONObject();
        result.put("key", key);
        return new Response<>(result, ResponseCodeEnum.SUCCESS);
    }

    /**
     * 返回值 请求参数要和源方法一致，注意添加的是BlockException 不是BlockedException
     */
    public Response<?> flowBlockHandler(String key,BlockException e) {
        log.info("flow 流控了->{}",e.getClass().getSimpleName());
        return new Response<>(ResponseCodeEnum.ACCESS_ERROR);
    }


    /**
     * 热点规则
     * @param id
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/limit/getOrder/{id}")
    @SentinelResource(value = ResourceConstant.GET_ORDER, blockHandler = "flowBlockHandler2")
    public Response<?> getOrder(@PathVariable("id") Long id) throws InterruptedException {
        log.info("order id :{}", id);
        Thread.sleep(50);

        JSONObject result = new JSONObject();
        result.put("id", id);
        return new Response<>(result, ResponseCodeEnum.SUCCESS);
    }

    /**
     * 返回值 请求参数要和源方法一致，注意添加的是BlockException 不是BlockedException
     */
    public Response<?> flowBlockHandler2(Long id,BlockException e) {
        log.info("flow 流控了->{}",e.getClass().getSimpleName());
        return new Response<>(ResponseCodeEnum.ACCESS_ERROR);
    }

}
