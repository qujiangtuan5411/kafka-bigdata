package com.dada.dm.qujia.controller;

import com.dada.dm.qujia.model.Response;
import com.dada.dm.qujia.model.ResponseSupport;
import com.dada.dm.qujia.util.KafkaUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * kafka控制器
 *
 * @author 154594742@qq.com
 * @date 2021/3/2 15:01
 */

@RestController
@Slf4j
@RequestMapping("/api/kafka")
public class KafkaTestController {

//    private String servers = "192.168.1.9:9092,192.168.1.9:9093,192.168.1.9:9094";
    private String servers = "10.236.192.138:9092, 10.236.192.139:9092, 10.236.192.140:9092";



    /**
     * 新增topic (支持批量，这里就单个作为演示)
     */
    @RequestMapping("addTopic/{topic}")
    public Response<?> add(@PathVariable String topic) {
        NewTopic newTopic = new NewTopic(topic, 3, (short) 1);
//        AdminClientUtils adminClientUtils = new AdminClientUtils(servers);
//        adminClientUtils.createTopic(Lists.newArrayList(newTopic));
        KafkaUtil.createTopic(Lists.newArrayList(newTopic),servers);
        return ResponseSupport.successResponse(topic);
    }

    /**
     * 查询topic信息 (支持批量，这里就单个作为演示)
     */
    @RequestMapping("/getBytTopic/{topic}")
    public Response<String> getBytTopic(@PathVariable String topic) {
//        AdminClientUtils adminClientUtils = new AdminClientUtils(servers);
//        String topicInfo = adminClientUtils.getTopicInfo(Lists.newArrayList(topic));
        String topicInfo = KafkaUtil.getTopicInfo(Lists.newArrayList(topic), servers);
        return ResponseSupport.successResponse(topicInfo);
    }

    /**
     * 删除topic (支持批量，这里就单个作为演示)
     * (注意：如果topic正在被监听会给人感觉删除不掉（但其实是删除掉后又会被创建）)
     */
    @GetMapping("delete/{topic}")
    public Response<?> delete(@PathVariable String topic) {
//        AdminClientUtils adminClientUtils = new AdminClientUtils(servers);
//        adminClientUtils.deleteTopic(Lists.newArrayList(topic));
        KafkaUtil.deleteTopic(Lists.newArrayList(topic),servers);
        return ResponseSupport.successResponse(topic);
    }

    /**
     * 查询所有topic
     */
    @RequestMapping("allTopic")
    public Response<List<String>> getAllTopic() {
        log.info("----allTopic-----");
//        AdminClientUtils adminClientUtils = new AdminClientUtils(servers);
//        List<String> allTopic = adminClientUtils.getAllTopic();
        List<String> allTopic = KafkaUtil.getAllTopic(servers);
        return ResponseSupport.successResponse(allTopic);
    }

    /**
     * 生产者往topic中发送消息demo
     *
     * @param topic
     * @param message
     * @return
     */
//    @RequestMapping("message")
//    public Response<?> sendMessage(String topic, String message) {
//        kafkaTemplate.send(topic, message);
//        return ResponseSupport.successResponse(null);
//    }

    /**
     * 消费者示例demo
     * <p>
     * 基于注解监听多个topic，消费topic中消息
     * （注意：如果监听的topic不存在则会自动创建）
     */
//    @KafkaListener(topics = {"topic_02", "topic_01", "access-topic"})
//    public void consume(String message) {
//        log.info("receive msg: " + message);
//    }
}
