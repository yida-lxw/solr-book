package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.util.ArrayList;
import java.util.List;

import static com.yida.solr.book.examples.ch13.schema.TestAddField.createAddField;
import static com.yida.solr.book.examples.ch13.schema.TestAddFieldType.createAddFieldType;

/**
 * Created by Lanxiaowei
 * 测试Schema管理的事务性批量操作
 */
public class TestMultiUpdate {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        List<SchemaRequest.Update> updateSchemaRequests = new ArrayList<SchemaRequest.Update>();
        String fieldName = "book_name";
        String fieldTypeName = "text_it";
        SchemaRequest.AddFieldType addFieldType = createAddFieldType(fieldTypeName);
        SchemaRequest.AddField addField = createAddField(fieldName,fieldTypeName);
        SchemaRequest.DeleteField deleteField = new SchemaRequest.DeleteField(fieldName);
        SchemaRequest.DeleteFieldType deleteFieldType = new SchemaRequest.DeleteFieldType(fieldTypeName);
        updateSchemaRequests.add(addFieldType);
        updateSchemaRequests.add(addField);
        updateSchemaRequests.add(deleteField);
        updateSchemaRequests.add(deleteFieldType);
        SchemaRequest.MultiUpdate multiUpdate = new SchemaRequest.MultiUpdate(updateSchemaRequests);
        SchemaResponse.UpdateResponse response = multiUpdate.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
