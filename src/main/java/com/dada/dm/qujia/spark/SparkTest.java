package com.dada.dm.qujia.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.jdbc.JdbcDialects;

/**
 * @author jt.Qu
 * @description spark test
 * @program: kafka-demo
 * @date 2022-07-20 14:38
 */
public class SparkTest {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Spark-Read-Hive-by-Java")
                .setMaster("local[2]");
        SparkSession sparkSession = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();

        HiveSqlDialect hiveSqlDialect = new HiveSqlDialect();
        JdbcDialects.registerDialect(hiveSqlDialect);

        Dataset<Row> rowDataset = sparkSession.read()
                .format("jdbc")
                .option("url", "jdbc:hive2://cdh5.mysql.imdada.local:10000/default")
                .option("dbtable", "table_name")
                .option("user", "bi_dwms")
                .option("password", "")
                .option("driver", "org.apache.hive.jdbc.HiveDriver").load().filter("`table_name.ds`='20210112'");
        rowDataset.show();

//        sparkSession.sql();

    }
}
