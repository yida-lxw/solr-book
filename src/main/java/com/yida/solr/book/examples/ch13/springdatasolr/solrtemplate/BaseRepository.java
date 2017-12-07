package com.yida.solr.book.examples.ch13.springdatasolr.solrtemplate;

import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.io.Serializable;

/**
 * Created by Lanxiaowei
 * 自定义的Repository接口，其他Repository接口需要继承此接口
 */
public interface BaseRepository <T, ID extends Serializable> {
    public UpdateResponse addDoc(T t);

    public UpdateResponse addDoc(T t,String core);

    public UpdateResponse delete(SolrDataQuery query);

    public <T> ScoredPage<T> queryForPage(Query query, Class<T> clazz);
}
