package com.yida.solr.book.examples.ch13.springdatasolr.solrtemplate;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.SolrCallback;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Lanxiaowei
 * 自定义的Repository接口实现类，其他Repository接口实现类需要继承此基类
 */
public class BaseRepositoryImpl<T, ID extends Serializable> implements BaseRepository<T, ID> {
    @Autowired
    private CustomSolrTemplate solrTemplate;
    /**默认操作的目标Core名称*/
    private String coreName;
    /**当前Repository对应的POJO类型*/
    private Class entityClass;
    /**获取POJO的主键域名称*/
    private String uniqueKey;

    public BaseRepositoryImpl(){
        this.entityClass = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        SolrDocument solrDocument = (SolrDocument)this.entityClass.getAnnotation(SolrDocument.class);
        if(null != solrDocument) {
            String core = solrDocument.solrCoreName();
            if(null == core || "".equals(core)) {
                this.coreName = entityClass.getSimpleName();
            } else {
                this.coreName = core;
            }
        }
        Field[] fields = this.entityClass.getDeclaredFields();
        for(Field f : fields) {
            f.setAccessible(true);
            //查找被@Id注解修饰的属性
            if(f.isAnnotationPresent(Id.class)) {
                this.uniqueKey = f.getName();
                break;
            }
        }
    }

    public UpdateResponse addDoc(T t) {
        return this.solrTemplate.saveBean(t);
    }

    public UpdateResponse addDoc(T t,String core) {
        this.solrTemplate.setSolrCore(core);
        return this.solrTemplate.saveBean(t);
    }

    public UpdateResponse delete(SolrDataQuery query) {
        return this.solrTemplate.delete(query);
    }

    public <T> ScoredPage<T> queryForPage(Query query, Class<T> clazz) {
        return this.solrTemplate.queryForPage(query,this.getEntityClass());
    }

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public CustomSolrTemplate getSolrTemplate() {
        this.solrTemplate.execute(new SolrCallback<QueryResponse>() {
            public QueryResponse doInSolr(SolrClient solrClient) throws SolrServerException, IOException {
                SolrQuery query = new org.apache.solr.client.solrj.SolrQuery();
                query.setRequestHandler("/select");
                query.set("q", "type:book");
                query.set("fl", "id,brand,color,size,score");
                query.set("indent","true");

                QueryResponse response = solrClient.query(query, SolrRequest.METHOD.GET);
                return response;
            }
        });
        return solrTemplate;
    }

    public void setSolrTemplate(CustomSolrTemplate solrTemplate) {
        this.solrTemplate = solrTemplate;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }
}
