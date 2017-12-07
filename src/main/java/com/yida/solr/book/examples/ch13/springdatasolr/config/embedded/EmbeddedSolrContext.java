package com.yida.solr.book.examples.ch13.springdatasolr.config.embedded;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactoryBean;

import javax.annotation.Resource;

/**
 * Created by Lanxiaowei
 * EmbeddedSolrServer的上下文对象
 */
@Configuration
@EnableSolrRepositories("com.yida.solr.book.examples.ch13.springdatasolr.repository")
@Profile("test")
@PropertySource("classpath:application-solr.properties")
public class EmbeddedSolrContext {
    @Resource
    private Environment environment;
    @Bean
    public EmbeddedSolrServerFactoryBean solrServerFactoryBean() {
        EmbeddedSolrServerFactoryBean factory = new EmbeddedSolrServerFactoryBean();
        factory.setSolrHome(environment.getRequiredProperty("solr.solr.home"));
        return factory;
    }

    @Bean
    public SolrTemplate solrTemplate() throws Exception {
        return new SolrTemplate(solrServerFactoryBean().getObject());
    }
}
