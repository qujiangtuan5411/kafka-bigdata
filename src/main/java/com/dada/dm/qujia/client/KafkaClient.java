package com.dada.dm.qujia.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jt.Qu
 * @description kafka 配置
 * @program: kafka-demo
 * @date 2022-04-19 21:15
 */
@Slf4j
public class KafkaClient {


    private String servers = "192.168.1.9:9092,192.168.1.9:9093,192.168.1.9:9094";


    public static Map<String, AdminClient> KAFKA_ADMIN_CLIENT_MAP = new ConcurrentHashMap<>();

    /**
     * 获取 RestHighLevelClient
     *
     * @param service
     * @return
     */
    public static AdminClient getKafkaAdminClient(String service) {
        //缓存超过50 清除所有缓存
        if(KAFKA_ADMIN_CLIENT_MAP.size() > 50){
            KAFKA_ADMIN_CLIENT_MAP.clear();
        }
        final String key = service;

        AdminClient adminClient;
        synchronized (KafkaClient.class) {
            adminClient = KAFKA_ADMIN_CLIENT_MAP.get(key);
            log.info("---adminClient->{}",adminClient);
            if (Objects.isNull(adminClient)) {
                adminClient = initAdminClient(service);
                KAFKA_ADMIN_CLIENT_MAP.put(key, adminClient);
            }
        }
        return adminClient;
    }

    /**
     * 初始化 AdminClient
     * @param service
     * @return
     */
    private static AdminClient initAdminClient(String service) {
        Map<String, Object> props = new HashMap<>(8);
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, service);
        return KafkaAdminClient.create(props);
    }

}
