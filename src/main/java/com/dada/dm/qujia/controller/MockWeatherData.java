package com.dada.dm.qujia.controller;

import com.dada.dm.qujia.util.InfluxdbUtil;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author jt.Qu
 * @description 模拟数据生成器
 * @program: kafka-demo
 * @date 2022-12-26 16:49
 */
public class MockWeatherData {

    public static void main(String[] args) throws InterruptedException {
        InfluxdbUtil influxdbUtil = new InfluxdbUtil("admin","admin","http://localhost:8099","mydb","influx_retention");

        Faker faker = new Faker(new Locale("zh-CN"));

        String[] areas = {"南","北","东","西"};

        Integer[] altitudes = {500, 800, 1000, 1500};

        int k = 1;
        while(true){
            System.out.println("----->插入第"+k+"条数据");
            int altitude_index = (int)Math.floor(Math.random() * altitudes.length);
            int areas_index = (int)Math.floor(Math.random() * areas.length);

            Map<String, String> tags = new HashMap<>();
            tags.put("altitude",altitudes[altitude_index]+"");
            tags.put("area",areas[areas_index]+"");

            Map<String, Object> fileds = new HashMap<>();
            fileds.put("temperature",faker.number().numberBetween(10,30));
            fileds.put("humidity",faker.number().numberBetween(-10,10));

            influxdbUtil.insert("weather",tags,fileds,System.currentTimeMillis(), TimeUnit.MILLISECONDS);

            Thread.sleep(faker.number().numberBetween(500,1000));

            k++;
        }


    }
}
