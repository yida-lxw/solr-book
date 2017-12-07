package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试查看schema的版本号
 */
public class TestGetSchemaVersion {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("collection",CORE_NAME);
        params.add("wt","json");
        SchemaRequest.SchemaName schemaName = new SchemaRequest.SchemaName(params);
        SchemaResponse.SchemaNameResponse response = schemaName.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        String name = result.get("name").toString();
        System.out.println("name: " + name);
    }
}
