package com.yida.solr.book.examples.ch16.mapreduce;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/15 21:19
 * 参数名称
 */
public class Params {
    public static final String DEPEND_JARS = "libjars";
    public static final String CSV_HEADERS = "csv.headers";
    public static final String SKIP_FIRST_LINE = "skip.firstLine";
    public static final String SOLR_CLOUD_ENABLE = "solr.cloud.enable";
    public static final String SOLR_URL = "solr.url";
    public static final String SOLR_ONLYY_SEND_TO_LEADER = "solr.onlySendToLeader";
    public static final String SOLR_PARALLER_UPDATE = "solr.parallelUpdate";
    public static final String SOLR_COLLECTION_TTL = "solr.timeToLive";
    public static final String SOLR_REQUEST_QUEUE_SIZE = "solr.request.queueSize";
    public static final String SOLR_REQUEST__THREADS = "solr.request.threads";
    public static final String SOLR_DEFAULT__COLLECTION = "solr.defaultCollection";
    public static final String SOLR_ID__FIELD = "solr.idField";

    public static final String ZK_HOST = "zk.host";
    public static final String ZK_CLIENT_TIMEOUT = "zk.clientTimeout";
    public static final String ZK_CONNECT_TIMEOUT = "zk.connectTimeout";


    public static final String HDFS_URL = "fs.defaultFS";
    public static final String HDFS_INPUT_PATH = "hdfs.input";
    public static final String HDFS_INPUT_DIR = "hdfs.inputDir";
    public static final String HDFS_OUT_PATH = "hdfs.out";

    public static final String MAPREDUCE_TASKS = "mapreduce.tasks";
    public static final String MAPREDUCE_JOB_NAME = "mapreduce.jobName";
    public static final String HADOOP_USER = "hadoop.user";



}
