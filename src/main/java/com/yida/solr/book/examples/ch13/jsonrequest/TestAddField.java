package com.yida.solr.book.examples.ch13.jsonrequest;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.GenericSolrRequest;
import org.apache.solr.client.solrj.response.SimpleSolrResponse;
import org.apache.solr.common.util.NamedList;


/**
 * Created by Lanxiaowei
 * 测试采用JSON Request API添加一个普通域
 */
public class TestAddField {
    private static final String SOLR_URL = "http://localhost:8080/solr/";
    private static final String CORE_NAME = "test1";
    public static void main(String[] args) throws Exception {
        //这里是使用原生的HttpClient实现的
        /*String url = "http://localhost:8080/solr/test1/schema/";
        String jsonParams =
                "{" +
                        "  \"add-field\":{" +
                        "     \"name\":\"sell-count\"," +
                        "     \"type\":\"int\"," +
                        "     \"stored\":true" +
                        "   }" +
                        "}";
        StringEntity requestEntity = new StringEntity(
                jsonParams,ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(url);
        postMethod.setEntity(requestEntity);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(postMethod);
        System.out.println(response.toString());*/

        HttpJSONSolrClient client = new HttpJSONSolrClient(SOLR_URL);
        String jsonParams =
                "{" +
                "  \"add-field\":{" +
                "     \"name\":\"sell-count4\"," +
                "     \"type\":\"int\"," +
                "     \"stored\":true" +
                "   }" +
                "}";
        client.setJsonParams(jsonParams);
        GenericSolrRequest addField = new
            GenericSolrRequest(SolrRequest.METHOD.POST,"/schema",null);
        SimpleSolrResponse response = addField.process(client,CORE_NAME);
        NamedList<Object> result = response.getResponse();
        System.out.println(result);
    }
}
