package com.yida.solr.book.examples.ch14;

import com.yida.solr.book.examples.ch14.index.TestSolrCloudIndex;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

/**
 * Created by Lanxiaowei
 */
public class TestSolrCloudQuery {
    public static Logger log = Logger.getLogger(TestSolrCloudQuery.class);
    /**
     * Zookeeper集群节点，多个使用逗号分割
     */
    private static final String ZK_HOST = "linux.yida01.com:2181,linux.yida02.com:2181,linux.yida03.com:2181";


    public static void main(String[] args) throws Exception {
        CloudSolrClient client = TestSolrCloudIndex.createCloudSolrClient(ZK_HOST,"books");
        SolrQuery solrQuery = new SolrQuery("*:*");
        solrQuery.setRows(0);
        //显式指定在哪个Collection上执行查询，由于我们在创建CloudSolrClient实例时，
        // 已经通过setDefaultCollection设置了默认查询的Collection，因此这里你可以不用指定Collection
        solrQuery.set("collection","books");
        //你也可以调用client.query("books",solrQuery);第一个参数即表示你想要查询的Collection
        QueryResponse resp = client.query(solrQuery);
        SolrDocumentList hits = resp.getResults();
        log.info("Match all docs distributed query found " + hits.getNumFound() + " docs.");

        //显式指定在shard1这个分片上执行查询
        solrQuery.set("shards", "shard1");
        resp = client.query(solrQuery);
        hits = resp.getResults();
        log.info("Match all docs non-distributed query to shard1 found "+hits.getNumFound()+" docs.");
        client.close();
    }
}
