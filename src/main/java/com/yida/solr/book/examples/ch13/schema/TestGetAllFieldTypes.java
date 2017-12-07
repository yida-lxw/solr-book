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
 * 测试查看schema.xml中所有域类型的定义信息
 */
public class TestGetAllFieldTypes {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        ModifiableSolrParams params = new ModifiableSolrParams();
        //是否显示域类型的默认属性信息
        params.add("showDefaults","true");
        SchemaRequest.FieldTypes allFieldTypes = new SchemaRequest.FieldTypes(params);
        SchemaResponse.FieldTypesResponse response = allFieldTypes.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        List<NamedList<Object>> fieldTypes = (List<NamedList<Object>>)result.get("fieldTypes");
        for(NamedList<Object> fieldType : fieldTypes) {
            for(Map.Entry<String,Object> entry : fieldType) {
                String key = entry.getKey();
                Object val = entry.getValue();
                System.out.println(key + ": " + (val == null? "" : val.toString()));
            }
            System.out.println("/******************************************/");
        }
    }
}
