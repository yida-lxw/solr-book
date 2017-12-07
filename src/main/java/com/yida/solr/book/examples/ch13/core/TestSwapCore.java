package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试Core交换
 */
public class TestSwapCore {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminRequest swap = new CoreAdminRequest();
        //将"test" Core与"test2" Core交换
        swap.setCoreName("test");
        swap.setOtherCoreName("test2");
        swap.setAction(CoreAdminParams.CoreAdminAction.SWAP);
        NamedList<Object> result = client.request(swap);
        System.out.println(result);
    }
}
