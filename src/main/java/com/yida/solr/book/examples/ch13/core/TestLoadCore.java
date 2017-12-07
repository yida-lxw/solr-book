package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * Core加载测试
 */
public class TestLoadCore {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminRequest request = new CoreAdminRequest();
        request.setCoreName("test");
        request.setAction(CoreAdminParams.CoreAdminAction.LOAD);
        NamedList<Object> result = client.request(request);
        System.out.println(result);
    }
}
