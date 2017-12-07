package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试Core卸载
 */
public class TestUnloadCore {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminRequest.Unload unload = new CoreAdminRequest.Unload(false);
        unload.setPath("/admin/cores");
        unload.setCoreName("test");

        //unload.setDeleteInstanceDir(true);  //删除整个Core目录
        //unload.setDeleteDataDir(true);      //删除Core的data目录
        //unload.setDeleteIndex(true);        //删除Core目录下的索引数据
        NamedList<Object> result = client.request(unload);
        System.out.println(result);
    }
}
