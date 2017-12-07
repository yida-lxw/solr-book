package com.yida.solr.book.examples.ch13.springdatasolr.config.http;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.HttpSolrClientFactoryBean;

import javax.annotation.Resource;

import static org.fusesource.leveldbjni.JniDBFactory.factory;

/**
 * Created by Lanxiaowei
 */
@Configuration
@ComponentScan(basePackages = {"com.yida.solr.book.examples.ch13.springdatasolr.service"})
@EnableSolrRepositories(basePackages = {
        "com.yida.solr.book.examples.ch13.springdatasolr.repository"},
        multicoreSupport = true)
@PropertySource("classpath:application-solr.properties")
public class HttpSolrContext {
    @Resource
    private Environment environment;
    @Bean
    public HttpSolrClientFactoryBean solrServerFactoryBean() {
        HttpSolrClientFactoryBean factory = new HttpSolrClientFactoryBean();
        String solrUrl = environment.getRequiredProperty("solr.server.url");
        String core = environment.getRequiredProperty("solr.core.test");
        if(null == core || "".equals(core)) {
            factory.setUrl(solrUrl);
            return factory;
        }
        if(solrUrl.endsWith("/")) {
            solrUrl = solrUrl + core;
            factory.setUrl(solrUrl);
            return factory;
        }
        solrUrl = solrUrl + "/" + core;
        factory.setUrl(solrUrl);
        return factory;
    }

    @Bean
    public SolrClient solrClient(@Value("${solr.server.url}") String solrHost,
                                 @Value("${solr.core.test}") String core) {
        if(null == core || "".equals(core)) {
            return new HttpSolrClient(solrHost);
        }
        if(solrHost.endsWith("/")) {
            return new HttpSolrClient(solrHost + core);
        }
        return new HttpSolrClient(solrHost + "/" + core);
    }
    @Bean
    public SolrTemplate solrTemplate() throws Exception {
        return new SolrTemplate(solrServerFactoryBean().getObject());
    }
}
