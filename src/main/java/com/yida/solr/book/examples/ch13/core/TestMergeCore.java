package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.util.NamedList;

import java.util.Arrays;

/**
 * Created by Lanxiaowei
 * 测试多个Core的索引目录合并
 */
public class TestMergeCore {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminRequest.MergeIndexes mergeIndex = new CoreAdminRequest.MergeIndexes();
        mergeIndex.setCoreName("test1");
        String[] srcCores = new String[] {"test2","test3"};
        //合并多个索引目录时需要使用的方法
        //mergeIndex.setIndexDirs(....);
        mergeIndex.setSrcCores(Arrays.asList(srcCores));
        NamedList<Object> result = client.request(mergeIndex);
        System.out.println(result);
    }
}
