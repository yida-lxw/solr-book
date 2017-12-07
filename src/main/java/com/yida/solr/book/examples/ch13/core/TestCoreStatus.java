package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;

import java.util.Map;

/**
 * Created by Lanxiaowei
 * 测试查看Core状态
 */
public class TestCoreStatus {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminResponse response = CoreAdminRequest.getStatus("core2",client);
        NamedList<Object> result = response.getResponse();
        SimpleOrderedMap map = (SimpleOrderedMap) result.get("status");
        map = (SimpleOrderedMap)map.get("core2");
        for(Object obj : map) {
            Map.Entry entry = (Map.Entry)obj;
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
