package com.yida.solr.book.examples.ch13.schema;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.AnalyzerDefinition;
import org.apache.solr.client.solrj.request.schema.FieldTypeDefinition;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.util.NamedList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Lanxiaowei
 * 测试添加域类型
 */
public class TestAddFieldType {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        addFieldType("text_general");
    }

    public static void addFieldType(String fieldTypeName) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        SchemaRequest.AddFieldType addFieldType = createAddFieldType(fieldTypeName);
        SchemaResponse.UpdateResponse response = addFieldType.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }

    public static SchemaRequest.AddFieldType createAddFieldType(String fieldTypeName) throws Exception {
        Map<String, Object> fieldTypeAttributes = new HashMap<String, Object>();
        fieldTypeAttributes.put("name",fieldTypeName);
        fieldTypeAttributes.put("class","solr.TextField");
        fieldTypeAttributes.put("positionIncrementGap",100);

        Map<String, Object> tokenizer = new HashMap<String, Object>();
        tokenizer.put("class","solr.StandardTokenizerFactory");

        Map<String, Object> stopFilter = new HashMap<String, Object>();
        stopFilter.put("class","solr.StopFilterFactory");
        stopFilter.put("ignoreCase",true);
        stopFilter.put("words","stopwords.txt");

        Map<String, Object> lowerCaseFilter = new HashMap<String, Object>();
        lowerCaseFilter.put("class","solr.LowerCaseFilterFactory");

        Map<String, Object> synonymFilter = new HashMap<String, Object>();
        synonymFilter.put("class","solr.SynonymFilterFactory");
        synonymFilter.put("synonyms","synonyms.txt");
        synonymFilter.put("ignoreCase",true);
        synonymFilter.put("expand",true);

        AnalyzerDefinition indexAnalyzer = new AnalyzerDefinition();
        Map<String, Object> indexAnalyzerAttributes = new HashMap<String, Object>();
        indexAnalyzerAttributes.put("type","index");
        List<Map<String, Object>> indexFilters = new ArrayList<Map<String, Object>>();
        indexFilters.add(stopFilter);
        indexFilters.add(lowerCaseFilter);
        indexAnalyzer.setAttributes(indexAnalyzerAttributes);
        indexAnalyzer.setTokenizer(tokenizer);
        indexAnalyzer.setFilters(indexFilters);

        AnalyzerDefinition queryAnalyzer = new AnalyzerDefinition();
        Map<String, Object> queryAnalyzerAttributes = new HashMap<String, Object>();
        queryAnalyzerAttributes.put("type","query");
        List<Map<String, Object>> queryFilters = new ArrayList<Map<String, Object>>();
        queryFilters.add(stopFilter);
        queryFilters.add(synonymFilter);
        queryFilters.add(lowerCaseFilter);
        queryAnalyzer.setAttributes(queryAnalyzerAttributes);
        queryAnalyzer.setTokenizer(tokenizer);
        queryAnalyzer.setFilters(queryFilters);

        FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
        fieldTypeDefinition.setAttributes(fieldTypeAttributes);
        fieldTypeDefinition.setIndexAnalyzer(indexAnalyzer);
        fieldTypeDefinition.setQueryAnalyzer(queryAnalyzer);

        SchemaRequest.AddFieldType addFieldType = new SchemaRequest.AddFieldType(fieldTypeDefinition);
        return addFieldType;
    }
}
