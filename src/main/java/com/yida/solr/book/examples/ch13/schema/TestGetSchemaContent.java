package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

import java.util.List;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * 测试获取整个schema.xml的信息
 */
public class TestGetSchemaContent {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        SchemaRequest request = new SchemaRequest();
        request.setPath("/schema");
        SchemaResponse response = request.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        result = (NamedList<Object>)result.get("schema");
        String schemaName = result.get("name").toString();
        String version = result.get("version").toString();
        List<NamedList<Object>> fieldTypes = (List<NamedList<Object>>)result.get("fieldTypes");
        List<NamedList<Object>> fields = (List<NamedList<Object>>)result.get("fields");
    }
}
