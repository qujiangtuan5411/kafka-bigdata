package com.dada.dm.qujia.spark;
import org.apache.spark.sql.jdbc.JdbcDialect;
/**
 * @author jt.Qu
 * @description
 * @program: kafka-demo
 * @date 2022-07-20 14:39
 */
public class HiveSqlDialect extends JdbcDialect {

    @Override
    public boolean canHandle(String url){
        return url.startsWith("jdbc:hive2");
    }

    @Override
    public String quoteIdentifier(String colName) {
        return colName.replace("\"","");
    }

}