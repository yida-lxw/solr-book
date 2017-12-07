package com.yida.solr.book.examples.ch13.export;

import com.yida.solr.book.examples.utils.FileUtils;
import com.yida.solr.book.examples.utils.GerneralUtils;
import com.yida.solr.book.examples.utils.IOUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.InputStreamResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

import java.io.InputStream;
import java.util.Map;

import static com.yida.solr.book.examples.utils.FileUtils.inputStream2String;
import static org.apache.solr.handler.component.StatsField.Stat.count;

/**
 * Created by Lanxiaowei
 * 测试Solr的数据导出接口
 */
public class TestDataExport {
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/mapreduce";
    public static void main(String[] args) throws Exception {
        HttpSolrClient client = new HttpSolrClient(SOLRPEDIA_INSTANT_CORE);
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/export");
        query.set("q", "*:*");
        query.set("fl", "word,count");
        query.set("sort","count desc");
        //设置响应数据的解析器
        client.setParser(new InputStreamResponseParser("json"));
        QueryResponse response = client.query(query, SolrRequest.METHOD.GET);
        NamedList<Object> result = response.getResponse();
        System.out.println("\n以下是数据导出查询请求的响应信息：\n");
        for(Map.Entry<String,Object> entry : result) {
            String content = FileUtils.inputStream2String((InputStream)entry.getValue());
            System.out.println(content + ":" + "");
        }
    }

}
