package com.dada.dm.qujia.controller;

import com.dada.dm.qujia.model.HbaseColumn;
import com.dada.dm.qujia.model.Response;
import com.dada.dm.qujia.model.ResponseSupport;
import com.dada.dm.qujia.util.HBaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @description hbase test
 * @className HbaseTestController
 * @packageName com.dada.dm.qujia.controller
 * @author jt.Qu
 * @date 2022/5/15 09:59
 */

@RestController
@Slf4j
@RequestMapping("/api/hbase")
public class HbaseTestController {

//    private String servers = "10.236.192.11:2181,10.236.192.12:2181,10.236.192.13:2181";
//    private String servers = "10.231.128.95:2181";
//    private String servers = "10.236.192.11:2181,10.236.192.12:2181,10.236.192.13:2181";
//    private String servers = "10.231.128.95:2181/hbase";
    private String servers = "10.231.128.95:2181";
//    private String servers = "10.231.128.95:2181/default";


    /**
     * 新增表 (支持批量，这里就单个作为演示)
     */
    @RequestMapping("create/{tableName}/{columnFamily}")
    public Response<?> create(@PathVariable String tableName,@PathVariable String[] columnFamily) {
        boolean table = HBaseUtil.createTable(tableName, Arrays.asList(columnFamily), servers);
        return ResponseSupport.successResponse(table);
    }

    /**
     * 查询table是否存在
     */
    @RequestMapping("/tableExists/{tableName}")
    public Response<?> tableExists(@PathVariable String tableName) {
        boolean exists = HBaseUtil.isExists(tableName, servers);
        return ResponseSupport.successResponse(exists);
    }

    /**
     * 删除table
     */
    @GetMapping("delete/{tableName}")
    public Response<?> delete(@PathVariable String tableName) {
        boolean flag = HBaseUtil.dropTable(tableName, servers);
        return ResponseSupport.successResponse(flag);
    }

    /**
     * 查询所有allTable
     */
    @RequestMapping("allTable")
    public Response<List<String>> allTable() {
        log.info("----allTable-----");
        List<String> allTable = HBaseUtil.allTable(servers);
        return ResponseSupport.successResponse(allTable);
    }

    /**
     * 获取表列信息
     * @param tableName
     * @return
     */
    @RequestMapping("tableColumns/{tableName}")
    public Response<?> tableColumns(@PathVariable String tableName) {
        log.info("----tableColumns-----");
        List<HbaseColumn> tableColumns = HBaseUtil.tableColumns(tableName,servers);
        return ResponseSupport.successResponse(tableColumns);
    }

    /**
     * 新增数据
     * @param tableName
     * @param family
     * @param column
     * @param value
     * @return
     */
    @RequestMapping("table/put/{tableName}/{rowKey}/{family}/{column}/{value}")
    public Response<?> tablePut(@PathVariable String tableName,@PathVariable String rowKey,
                                    @PathVariable String family,@PathVariable String column,
                                    @PathVariable String value) {
        log.info("----tablePut-----");
        HBaseUtil.tablePut(tableName,rowKey,family,column,value,servers);
        return ResponseSupport.successResponse(true);
    }
}
