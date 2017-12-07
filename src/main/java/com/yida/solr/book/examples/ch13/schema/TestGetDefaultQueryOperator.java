package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试返回schema.xml中定义的默认查询连接符信息
 */
public class TestGetDefaultQueryOperator {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        SchemaRequest.DefaultQueryOperator defaultQueryOperator = new SchemaRequest.DefaultQueryOperator();
        SchemaResponse.DefaultQueryOperatorResponse response = defaultQueryOperator.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        String defaultOperator = result.get("defaultOperator").toString();
        System.out.println("defaultQueryOperator: " + defaultOperator);
    }
}
