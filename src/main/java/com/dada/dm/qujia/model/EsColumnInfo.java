package com.dada.dm.qujia.model;

/**
 * @author jt.Qu
 * @description Es  字段信息
 * @program: kafka-demo
 * @date 2022-05-10 19:11
 */
public class EsColumnInfo {
    private String columnName;
    private String columnType;

    public EsColumnInfo() {
    }

    public EsColumnInfo(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
