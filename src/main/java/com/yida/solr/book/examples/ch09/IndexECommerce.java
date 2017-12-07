package com.yida.solr.book.examples.ch09;

import com.yida.solr.book.examples.utils.FileUtils;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.util.NamedList;


/**
 * Created by Lanxiaowei
 * 上传XML文件到Solr Server并创建索引
 */
public class IndexECommerce {

    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/ecommerce";
    private static final String XML_FILE_PATH = "E:/git-space/solr-book/example-docs/ch09/documents/ecommerce.xml";
    private FileUtils fileUtils;

    public static void main(String[] args) throws Exception {
        /*SolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
        req.addFile(new File(XML_FILE_PATH),"application/xml;charset=utf8");
        NamedList<Object> result = client.request(req);
        System.out.println("Result: " + result);*/

        HttpSolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        request.setAction(UpdateRequest.ACTION.COMMIT, true, true);
        //设置文件路径，直接上传到Solr Server
        request.setParam("stream.file", XML_FILE_PATH);

        //设置文件的内容，即自己读取文件的内容，与上面的stream.file参数二选一即可
        //request.setParam("stream.body", "这里可以是XML或JSON文件的内容");

        //如果是上传JSON文件，那么stream.contentType=application/json
        request.setParam("stream.contentType", "application/xml");
        //request.process(client);
        NamedList<Object> result = client.request(request);
        System.out.println("Result: " + result);
    }
}
