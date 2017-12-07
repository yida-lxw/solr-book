package com.yida.solr.book.examples.ch14.index;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * SolrCloud模式下添加索引文档测试
 */
public class TestSolrCloudIndex {
    public static Logger log = Logger.getLogger(TestSolrCloudIndex.class);
    /**
     * Zookeeper集群节点，多个使用逗号分割
     */
    private static final String ZK_HOST = "linux.yida01.com:2181,linux.yida02.com:2181,linux.yida03.com:2181";

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        int totalDocument = 100;
        CloudSolrClient client = createCloudSolrClient(ZK_HOST,"books");
        client.connect();
        for(int i=0; i < totalDocument; i++) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id",i + 1);
            doc.addField("book_name","book_" + (i+1));
            addDocWithRetry(client,doc,2);
        }
        //设置为硬提交
        client.commit(true,true,false);
        long end = System.currentTimeMillis();
        System.out.println("add document to SolrCloud have take " + (end - start) + " ms.");
        client.close();
    }

    /**
     * 创建CloudSolrClient实例
     *
     * @param zkHost
     * @return
     */
    public static CloudSolrClient createCloudSolrClient(String zkHost,String defaultCollection) {
        //是否只将索引文档更新请求发送给Shard Leader
        boolean onlySendToLeader = true;
        //指定索引文档属于哪个Collection
        //String defaultCollection = "books";
        //Zookeeper客户端连接Zookeeper集群的超时时间，默认值10000，单位：毫秒
        int zkClientTimeout = 30000;
        //Zookeeper Server端等待客户端成功连接的最大超时时间，默认值10000，单位：毫秒
        int zkConnectTimeout = 30000;
        CloudSolrClient client = new CloudSolrClient(zkHost, onlySendToLeader);
        client.setDefaultCollection(defaultCollection);
        client.setZkClientTimeout(zkClientTimeout);
        client.setZkConnectTimeout(zkConnectTimeout);
        //设置是否并行更新索引文档
        client.setParallelUpdates(true);
        //显式设置索引文档的UniqueKey域，默认值就是id
        client.setIdField("id");
        //设置Collection缓存的存活时间，默认值为1，单位：分钟
        client.setCollectionCacheTTl(2);
        return client;
    }

    /**
     * 添加索引文档并支持自动重试
     * @param client
     * @param doc
     * @param retryInSecs  添加失败后等待retryInSecs秒后自动重试一次，单位：秒
     * @throws Exception
     */
    protected static void addDocWithRetry(CloudSolrClient client, SolrInputDocument doc, int retryInSecs)
            throws Exception {
        try {
            client.add(doc);
        } catch (Exception solrExc) {
            Throwable rootCause = SolrException.getRootCause(solrExc);
            //只有IOException才进行重试
            if (rootCause instanceof IOException) {
                log.error(rootCause.getClass().getSimpleName() +
                        " when trying to send a document to SolrCloud, will re-try after waiting " +
                        retryInSecs + " seconds; " + rootCause);
                try {
                    Thread.sleep(retryInSecs * 1000);
                } catch (InterruptedException ignor) {
                }
                // 重试添加索引文档
                client.add(doc);
            }
        }
    }
}
