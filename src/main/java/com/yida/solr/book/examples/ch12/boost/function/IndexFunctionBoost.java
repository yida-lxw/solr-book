package com.yida.solr.book.examples.ch12.boost.function;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.util.NamedList;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * 12.4.4章节测试数据导入
 */
public class IndexFunctionBoost {

    private static final String SOLR_INSTANT_BASE_PATH = "http://localhost:8080/solr/";
    private static final String XML_FILE_BASE_PATH = "E:/git-space/solr-book/example-docs/ch12/documents/";

    public static void main(String[] args) throws Exception {
        indexData("distance-relevancy","distance-relevancy.xml");
        indexData("news-relevancy","news-relevancy.xml");
    }

    public static void indexData(String coreURL,String xmlPath) {
        HttpSolrClient client = new HttpSolrClient(SOLR_INSTANT_BASE_PATH + coreURL);
        client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        request.setAction(UpdateRequest.ACTION.COMMIT, true, true);
        request.setParam("stream.file", XML_FILE_BASE_PATH + xmlPath);
        request.setParam("stream.contentType", "application/xml");
        NamedList<Object> result = null;
        try {
            result = client.request(request);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Result: " + result);
    }
}
