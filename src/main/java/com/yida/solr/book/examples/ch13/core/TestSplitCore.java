package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.util.NamedList;

import java.util.Arrays;

/**
 * Created by Lanxiaowei
 * 测试Core分裂
 */
public class TestSplitCore {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        SplitIndexes split = new SplitIndexes();
        split.setCoreName("test1");
        String[] targetCores = new String[] {"test2","test3"};
        split.setTargetCores(Arrays.asList(targetCores));
        NamedList<Object> result = client.request(split);
        System.out.println(result);
    }
}
