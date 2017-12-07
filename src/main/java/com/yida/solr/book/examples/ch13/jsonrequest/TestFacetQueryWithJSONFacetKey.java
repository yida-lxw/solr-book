package com.yida.solr.book.examples.ch13.jsonrequest;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.GenericSolrRequest;
import org.apache.solr.client.solrj.response.SimpleSolrResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试使用JSON REquest API实现Facet Query
 */
public class TestFacetQueryWithJSONFacetKey {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";

    public static void main(String[] args) throws Exception {
        HttpJSONSolrClient client = new HttpJSONSolrClient(SOLR_URL);
        String jsonParams =
                "{" +
                        "  top_product : {" +
                        "    type : terms," +
                        "    field : product_name," +
                        "    limit : 3," +
                        "    mincount : 1" +
                        "  }" +
                "}";
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("wt","json");
        params.add("indent","true");
        params.add("q","*:*");
        params.add("rows","3");
        params.add("json.facet",jsonParams);
        GenericSolrRequest queryRequest = new
                GenericSolrRequest(SolrRequest.METHOD.GET,"/select",params);
        SimpleSolrResponse response = queryRequest.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
