package com.yida.solr.book.examples.ch13.query;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * QueryRequest测试
 */
public class TestQueryRequest {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/book";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/select");
        query.set("q", "type:book");
        query.set("fl", "id,brand,color,size,score");
        query.set("indent","true");
        //演示QueryRequest如何使用
        QueryRequest request = new QueryRequest(query, SolrRequest.METHOD.GET);
        NamedList<Object> result = client.request(request);
        System.out.println("\n以下是普通select查询请求的响应信息：\n");
        System.out.println(result.toString());
    }
}
