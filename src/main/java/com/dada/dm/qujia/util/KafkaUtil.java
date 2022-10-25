package com.dada.dm.qujia.util;

import com.dada.dm.qujia.client.KafkaClient;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartitionInfo;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


/**
 * @author jt.Qu
 * @description kafka 配置
 * @program: kafka-demo
 * @date 2022-04-19 21:15
 */
@Slf4j
public class KafkaUtil {

    /**
     * 新增topic，支持批量
     */
    public static void createTopic(Collection<NewTopic> newTopics, String servers) {
        AdminClient adminClient = KafkaClient.getKafkaAdminClient(servers);
        try {
            adminClient.createTopics(newTopics);
        }catch (Exception e){
            log.error("createTopic error , e -> {}",e.getMessage());
        }
    }

    /**
     * 删除topic，支持批量
     */
    public static void deleteTopic(Collection<String> topics, String servers) {
        AdminClient adminClient = KafkaClient.getKafkaAdminClient(servers);
        try {
            adminClient.deleteTopics(topics);
        }catch (Exception e){
            log.error("deleteTopic error , e -> {}",e.getMessage());
        }
    }

    /**
     * 获取指定topic的信息
     */
    public static String getTopicInfo(Collection<String> topics, String servers) {
        AdminClient adminClient = KafkaClient.getKafkaAdminClient(servers);
        AtomicReference<String> info = new AtomicReference<>("");
        try {
            adminClient.describeTopics(topics).all().get().forEach((topic, description) -> {
                for (TopicPartitionInfo partition : description.partitions()) {
                    info.set(info + partition.toString() + "\n");
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            log.error("getTopicInfo error , e -> {}",e.getMessage());
        }
        return info.get();
    }

    /**
     * 获取全部topic
     */
    public static List<String> getAllTopic(String servers) {
        AdminClient adminClient = KafkaClient.getKafkaAdminClient(servers);
        try {
            ListTopicsResult listTopicsResult = adminClient.listTopics();
            Set<String> strings = listTopicsResult.names().get();
            System.out.println("strings->"+strings);

            Map<String, TopicListing> stringTopicListingMap = listTopicsResult.namesToListings().get();
            System.out.println("stringTopicListingMap->"+stringTopicListingMap);

            Collection<TopicListing> topicListings = listTopicsResult.listings().get();
            System.out.println("topicListings->"+topicListings);

            return topicListings.stream().map(TopicListing::name).collect(Collectors.toList());
//            return adminClient.get().listTopics().listings().get().stream().map(TopicListing::name).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            log.error("getAllTopic error , e -> {}",e.getMessage());
        }
        return Lists.newArrayList();
    }
}
