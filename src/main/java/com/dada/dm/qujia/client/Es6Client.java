//package com.dada.dm.qujia.client;
//
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
///**
// * @author jt.Qu
// * @description es client
// * @program: kafka-demo
// * @date 2022-04-25 21:29
// */
//@Slf4j
//public class Es6Client {
//
//
//    public static TransportClient getTransportClient(String service) {
//        if (StringUtils.isEmpty(service)) {
//            return null;
//        }
//        TransportClient transportClient;
//        synchronized (EsClient.class) {
//            transportClient = initTransportClient(service);
//        }
//        return transportClient;
//    }
//
//
//
//
//
//    private static TransportClient initTransportClient(String server) {
//
//        log.info("init elastic-search client!");
//        String[] servers = server.split(",");
//
//        Settings settings = Settings.builder()
////                .put("cluster.name", "data")
////                .put("client.transport.sniff", 3)
//                .build();
//
//        /*初始化client*/
//        TransportClient transportClient = new PreBuiltTransportClient(settings);
//
//        if (0 != servers.length) {
//            for (String s : servers) {
//                try {
//                    String[] temp = s.split(":");
//                    String host = temp[0].trim();
//                    int port = Integer.parseInt(temp[1].trim());
//                    transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                    log.error("init elastic-search client failed",e);
//                }
//            }
//        }
//
//        return transportClient;
//    }
//
//
////    public static TransportClient getClient() {
////        TransportClient client;
////        try {
////            if(client == null) {
////                //指定集群
////                Settings settings = Settings.builder().put("cluster.name", "data").build();
////                //创建访问ES服务器端客户端
////                client = new PreBuiltTransportClient(settings)
////                        // 设置集群的节点
////                        .addTransportAddress(new TransportAddress(InetAddress.getByName(IP), PORT));
////            }
////
////        } catch (UnknownHostException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////        return client;
////    }
//
//
//}
