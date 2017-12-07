package com.yida.solr.book.examples.ch13.simple;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by Lanxiaowei
 */
public class TestSolrClient {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/";

    public static void main(String[] args) throws Exception {
        //可以先不设置请求哪个Core
        SolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);

        SolrQuery query = new SolrQuery();

        query.setRequestHandler("/select");
        query.set("q", "id:11");
        query.set("fl", "id,brand,color,size,score");
        query.set("indent","true");
        //可以在查询时临时指定请求哪个Core
        QueryResponse response = client.query("clothes",query);
        System.out.println("\n以下是普通select查询请求的响应信息：\n");
        System.out.println(response.toString());

        NamedList<Object> result = client.request(new QueryRequest(query),"clothes");
        System.out.println(result);

    }

}
