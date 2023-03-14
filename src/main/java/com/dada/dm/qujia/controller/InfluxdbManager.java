package com.dada.dm.qujia.controller;

import com.alibaba.fastjson.JSONObject;
import com.dada.dm.qujia.util.InfluxdbUtil;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Pong;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jt.Qu
 * @description influxdb 管理
 * @program: kafka-demo
 * @date 2022-12-26 10:19
 */
@Component
public class InfluxdbManager {

    public static void main(String[] args) {
        InfluxdbUtil influxdbUtil = new InfluxdbUtil("admin","admin","http://localhost:8099","mydb","influx_retention");
        /**
         * 测试连接
         */
        boolean pong = pong(influxdbUtil);
        System.out.println("pong:"+pong);


        boolean insert = insertInfluxdb(influxdbUtil);
        System.out.println("insert:"+insert);

        String query = queryInfluxdb(influxdbUtil);
        System.out.println("query:"+query);

    }

    /**
     * 测试连接
     * @param influxdbUtil
     * @return
     */
    private static boolean pong(InfluxdbUtil influxdbUtil) {
        boolean isConnected = false;
        boolean ping = influxdbUtil.ping();
        if(ping){
            isConnected = true;
            System.out.println("influxdb 数据库连接成功");
        }else{
            System.out.println("influxdb 数据库连接失败");
        }
        return isConnected;

    }

    /**
     * 插入数据
     * @param influxdbUtil
     * @return
     */
    private static boolean insertInfluxdb(InfluxdbUtil influxdbUtil) {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("altitude","1500"); //海拔
        tags.put("area","北"); //地区

        HashMap<String, Object> fields = new HashMap<>();
        fields.put("temperature",5); //温度
        fields.put("humidity",1); //湿度

        String measurement = "weather";

        influxdbUtil.insert(measurement,tags,fields,System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     *
     * 查询数据
     * @param influxdbUtil
     * @return
     */
    private static String queryInfluxdb(InfluxdbUtil influxdbUtil) {
        String sql = "select * from weather";
        QueryResult query = influxdbUtil.query(sql);
        List<QueryResult.Result> results = query.getResults();
        String json = JSONObject.toJSONString(results);
        return json;
    }


}
