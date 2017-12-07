package com.yida.solr.book.examples.ch13.springdatasolr.repository;

import com.yida.solr.book.examples.ch13.springdatasolr.po.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Lanxiaowei
 * ProductRepository接口，不带实现类
 */
public interface ProductRepositoryWithNoImpl extends SolrCrudRepository<Product,String> {
    Page<Product> findByProductName(String productName, Pageable pageable);
    List<Product> findByProductNameLike(String productName);
    @Query
    List<Product> findByIdIn(List<String> ids);
    Product findByNamedQuery(String id);
}
