package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 测试通过从共享配置目录加载配置文件方式创建Core
 */
public class TestCreateCoreWithConfigSet {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        String coreName = "test2";
        SolrClient client = new HttpSolrClient(SOLR_URL);
        CoreAdminRequest.Create create = new CoreAdminRequest.Create();
        create.setPath("/admin/cores");
        create.setCoreName(coreName);
        create.setInstanceDir("C:/solr_home/" + coreName);
        //设置configSet，一个configSet表示一套共享配置文件
        create.setConfigSet("testConfigSet");
        NamedList<Object> result = client.request(create);
        System.out.println(result);
    }
}
