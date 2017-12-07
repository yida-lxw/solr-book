package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试获取schema.xml中定义的全局Similarity信息
 */
public class TestGetGlobalSimilarity {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        SchemaRequest.GlobalSimilarity globalSimilarity = new SchemaRequest.GlobalSimilarity();
        SchemaResponse.GlobalSimilarityResponse response = globalSimilarity.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        result = (NamedList<Object>) result.get("similarity");
        String similarityClass = result.get("class").toString();
        System.out.println("similarityClass: " + similarityClass);
    }
}
