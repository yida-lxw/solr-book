package com.yida.solr.book.examples.ch12.queryparser;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lanxiaowei
 * 12.9章节自定义Query Parser测试
 */
public class IndexPrefixBoostQParser {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/prefixboost";

    private static final String[] names = {
            "iPhone 6S Plus",
            "Apple iPhone 6S Plus",
            "Apple iPhone 6S",
            "iPhone 6S",
            "iPhone 6",
            "iPhone 6S White 64G",
            "Apple iPhone 6S Plus Black 64G",
            "Apple iPhone 6 Plus White 64G"
    };
    public static void main(String[] args) throws Exception {
        HttpSolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        //这个用于设置硬提交
        request.setAction(UpdateRequest.ACTION.COMMIT, true, true);

        List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();

        int id = 0;
        for(String name : names) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id",++id + "");
            doc.addField("name",name);
            docList.add(doc);
        }
        request.add(docList);
        NamedList<Object> result = client.request(request);
        System.out.println("Result: " + result);

        SolrQuery query = new SolrQuery();
        //select查询测试
        query.setRequestHandler("/select");
        query.set("q", "{!prefixBoost qf=\"name exactname^10\"}\"^iphone 6S\"^10 \"^iphone 6\"^20");
        query.set("wt", "json");
        query.set("indent", "true");
        query.set("fl", "id,name,score");
        QueryResponse response = client.query(query);
        System.out.println("以下是select查询的响应信息：\n");
        System.out.println(response.toString());
    }
}
