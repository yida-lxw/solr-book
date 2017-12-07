package com.yida.solr.book.examples.ch13.query;

import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * Created by Lanxiaowei
 * ModifiableSolrParams测试2：演示如何使用一个ModifiableSolrParams构造另一个ModifiableSolrParams对象
 */
public class TestModifiableSolrParams1 {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/restaurants";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        ModifiableSolrParams mp = new ModifiableSolrParams();
        mp.add("wt", "json");
        mp.add("indent", "true");
        mp.add("rows", "10");
        mp.add("q", "*:*");
        mp.add("facet.field", "name");
        mp.add("fl", "id,name,type,city,price,score");
        //如何使用ModifiableSolrParams装载请求参数
        ModifiableSolrParams params = new ModifiableSolrParams(mp);

        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/select");
        query.add(params);

        QueryResponse response = client.query(query);
        System.out.println("\n以下是普通select查询请求的响应信息：\n");
        System.out.println(response.toString());
    }
}
