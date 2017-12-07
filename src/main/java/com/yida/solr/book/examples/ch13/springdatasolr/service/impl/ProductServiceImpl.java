package com.yida.solr.book.examples.ch13.springdatasolr.service.impl;

import com.yida.solr.book.examples.ch13.springdatasolr.po.Product;
import com.yida.solr.book.examples.ch13.springdatasolr.repository.ProductRepository;
import com.yida.solr.book.examples.ch13.springdatasolr.repository.ProductRepositoryWithNoImpl;
import com.yida.solr.book.examples.ch13.springdatasolr.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Lanxiaowei
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepositoryWithNoImpl productRepositoryWithNoImpl;

    @Transactional
    public void addProductToSolr(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(String id) {
        productRepository.delete(id);
    }

    @Transactional
    public void updateProductToSolr(Product product) {
        Product oldProduct = productRepository.findOne(product.getId());
        oldProduct.setProductName(product.getProductName());
        productRepository.save(oldProduct);
    }

    public Product findProductById(String id) {
        return productRepository.findOne(id);
    }
    public SolrResultPage<Product> findProductsByIds(List<String> ids) {
        return (SolrResultPage<Product>) productRepository.findAll(ids);
    }
    public SolrResultPage<Product> findAllProducts() {
        return (SolrResultPage<Product>) productRepository.findAll();
    }

    public Page<Product> findByProductName(String productName,
               int currentPage, int pageSize) {
        return productRepositoryWithNoImpl.findByProductName(productName,
                new SolrPageRequest(currentPage,pageSize));
    }

    public List<Product> findByProductNameLike(String productName) {
        return productRepositoryWithNoImpl.findByProductNameLike(productName);
    }

    public List<Product> findByIdIn(List<String> ids) {
        return productRepositoryWithNoImpl.findByIdIn(ids);
    }

    public Product findByNamedQuery(String id) {
        return productRepositoryWithNoImpl.findByNamedQuery(id);
    }
}
