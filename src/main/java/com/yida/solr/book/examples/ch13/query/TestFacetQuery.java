package com.yida.solr.book.examples.ch13.query;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;
import java.util.Map;

import static org.apache.solr.handler.component.StatsField.Stat.count;

/**
 * Created by Lanxiaowei
 * Facet Query响应数据解析测试
 */
public class TestFacetQuery {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/restaurants";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/select");
        query.addFacetField("name");
        query.setFacet(true);
        query.setQuery("*:*");
        query.setFields(new String[] {"id","name","type","city","price","score"});
        query.setRows(10);
        //返回socre这个伪域
        query.setIncludeScore(true);
        query.setParam("wt", "json");
        query.setParam("indent", "true");
        QueryResponse response = client.query(query);
        FacetField facetField = response.getFacetField("name");
        String name = facetField.getName();
        int totalCount = facetField.getValueCount();
        System.out.println(name + ": " + totalCount);
        List<FacetField.Count> counts = facetField.getValues();
        for(FacetField.Count count : counts) {
            System.out.println(count.getName() + "-->" + count.getCount());
        }
    }
}
