package com.yida.solr.book.examples.ch13.automicupdate;

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

/**
 * Created by Lanxiaowei
 * SolrJ 原子更新测试
 */
public class TestAutomicUpdate {
    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/mapreduce";

    public static void main(String[] args) throws Exception {
        SolrClient client = new HttpSolrClient(SOLR_INSTANT_CORE);
        SolrInputDocument doc = new SolrInputDocument();
        String id = UUID.randomUUID().toString();
        doc.addField("id", id);
        doc.addField("word","Sqoop");
        doc.addField("count",100);
        client.add(doc);
        //采用软提交
        client.commit(false,false,true);

        //Real-time Get
        realTimeGet(id,client);

        //原子更新
        SolrInputDocument sdoc = new SolrInputDocument();
        sdoc.addField("id",id);
        Map<String,Object> fieldModifier1 = new HashMap<String,Object>(1);
        fieldModifier1.put("inc",3);
        Map<String,Object> fieldModifier2 = new HashMap<String,Object>(1);
        fieldModifier2.put("remove","Sqoop");
        sdoc.addField("count", fieldModifier1); //count值加3
        sdoc.addField("word", fieldModifier2); //删除word域
        client.add(sdoc);

        //Real-time Get
        realTimeGet(id,client);

        //最终释放资源
        client.close();
    }

    private static void realTimeGet(String id,SolrClient client) throws Exception {
        SolrQuery query = new SolrQuery();
        //Real time GET实时获取我们刚刚软提交的那个索引文档
        query.setRequestHandler("/get");
        query.set("id", id);
        query.set("fl", "id,word,count");
        QueryResponse response = client.query(query);
        NamedList<Object> result = response.getResponse();

        System.out.println("/***************************************************/");
        System.out.println("以下是Real time GET的响应信息：");
        SolrDocument solrDocument = (SolrDocument) result.get("doc");
        String idVal = solrDocument.getFieldValue("id").toString();
        Object wordVal = solrDocument.getFieldValue("word");
        String word = wordVal == null ? "" : wordVal.toString();
        int count = Integer.valueOf(solrDocument.getFieldValue("count").toString());
        System.out.println("id: " + idVal);
        System.out.println("word: " + word);
        System.out.println("count: " + count);
        System.out.println("/***************************************************/\n\n");
    }
}
