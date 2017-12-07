package com.yida.solr.book.examples.ch13.springdatasolr.repository.impl;


import com.yida.solr.book.examples.ch13.springdatasolr.po.Product;
import com.yida.solr.book.examples.ch13.springdatasolr.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Lanxiaowei
 */
@Repository("productRepository")
public class ProductRepositoryImpl extends SimpleSolrRepository<Product,String> implements ProductRepository {
    @Resource
    private SolrTemplate solrTemplate;
    @Autowired(required = true)
    public ProductRepositoryImpl(@Qualifier(value="solrTemplate") SolrOperations solrOperations) {
        super(solrOperations);
    }


}
