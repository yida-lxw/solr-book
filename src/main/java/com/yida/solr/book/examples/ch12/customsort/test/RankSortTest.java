package com.yida.solr.book.examples.ch12.customsort.test;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.lucene.util.NumericUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lanxiaowei
 */
public class RankSortTest {
    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/weibo";
    private static final String INDEX_PATH = "C:\\solr_home\\weibo\\data\\index";

    public static void main(String[] args) throws Exception {
        //querytop10();

        query(INDEX_PATH,"id:\"1\"");
    }

    private static void query(String path,String expression) throws Exception {
        QueryParser parser = new QueryParser("id", new KeywordAnalyzer());
        BytesRefBuilder builder = new BytesRefBuilder();
        NumericUtils.intToPrefixCoded(1,0,builder);
        Query query = new TermQuery(new Term("id",builder.toBytesRef()));
        List<Document> documentList = query(INDEX_PATH,query);
        print(documentList);
    }

    public static void querytop10() {
        SolrClient solrClient = new HttpSolrClient(SOLR_INSTANT_CORE);
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("id:\"1\"");
            query.add("fl","id,title,rank");
            query.setRows(10);
            SolrDocumentList docs = solrClient.query(query).getResults();
            for (SolrDocument sd : docs) {
                System.out.println("/*****************************************/");
                System.out.println("id:" + sd.getFieldValue("id"));
                System.out.println("title:" + sd.getFieldValue("title"));
                System.out.println("rank:" + sd.getFieldValue("rank"));
                System.out.println("/*****************************************/\n");
            }
            System.out.println(query);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != solrClient) {
                    solrClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建索引阅读器
     * @param directoryPath  索引目录
     * @return
     * @throws IOException   可能会抛出IO异常
     */
    public static IndexReader createIndexReader(String directoryPath) throws IOException {
        return DirectoryReader.open(FSDirectory.open(Paths.get(directoryPath, new String[0])));
    }

    /**
     * 创建索引查询器
     * @param directoryPath   索引目录
     * @return
     * @throws IOException
     */
    public static IndexSearcher createIndexSearcher(String directoryPath) throws IOException {
        return new IndexSearcher(createIndexReader(directoryPath));
    }

    /**
     * 创建索引查询器
     * @param reader
     * @return
     */
    public static IndexSearcher createIndexSearcher(IndexReader reader) {
        return new IndexSearcher(reader);
    }

    public static List<Document> query(String directoryPath,Query query) throws IOException {
        IndexSearcher searcher = createIndexSearcher(directoryPath);
        TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE);
        List<Document> docList = new ArrayList<Document>();
        ScoreDoc[] docs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : docs) {
            int docID = scoreDoc.doc;
            Document document = searcher.doc(docID);
            docList.add(document);
        }
        searcher.getIndexReader().close();
        return docList;
    }

    private static void print(List<Document> docList) {
        if(null == docList || docList.size() <= 0) {
            return;
        }
        for(Document doc : docList) {
            int docId = Integer.valueOf(doc.get("id"));
            String title = doc.get("title");
            //int rank = Integer.valueOf(doc.get("rank"));
            System.out.println("docId-->" + docId);
            System.out.println("title-->" + title);
            //System.out.println("rank-->" + rank);
        }
    }
}
