package com.dada.dm.qujia.util;

import com.dada.dm.qujia.client.HbaseClient;
import com.dada.dm.qujia.model.HbaseColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author jt.Qu
 * @description hbase 工具类
 * @program: kafka-demo
 * @date 2022-04-27 14:23
 */
@Slf4j
public class HBaseUtil {

    /**
     * 判断表是否存在
     *
     * @param tableName 表名
     * @return true/false
     */
    public static boolean isExists(String tableName, String service) {
        Connection conn = HbaseClient.getHbaseConnection(service);
        boolean tableExists = false;
        Admin admin = null;
        try {
            admin = conn.getAdmin();
            TableName table = TableName.valueOf(tableName);
            tableExists = admin.tableExists(table);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, admin);
        }
        return tableExists;
    }

    /**
     * 创建表
     *
     * @param tableName    表名
     * @param columnFamily 列族
     * @return true/false
     */
    public static boolean createTable(String tableName, List<String> columnFamily, String service) {
        return createTable(tableName, columnFamily, null, service);
    }

    /**
     * 预分区创建表
     *
     * @param tableName    表名
     * @param columnFamily 列族
     * @param keys         分区集合
     * @return true/false
     */
    public static boolean createTable(String tableName, List<String> columnFamily, List<String> keys, String service) {
        if (!isExists(tableName, service)) {
            Connection conn = HbaseClient.getHbaseConnection(service);
            Admin admin = null;
            try {
                admin = conn.getAdmin();
                TableName table = TableName.valueOf(tableName);
                HTableDescriptor desc = new HTableDescriptor(table);
                for (String cf : columnFamily) {
                    desc.addFamily(new HColumnDescriptor(cf));
                }
                if (keys == null) {
                    admin.createTable(desc);
                } else {
                    byte[][] splitKeys = getSplitKeys(keys);
                    admin.createTable(desc, splitKeys);
                }
                return true;
            } catch (IOException e) {
                log.error("createTable error -> {}", e.getMessage());
            } finally {
                closeConnection(conn, admin);
            }
        } else {
            System.out.println(tableName + "is exists!!!");
            return false;
        }
        return false;
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    public static boolean dropTable(String tableName, String service) {
        if (isExists(tableName, service)) {
            Connection conn = HbaseClient.getHbaseConnection(service);
            TableName table = TableName.valueOf(tableName);
            Admin admin = null;
            try {
                admin = conn.getAdmin();
                admin.disableTable(table);
                admin.deleteTable(table);
                return true;
            } catch (Exception e) {
                log.error("--dropTable error e->{}", e.getMessage());
            } finally {
                closeConnection(conn, admin);
            }
        }
        return false;
    }

    /**
     * 查询所有allTable
     *
     * @param servers
     * @return
     */
    public static List<String> allTable(String servers) {
        Connection conn = HbaseClient.getHbaseConnection(servers);
        Admin admin = null;
        List<String> tableNameList = new ArrayList<>();
        try {
            admin = conn.getAdmin();
            List<TableDescriptor> tableDescriptors = admin.listTableDescriptors();
            if (!CollectionUtils.isEmpty(tableDescriptors)) {
                tableDescriptors.stream().forEach(table -> {
                    tableNameList.add(table.getTableName().getNameAsString());
                });
            }
        } catch (Exception e) {
            log.error("allTable->{}", e.getMessage());
        } finally {
            closeConnection(conn, admin);
        }
        return tableNameList;
    }

    /**
     * 获取表列信息
     *
     * @param servers
     * @return
     */
    public static List<HbaseColumn> tableColumns(String tbName, String servers) {
        TableName tableName = TableName.valueOf(tbName);
        Connection conn = HbaseClient.getHbaseConnection(servers);
        Admin admin = null;
        List<HbaseColumn> hbaseColumnList = new ArrayList<>();
        try {
            Table table = conn.getTable(tableName);
            Scan scan = new Scan();
            scan.setOneRowLimit();
            scan.setReversed(true);
            ResultScanner scanner = table.getScanner(scan);
            Get g = new Get(scanner.next().getRow());
            Result result = table.get(g);
            for (Cell cell : result.listCells()) {
                String rowKey = new String(cell.getRowArray());
                String columnFamily = new String(cell.getFamilyArray());
                String columnName = new String(cell.getQualifierArray());
                String value = new String(cell.getValueArray());
                hbaseColumnList.add(new HbaseColumn(rowKey, columnFamily, columnName,value));
            }
        } catch (Exception e) {
            log.error("tableColumns->{}", e.getMessage());
        } finally {
            closeConnection(conn, admin);
        }
        return hbaseColumnList;
    }

    /**
     * 新增数据
     * @param tbName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @param servers
     */
    public static boolean tablePut(String tbName, String rowKey, String family, String column, String value, String servers) {
        TableName tableName = TableName.valueOf(tbName);
        Connection conn = HbaseClient.getHbaseConnection(servers);
        Admin admin = null;
        try {
            Table table = conn.getTable(tableName);
            Put put = new Put(rowKey.getBytes());
            put.addColumn(family.getBytes(),column.getBytes(StandardCharsets.UTF_8),value.getBytes());
            table.put(put);
            return true;
        } catch (Exception e) {
            log.error("tablePut->{}", e.getMessage());
        } finally {
            closeConnection(conn, admin);
        }
        return false;
    }





    /**
     * 关闭hbase 连接
     *
     * @param conn
     * @param admin
     */
    private static void closeConnection(Connection conn, Admin admin) {
        if (null != conn) {
            try {
                conn.close();
            } catch (IOException e) {
                log.error("conn close error -> {}", e.getMessage());
            }
        }
        if (null != admin) {
            try {
                admin.close();
            } catch (IOException e) {
                log.error("admin close error -> {}", e.getMessage());
            }
        }
    }

    /**
     * 分区【10, 20, 30】 -> ( ,10] (10,20] (20,30] (30, )
     *
     * @param keys 分区集合[10, 20, 30]
     * @return byte二维数组
     */
    private static byte[][] getSplitKeys(List<String> keys) {
        byte[][] splitKeys = new byte[keys.size()][];
        TreeSet<byte[]> rows = new TreeSet<>(Bytes.BYTES_COMPARATOR);
        for (String key : keys) {
            rows.add(Bytes.toBytes(key));
        }
        int i = 0;
        for (byte[] row : rows) {
            splitKeys[i] = row;
            i++;
        }
        return splitKeys;
    }

}
