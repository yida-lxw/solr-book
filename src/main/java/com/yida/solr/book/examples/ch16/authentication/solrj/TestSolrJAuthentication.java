package com.yida.solr.book.examples.ch16.authentication.solrj;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * Created by Lanxiaowei
 * Solr开启了安全认证之后，SolrJ访问Solr Server的示例代码
 */
public class TestSolrJAuthentication {
    /*** Zookeeper集群节点，多个使用逗号分割*/
    private static final String ZK_HOST = "linux.yida01.com:2181,linux.yida02.com:2181,linux.yida03.com:2181";
    /**Client访问Solr Server的帐号*/
    private static final String USER_NAME = "solr";
    /**Client访问Solr Server的密码*/
    private static final String PASS_WORD = "SolrRocks";
    /**默认访问的Collection名称*/
    private static final String DEFAULT_COLLECTION = "joinTest";
    public static void main(String[] args) throws Exception {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 500);
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 50);
        params.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false);
        HttpClient httpClient = HttpClientUtil.createClient(params);
        httpClient = new InsecureHttpClient(httpClient, USER_NAME, PASS_WORD);
        SolrClient client = createCloudSolrClient(ZK_HOST,DEFAULT_COLLECTION,httpClient);
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","100");
        doc.addField("user_name","Bruce Lee");
        doc.addField("age","33");
        client.add(doc);
        //设置为硬提交
        client.commit(true,true,false);

        SolrQuery solrQuery = new SolrQuery("*:*");
        solrQuery.setRows(0);
        solrQuery.set("collection","joinTest");
        QueryResponse resp = client.query(solrQuery);
        SolrDocumentList hits = resp.getResults();
        System.out.println("Collection total hit:" + hits.getNumFound());

        //显式指定在shard1这个分片上执行查询
        solrQuery.set("shards", "shard1");
        resp = client.query(solrQuery);
        hits = resp.getResults();
        System.out.println("shard1 total hit:" + hits.getNumFound());
        client.close();
    }

    public static CloudSolrClient createCloudSolrClient(String zkHost, String defaultCollection,HttpClient httpClient) {
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
