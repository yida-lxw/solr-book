package com.yida.solr.book.examples.ch13.query;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * Created by Lanxiaowei
 * 演示如何通过SolrQuery提供的工具方法来设置请求参数
 */
public class TestSolrQuery2 {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/restaurants";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/select");
        query.addFacetField("name");
        query.setFacet(true);
        query.setQuery("*:*");
        query.setFields(new String[] {"id","name","type","city","price","score"});
        query.setRows(10);
        //返回socre这个伪域
        query.setIncludeScore(true);
        query.setParam("wt", "json");
        query.setParam("indent", "true");
        QueryResponse response = client.query(query);
        System.out.println("\n以下是普通select查询请求的响应信息：\n");
        System.out.println(response.toString());
    }
}
