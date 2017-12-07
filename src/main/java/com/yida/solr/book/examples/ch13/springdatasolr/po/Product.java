package com.yida.solr.book.examples.ch13.springdatasolr.po;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by Lanxiaowei
 * 产品类，用于描述Schema中定义的域并与之进行映射，从而便于以OOP的方式创建索引
 */
@SolrDocument(solrCoreName = "test2")
public class Product {
    @Id
    @Field
    private String id;

    @Field("name")
    private String productName;

    public Product(String id, String productName) {
        this.id = id;
        this.productName = productName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
