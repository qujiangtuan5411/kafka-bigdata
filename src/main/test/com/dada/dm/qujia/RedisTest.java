package com.dada.dm.qujia;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author jt.Qu
 * @description redis 测试类
 * @program: dw-bpt
 * @date 2021-09-06 11:48
 */
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class RedisTest {
//
    @Autowired
    private RedisTemplate stringRedisTemplate;

    @Test
    public void redisTemplate() throws Exception {
        System.out.println("--------------测试开始------------------");
        stringRedisTemplate.opsForValue().set("author", "qujiangtuan");
        if(stringRedisTemplate.hasKey("author")){
            System.out.println("-------------是否存在key : "+stringRedisTemplate.hasKey("author") +"------------------");
            System.out.println("-------------打印key的value值 : "+stringRedisTemplate.opsForValue().get("author") +"------------------");
            stringRedisTemplate.delete("author");
            System.out.println("--------------删除成功！------------------");
            System.out.println("-------------是否存在key : "+stringRedisTemplate.hasKey("author") +"------------------");
        }
    }
}
