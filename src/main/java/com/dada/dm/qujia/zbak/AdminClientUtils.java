//package com.dada.dm.qujia.util;
//
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.admin.*;
//import org.apache.kafka.common.TopicPartitionInfo;
//
//import java.util.*;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//
//
///**
// * @author jt.Qu
// * @description kafka 配置
// * @program: kafka-demo
// * @date 2022-04-19 21:15
// */
//@Slf4j
//public class AdminClientUtils {
//
//    private ThreadLocal<AdminClient> adminClient = new InheritableThreadLocal<>();
//
//    private String servers = "192.168.1.9:9092,192.168.1.9:9093,192.168.1.9:9094";
//
//    public AdminClientUtils(String servers) {
//        this.servers = servers;
//        Map<String, Object> props = new HashMap<>(8);
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//        adminClient.set(KafkaAdminClient.create(props));
//    }
//
//    /**
//     * 新增topic，支持批量
//     */
//    public void createTopic(Collection<NewTopic> newTopics) {
//        try {
//            adminClient.get().createTopics(newTopics);
//        }catch (Exception e){
//            log.error("createTopic error , e -> {}",e.getMessage());
//        }finally {
//            adminClient.remove();
//        }
//    }
//
//    /**
//     * 删除topic，支持批量
//     */
//    public void deleteTopic(Collection<String> topics) {
//        try {
//            adminClient.get().deleteTopics(topics);
//        }catch (Exception e){
//            log.error("deleteTopic error , e -> {}",e.getMessage());
//        }finally {
//            adminClient.remove();
//        }
//    }
//
//    /**
//     * 获取指定topic的信息
//     */
//    public String getTopicInfo(Collection<String> topics) {
//        AtomicReference<String> info = new AtomicReference<>("");
//        try {
//            adminClient.get().describeTopics(topics).all().get().forEach((topic, description) -> {
//                for (TopicPartitionInfo partition : description.partitions()) {
//                    info.set(info + partition.toString() + "\n");
//                }
//            });
//        } catch (InterruptedException | ExecutionException e) {
//            log.error("getTopicInfo error , e -> {}",e.getMessage());
//        } finally {
//            adminClient.remove();
//        }
//        return info.get();
//    }
//
//    /**
//     * 获取全部topic
//     */
//    public List<String> getAllTopic() {
//        try {
//            ListTopicsResult listTopicsResult = adminClient.get().listTopics();
//            Set<String> strings = listTopicsResult.names().get();
//            System.out.println("strings->"+strings);
//
//            Map<String, TopicListing> stringTopicListingMap = listTopicsResult.namesToListings().get();
//            System.out.println("stringTopicListingMap->"+stringTopicListingMap);
//
//            Collection<TopicListing> topicListings = listTopicsResult.listings().get();
//            System.out.println("topicListings->"+topicListings);
//
//            return topicListings.stream().map(TopicListing::name).collect(Collectors.toList());
////            return adminClient.get().listTopics().listings().get().stream().map(TopicListing::name).collect(Collectors.toList());
//        } catch (InterruptedException | ExecutionException e) {
//            log.error("getAllTopic error , e -> {}",e.getMessage());
//        } finally {
//            adminClient.remove();
//        }
//        return Lists.newArrayList();
//    }
//}
