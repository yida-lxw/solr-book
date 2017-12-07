package com.yida.solr.book.examples.ch13.query;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * ModifiableSolrParams测试1
 */
public class TestModifiableSolrParams3 {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/book";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        //演示ModifiableSolrParams如何使用
        ModifiableSolrParams params = new ModifiableSolrParams();
        //params.set用于设置单个参数值，当一个参数有多个参数值时，你需要使用params.add方法
        params.set("q", "type:book");
        params.set("fl", "id,brand,color,size,score");
        params.set("indent","true");


        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/select");
        query.add(params);

        QueryRequest request = new QueryRequest(query, SolrRequest.METHOD.GET);
        NamedList<Object> result = client.request(request);
        System.out.println("\n以下是普通select查询请求的响应信息：\n");
        System.out.println(result.toString());
    }
}
