package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.util.NamedList;

import static com.sun.tools.javac.jvm.ByteCodes.swap;

/**
 * Created by Lanxiaowei
 * 测试Core重命名
 */
public class TestRenameCore {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminRequest rename = new CoreAdminRequest();
        //将"test" Core重命名为"test3"
        rename.setCoreName("test");
        rename.setOtherCoreName("test3");
        rename.setAction(CoreAdminParams.CoreAdminAction.RENAME);
        NamedList<Object> result = client.request(rename);
        System.out.println(result);
    }
}
