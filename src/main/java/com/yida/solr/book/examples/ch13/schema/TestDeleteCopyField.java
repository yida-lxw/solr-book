package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lanxiaowei
 * 测试删除一个复制域
 */
public class TestDeleteCopyField {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        String sourceField = "name";
        String destField = "title";
        List<String> destFields = new ArrayList<String>();
        destFields.add(destField);
        SchemaRequest.DeleteCopyField deleteCopyField = new SchemaRequest.DeleteCopyField(sourceField,destFields);
        SchemaResponse.UpdateResponse response = deleteCopyField.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
