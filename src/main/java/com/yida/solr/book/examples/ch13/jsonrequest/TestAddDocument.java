package com.yida.solr.book.examples.ch13.jsonrequest;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.GenericSolrRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.SimpleSolrResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试使用JSON Request API添加索引文档
 */
public class TestAddDocument {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";

    public static void main(String[] args) throws Exception {
        HttpJSONSolrClient client = new HttpJSONSolrClient(SOLR_URL);
        String jsonParams =
                "[{" +
                "  id: \"1\"," +
                "  product_name: \"product-1\"," +
                "  sell-count: 100" +
                "" +
                "}," +
                "{" +
                "  id: \"2\"," +
                "  product_name: \"product-2\"," +
                "  sell-count: 200" +
                "" +
                "}]";
        client.setJsonParams(jsonParams);
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("commitWithin","1000");
        GenericSolrRequest addDocument = new
                GenericSolrRequest(SolrRequest.METHOD.POST,"/update",params);
        SimpleSolrResponse response = addDocument.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
