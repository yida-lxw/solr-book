package com.yida.solr.book.examples.ch12.join;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 */
public class BlockJoinTest {
    private static final String SOLR_INSTANT_URL = "http://localhost:8080/solr/book/";
    public static void main(String[] args) throws IOException, SolrServerException {
        HttpSolrClient client = new HttpSolrClient(SOLR_INSTANT_URL);
        client.setRequestWriter(new BinaryRequestWriter());

        SolrInputDocument book1 = new SolrInputDocument();
        book1.setField("id", "978-1617291029_book");
        book1.setField("name", "Solr in Action");
        book1.setField("isbn", "978-1617291029");
        book1.setField("type", "book");

        SolrInputDocument book1_chapter1 = new SolrInputDocument();
        book1_chapter1.setField("id", "978-1617291029_chapter1");
        book1_chapter1.setField("name", "Introduction to Solr");
        book1_chapter1.setField("isbn", "978-1617291029");
        book1_chapter1.setField("type", "chapter");
        book1.addChildDocument(book1_chapter1);

        SolrInputDocument book1_chapter2 = new SolrInputDocument();
        book1_chapter2.setField("id", "978-1617291029_chapter2");
        book1_chapter2.setField("name", "Getting to know Solr");
        book1_chapter2.setField("isbn", "978-1617291029");
        book1_chapter2.setField("type", "chapter");
        book1.addChildDocument(book1_chapter2);

        SolrInputDocument book2 = new SolrInputDocument();
        book2.setField("id", "978-1783553150_book");
        book2.setField("name", "Solr Cookbook - Third Edition");
        book2.setField("isbn", "978-1783553150");
        book2.setField("type", "book");

        SolrInputDocument book2_chapter1 = new SolrInputDocument();
        book2_chapter1.setField("id", "978-1783553150_chapter1");
        book2_chapter1.setField("name", "Apache Solr Configuration");
        book2_chapter1.setField("isbn", "978-1783553150");
        book2_chapter1.setField("type", "chapter");
        book2.addChildDocument(book2_chapter1);

        SolrInputDocument book2_chapter2 = new SolrInputDocument();
        book2_chapter2.setField("id", "978-1783553150_chapter2");
        book2_chapter2.setField("name", "Indexing your data");
        book2_chapter2.setField("isbn", "978-1783553150");
        book2_chapter2.setField("type", "chapter");
        book2.addChildDocument(book2_chapter2);

        client.add(book1);
        client.add(book2);

        client.commit();

        client.close();
    }
}
