package com.yida.solr.book.examples.ch16.hbase;

import org.apache.hadoop.conf.Configuration;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/17 13:54
 * HBase配置工具类
 */
public class HBaseConfigUtils {
    public static Configuration getHBaseConfig(){
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://linux.yida02.com:9000/");
        conf.set("hbase.zookeeper.quorum",
                "linux.yida01.com,linux.yida02.com,linux.yida03.com");
        return conf;
    }
}
