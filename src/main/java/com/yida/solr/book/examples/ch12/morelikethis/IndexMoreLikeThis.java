package com.yida.solr.book.examples.ch12.morelikethis;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lanxiaowei
 * 12.8章节测试数据导入
 */
public class IndexMoreLikeThis {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/morelikethis";

    private static final String[] BOOKS = {
            "Thinking in Java","Head First Java","Effective Java",
            "Java 8 Lambdas","Java Concurrency in Practice","Java Threads 3rd Edition",
            "Java Network Programming 4th Edition","Java 8 in Action","Core Java Volume",
            "Concurrent Programming in Java Design Principles and Pattern","A Java ForkJoin Framework","Data Structures and Algorithm Analysis in Java 3rd Edition",
            "Solr in action","Solr Cookbook","Administrating Solr",
            "Hadoop in action","Hadoop in Practice","Mastering Hadoop"
    };
    public static void main(String[] args) throws Exception {
        HttpSolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        request.setAction(UpdateRequest.ACTION.COMMIT, true, true);

        List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
        int id = 0;
        for(String bookName : BOOKS) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("book_name",bookName);
            doc.addField("id",++id);
            docList.add(doc);
        }
        request.add(docList);
        NamedList<Object> result = client.request(request);
        System.out.println("Result: " + result);
    }
}
