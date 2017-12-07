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
 * 测试查看指定复制域的定义信息
 */
public class TestGetCopyField {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        String fieldName = "name";
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("wt","json");
        //设置返回哪些source field信息
        params.add("source.fl","name");
        //设置返回哪些dest field信息
        params.add("dest.fl","product_name,title");
        SchemaRequest.CopyFields allCopyFields = new SchemaRequest.CopyFields(params);
        SchemaResponse.CopyFieldsResponse response = allCopyFields.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        List<NamedList<Object>> copyFields = (List<NamedList<Object>>)result.get("copyFields");
        for(NamedList<Object> copyField : copyFields) {
            for(Map.Entry<String,Object> entry : copyField) {
                String key = entry.getKey();
                Object val = entry.getValue();
                System.out.println(key + ": " + (val == null? "" : val.toString()));
            }
        }
    }
}
