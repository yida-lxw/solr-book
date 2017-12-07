package com.yida.solr.book.examples.ch13.query;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * Created by Lanxiaowei
 * SolrQuery测试
 */
public class TestSolrQuery {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/book";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        org.apache.solr.client.solrj.SolrQuery query = new org.apache.solr.client.solrj.SolrQuery();
        query.setRequestHandler("/select");
        query.set("q", "type:book");
        query.set("fl", "id,brand,color,size,score");
        query.set("indent","true");

        QueryResponse response = client.query(query, SolrRequest.METHOD.GET);
        System.out.println("\n以下是普通select查询请求的响应信息：\n");
        System.out.println(response.toString());
    }
}
