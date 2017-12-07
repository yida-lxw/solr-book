package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.util.Map;

/**
 * Created by Lanxiaowei
 * 测试获取一个动态域的定义信息
 */
public class TestGetDynamicField {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        String fieldName = "*_n";
        SchemaRequest.DynamicField dynamicField = new SchemaRequest.DynamicField(fieldName);
        SchemaResponse.DynamicFieldResponse response = dynamicField.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
        result = (NamedList<Object>) result.get("dynamicField");
        for(Map.Entry<String,Object> entry : result) {
            String key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(key + ": " + (val == null? "" : val.toString()));
        }
    }
}
