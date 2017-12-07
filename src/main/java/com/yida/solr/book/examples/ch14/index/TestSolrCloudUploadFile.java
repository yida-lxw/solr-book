package com.yida.solr.book.examples.ch14.index;

import com.yida.solr.book.examples.utils.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lanxiaowei
 */
public class TestSolrCloudUploadFile {
    /**
     * Zookeeper集群节点，多个使用逗号分割
     */
    private static final String ZK_HOST = "linux.yida01.com:2181,linux.yida02.com:2181,linux.yida03.com:2181";
    private static final String XML_FILE_BASE_PATH = "E:/git-space/solr-book/example-docs/ch12/documents/";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.Sz");

    public static void main(String[] args) throws Exception {
        //indexData("users.xml","joinTest");
        //显式指定索引文档需要提交到joinTest这个Collection
        String collection = "joinTest";
        CloudSolrClient client = TestSolrCloudIndex.createCloudSolrClient(ZK_HOST,collection);
        List<SolrInputDocument> docs = createDocument();
        client.add(docs);
        client.commit(collection,true,true,true);
        client.close();
    }

    public static List<SolrInputDocument> createDocument() throws ParseException {
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

        SolrInputDocument doc1 = new SolrInputDocument();
        doc1.addField("id","1");
        doc1.addField("user_name","张三");
        doc1.addField("age","25");
        /******************doc1的子Document begin*******************/
        List<SolrInputDocument> docs1 = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc11 = new SolrInputDocument();
        doc11.addField("id","4");
        doc11.addField("amount",725.4);
        String dateStr11 = "2016-09-30T08:00:00.0+0800";
        Date date11 = dateFormat.parse(dateStr11);
        doc11.addField("occur_date",date11);

        SolrInputDocument doc12 = new SolrInputDocument();
        doc12.addField("id","5");
        doc12.addField("amount",199.0);
        String dateStr12 = "2016-10-02T11:00:00.0+0800";
        Date date12 = dateFormat.parse(dateStr12);
        doc12.addField("occur_date",date12);

        SolrInputDocument doc13 = new SolrInputDocument();
        doc13.addField("id","6");
        doc13.addField("amount",490.5);
        String dateStr13 = "2016-10-18T09:10:00.0+0800";
        Date date13 = dateFormat.parse(dateStr13);
        doc13.addField("occur_date",date13);
        docs1.add(doc11);
        docs1.add(doc12);
        docs1.add(doc13);
        /******************doc1的子Document end*******************/


        SolrInputDocument doc2 = new SolrInputDocument();
        doc2.addField("id","2");
        doc2.addField("user_name","李四");
        doc2.addField("age","28");
        /******************doc2的子Document begin*******************/
        List<SolrInputDocument> docs2 = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc21 = new SolrInputDocument();
        doc21.addField("id","7");
        doc21.addField("amount",288.2);
        String dateStr21 = "2016-10-09T08:00:00.0+0800";
        Date date21 = dateFormat.parse(dateStr21);
        doc21.addField("occur_date",date21);

        SolrInputDocument doc22 = new SolrInputDocument();
        doc22.addField("id","8");
        doc22.addField("amount",69.1);
        String dateStr22 = "2016-10-10T11:00:00.0+0800";
        Date date22 = dateFormat.parse(dateStr22);
        doc22.addField("occur_date",date22);
        docs2.add(doc21);
        docs2.add(doc22);
        /******************doc2的子Document end*******************/


        SolrInputDocument doc3 = new SolrInputDocument();
        doc3.addField("id","3");
        doc3.addField("user_name","王五");
        doc3.addField("age","20");
        /******************doc3的子Document begin*******************/
        List<SolrInputDocument> docs3 = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc31 = new SolrInputDocument();
        doc31.addField("id","9");
        doc31.addField("amount",1828.2);
        String dateStr31 = "2016-08-19T11:30:00.0+0800";
        Date date31 = dateFormat.parse(dateStr31);
        doc31.addField("occur_date",date31);
        docs3.add(doc31);
        /******************doc3的子Document end*******************/

        doc1.addChildDocuments(docs1);
        doc2.addChildDocuments(docs2);
        doc3.addChildDocuments(docs3);
        docs.add(doc1);
        docs.add(doc2);
        docs.add(doc3);
        return docs;
    }

    public static void indexData(String xmlPath,String defaultCollection) throws IOException {
        CloudSolrClient client = TestSolrCloudIndex.createCloudSolrClient(ZK_HOST,defaultCollection);
        //client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        request.setAction(UpdateRequest.ACTION.COMMIT, true, true);
        String body = FileUtils.readFile(XML_FILE_BASE_PATH + xmlPath,"UTF-8");
        request.setParam("stream.body", body);
        //request.setParam("stream.file", XML_FILE_BASE_PATH + xmlPath);
        request.setParam("stream.contentType", "application/xml");
        NamedList<Object> result = null;
        try {
            result = client.request(request,"joinTest");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Result: " + result);
    }
}
