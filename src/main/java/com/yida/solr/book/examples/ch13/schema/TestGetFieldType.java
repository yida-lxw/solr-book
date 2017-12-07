package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.util.Map;

/**
 * Created by Lanxiaowei
 * 测试查看指定域类型的定义信息
 */
public class TestGetFieldType {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        String fieldTypeName = "text_general";
        SchemaRequest.FieldType fieldType = new SchemaRequest.FieldType(fieldTypeName);
        SchemaResponse.FieldTypeResponse response = fieldType.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        result = (NamedList<Object>) result.get("fieldType");
        for(Map.Entry<String,Object> entry : result) {
            String key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(key + ": " + (val == null? "" : val.toString()));
        }
    }
}
