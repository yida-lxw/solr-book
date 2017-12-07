package com.yida.solr.book.examples.ch13.springdatasolr.solrtemplate;


import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.data.solr.core.QueryParsers;
import org.springframework.data.solr.core.RequestMethod;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.SolrConverter;
import org.springframework.data.solr.server.SolrClientFactory;
import org.springframework.data.solr.server.support.HttpSolrClientFactory;
import org.springframework.data.solr.server.support.SolrClientUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lanxiaowei
 * 重写默认的SolrTemplate,主要修改点：
 * 1.能灵活的设置操作的目标Core
 * 2.各种工具方法添加core参数，增加灵活性
 */
public class CustomSolrTemplate extends SolrTemplate {
    private String solrCore;
    private SolrClientFactory solrClientFactory;
    private final QueryParsers queryParsers;
    private final RequestMethod defaultRequestMethod;
    private static final String BASEURL_REGEX = "((http|ftp|https)://)((localhost)|([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*/([a-zA-Z0-9\\&%_\\.~]*)/?$";
    private static final String URL_REGEX = "(((http|ftp|https)://)((localhost)|([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*/([a-zA-Z0-9\\&%_\\.~]*)).*/?$";
    public CustomSolrTemplate(SolrClient solrClient) {
        this(solrClient,(String)null);
    }

    public CustomSolrTemplate(SolrClient solrClient, String core) {
        this(new HttpSolrClientFactory(solrClient, core));
        this.solrCore = core;
    }

    public CustomSolrTemplate(SolrClient solrClient, String core, RequestMethod requestMethod) {
        this(new HttpSolrClientFactory(solrClient, core), requestMethod);
        this.solrCore = core;
    }

    public CustomSolrTemplate(SolrClientFactory solrClientFactory) {
        this(solrClientFactory, (SolrConverter)null);
    }

    public CustomSolrTemplate(SolrClientFactory solrClientFactory, RequestMethod requestMethod) {
        this((SolrClientFactory)solrClientFactory, (SolrConverter)null, requestMethod);
    }

    public CustomSolrTemplate(SolrClientFactory solrClientFactory, SolrConverter solrConverter) {
        this(solrClientFactory, solrConverter, RequestMethod.GET);
    }

    public CustomSolrTemplate(SolrClientFactory solrClientFactory, SolrConverter solrConverter, RequestMethod defaultRequestMethod) {
        super(solrClientFactory,solrConverter,defaultRequestMethod);
        this.queryParsers = new QueryParsers();
        this.solrClientFactory = solrClientFactory;
        this.defaultRequestMethod = defaultRequestMethod != null?defaultRequestMethod:RequestMethod.GET;
    }

    /**
     * 将Core名称追加到Solr Server的访问BaseURL上
     * @param core
     * @param solrClient
     */
    private void appendCoreToBaseUrl(String core, SolrClient solrClient) {
        if(StringUtils.isNotEmpty(core) && this.isHttpSolrClient(solrClient)) {
            HttpSolrClient httpSolrClient = (HttpSolrClient)solrClient;
            //如果当前BaseUrl上已经追加了其他Core名称，则需要将其替换为新设置的core名称
            if(StringUtils.isNotEmpty(this.solrCore)) {
                resetCoreFromBaseUrl(this.solrCore,httpSolrClient);
            }
            String url = SolrClientUtils.appendCoreToBaseUrl(httpSolrClient.getBaseURL(), core);
            httpSolrClient.setBaseURL(url);
        }
    }

    /**
     * 将Core名称从Solr Server访问URL上剔除掉
     * @param core
     * @param solrClient
     */
    private void resetCoreFromBaseUrl(String core, SolrClient solrClient) {
        if(StringUtils.isNotEmpty(core) && this.isHttpSolrClient(solrClient)) {
            HttpSolrClient httpSolrClient = (HttpSolrClient)solrClient;
            String baseUrl = httpSolrClient.getBaseURL();
            //如果是BaseUrl即还没有在后面追加Core名称
            if(isBaseUrl(baseUrl)) {
                return;
            }
            if(null == core || "".equals(core)) {
                if(null != this.solrCore && !"".equals(this.solrCore)) {
                    baseUrl = getBaseUrl(baseUrl);
                }
            } else {
                baseUrl = baseUrl.substring(0,baseUrl.indexOf(core) - 1);
            }
            httpSolrClient.setBaseURL(baseUrl);
        }
    }

    /*public static void main(String[] args) {
        String url = "https://localhost:8080/solr/";
        boolean result = isBaseUrl(url);
        System.out.println(result);

        String baseUrl = getBaseUrl(url);
        System.out.println(baseUrl);
    }*/

    public boolean isBaseUrl(String url) {
        Pattern pattern = Pattern.compile(BASEURL_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public String getBaseUrl(String url) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String group = matcher.group(9);
            url = url.substring(0,url.indexOf(group) + group.length());
        }
        if(!url.endsWith("/")) {
            url += "/";
        }
        return url;
    }

    public final boolean isHttpSolrClient(SolrClient solrClient) {
        return solrClient instanceof HttpSolrClient;
    }

    @Override
    public String getSolrCore() {
        return solrCore;
    }

    @Override
    public void setSolrCore(String solrCore) {
        if(null == solrCore || "".equals(solrCore)) {
            //若初始化SolrTemplate时未指定Core
            if(null == this.solrCore || "".equals(this.solrCore)) {
                return;
            }
            resetCoreFromBaseUrl(solrCore,this.getSolrClientFactory().getSolrClient());
            this.solrCore = solrCore;
            return;
        }
        appendCoreToBaseUrl(solrCore,this.getSolrClientFactory().getSolrClient());
        this.solrCore = solrCore;
    }

    public SolrClientFactory getSolrClientFactory() {
        return solrClientFactory;
    }

    public void setSolrClientFactory(SolrClientFactory solrClientFactory) {
        this.solrClientFactory = solrClientFactory;
    }
}
