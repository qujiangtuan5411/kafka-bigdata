package com.dada.dm.qujia.model;

import lombok.Data;

/**
 * @author jt.Qu
 * @description hbase column
 * @program: kafka-demo
 * @date 2022-05-14 21:38
 */
@Data
public class HbaseColumn {

     private String rowKey;
     private String columnFamily;
     private String columnName;
     private String value;

     public HbaseColumn() {
     }

     public HbaseColumn(String rowKey, String columnFamily, String columnName) {
          this.rowKey = rowKey;
          this.columnFamily = columnFamily;
          this.columnName = columnName;
     }

     public HbaseColumn(String rowKey, String columnFamily, String columnName, String value) {
          this.rowKey = rowKey;
          this.columnFamily = columnFamily;
          this.columnName = columnName;
          this.value = value;
     }
}
