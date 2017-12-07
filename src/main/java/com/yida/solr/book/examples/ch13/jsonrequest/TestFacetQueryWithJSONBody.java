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
public class TestFacetQueryWithJSONBody {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";

    public static void main(String[] args) throws Exception {
        HttpJSONSolrClient client = new HttpJSONSolrClient(SOLR_URL);
        String jsonParams =
                "{" +
                        "query:\"*:*\"," +
                        "facet:{" +
                        "  top_product : {" +
                        "    type : terms," +
                        "    field : product_name," +
                        "    limit : 3," +
                        "    mincount : 1" +
                        "  }" +
                        "}" +
                "}";
        client.setJsonParams(jsonParams);
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("wt","json");
        params.add("indent","true");
        GenericSolrRequest queryRequest = new
                GenericSolrRequest(SolrRequest.METHOD.POST,"/select",params);
        SimpleSolrResponse response = queryRequest.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
