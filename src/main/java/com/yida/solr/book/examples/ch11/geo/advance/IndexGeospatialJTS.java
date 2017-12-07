package com.yida.solr.book.examples.ch11.geo.advance;

import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.util.NamedList;

/**
 * Created by Lanxiaowei
 * 第11章第2节第2部分的测试数据导入
 */
public class IndexGeospatialJTS {
    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/geospatial_jts";
    private static final String XML_FILE_PATH = "E:/git-space/solr-book/example-docs/ch11/documents/geospatial_jts.xml";
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
