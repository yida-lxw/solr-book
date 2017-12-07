package com.yida.solr.book.examples.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.util.NamedList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Lanxiaowei
 */
public class HttpClientUtils {
    public static Logger log = Logger.getLogger(HttpClientUtils.class);

    public static final String DEFAULT_SOLR_URL = "http://localhost:8080/solr/core1";


    /**
     * 发送POST请求到Solr
     */
    public static NamedList<Object> sendRequest(HttpClient httpClient, String getUrl) throws Exception {
        NamedList<Object> solrResp = null;

        // 创建HttpGet对象
        HttpGet httpget = new HttpGet(getUrl);

        // 发起一个GET请求
        HttpResponse response = httpClient.execute(httpget);

        // 获取返回的响应体
        HttpEntity entity = response.getEntity();
        //如果返回的响应状态码不是200，则表明请求失败了
        if (response.getStatusLine().getStatusCode() != 200) {
            StringBuilder body = new StringBuilder();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String line;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                    while ((line = reader.readLine()) != null) {
                        body.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    instream.close();
                }
            }
            throw new Exception("GET request ["+ getUrl + "] failed due to: " +
                    response.getStatusLine()+": " + body);
        }

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                //解析返回的响应数据，默认采用XML方式
                solrResp = (new XMLResponseParser()).processResponse(instream, "UTF-8");
            } catch (RuntimeException ex) {
                //中断客户端的Http连接
                httpget.abort();
                throw ex;
            } finally {
                //关闭输入流，触发http连接资源的回收
                instream.close();
            }
        }
        return solrResp;
    }

}
