//package com.dada.dm.qujia.util;
//
//import com.dada.dm.qujia.client.Es6Client;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import java.util.*;
//
//
///**
// * @author jt.Qu
// * @description 解决netty引起的issue
// * @program: dw-bpt
// * @date 2021-09-06 14:56
// */
//@Slf4j
//public class Es6Util {
//
//
////    /**
////     * 创建索引
////     *
////     * @param index
////     * @return
////     */
////    public static boolean createIndex(String index, String server) {
////        RestHighLevelClient restHighLevelClient = EsClient.getRestHighLevelClient(server);
////        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
////        boolean flag = false;
////        try {
////            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
////            flag = createIndexResponse.isAcknowledged();
////            log.info("createIndexResponse->{}", flag);
////        } catch (Exception e) {
////            log.error("error->{}", e.getMessage());
////        } finally {
////            closeClient(restHighLevelClient);
////        }
////        return flag;
////    }
////
////
////    public static List<String> getIndexType(String index, String server) {
////        RestHighLevelClient restHighLevelClient = EsClient.getRestHighLevelClient(server);
////        List<String> result = new ArrayList<>();
////        try {
////            GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
////            getMappingsRequest.indices(index);
////            GetMappingsResponse mapping = restHighLevelClient.indices().getMapping(getMappingsRequest, RequestOptions.DEFAULT);
////            Map<String, MappingMetadata> mappings = mapping.mappings();
////            log.info("mappings->{}", JSON.toJSONString(mappings));
////            Set<String> strings = mappings.keySet();
////            log.info("strings->{}", strings);
////            result = new ArrayList<>(strings);
////        } catch (Exception e) {
////            log.error("error->{}", e.getMessage());
////        } finally {
////            closeClient(restHighLevelClient);
////        }
////        return result;
////    }
////
////
////    /**
////     * 获取所有别名
////     * @param server
////     * @return
////     */
////    public static List<Map<String,String>> getAliasesIndex(String server) {
////        RestHighLevelClient restHighLevelClient = EsClient.getRestHighLevelClient(server);
////        List<Map<String,String>> res = new ArrayList<>();
////        try {
////            GetAliasesRequest getAliasesRequest = new GetAliasesRequest();
////            GetAliasesResponse aliasesResponse = restHighLevelClient.indices().getAlias(getAliasesRequest, RequestOptions.DEFAULT);
////            Map<String, Set<AliasMetadata>> aliasesMap = aliasesResponse.getAliases();
//////            log.info("aliasesMap->{}", JSON.toJSONString(aliasesMap));
////            if(!aliasesMap.isEmpty()){
////                aliasesMap.forEach((k,v) -> {
////                    Map<String, String> map = new HashMap<>();
////                    String alias = "";
////                    if(!v.isEmpty()){
////                        alias = v.stream().map(value -> value.getAlias()).findFirst().get();
////                    }
////                    map.put(k,alias);
////                    res.add(map);
////                });
////            }
////
////        } catch (Exception e) {
////            log.error("error->{}", e.getMessage());
////        } finally {
////            closeClient(restHighLevelClient);
////        }
////        return res;
////    }
////
////    public static List<String> getAliasesIndex(String aliases, String server) {
////        RestHighLevelClient restHighLevelClient = EsClient.getRestHighLevelClient(server);
////        List<String> result = new ArrayList<>();
////        try {
////
////            GetAliasesRequest getAliasesRequest = new GetAliasesRequest();
////            getAliasesRequest.aliases(aliases);
////            GetAliasesResponse alias = restHighLevelClient.indices().getAlias(getAliasesRequest, RequestOptions.DEFAULT);
////            Map<String, Set<AliasMetadata>> aliasesMap = alias.getAliases();
////            Set<String> keySet = aliasesMap.keySet();
////            log.info("keySet->{}", keySet);
////            result = new ArrayList<>(keySet);
////        } catch (Exception e) {
////            log.error("error->{}", e.getMessage());
////        } finally {
////            closeClient(restHighLevelClient);
////        }
////        return result;
////    }
////
////    public static List<String> searchAllTemplate(String server) {
////        RestHighLevelClient restHighLevelClient = EsClient.getRestHighLevelClient(server);
////        List<String> templateList = new ArrayList<>();
////        try {
////            GetIndexTemplatesRequest getIndexTemplatesRequest = new GetIndexTemplatesRequest();
////            GetIndexTemplatesResponse indexTemplate = restHighLevelClient.indices().getIndexTemplate(getIndexTemplatesRequest, RequestOptions.DEFAULT);
////            List<IndexTemplateMetadata> indexTemplates = indexTemplate.getIndexTemplates();
////            log.info("---indexTemplate->{}",indexTemplates);
////            log.info("---indexTemplate->{}",JSON.toJSONString(indexTemplates));
////            if(!CollectionUtils.isEmpty(indexTemplates)){
////                indexTemplates.stream().forEach(tmp -> {
////                    log.info("---tmp->{}",tmp);
////                    log.info("---tmp_json->{}",JSON.toJSONString(tmp));
////                    String name = tmp.name();
////                    templateList.add(name);
////                    log.info("---name->{}",name);
////                    Settings settings = tmp.settings();
////                    log.info("---settings->{}",settings);
////                    MappingMetadata mappings = tmp.mappings();
////                    log.info("---mappings->{}",mappings);
////                    if(null != mappings) {
////                        Map<String, Object> sourceAsMap = mappings.getSourceAsMap();
////                        log.info("---sourceAsMap->{}",sourceAsMap);
////                        log.info("---sourceAsMap->{}",JSON.toJSONString(sourceAsMap));
////                        ImmutableOpenMap<String, AliasMetadata> aliases = tmp.aliases();
////                        if (!aliases.isEmpty()) {
////                            aliases.iterator().forEachRemaining(ali -> {
////                                String key = ali.key;
////                                log.info("---key->{}", key);
////                                int index = ali.index;
////                                log.info("---index->{}", index);
////
////                            });
////                        }
////                    }
////                });
////            }
////        } catch (IOException e) {
////            log.error("searchAllIndex error -> {}",e);
////            e.printStackTrace();
////        }
////        return templateList;
////    }
////
////
////    /**
////     * 关闭 RestHighLevelClient
////     *
////     * @param restHighLevelClient
////     */
////    private static void closeClient(RestHighLevelClient restHighLevelClient) {
////        try {
////            restHighLevelClient.close();
////        } catch (Exception e) {
////            log.info("restHighLevelClient close error ->{}", e.getMessage());
////        }
////    }
////
////    /**
////     * 查看索引是否存在
////     *
////     * @param index
////     * @return
////     */
////    public static boolean searchIndexExists(String index, String server) {
////        RestHighLevelClient restHighLevelClient = EsClient.getRestHighLevelClient(server);
////        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
////        boolean exists = false;
////        try {
////            exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
////        } catch (Exception e) {
////            log.error("error->{}", e.getMessage());
////        } finally {
////            closeClient(restHighLevelClient);
////        }
////        return exists;
////    }
////
////    /**
////     * 查询索引信息
////     *
////     * @param index
////     * @return
////     */
////    public static List<EsColumnInfo> searchIndex(String index, String server) {
////        RestHighLevelClient restHighLevelClient = EsClient.getRestHighLevelClient(server);
////        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
////        Map<String, MappingMetadata> mappings = null;
////        List<EsColumnInfo> esColumnInfoList = new ArrayList<>();
////        try {
////            GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
////            mappings = getIndexResponse.getMappings();
//////            log.info("mappings->{}", JSON.toJSONString(mappings));
////            MappingMetadata mappingMetadata = mappings.get(index);
////            if (null != mappingMetadata) {
////                Map<String, Object> sourceAsMap = mappingMetadata.getSourceAsMap();
////                if (!sourceAsMap.isEmpty()) {
////                    Map<String, Object> properties = (Map) sourceAsMap.get("properties");
//////                    log.info("properties->{}", JSON.toJSONString(properties));
////                    if (!properties.isEmpty()) {
////                        properties.entrySet().stream().forEach(entry -> {
////                            Map<String, Object> map = (Map) entry.getValue();
////                            if (!map.isEmpty()) {
////                                String type = String.valueOf(map.get("type"));
////                                esColumnInfoList.add(new EsColumnInfo(entry.getKey(), type));
////                            }
////
////                        });
////                    }
////                }
////            }
////        } catch (Exception e) {
////            log.error("error->{}", e.getMessage());
////        } finally {
////            closeClient(restHighLevelClient);
////        }
////        return esColumnInfoList;
////
////    }
//
//    /**
//     * 查询所有索引信息
//     *
//     * @param server
//     * @return
//     */
//    public static  List<String> searchAllIndex(String server) {
//        TransportClient client = Es6Client.getTransportClient(server);
//        List<String> indexList = new ArrayList<>();
//        try {
//            SearchRequest searchRequest = new SearchRequest();
//            searchRequest.indices();
//            SearchResponse searchResponse = client.search(searchRequest).actionGet();
//            SearchHits hits = searchResponse.getHits();
//            SearchHit[] hits1 = hits.getHits();
//            for (SearchHit hit : hits1) {
//                String index = hit.getIndex();
//                System.out.println(index);
//                indexList.add(index);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return indexList;
//    }
//
////    public static Map<String, Set<AliasMetadata>> search(String server) {
//////        RestHighLevelClient restHighLevelClient = EsClient.getRestHighLevelClient(server);
////        RestClient restClient = EsClient.getRestClient(server);
////        Map<String, Set<AliasMetadata>> map = null;
////        try {
////            String method = "GET";
////            String endpoint = "/_cat/tasks";
////            Request request1 = new Request(method,endpoint);
////            Response response = restClient.performRequest(request1);
//////            RequestLine requestLine = restClient.performRequest(request1).getRequestLine();
////            System.out.println(EntityUtils.toString(response.getEntity()));
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return map;
////    }
//
//}
