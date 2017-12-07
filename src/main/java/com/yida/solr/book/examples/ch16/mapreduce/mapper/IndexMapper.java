package com.yida.solr.book.examples.ch16.mapreduce.mapper;

import com.yida.solr.book.examples.ch16.mapreduce.Params;
import com.yida.solr.book.examples.ch16.mapreduce.fieldmapping.FieldMapping;
import com.yida.solr.book.examples.ch16.mapreduce.parser.CSVParser;
import com.yida.solr.book.examples.ch16.mapreduce.parser.DocumentParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/15 19:56
 * Solr索引Mapper抽象类
 */
public abstract class IndexMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private static final Logger LOG = LoggerFactory.getLogger(IndexMapper.class);

    /**SolrJ客户端对象*/
    protected SolrClient client;

    /**Solr索引文档对象解析器*/
    protected DocumentParser parser;
    /**Solr域名称映射器*/
    protected FieldMapping mapping;

    @Override
    protected void setup(Context context) throws IOException,
            InterruptedException {
        Configuration conf = context.getConfiguration();
        boolean solrCloud = conf.getBoolean(Params.SOLR_CLOUD_ENABLE,false);
        if(solrCloud) {
            String zkHost = conf.get(Params.ZK_HOST);
            if(null == zkHost || "".equals(zkHost)) {
                throw new RuntimeException("zkHost MUST not be null.");
            }
            boolean onlySendToLeader = conf.getBoolean(Params.SOLR_ONLYY_SEND_TO_LEADER,false);
            String defaultCollection = conf.get(Params.SOLR_DEFAULT__COLLECTION);
            int zkClientTimeout = conf.getInt(Params.ZK_CLIENT_TIMEOUT,30000);
            int zkConnectTimeout = conf.getInt(Params.ZK_CONNECT_TIMEOUT,30000);
            boolean parallelUpdate = conf.getBoolean(Params.SOLR_PARALLER_UPDATE,true);
            String idField = conf.get(Params.SOLR_ID__FIELD,"id");
            int timeToLive = conf.getInt(Params.SOLR_COLLECTION_TTL,2);
            CloudSolrClient cloudClient = (CloudSolrClient)client;
            cloudClient = new CloudSolrClient(zkHost, onlySendToLeader);
            cloudClient.setDefaultCollection(defaultCollection);
            cloudClient.setZkClientTimeout(zkClientTimeout);
            cloudClient.setZkConnectTimeout(zkConnectTimeout);
            cloudClient.setParallelUpdates(parallelUpdate);
            cloudClient.setIdField(idField);
            cloudClient.setCollectionCacheTTl(timeToLive);
        } else {
            String solrURL = conf.get(Params.SOLR_URL);
            if(null == solrURL || "".equals(solrURL)) {
                throw new RuntimeException("Solr Server accessd URL MUST not be null.");
            }
            int queueSize = conf.getInt(Params.SOLR_REQUEST_QUEUE_SIZE,200);
            int threads = conf.getInt(Params.SOLR_REQUEST__THREADS,20);
            this.client = new ConcurrentUpdateSolrClient(solrURL,queueSize,threads);
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        boolean skipFirst = false;
        if(key.get() == 0) {
            Configuration conf = context.getConfiguration();
            skipFirst = conf.getBoolean(Params.SKIP_FIRST_LINE,false);
            //如果用户配置了跳过第一行
            if(skipFirst) {
                return;
            }
        }
        SolrInputDocument doc = this.parser.parse(value.toString());
        if(null == doc) {
            return;
        }
        try {
            this.client.add(doc);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException,
            InterruptedException {
        try {
            //设置为软提交
            this.client.commit(false,false,true);
        } catch (SolrServerException ex) {
            IndexMapper.LOG.error(ex.getMessage());
        }
    }

    public DocumentParser getParser() {
        return parser;
    }

    public void setParser(DocumentParser parser) {
        this.parser = parser;
    }

    public FieldMapping getMapping() {
        return mapping;
    }

    public void setMapping(FieldMapping mapping) {
        this.mapping = mapping;
    }
}
