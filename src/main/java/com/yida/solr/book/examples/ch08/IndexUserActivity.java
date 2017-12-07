package com.yida.solr.book.examples.ch08;

import com.yida.solr.book.examples.utils.FileUtils;
import com.yida.solr.book.examples.utils.JSONUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lanxiaowei
 */
public class IndexUserActivity {
    public static Logger log = Logger.getLogger(IndexUserActivity.class);
    private static final String SOLRPEDIA_INSTANT_CORE = "http://localhost:8080/solr/solrpedia_instant";
    private static final String JSON_FILE_PATH = "E:/git-space/solr-book/example-docs/ch08/documents/solrpedia_instant.json";
    private FileUtils fileUtils;

    public IndexUserActivity() {
        this.fileUtils = new FileUtils();
    }

    public static void main(String[] args) throws Exception {
        int batchSize = 5000;
        IndexUserActivity index = new IndexUserActivity();
        index.importData(SOLRPEDIA_INSTANT_CORE,batchSize, JSON_FILE_PATH);
    }

    /**
     * 导入索引数据到solrpedia_instant Core中
     * @param serverUrl
     * @param batchSize
     * @param jsonFilePath
     * @throws Exception
     */
    public void importData(String serverUrl,int batchSize,String jsonFilePath) throws Exception {
        long startMs = System.currentTimeMillis();
        SolrInputDocument doc = null;
        SolrClient solrClient = new ConcurrentUpdateSolrServer(serverUrl, batchSize, 1);
        String json = new FileUtils().readFileContent(jsonFilePath);
        List<Map<String,Object>> list = JSONUtils.fromJson(json,List.class);
        for(Map<String,Object> map : list) {
            doc = new SolrInputDocument();
            for(Map.Entry<String,Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if("last_executed_on".equals(key)) {
                    String value = entry.getValue().toString();
                    Date date = toDate(value);
                    doc.setField(key,date);
                } else if("popularity".equals(key)) {
                    String value = entry.getValue().toString();
                    float popularity = Float.valueOf(value);
                    doc.setField(key,popularity);
                } else {
                    Object value = entry.getValue();
                    doc.setField(key,value);
                }
            }
            solrClient.add(doc);
        }

        // 硬提交
        solrClient.commit(true,true);

        //关闭资源
        solrClient.shutdown();

        //打印消耗时间
        float tookSecs = Math.round(((System.currentTimeMillis() - startMs)/1000f)*100f)/100f;
        log.info(String.format("import data had took %f seconds", tookSecs));
    }

    /**
     * String to Date
     * @param dateStr
     * @return
     */
    private Date toDate(String dateStr) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
