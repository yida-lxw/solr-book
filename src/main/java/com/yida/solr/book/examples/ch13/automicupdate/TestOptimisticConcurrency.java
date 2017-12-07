package com.yida.solr.book.examples.ch13.automicupdate;

import com.yida.solr.book.examples.utils.GerneralUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id;

/**
 * Created by Lanxiaowei
 * Solr并发乐观锁测试
 */
public class TestOptimisticConcurrency {
    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/optimistic-concurrency";
    //线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(3);
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_INSTANT_CORE);
        //先添加一个测试文档
        String id = addDoc(client);
        int taskCount = 5;
        for(int i=0; i < taskCount; i++) {
            FutureTask<Integer> task = new FutureTask<Integer>(new MyCallable(i,id,client));
            executor.submit(task);
        }
    }

    //提交一个索引文档
    private static String addDoc(SolrClient client) throws Exception {
        SolrInputDocument doc = new SolrInputDocument();
        String id = UUID.randomUUID().toString();
        doc.addField("id", id);
        doc.addField("title","book1");
        doc.addField("author","Mr Hunter");
        doc.addField("borrowOut",999);
        client.add(doc);
        //采用软提交
        client.commit(false,false,true);
        return id;
    }

    private static void automicUpdate(int threadno,String id,SolrClient client) throws Exception {
        String[] title = {"test1","test2","test3","test4","test5","test6","test7","test8","test9","test10"};
        //原子更新
        SolrInputDocument sdoc = new SolrInputDocument();
        sdoc.addField("id",id);
        Map<String,Object> fieldModifier = new HashMap<String,Object>(1);
        String titleRanom = title[GerneralUtils.generateRandomNumber(9,0)];
        System.out.println("threadno:" + threadno + "/titleRanom:" + titleRanom);
        fieldModifier.put("set",titleRanom);
        sdoc.addField("title", fieldModifier); //borrowOut值加1
        client.add(sdoc);
    }

    private static int realTimeGet(int threadno,String id,SolrClient client) throws Exception {
        SolrQuery query = new SolrQuery();
        //Real time GET实时获取当前最新版本的索引文档
        query.setRequestHandler("/get");
        query.set("id", id);
        query.set("fl", "id,title,author,borrowOut,_version_");
        QueryResponse response = client.query(query);
        NamedList<Object> result = response.getResponse();
        System.out.println("/***************************************************/");
        System.out.println("以下是[" + threadno + "]Real time GET的响应信息：");
        SolrDocument solrDocument = (SolrDocument) result.get("doc");
        String idVal = solrDocument.getFieldValue("id").toString();
        String title = solrDocument.getFieldValue("title").toString();
        String author = solrDocument.getFieldValue("author").toString();
        int borrowOut = Integer.valueOf(solrDocument.getFieldValue("borrowOut").toString());
        long version = Long.valueOf(solrDocument.getFieldValue("_version_").toString());
        System.out.println("id: " + idVal);
        System.out.println("title: " + title);
        System.out.println("author: " + author);
        System.out.println("borrowOut: " + borrowOut);
        System.out.println("_version_: " + version);
        System.out.println("/***************************************************/\n\n");
        return borrowOut;
    }


    static class MyCallable implements Callable<Integer> {
        private int threadno;
        private String id;
        private SolrClient client;
        public MyCallable(int threadno,String id,SolrClient client) {
            this.id = id;
            this.threadno = threadno;
            this.client = client;
        }
        public Integer call() throws Exception {
            lock.lock();
            //原子更新
            automicUpdate(threadno,id,client);
            //然后实时GET最新值
            int borrowOut = realTimeGet(threadno,id,client);
            lock.unlock();
            return borrowOut;
        }
    }
}
