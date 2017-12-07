package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.util.NamedList;

import java.util.Arrays;

/**
 * Created by Lanxiaowei
 * 测试索引目录分裂
 */
public class TestSplitIndexDir {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        SplitIndexes split = new SplitIndexes();
        split.setCoreName("test1");
        String[] paths = new String[] {"C:/solr_home/test2/data/index","C:/solr_home/test3/data/index"};
        split.setPaths(Arrays.asList(paths));
        NamedList<Object> result = client.request(split);
        System.out.println(result);
    }
}
