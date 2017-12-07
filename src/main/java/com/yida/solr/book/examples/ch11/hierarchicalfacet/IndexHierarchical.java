package com.yida.solr.book.examples.ch11.hierarchicalfacet;

import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 11.8章节测试数据导入
 */
public class IndexHierarchical {

    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/hierarchical-facet";
    //使用Hierarchical Facet时请改成索引hierarchical2.xml
    private static final String XML_FILE_PATH = "E:/git-space/solr-book/example-docs/ch11/documents/hierarchical2.xml";
    public static void main(String[] args) throws Exception {
        HttpSolrClient client = new HttpSolrClient(SOLR_INSTANT_CORE);
        client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        request.setAction(UpdateRequest.ACTION.COMMIT, true, true);
        request.setParam("stream.file", XML_FILE_PATH);
        request.setParam("stream.contentType", "application/xml");
        NamedList<Object> result = client.request(request);
        System.out.println("Result: " + result);
    }
}
