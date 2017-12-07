package com.yida.solr.book.examples.ch16.sql.http;

import com.yida.solr.book.examples.ch14.index.TestSolrCloudIndex;
import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/21 22:33
 * 测试通过HTTP方式来发送Solr SQL查询请求
 */
public class TestHTTPSQL {
    /**
     * Zookeeper集群节点，多个使用逗号分割
     */
    private static final String ZK_HOST = "linux.yida01.com:2181,linux.yida02.com:2181,linux.yida03.com:2181";
    private static final String COLLECTION_NAME = "logs";
    public static void main(String[] args) throws Exception {
        CloudSolrClient client = createCloudSolrClient(ZK_HOST,COLLECTION_NAME);
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/sql");
        solrQuery.setRows(10);
        solrQuery.set("aggregationMode","facet");
        solrQuery.set("collection",COLLECTION_NAME);
        solrQuery.set("stmt","SELECT id,log_level,log_msg FROM " + COLLECTION_NAME + " ORDER BY id LIMIT 10");
        //由于这里使用的是SolrJ5，所以这里无法使用JsonMapResponseParser
        //client.setParser(new DelegationTokenResponse.JsonMapResponseParser());
        QueryResponse resp = client.query(solrQuery, SolrRequest.METHOD.POST);
        NamedList<Object> nameList = resp.getResponse();
        System.out.println(nameList.toString());
        client.close();
    }

    public static CloudSolrClient createCloudSolrClient(String zkHost, String defaultCollection) {
        return  createCloudSolrClient(zkHost,defaultCollection,null);
    }

    public static CloudSolrClient createCloudSolrClient(String zkHost, String defaultCollection, HttpClient httpClient) {
        //是否只将索引文档更新请求发送给Shard Leader
        boolean onlySendToLeader = true;
        //指定索引文档属于哪个Collection
        //String defaultCollection = "books";
        //Zookeeper客户端连接Zookeeper集群的超时时间，默认值10000，单位：毫秒
        int zkClientTimeout = 30000;
        //Zookeeper Server端等待客户端成功连接的最大超时时间，默认值10000，单位：毫秒
        int zkConnectTimeout = 30000;
        CloudSolrClient client = new CloudSolrClient(zkHost, onlySendToLeader,httpClient);
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
}
