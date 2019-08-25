package com.wesley.growth.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.io.Serializable;

/**
 * <p>
 * HBase操作工具类
 * </p>
 * Email yani@uoko.com
 * @author Created by Yani on 2018/10/31
 */
@Slf4j
public class HbaseUtil implements Serializable {

    private static final String HBASE_ROOT_DIR = "hbase.rootdir";
    private static final String ZK_QUORUM = "hbase.zookeeper.quorum";

    private static HbaseUtil hbaseUtil = null;

    private Connection connection;

    private HbaseUtil() {
        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();
        Configuration configuration = HBaseConfiguration.create();
        try {
            configuration.set(HBASE_ROOT_DIR, propertiesUtil.getProperty(HBASE_ROOT_DIR));
            configuration.set(ZK_QUORUM, propertiesUtil.getProperty(ZK_QUORUM));
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            log.error("HbaseUtil 初始化出错",e);
        }
    }

    /**
     * @param tableName 表名
     * @param rowkey 主键
     * @param family 列族
     * @param qualifier 字段名称
     * @param value 值
     */
    public void put(String tableName, String rowkey, String family, String qualifier, Object value){
        TableName tName = TableName.valueOf(tableName);
        try {
            Admin admin = connection.getAdmin();
            if(!admin.tableExists(tName)){
                log.warn("{} 表不存在!", tableName);
                return;
            }

            try(Table table = connection.getTable(tName)){
                Put put = new Put(rowkey.getBytes());
                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value.toString()));
                table.put(put);
            }
        } catch (IOException e) {
            log.error("插入数据出错", e);
        }
    }

    public void incrementValue(String tableName, String rowkey, String family, String qualifier, long value){
        TableName tName = TableName.valueOf(tableName);

        try(Table table = connection.getTable(tName)){
            table.incrementColumnValue(Bytes.toBytes(rowkey), Bytes.toBytes(family), Bytes.toBytes(qualifier), value);
        }catch (IOException e){
            log.error("插入数据出错", e);
        }
    }

    public boolean existRowkey(String tableName, String rowkey){
        TableName tName = TableName.valueOf(tableName);
        try(Table table = connection.getTable(tName)){
            return table.get(new Get(Bytes.toBytes(rowkey))).size() > 0;
        }catch (IOException e){
            log.error("查询数据出错", e);
        }
        return false;
    }

    public static HbaseUtil getInstance(){
        return HbaseUtilInstance.instance;
    }

    private static class HbaseUtilInstance{
        static HbaseUtil instance = new HbaseUtil();
    }

}
