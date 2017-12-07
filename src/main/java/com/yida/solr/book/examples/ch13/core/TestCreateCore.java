package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest.Create;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 使用SolrJ创建Core测试
 */
public class TestCreateCore {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_URL);
        Create create = new Create();
        create.setPath("/admin/cores");
        //设置Core名称
        create.setCoreName("test1");
        //设置Core在硬盘上的目录路径，该目录必须提前存在
        create.setInstanceDir("C:/solr-home/test1");
        //这些都是默认值，如果你需要自定义为其他值，那么你需要显式设置
        //create.setDataDir("data");
        //create.setSchemaName("schema.xml");
        //create.setConfigName("solrconfig.xml");
        //create.setIsLoadOnStartup(true);
        //create.setIsTransient(false);
        NamedList<Object> result = client.request(create);
        System.out.println(result);
    }
}
