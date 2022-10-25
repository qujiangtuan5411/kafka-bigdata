//package com.dada.dm.qujia.controller;
//
//import com.dada.dm.qujia.model.Response;
//import com.dada.dm.qujia.model.ResponseSupport;
//import com.dada.dm.qujia.util.Es6Util;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @description  es test
// * @className EsTestController
// * @packageName com.dada.dm.qujia.controller
// * @author jt.Qu
// * @date 2022/5/15 10:11
// */
//@RestController
//@Slf4j
//@RequestMapping("/api/es/six")
//public class Es6TestController {
//
////    private String server = "192.168.1.9:9201,192.168.1.9:9202,192.168.1.9:9203";
//    private String server = "10.236.194.119:9200,10.236.194.117:9200,10.236.194.118:9200";
//
////    /**
////     * 创建索引
////     */
////    @RequestMapping("createIndex/{index}")
////    public Response<?> createIndex(@PathVariable String index) {
//////        ElasticSearchUtil esUtil = new ElasticSearchUtil(server);
//////        boolean flag = esUtil.createIndex(index);
////        boolean flag = EsUtil.createIndex(index, server);
////        System.out.println(flag);
////        return ResponseSupport.successResponse(flag);
////    }
////
////    /**
////     * 获取索引 下type
////     * @param index
////     * @return
////     */
////    @RequestMapping("getIndexType/{index}")
////    public Response<?> getIndexType(@PathVariable String index) {
////        List<String> indexType = EsUtil.getIndexType(index, server);
////        System.out.println(indexType);
////        return ResponseSupport.successResponse(indexType);
////    }
////
////    /**
////     * 获取所有别名
////     * @return
////     */
////    @RequestMapping("getAllAliases")
////    public Response<?> getAliasesIndex() {
////        List<Map<String, String>> aliasesIndex = EsUtil.getAliasesIndex(server);
////        System.out.println(aliasesIndex);
////        return ResponseSupport.successResponse(aliasesIndex);
////    }
////
////    /**
////     * 获取别名希所有索引
////     * @param aliases
////     * @return
////     */
////    @RequestMapping("getAliasesIndex/{aliases}")
////    public Response<?> getAliasesIndex(@PathVariable String aliases) {
////        List<String> indexType = EsUtil.getAliasesIndex(aliases, server);
////        System.out.println(indexType);
////        return ResponseSupport.successResponse(indexType);
////    }
////
////    /**
////     * 查询所有索引模板信息
////     * @return
////     */
////    @RequestMapping("searchAllTemplate")
////    public Response<?> searchAllTemplate() {
////        List<String> strings = EsUtil.searchAllTemplate(server);
////        System.out.println(strings);
////        return ResponseSupport.successResponse(strings);
////    }
////
////    /**
////     * 查看索引是否存在
////     */
////    @RequestMapping("indexExists/{index}")
////    public Response<?> searchIndexExists(@PathVariable String index) {
//////        ElasticSearchUtil esUtil = new ElasticSearchUtil(server);
//////        boolean flag = esUtil.searchIndexExists(index);
////        boolean flag = EsUtil.searchIndexExists(index, server);
////        System.out.println(flag);
////        return ResponseSupport.successResponse(flag);
////    }
////
////    /**
////     * 查询索引信息
////     */
////    @RequestMapping("searchIndex/{index}")
////    public Response<?> searchIndex(@PathVariable String index) {
//////        ElasticSearchUtil esUtil = new ElasticSearchUtil(server);
//////        Map<String, String> result = esUtil.searchIndex(index);
//////        Map<String, MappingMetadata> result = EsUtil.searchIndex(index, server);
////        List<EsColumnInfo> result = EsUtil.searchIndex(index, server);
////        System.out.println(result);
////        return ResponseSupport.successResponse(result);
////    }
//
//    /**
//     * 查询所有索引信息
//     * @return
//     */
//    @RequestMapping("searchAllIndex")
//    public Response<?> searchIndex() {
////        ElasticSearchUtil esUtil = new ElasticSearchUtil(server);
////        Map<String, Set<AliasMetadata>> stringSetMap = esUtil.searchAllIndex();
//        List<String> strings = Es6Util.searchAllIndex(server);
//        System.out.println(strings);
//        return ResponseSupport.successResponse(strings);
//    }
//
////    @RequestMapping("search")
////    public Response<?> search() {
////        Map<String, Set<AliasMetadata>> stringSetMap = EsUtil.search(server);
////        System.out.println(stringSetMap);
////        return ResponseSupport.successResponse(stringSetMap);
////    }
//
//}
