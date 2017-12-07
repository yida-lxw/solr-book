package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * 测试查看指定域的定义信息
 */
public class TestGetField {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        String fieldName = "name";
        SchemaRequest.Field field = new SchemaRequest.Field(fieldName);
        SchemaResponse.FieldResponse response = field.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        result = (NamedList<Object>) result.get("field");
        for(Map.Entry<String,Object> entry : result) {
            String key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(key + ": " + (val == null? "" : val.toString()));
        }
    }
}
