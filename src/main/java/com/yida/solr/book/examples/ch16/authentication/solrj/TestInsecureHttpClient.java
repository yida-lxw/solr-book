package com.yida.solr.book.examples.ch16.authentication.solrj;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * Created by Lanxiaowei
 * InsecureHttpClient类使用示例
 */
public class TestInsecureHttpClient {
    public static void main(String[] args) throws Exception {
        //Solr请求BaseURL，比如http://localhost:8983/solr
        String url = "";
        String username = "";
        String password = "";
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 128);
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 32);
        params.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false);
        HttpClient httpClient = HttpClientUtil.createClient(params);
        httpClient = new InsecureHttpClient(httpClient, username, password);
        SolrClient client = new HttpSolrClient(url, httpClient);
    }
}
