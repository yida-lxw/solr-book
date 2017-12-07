package com.yida.solr.book.examples.ch13.jsonrequest;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.GenericSolrRequest;
import org.apache.solr.client.solrj.response.SimpleSolrResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试使用JSON Request API执行Solr索引文档查询
 */
public class TestQueryDocumentWithJSONParams {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";

    public static void main(String[] args) throws Exception {
        HttpJSONSolrClient client = new HttpJSONSolrClient(SOLR_URL);
        String jsonParams =
                "{" +
                     "\"query\":\"product_name:product-2\"" +
                "}";
        client.setJsonParams(jsonParams);
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("wt","json");
        params.add("indent","true");
        params.add("json.filter","\"sell-count:[10 TO *]\"");
        GenericSolrRequest queryRequest = new
                GenericSolrRequest(SolrRequest.METHOD.GET,"/select",params);
        SimpleSolrResponse response = queryRequest.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
