//package com.dada.dm.qujia.util;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpHost;
//import org.apache.http.client.config.RequestConfig;
//import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
//import org.elasticsearch.client.*;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.client.indices.GetIndexResponse;
//import org.elasticsearch.cluster.metadata.AliasMetadata;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//
//
///**
// * @author jt.Qu
// * @description 解决netty引起的issue
// * @program: dw-bpt
// * @date 2021-09-06 14:56
// */
//@Slf4j
//public class ElasticSearchUtil {
//
//    private static final int ADDRESS_LENGTH = 2;
//
//    private Integer connectionRequestTimeout =5000;
//
//    private Integer socketTimeout = 30000;
//
//    private Integer connectTimeout = 30000;
//
//    private String server = "192.168.1.9:9201,192.168.1.9:9202,192.168.1.9:9203";
//
//    ThreadLocal<RestHighLevelClient> restHighLevelClient = new InheritableThreadLocal<>();
//
//    public ElasticSearchUtil(String server) {
//        if(!StringUtils.isEmpty(server)){
//            this.server = server;
//        }
//        log.info("------restHighLevelClient------server-> {}",server);
//        RestClientBuilder restClientBuilder = RestClient.builder(getHttpHosts(server));
//
//        //请求超时参数设置
//        restClientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
//            @Override
//            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
//                return requestConfigBuilder
//                        .setConnectionRequestTimeout(connectionRequestTimeout)
//                        .setSocketTimeout(socketTimeout)
//                        .setConnectTimeout(connectTimeout);
//            }
//        });
//        restHighLevelClient.set(new RestHighLevelClient(restClientBuilder));
//    }
//
//    private HttpHost[] getHttpHosts(String server) {
//        String[] ipPortList = server.split(",");
//        return Arrays.stream(ipPortList)
//                .map(this::makeHttpHost)
//                .filter(Objects::nonNull)
//                .toArray(HttpHost[]::new);
//    }
//
//    /**
//     * 根据配置创建HttpHost
//     * @param ipPort
//     * @return
//     */
//    private HttpHost makeHttpHost(String ipPort) {
//        String[] address = ipPort.split(":");
//        if (address.length == ADDRESS_LENGTH) {
//            String ip = address[0];
//            int port = Integer.parseInt(address[1]);
//            return new HttpHost(ip, port, "http");
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 创建索引
//     * @param index
//     * @return
//     */
//    public boolean createIndex(String index) {
//        RestHighLevelClient restHighLevelClient = this.restHighLevelClient.get();
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
//        boolean flag = false;
//        try {
//            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
//            flag =  createIndexResponse.isAcknowledged();
//            log.info("createIndexResponse->{}",flag);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            this.restHighLevelClient.remove();
//        }
//        return flag;
//    }
//
//    /**
//     * 查看索引是否存在
//     * @param index
//     * @return
//     */
//    public boolean searchIndexExists(String index) {
//        RestHighLevelClient restHighLevelClient = this.restHighLevelClient.get();
//        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
//        boolean exists = false;
//        try {
//            exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            this.restHighLevelClient.remove();
//        }
//        return exists;
//    }
//
//    /**
//     * 查询索引信息
//     * @param index
//     * @return
//     */
//    public Map<String, String> searchIndex(String index) {
//        RestHighLevelClient restHighLevelClient = this.restHighLevelClient.get();
//        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
//        Map<String, String> dataStreams  = null;
//        try {
//            GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
//            dataStreams = getIndexResponse.getDataStreams();
//            log.info("dataStreams->{}", JSON.toJSONString(dataStreams));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
////            this.restHighLevelClient.get().close();
//            this.restHighLevelClient.remove();
//        }
//        return dataStreams;
//
//    }
//
//    /**
//     * 查询所有索引信息
//     * @return
//     */
//    public Map<String, Set<AliasMetadata>> searchAllIndex() {
//        RestHighLevelClient restHighLevelClient = this.restHighLevelClient.get();
//        Map<String, Set<AliasMetadata>> map  = null;
//        try {
//            GetAliasesRequest request = new GetAliasesRequest();
//            GetAliasesResponse getAliasesResponse =  restHighLevelClient.indices().getAlias(request,RequestOptions.DEFAULT);
//            map = getAliasesResponse.getAliases();
//            Set<String> indices = map.keySet();
//            for (String key : indices) {
//                System.out.println(key);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return map;
//
//    }
//
//
//
//}
