package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * 测试添加普通域
 */
public class TestAddField {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        addField("product_name","string");
    }

    public static void addField(String fieldName,String fieldTypeName) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        SchemaRequest.AddField addField = createAddField(fieldName,fieldTypeName);
        SchemaResponse.UpdateResponse response = addField.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }

    public static SchemaRequest.AddField createAddField(String fieldName, String fieldTypeName) {
        Map<String, Object> fieldAttributes = new HashMap<String, Object>();
        fieldAttributes.put("name",fieldName);
        fieldAttributes.put("type",fieldTypeName);
        fieldAttributes.put("stored",true);
        SchemaRequest.AddField addField = new SchemaRequest.AddField(fieldAttributes);
        return addField;
    }
}
