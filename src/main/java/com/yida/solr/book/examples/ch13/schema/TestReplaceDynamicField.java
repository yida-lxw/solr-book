package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * 测试更新一个动态域的信息,比如修改其域类型
 */
public class TestReplaceDynamicField {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        Map<String, Object> fieldAttributes = new HashMap<String, Object>();
        fieldAttributes.put("name","*_n");
        fieldAttributes.put("type","int");
        fieldAttributes.put("stored",true);
        fieldAttributes.put("omitNorms",true);
        SchemaRequest.ReplaceDynamicField replaceDynamicField = new SchemaRequest.ReplaceDynamicField(fieldAttributes);
        SchemaResponse.UpdateResponse response = replaceDynamicField.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
