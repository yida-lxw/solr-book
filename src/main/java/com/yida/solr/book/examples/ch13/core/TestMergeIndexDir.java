package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.util.NamedList;

import java.util.Arrays;

/**
 * Created by Lanxiaowei
 * 测试多个索引目录合并
 */
public class TestMergeIndexDir {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminRequest.MergeIndexes mergeIndex = new CoreAdminRequest.MergeIndexes();
        mergeIndex.setCoreName("test1");
        String[] indexDirs = new String[] {"C:/solr_home/test2/data/index","C:/solr_home/test3/data/index"};
        mergeIndex.setIndexDirs(Arrays.asList(indexDirs));
        NamedList<Object> result = client.request(mergeIndex);
        System.out.println(result);
    }
}
