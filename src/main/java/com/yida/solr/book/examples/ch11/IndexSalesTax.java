package com.yida.solr.book.examples.ch11;

import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.util.NamedList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lanxiaowei
 * 第11章第1节11.1.2测试数据导入
 */
public class IndexSalesTax {
    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/salestax";
    private static final String XML_FILE_PATH = "E:/git-space/solr-book/example-docs/ch11/documents/salestax.xml";
    public static void main(String[] args) throws Exception {
        HttpSolrClient client = new HttpSolrClient(SOLR_INSTANT_CORE);
        client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        request.setAction(UpdateRequest.ACTION.COMMIT, true, true);
        //设置文件路径，直接上传到Solr Server
        request.setParam("stream.file", XML_FILE_PATH);
        request.setParam("stream.contentType", "application/xml");
        NamedList<Object> result = client.request(request);
        System.out.println("Result: " + result);
    }
}
