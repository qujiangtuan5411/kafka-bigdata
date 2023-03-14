package com.dada.dm.qujia.controller;

import com.alibaba.fastjson.JSONObject;
import com.dada.dm.qujia.exception.ResponseCodeEnum;
import com.dada.dm.qujia.limit.redis.CounterLimit;
import com.dada.dm.qujia.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
public class RedisLimitTestController {

    @CounterLimit(name = "token",limitTimes = 3, timeout = 2, timeUnit = TimeUnit.SECONDS)
//    @CounterLimit(name = "token")
    @GetMapping("/limit/count-test")
    public Response<?> counterLimiter(String name, String token) {
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("token", token);
        return new Response<>(result, ResponseCodeEnum.SUCCESS);
    }
}
