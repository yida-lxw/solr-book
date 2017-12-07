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
 * 测试查看所有Core状态
 */
public class TestAllCoreStatus {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminRequest request = new CoreAdminRequest();
        request.setAction(CoreAdminParams.CoreAdminAction.STATUS);
        NamedList<Object> result = client.request(request);
        System.out.println(result);
        SimpleOrderedMap map = (SimpleOrderedMap) result.get("status");
        for(Object obj : map) {
            Map.Entry entry = (Map.Entry)obj;
            SimpleOrderedMap coreMap = (SimpleOrderedMap)entry.getValue();
            for(Object object : coreMap) {
                Map.Entry entry2 = (Map.Entry)object;
                System.out.println(entry2.getKey() + ": " + entry2.getValue());
            }
            System.out.println();
        }
    }
}
