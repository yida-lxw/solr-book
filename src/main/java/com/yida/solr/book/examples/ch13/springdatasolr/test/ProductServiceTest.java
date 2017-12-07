package com.yida.solr.book.examples.ch13.springdatasolr.test;

import com.yida.solr.book.examples.ch13.springdatasolr.config.http.HttpSolrContext;
import com.yida.solr.book.examples.ch13.springdatasolr.po.Product;
import com.yida.solr.book.examples.ch13.springdatasolr.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lanxiaowei
 * ProductService Junit 测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {HttpSolrContext.class})
@ContextConfiguration(locations = "classpath:applicationContext-solr.xml")
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void addProductToSolr() {
        Product product = new Product("1","Product-01");
        productService.addProductToSolr(product);
    }

    @Test
    public void updateProductToSolr() {
        Product product = new Product("2","Product-002");
        productService.updateProductToSolr(product);
    }

    @Test
    public void deleteProductById() {
        productService.deleteProductById("66");
    }

    @Test
    public void findProductsByIds() {
        List<String> ids = new ArrayList<String>();
        ids.add("2");
        ids.add("6");
        ids.add("10");
        SolrResultPage<Product> products = productService.findProductsByIds(ids);
        for(Product product : products) {
            String id = product.getId();
            String name = product.getProductName();
            System.out.println(id + ":" + name);
        }
    }

    @Test
    public void findByProductName() {
        String productName = "Product-1";
        int currentPage = 0;
        int pageSize = 10;
        Page<Product> page = productService.
                findByProductName(productName,currentPage,pageSize);
        for(Product product : page) {
            String id = product.getId();
            String name = product.getProductName();
            System.out.println(id + ":" + name);
        }
        System.out.println("totalPage:" + page.getTotalPages());
    }

    /**
     * Solr简单的前缀查询测试
     */
    @Test
    public void findByProductNameLike() {
        String productName = "Product";
        List<Product> searchResultPage =
            productService.findByProductNameLike(productName);
        for(Product product : searchResultPage) {
            String id = product.getId();
            String name = product.getProductName();
            System.out.println(id + ":" + name);
        }
    }

    @Test
    public void findByIdIn() {
        List<String> ids = new ArrayList<String>();
        ids.add("2");
        ids.add("6");
        ids.add("10");
        List<Product> products = productService.findByIdIn(ids);
        for(Product product : products) {
            String id = product.getId();
            String name = product.getProductName();
            System.out.println(id + ":" + name);
        }
    }

    @Test
    public void findByNamedQuery(){
        String id = "88";
        Product product = productService.findByNamedQuery(id);
        String name = product.getProductName();
        System.out.println(id + ":" + name);
    }
}
