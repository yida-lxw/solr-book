package com.yida.solr.book.examples.ch13.springdatasolr.service;


import com.yida.solr.book.examples.ch13.springdatasolr.po.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import java.util.List;


/**
 * Created by Lanxiaowei
 */
public interface ProductService {
    public void addProductToSolr(Product product);
    public void deleteProductById(String id);
    public void updateProductToSolr(Product product);
    public Product findProductById(String id);
    public SolrResultPage<Product> findProductsByIds(List<String> ids);
    public SolrResultPage<Product> findAllProducts();
    Page<Product> findByProductName(String productName, int currentPage,int pageSize);
    List<Product> findByProductNameLike(String productName);
    List<Product> findByIdIn(List<String> ids);

    Product findByNamedQuery(String id);
}
