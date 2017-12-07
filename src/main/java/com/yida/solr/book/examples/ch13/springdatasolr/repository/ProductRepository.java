package com.yida.solr.book.examples.ch13.springdatasolr.repository;

import com.yida.solr.book.examples.ch13.springdatasolr.po.Product;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * Created by Lanxiaowei
 */
public interface ProductRepository extends SolrCrudRepository<Product,String> {
}
