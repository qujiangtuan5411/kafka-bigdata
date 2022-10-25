package com.dada.dm.qujia.client;

import com.alibaba.fastjson.JSON;
import com.dada.dm.qujia.model.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


/**
 * @author jt.Qu
 * @description hbase
 * @program: kafka-demo
 * @date 2022-04-27 10:41
 */
@Slf4j
public class HbaseClient {

    public static final String ZK_PORT = "2181";
    public static final String COMMA_SYMBOL = ",";
    public static final String COLON_SYMBOL = ":";
    public static final String BACKSLASH_SYMBOL = "/";
//    private String servers = "10.236.192.11:2181,10.236.192.12:2181,10.236.192.13:2181";

    /**
     * 获取 RestHighLevelClient
     *
     * @param service
     * @return
     */
    public static Connection getHbaseConnection(String service) {
        Connection connection;
        synchronized (HbaseClient.class) {
            connection = initConnection(service);
        }
        return connection;
    }

//    private static Connection initConnection(String service) {
//        //创建连接
//        Configuration configuration = HBaseConfiguration.create();
////        configuration.set("hbase.zookeeper.quorum", "10.231.128.95");
////        configuration.set("hbase.zookeeper.quorum", "10.236.192.11");
////        configuration.set("hbase.zookeeper.property.clientPort", "2181");
////        configuration.set("hbase.master", "10.236.192.11:60000");
//
//        //home 旭哥给的ip
//        configuration.set("hbase.zookeeper.quorum", "10.231.128.95");
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
////        configuration.set("hbase.master", "10.231.128.95:60000");
//        configuration.set("zookeeper.znode.parent", "/default");
//
//        //home 虚拟机ip
////        configuration.set("hbase.zookeeper.quorum", "192.168.1.9");
////        configuration.set("hbase.zookeeper.property.clientPort", "2181");
////        configuration.set("hbase.master", "192.168.1.9:60010");
//
//        try {
//            return ConnectionFactory.createConnection(configuration);
//        }catch (Exception e){
//            log.error("--hbase admint error -> {}",e.getMessage());
//        }
//        return null;
//    }

    private static Connection initConnection(String service) {
        if (StringUtils.isEmpty(service)) {
            return null;
        }
        IpPort ipPort = getIpPort(service);
        //创建连接
        Configuration configuration = HBaseConfiguration.create();
        //home 旭哥给的ip
        configuration.set("hbase.zookeeper.quorum", ipPort.getIps());
        configuration.set("hbase.zookeeper.property.clientPort", ipPort.getPort());
        configuration.set("zookeeper.znode.parent", ipPort.getNamespace());
        try {
            return ConnectionFactory.createConnection(configuration);
        } catch (Exception e) {
            log.error("--hbase admint error -> {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取ips 和 post
     *
     * @param service
     * @return
     */
    private static IpPort getIpPort(String service) {
        List<String> ipList = new ArrayList<>();
        //默认port 为 2181
        AtomicReference<String> port = new AtomicReference<>(ZK_PORT);
        String namespace = "/hbase";
        if (service.contains(BACKSLASH_SYMBOL)) {
            String[] split = service.split(BACKSLASH_SYMBOL);
            service = split[0];
            namespace = BACKSLASH_SYMBOL + split[1];
        }
        if (service.contains(COMMA_SYMBOL)) {
            String[] split = service.split(COMMA_SYMBOL);
            Arrays.stream(split).forEach(ipPort -> {
                if (StringUtils.isNotEmpty(ipPort)) {
                    if (ipPort.contains(COLON_SYMBOL)) {
                        String[] ipPorts = ipPort.split(COLON_SYMBOL);
                        ipList.add(ipPorts[0]);
                        port.set(ipPorts[1]);
                    } else {
                        ipList.add(ipPort);
                    }
                }
            });
        } else {
            if (service.contains(COLON_SYMBOL)) {
                String[] ipPorts = service.split(COLON_SYMBOL);
                ipList.add(ipPorts[0]);
                port.set(ipPorts[1]);
            } else {
                ipList.add(service);
            }
        }
        IpPort ipPort = new IpPort();
        ipPort.setIps(ipList.stream().collect(Collectors.joining(COMMA_SYMBOL)));
        ipPort.setPort(port.get());
        ipPort.setNamespace(namespace);
        return ipPort;
    }

//    /**
//     * 获取ips 和 post
//     * @param key
//     * @return
//     */
//    public static IpPort getIpPort(String key){
//        String[] parts = key.split(":");
//        IpPort ipPort = new IpPort();
//        if (parts.length == 3) {
//            if (!parts[2].matches("/.*[^/]")) {
//                log.error("Cluster key passed " + key + " is invalid, the format should be:" + "hbase.zookeeper.quorum" + ":" + "hbase.zookeeper.property.clientPort" + ":" + "zookeeper.znode.parent");
////                throw new IOException("Cluster key passed " + key + " is invalid, the format should be:" + "hbase.zookeeper.quorum" + ":" + "hbase.zookeeper.property.clientPort" + ":" + "zookeeper.znode.parent");
//            } else {
//                ipPort = new IpPort(parts[0], parts[1], parts[2]);
////                return new ZKConfig.ZKClusterKey(parts[0], Integer.parseInt(parts[1]), parts[2]);
//            }
//        } else if (parts.length > 3) {
//            String zNodeParent = parts[parts.length - 1];
//            if (!zNodeParent.matches("/.*[^/]")) {
//                log.error("Cluster key passed " + key + " is invalid, the format should be:" + "hbase.zookeeper.quorum" + ":" + "hbase.zookeeper.property.clientPort" + ":" + "zookeeper.znode.parent");
////                throw new IOException("Cluster key passed " + key + " is invalid, the format should be:" + "hbase.zookeeper.quorum" + ":" + "hbase.zookeeper.property.clientPort" + ":" + "zookeeper.znode.parent");
//            } else {
//                String clientPort = parts[parts.length - 2];
//                int endQuorumIndex = key.length() - zNodeParent.length() - clientPort.length() - 2;
//                String quorumStringInput = key.substring(0, endQuorumIndex);
//                String[] serverHosts = quorumStringInput.split(",");
//                ipPort = parts.length - 2 == serverHosts.length + 1 ? new IpPort(quorumStringInput, clientPort, zNodeParent) : new IpPort(buildZKQuorumServerString(serverHosts, clientPort), clientPort, zNodeParent);
//            }
//        } else {
//            log.error("Cluster key passed " + key + " is invalid, the format should be:" + "hbase.zookeeper.quorum" + ":" + "hbase.zookeeper.property.clientPort" + ":" + "zookeeper.znode.parent");
////            throw new IOException("Cluster key passed " + key + " is invalid, the format should be:" + "hbase.zookeeper.quorum" + ":" + "hbase.zookeeper.property.clientPort" + ":" + "zookeeper.znode.parent");
//        }
//        return ipPort;
//    }
//
//    public static String buildZKQuorumServerString(String[] serverHosts, String clientPort) {
//        StringBuilder quorumStringBuilder = new StringBuilder();
//        for(int i = 0; i < serverHosts.length; ++i) {
//            String serverHost;
//            if (serverHosts[i].contains(":")) {
//                serverHost = serverHosts[i];
//            } else {
//                serverHost = serverHosts[i] + ":" + clientPort;
//            }
//            if (i > 0) {
//                quorumStringBuilder.append(',');
//            }
//            quorumStringBuilder.append(serverHost);
//        }
//        return quorumStringBuilder.toString();
//    }


    public static void main(String[] args) {
        String servers = "10.236.192.11:2181,10.236.192.12:2181,10.236.192.13:2181/default";
        IpPort ipPort = getIpPort(servers);
        log.info(JSON.toJSONString(ipPort));
        System.out.println(JSON.toJSONString(ipPort));
    }
}
