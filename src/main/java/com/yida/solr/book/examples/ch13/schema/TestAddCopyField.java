package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * 测试添加一个复制域
 */
public class TestAddCopyField {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        String sourceField = "name";
        List<String> destFields = new ArrayList<String>();
        destFields.add("product_name");
        destFields.add("title");
        SchemaRequest.AddCopyField addCopyField = new SchemaRequest.AddCopyField(sourceField,destFields);
        SchemaResponse.UpdateResponse response = addCopyField.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
