package com.dada.dm.qujia.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author jt.Qu
 * @description influxdb 工具类
 * @program: kafka-demo
 * @date 2022-12-26 10:12
 */
@Data
@NoArgsConstructor
public class InfluxdbUtil {

    private String username;
    private String password;
    private String openurl;
    private String database;
    private String retentionPolicy;

    private InfluxDB influxDB;

    public InfluxdbUtil(String username, String password, String openurl, String database) {
        this.username = username;
        this.password = password;
        this.openurl = openurl;
        this.database = database;
        influxDB = getInfluxDB();
    }

    public InfluxdbUtil(String username, String password, String openurl, String database, String retentionPolicy) {
        this.username = username;
        this.password = password;
        this.openurl = openurl;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        influxDB = getInfluxDB();
    }

    /**
     * 连接influxdb
     * @return
     */
    public InfluxDB getInfluxDB() {
        if(influxDB == null){
            return InfluxDBFactory.connect(openurl,username,password);
        }
        return influxDB;
    }

    public boolean ping(){
        boolean isConnected = false;
        Pong ping = influxDB.ping();
        if(ping != null){
            isConnected = true;
        }
        return isConnected;
    }

    /**
     * 插入数据
     * @param measurement
     * @param tags
     * @param fields
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean insert(String measurement, Map<String,String> tags, Map<String,Object> fields, long time, TimeUnit timeUnit){
        Point.Builder builder = Point.measurement(measurement);

        builder.tag(tags);
        builder.fields(fields);

        if(time != 0){
            builder.time(time,timeUnit);
        }
        influxDB.write(database,retentionPolicy,builder.build());

        return false;
    }

    /**
     * 查询 influxdb
     * @param sql
     * @return
     */
    public QueryResult query(String sql){
        return influxDB.query(new Query(sql, database));
    }


}
