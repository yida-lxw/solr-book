package com.yida.solr.book.examples.ch11.jsonfacet;

import com.yida.solr.book.examples.utils.BaseUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * 11.6.8章节中导入测试数据
 */
public class IndexMultiSelect {
    //尺码
    private static final String[] sizes = {"Small","Medium","Large"};

    //颜色
    private static final String[] colors = {"Red","Blue","Green","Black"};

    //品牌
    private static final String[] brands = {"Nike","Adidas","Reebok","Under Armour"};


    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/multiselect";


    private static int id = 1;

    public static void main(String[] args) throws Exception {
        commitDoc(10000);
    }

    private static void commitDoc(int totalDoc) throws Exception {
        ConcurrentUpdateSolrClient solrClient =
                new ConcurrentUpdateSolrClient(SOLR_INSTANT_CORE,1000,10);
        for(int i=0; i < totalDoc; i++) {
            SolrInputDocument doc = createDocument();
            solrClient.add(doc);
        }
        solrClient.commit();
        solrClient.close();
    }

    /**
     * 随机生成Document
     * @return
     */
    public static SolrInputDocument createDocument() {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id",id++);
        doc.addField("size",sizes[BaseUtils.randNum(0,2)]);
        doc.addField("color",colors[BaseUtils.randNum(0,3)]);
        doc.addField("brand",brands[BaseUtils.randNum(0,3)]);
        return doc;
    }
}
