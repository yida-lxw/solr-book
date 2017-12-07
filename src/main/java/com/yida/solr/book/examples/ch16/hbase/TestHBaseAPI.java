package com.yida.solr.book.examples.ch16.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/17 13:53
 * 测试HBase Java API
 */
public class TestHBaseAPI {
    private static final String REGEX_SPECIAL_CHAR = "*.?+$^[](){}|\\/";

    /**
     * 创建表
     * @param tableName       表名称
     * @param columnFamilies  列簇
     * @throws IOException
     */
    public static void createTable(String tableName,String[] columnFamilies) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HBaseAdmin admin = new HBaseAdmin(conf);
        //如果表已存在，则什么都不做
        if(admin.tableExists(tableName)) {
            return;
        }
        HTableDescriptor tableDesc = new HTableDescriptor(tableName);
        if(null != columnFamilies && columnFamilies.length > 0) {
            for(String columnFamily : columnFamilies) {
                tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            }
        }
        admin.createTable(tableDesc);
    }

    /**
     * 删除指定表
     * @param tableName
     * @throws IOException
     */
    public static void delteTable(String tableName) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HBaseAdmin admin=new HBaseAdmin(conf);
        if(admin.tableExists(tableName)){
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }
    }

    /**
     * 为表添加一个列簇
     * @param tableName       表名称
     * @param columnFamily    列簇
     * @throws IOException
     */
    public static void addColumnFamily(String tableName,String columnFamily) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor tableDesc = new HTableDescriptor(tableName);
        tableDesc.addFamily(new HColumnDescriptor(columnFamily));
        admin.modifyTable(tableName,tableDesc);
    }

    /**
     * 为表添加多个列簇
     * @param tableName
     * @param columnFamilies
     * @throws IOException
     */
    public static void addColumnFamilies(String tableName,String[] columnFamilies) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor tableDesc = new HTableDescriptor(tableName);
        if(null != columnFamilies && columnFamilies.length > 0) {
            for(String columnFamily : columnFamilies) {
                tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            }
        }
        admin.modifyTable(tableName,tableDesc);
    }

    /**
     * 往表的指定列簇里添加一个列
     * @param tableName       表名称
     * @param columnFamily    列簇名称
     * @param rowKey          行主键
     * @param columnName      列名称
     * @param columnValue     列值
     */
    public static void addColumn(String tableName,String columnFamily,String rowKey,
            String columnName,String columnValue) throws  IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        Put put = new Put(rowKey.getBytes());
        put.add(columnFamily.getBytes(), columnName.getBytes(), null == columnValue? null : columnValue.getBytes());
        HTable table = new HTable(conf, tableName);
        table.put(put);
        table.close();
    }

    /**
     * 往表的指定列簇里添加多个列
     * @param tableName       表名称
     * @param columnFamily    列簇名称
     * @param rowKey          行主键
     * @param columns         多个列的Map结构，key为列名称，value为列值
     * @throws IOException
     */
    public static void addColumns(String tableName,String columnFamily,String rowKey,
        Map<String,String> columns) throws  IOException {
        if(null == columns || columns.size() <= 0) {
            return;
        }
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        Put put = new Put(rowKey.getBytes());
        for(Map.Entry<String,String> entry : columns.entrySet()) {
            String columnName = entry.getKey();
            String columnValue = entry.getValue();
            put.add(columnFamily.getBytes(), columnName.getBytes(), null == columnValue? null : columnValue.getBytes());
        }
        HTable table = new HTable(conf, tableName);
        table.put(put);
        table.close();
    }

    /**
     * 为表的指定列簇添加多行
     * @param tableName       表名称
     * @param columnFamily    列簇名称
     * @param rowKeys         行主键
     * @param columns         rowKey + columnFamily下的每列的列值
     * @throws IOException
     */
    public static void addRows(String tableName,String columnFamily,String[] rowKeys,
        List<Map<String,String>> columns) throws  IOException {
        if(null == columns || columns.size() <= 0 ||
                null == rowKeys || rowKeys.length <= 0) {
            return;
        }
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        List<Put> puts = new ArrayList<Put>();
        for(int i=0; i < rowKeys.length; i++) {
            String rowKey = rowKeys[i];
            //当前rowKey下的columnFamily的每一列的列值
            Map<String,String> columnMap = columns.get(i);
            Put put = new Put(rowKey.getBytes());
            for(Map.Entry<String,String> entry : columnMap.entrySet()) {
                //列名称
                String columnName = entry.getKey();
                //列值
                String columnValue = entry.getValue();
                put.add(columnFamily.getBytes(), columnName.getBytes(),
                    null == columnValue? null : columnValue.getBytes());
            }
            puts.add(put);
        }
        HTable table = new HTable(conf, tableName);
        table.put(puts);
        table.close();
    }

    /**
     * 为表的多个列簇添加多行
     * @param tableName       表名称
     * @param rowKeys         rowKey数组
     * @param columnFamilies  列簇名称数组
     * @param columns         key为rowKey + columnFamily + columnName,value为列值
     * @param splitChar       columns参数的key的分隔符，默认是使用+加号来分割rowKey、columnFamily、columnName
     * @throws IOException
     */
    public static void addRows(String tableName,String[] columnFamilies,String[] rowKeys,
        Map<String,String> columns,String splitChar) throws  IOException {
        if(null == columns || columns.size() <= 0) {
            return;
        }
        if(null == splitChar || "".equals(splitChar)) {
            splitChar = "+";
        }
        //如果指定的分隔符是正则表达式中的特殊字符，则需要使用反斜杠进行转义
        if(REGEX_SPECIAL_CHAR.contains(splitChar)) {
            splitChar = "\\" + splitChar;
        }
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        List<Put> puts = new ArrayList<Put>();
        for(String rowKey : rowKeys) {
            Put put = new Put(rowKey.getBytes());
            for(String columnFamily : columnFamilies) {
                for(Map.Entry<String,String> entry : columns.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String[] keys = key.split(splitChar);
                    String columnName = keys[2];
                    if(key.startsWith(rowKey + splitChar + columnFamily)) {
                        put.add(columnFamily.getBytes(), columnName.getBytes(), null == value? null : value.getBytes());
                    }
                }
            }
            puts.add(put);
        }
        HTable table = new HTable(conf, tableName);
        table.put(puts);
        table.close();
    }

    /**
     * 删除表的指定列簇
     * @param tableName       表名称
     * @param columnFamily    待删除的列簇名称
     */
    public static void deleteColumnFamily(String tableName,String columnFamily) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HBaseAdmin admin = new HBaseAdmin(conf);
        admin.deleteColumn(tableName,columnFamily);
    }

    /**
     * 删除表的某一行的指定列簇
     * @param tableName       表名称
     * @param rowKey          行主键
     * @param columnFamily    待删除的列簇名称
     */
    public static void deleteColumnFamilyOfRow(String tableName,String rowKey,String columnFamily) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table = new HTable(conf, tableName);
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.deleteFamily(Bytes.toBytes(columnFamily));
        table.delete(delete);
        table.close();
    }

    /**
     * 批量删除表的多个列簇
     * @param tableName          表名称
     * @param rowKey             行主键
     * @param columnFamilies    列名称数组
     * @throws IOException
     */
    public static void deleteColumnFamiliesOfRow(String tableName,String rowKey,String[] columnFamilies) throws IOException {
        if(null == columnFamilies || columnFamilies.length <= 0) {
            return;
        }
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table = new HTable(conf, tableName);
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        for(String columnFamily : columnFamilies) {
            delete.deleteFamily(Bytes.toBytes(columnFamily));
        }
        table.delete(delete);
        table.close();
    }

    /**
     * 删除表的指定行指定列簇的指定列
     * @param tableName       表名称
     * @param rowKey          行主键
     * @param columnName      列名称
     * @param columnFamily    待删除的列簇名称
     */
    public static void deleteColumnFamilyOfRow(String tableName,String rowKey,String columnFamily,
        String columnName) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table = new HTable(conf, tableName);
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.deleteColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));
        table.delete(delete);
        table.close();
    }

    /**
     * 根据RowkKey获取一行数据
     * @param tableName    表名称
     * @param rowKey       行主键
     * @return
     * @throws IOException
     */
    public static Result getRowByRowKey(String tableName,String rowKey) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table=new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);
        table.close();
        return result;
    }

    /**
     * 扫描获取Rowkey以指定前缀开头的所有行数据
     * @param tableName       表名称
     * @param prefixRowKey    RowKey前缀
     * @return
     * @throws IOException
     */
    public static List<Result> getRowsByPrefixRowKey(String tableName,String prefixRowKey) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table=new HTable(conf, tableName);
        Scan scan=new Scan();
        scan.setFilter(new PrefixFilter(Bytes.toBytes(prefixRowKey)));
        ResultScanner rs = table.getScanner(scan);
        List<Result> results = new ArrayList<Result>();
        for(Result result : rs){
            results.add(result);
        }
        table.close();
        return results;
    }

    /**
     * 行键区间范围查询，返回符合该条件的所有行数据
     * @param tableName      表名称
     * @param startRowKey    行键区间起始值
     * @param endRowKey      行键区间结束值
     * @return
     * @throws Exception
     */
    public static List<Result> getRowsByRowKeyRange(String tableName,
        String startRowKey,String endRowKey) throws Exception {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table=new HTable(conf, tableName);
        Scan scan=new Scan();
        scan.setStartRow(Bytes.toBytes(startRowKey));
        scan.setStopRow(Bytes.toBytes(endRowKey));
        ResultScanner rs=table.getScanner(scan);
        List<Result> results = new ArrayList<Result>();
        for(Result result : rs){
            results.add(result);
        }
        table.close();
        return results;
    }

    /**
     * 获取指定表的指定列簇的指定列的列值
     * @param tableName       表名称
     * @param columnFalimy    列簇名称
     * @param columnName      列名称
     * @return
     * @throws Exception
     */
    public static List<String> getColumnValues(String tableName,String columnFalimy,String columnName) throws Exception{
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table = new HTable(conf, tableName);
        Scan scan = new Scan();
        ResultScanner rs=table.getScanner(scan);
        List<String> columnValues = new ArrayList<String>();
        for(Result result : rs) {
            String columnValue = new String(result.getValue(Bytes.toBytes(columnFalimy), Bytes.toBytes(columnName)));
            columnValues.add(columnValue);
        }
        table.close();
        return columnValues;
    }

    /**
     * 获取指定表的指定RowKey指定列簇的指定列的列值
     * @param tableName        表名称
     * @param rowKey           行主键
     * @param columnFamily     列簇名称
     * @param columnName       列名称
     * @return
     * @throws IOException
     */
    public static String getColumnValue(String tableName,String rowKey,
        String columnFamily,String columnName) throws IOException {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table=new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);
        String columnValue = new String(result.getValue(
                Bytes.toBytes(columnFamily),Bytes.toBytes(columnName)));
        table.close();
        return columnValue;
    }

    /**
     * 扫描全表获取所有行数据
     * @param tableName    表名称
     * @return
     * @throws Exception
     */
    public static List<Result> getAllRow(String tableName) throws Exception {
        Configuration conf = HBaseConfigUtils.getHBaseConfig();
        HTable table = new HTable(conf, tableName);
        Scan scan=new Scan();
        ResultScanner rs=table.getScanner(scan);
        List<Result> results = new ArrayList<Result>();
        for(Result result : rs){
            results.add(result);
        }
        table.close();
        return results;
    }
}
