package com.yida.solr.book.examples.ch07;

import com.yida.solr.book.examples.utils.FileUtils;
import com.yida.solr.book.examples.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.noggit.ObjectBuilder;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lanxiaowei
 */
public class IndexUFO {
    public static Logger log = Logger.getLogger(IndexUFO.class);

    private static final String UFO_CORE = "http://localhost:8080/solr/ufo";

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat MONTH_NAME_FMT = new SimpleDateFormat("MMMM", Locale.US);

    private static final Pattern MATCH_US_CITY_AND_STATE = Pattern.compile("^([^,]+),\\s([A-Z]{2})$");

    private boolean beVerbose = false;

    private FileUtils fileUtils;

    public IndexUFO() {
        this.fileUtils = new FileUtils();
    }

    public static void main(String[] args) throws Exception {
        String serverUrl = "http://localhost:8080/solr/ufo";
        int batchSize = 5000;
        String jsonFilePath = "E:/git-space/solr-book/example-docs/ch07/documents/ufo_awesome.json";

        IndexUFO index = new IndexUFO();
        index.importData(serverUrl,batchSize,jsonFilePath);
    }

    /**
     * 导入索引数据
     * @param serverUrl     Solr Server的请求URL
     * @param batchSize      一个批次处理多少条
     * @param jsonFilePath  JSON文件的路径
     * @throws Exception
     */
    public void importData(String serverUrl,int batchSize,String jsonFilePath) throws Exception {
        long startMs = System.currentTimeMillis();

        SolrClient solrClient = new ConcurrentUpdateSolrServer(serverUrl, batchSize, 1);

        int numSent = 0;
        int numSkipped = 0;
        int lineNum = 0;
        SolrInputDocument doc = null;
        String line = null;

        BufferedReader reader = new BufferedReader(this.fileUtils.readFileByPath(jsonFilePath));
        this.fileUtils.rememberCloseable(reader);

        //读取JSON文件的每一行
        while ((line = reader.readLine()) != null) {
            doc = parseNextDoc(line, ++lineNum);
            if (doc != null) {
                solrClient.add(doc);
                ++numSent;
            } else {
                ++numSkipped;
                continue;
            }

            if (lineNum % 5000 == 0) {
                log.info(String.format("Processed %d lines.", lineNum));
            }
        }

        // 添加一条模拟数据，用于多值域高亮
        solrClient.add(createFictitiousSightingWithMultiValuedField());

        // 硬提交
        solrClient.commit(true,true);

        //关闭资源
        solrClient.shutdown();

        //打印消耗时间
        float tookSecs = Math.round(((System.currentTimeMillis() - startMs)/1000f)*100f)/100f;
        log.info(String.format("Sent %d sightings (skipped %d) took %f seconds", numSent, numSkipped, tookSecs));
    }

    /**
     * 将JSON文件中的每一行JSON数据转换成Solr中的SolrInputDocument对象
     * @param line
     * @param lineNum
     * @return
     */
    protected SolrInputDocument parseNextDoc(String line, int lineNum) {
        Map jsonObj = null;
        try {
            jsonObj = (Map) ObjectBuilder.fromJSON(line);
        } catch (Exception jsonErr) {
            if (beVerbose) {
                log.warn("Skipped invalid sighting at line "+lineNum+
                        "; Failed to parse ["+line+"] into JSON due to: "+jsonErr);
            }
            return null;
        }

        String sighted_at = readField(jsonObj, "sighted_at");
        String location = readField(jsonObj, "location");
        String description = readField(jsonObj, "description");

        // 数据不合法的,直接忽略不处理
        if (sighted_at == null || location == null || description == null) {
            if (beVerbose) {
                log.warn("Skipped incomplete sighting at line "+lineNum+"; "+line);
            }
            return null;
        }

        // 处理日期
        Date sighted_at_dt = null;
        try {
            sighted_at_dt = DATE_FORMATTER.parse(sighted_at);
        } catch (java.text.ParseException pe) {
            if (beVerbose) {
                log.warn("Skipped sighting at line "+lineNum+
                        " due to invalid sighted_at date ("+sighted_at+") caused by: "+pe);
            }
            return null;
        }

        // 通过正则表达式提取出state和city
        Matcher matcher = MATCH_US_CITY_AND_STATE.matcher(location);
        if (!matcher.matches()) {
            if (beVerbose) {
                log.warn("Skipped sighting at line "+lineNum+
                        " because location ["+location+"] does not look like a US city and state.");
            }
            return null;
        }

        // 获取city和state数据
        String city = matcher.group(1);
        String state = matcher.group(2);

        // 清理description描述信息
        description = description.replace("&quot;", "\"").replace("&amp;", "&").replace("&apos;", "'");
        description = description.replaceAll("\\s+", " "); // collapse all whitespace down to 1 space
        description = description.replaceAll("([a-z])([\\.\\?!,;])([A-Z])", "$1$2 $3"); // fix missing space at end of sentence
        description = description.replaceAll("([a-z])([A-Z])", "$1 $2"); // fix missing space between end of word and new word

        String reported_at = readField(jsonObj, "reported_at");
        String shape = readField(jsonObj, "shape");
        String duration = readField(jsonObj, "duration");

        // 手动生成主键ID
        String docId = String.format("%s/%s/%s/%s/%s/%s",
                sighted_at,
                (reported_at != null ? reported_at : "?"),
                city.replaceAll("\\s+",""),
                state,
                (shape != null ? shape : "?"),
                StringUtils.getMD5Hash(description)).toLowerCase();

        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", docId);
        doc.setField("sighted_at_dt", sighted_at_dt);
        doc.setField("month_s", MONTH_NAME_FMT.format(sighted_at_dt));

        if (reported_at != null) {
            try {
                doc.setField("reported_at_dt", DATE_FORMATTER.parse(reported_at));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        doc.setField("city_s", city);
        doc.setField("state_s", state);
        doc.setField("location_s", location);

        if (shape != null) {
            doc.setField("shape_s", shape);
        }

        if (duration != null) {
            doc.setField("duration_s", duration);
        }
        doc.setField("sighting_en", description);

        return doc;
    }

    /**
     * 根据Key获取Map中的Value
     * @param jsonObj
     * @param key
     * @return
     */
    protected String readField(Map jsonObj, String key) {
        String val = (String)jsonObj.get(key);
        if (val != null) {
            val = val.trim();
            if (val.length() == 0) {
                val = null;
            }
        }
        return val;
    }

    /**
     * 添加一条多值域的测试数据
     * @return
     * @throws ParseException
     */
    protected SolrInputDocument createFictitiousSightingWithMultiValuedField() throws ParseException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", "sia-fictitious-sighting");
        doc.setField("sighted_at_dt", DATE_FORMATTER.parse("20130401"));
        doc.setField("month_s", "April");
        doc.setField("reported_at_dt", DATE_FORMATTER.parse("20130401"));
        doc.setField("city_s", "Denver");
        doc.setField("state_s", "CO");
        doc.setField("location_s", "Denver, CO");
        doc.setField("shape_s", "unicorn");
        doc.setField("duration_s", "5 seconds");
        doc.setField("sighting_en", "This is a fictitious UFO sighting.");
        doc.addField("nearby_objects_en", "arc of red fire");
        doc.addField("nearby_objects_en", "cluster of dark clouds");
        doc.addField("nearby_objects_en", "thunder and lightning");
        return doc;
    }
}
