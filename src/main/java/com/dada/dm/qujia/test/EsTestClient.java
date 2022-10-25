package com.dada.dm.qujia.test;

import com.dada.dm.qujia.model.EsColumnInfo;
import com.dada.dm.qujia.util.EsUtil;
import org.springframework.stereotype.Component;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author jt.Qu
 * @description es test
 * @program: kafka-demo
 * @date 2022-04-25 16:14
 */
@Component
public class EsTestClient {

    private String server = "192.168.1.9:9201,192.168.1.9:9202,192.168.1.9:9203";

    /**
     * 创建索引
     */
    @Test
    public void createIndex(){
        String index = "user_info_2";
//        ElasticSearchUtil esUtil = new ElasticSearchUtil(server);
//        boolean flag = esUtil.createIndex(index);
        boolean flag = EsUtil.createIndex(index, server);
        System.out.println(flag);
    }

    /**
     * 查看索引是否存在
     */
    @Test
    public void searchIndexExists(){
        String index = "user";
//        ElasticSearchUtil esUtil = new ElasticSearchUtil(server);
//        boolean flag = esUtil.searchIndexExists(index);
        boolean flag = EsUtil.searchIndexExists(index, server);
        System.out.println(flag);
    }

    /**
     * 查询索引信息
     */
    @Test
    public void searchIndex(){
        String index = "user";
//        ElasticSearchUtil esUtil = new ElasticSearchUtil(server);
//        Map<String, String> result= esUtil.searchIndex(index);
//        Map<String, MappingMetadata> result = EsUtil.searchIndex(index, server);
        List<EsColumnInfo> result = EsUtil.searchIndex(index, server);
        System.out.println(result);
    }

}
