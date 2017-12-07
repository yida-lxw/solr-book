package com.yida.solr.book.examples.ch12.realtimeget;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * Real-time Get索引测试
 */
public class IndexRealTimeGet {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/real-time-get";
    public static void main(String[] args) throws Exception {
        HttpSolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        //这个用于设置硬提交
        //request.setAction(UpdateRequest.ACTION.COMMIT, true, true);
        //设置在2万秒之后，自动触发一次软提交，commitWith默认是软提交，
        // 软提交方式默认的查询方式是获取不到最新的索引数据的
        request.setCommitWithin(20000000);
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","1");
        doc.addField("name","This is a Real-time Get Test1.");
        request.add(doc);
        System.out.println(request.getXML());
        NamedList<Object> result = client.request(request);
        System.out.println("Result: " + result);

        SolrQuery query = new SolrQuery();
        //Real time GET查询测试
        query.setRequestHandler("/get");
        query.set("id", "1");
        query.set("fl", "id,name");
        QueryResponse response = client.query(query);
        System.out.println("以下是Real time GET的响应信息：\n");
        System.out.println(response.toString());

        //普通select查询测试
        query.setRequestHandler("/select");
        query.set("q", "id:1");
        query.set("fl", "id,name");
        response = client.query(query);
        System.out.println("\n以下是普通select查询请求的响应信息：\n");
        System.out.println(response.toString());
    }
}
