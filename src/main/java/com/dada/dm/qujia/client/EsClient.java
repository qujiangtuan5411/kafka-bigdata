package com.dada.dm.qujia.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jt.Qu
 * @description es client
 * @program: kafka-demo
 * @date 2022-04-25 21:29
 */
@Slf4j
public class EsClient {

    private static final int ADDRESS_LENGTH = 2;

    private static final Integer connectionRequestTimeout =5000;

    private static final Integer socketTimeout = 30000;

    private static final Integer connectTimeout = 30000;

//    private String server = "192.168.1.9:9201,192.168.1.9:9202,192.168.1.9:9203";

    public static Map<String, RestHighLevelClient> REST_HIGH_LEVEL_CLIENT_MAP = new ConcurrentHashMap<>();

    /**
     * 获取 RestHighLevelClient
     * @param service
     * @return
     */
    public static RestHighLevelClient getRestHighLevelClient(String service) {
        RestHighLevelClient restHighLevelClient;
        synchronized (EsClient.class) {
            restHighLevelClient = initRestHighLevelClient(service);
        }
        return restHighLevelClient;
    }



//    public static RestHighLevelClient getRestHighLevelClient(String service) {
//
//        final String key = service;
//
//        RestHighLevelClient restHighLevelClient = REST_HIGH_LEVEL_CLIENT_MAP.get(key);
//        log.info("---restHighLevelClient->{}",restHighLevelClient);
//
//        if (Objects.isNull(restHighLevelClient)) {
//
//            synchronized (EsClient.class) {
//                restHighLevelClient = REST_HIGH_LEVEL_CLIENT_MAP.get(key);
//                if (Objects.isNull(restHighLevelClient)) {
//                    restHighLevelClient = initRestHighLevelClient(service);
//                    REST_HIGH_LEVEL_CLIENT_MAP.put(key, restHighLevelClient);
//                }
//            }
//
//        }
//        return restHighLevelClient;
//    }

    /**
     * 初始化 es client
     *
     * @param server
     * @return
     */
    private static RestHighLevelClient initRestHighLevelClient(String server) {
        if (StringUtils.isEmpty(server)) {
            return null;
        }
        log.info("------restHighLevelClient------server-> {}", server);
        RestClientBuilder restClientBuilder = RestClient.builder(getHttpHosts(server));

        //请求超时参数设置
        restClientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder
                        .setConnectionRequestTimeout(connectionRequestTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectTimeout(connectTimeout);
            }
        });
        return new RestHighLevelClient(restClientBuilder);
    }

    private static HttpHost[] getHttpHosts(String server) {
        String[] ipPortList = server.split(",");
        return Arrays.stream(ipPortList)
                .map(ipPort -> makeHttpHost(ipPort))
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
    }
    /**
     * 根据配置创建HttpHost
     *
     * @param server
     * @return
     */
    /**
     * 根据配置创建HttpHost
     * @param ipPort
     * @return
     */
    private static HttpHost makeHttpHost(String ipPort) {
        String[] address = ipPort.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, "http");
        } else {
            return null;
        }
    }



    /**
     * 获取 RestClient
     * @param service
     * @return
     */
    public static RestClient getRestClient(String service) {
        if (StringUtils.isEmpty(service)) {
            return null;
        }
        RestClient restClient;
        synchronized (EsClient.class) {
            restClient = RestClient.builder(getHttpHosts(service)).build();
        }
        return restClient;
    }



}
