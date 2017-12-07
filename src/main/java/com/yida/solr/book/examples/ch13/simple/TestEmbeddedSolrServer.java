package com.yida.solr.book.examples.ch13.simple;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.core.CoreContainer;

/**
 * Created by Lanxiaowei
 * EmbeddedSolrServer测试
 */
public class TestEmbeddedSolrServer {
    public static void main(String[] args) throws Exception {
        String coreName = "book";
        //设置SOLR_HOME目录
        System.setProperty("solr.solr.home","C:/solr_home");
        CoreContainer container = new CoreContainer();
        //你也可以在构建CoreContainer时通过其构造函数传入SOLR_HOME目录
        container = new CoreContainer("C:/solr_home");
        //加载SOLR_HOME目录下的所有core
        container.load();
        EmbeddedSolrServer client = new EmbeddedSolrServer(container,coreName);

        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/select");
        query.set("q", "type:book");
        query.set("fl", "id,name,type,isbn,score");
        query.set("indent","true");
        QueryResponse response = client.query(coreName,query);
        System.out.println("\n以下是普通select查询请求的响应信息：\n");
        System.out.println(response.toString());


    }
}
