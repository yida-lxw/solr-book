package com.yida.solr.book.examples.ch11.geo.advance;

import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.util.NamedList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lanxiaowei
 * 生成测试数据用于根据距离值进行Facet
 */
public class DistanceFacetDocGenerator {
    private static Random random = new Random();

    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/distancefacet";
    private static final String FILE_PATH = "E:/git-space/solr-book/example-docs/ch11/documents/distancefacet.xml";

    public static void main(String[] args) throws Exception {
        //生成测试数据文件
        createDataFile();

        //索引数据
        indexData();
    }

    private static void indexData() throws Exception {
        HttpSolrClient client = new HttpSolrClient(SOLR_INSTANT_CORE);
        client.setRequestWriter(new BinaryRequestWriter());
        UpdateRequest request = new UpdateRequest();
        request.setAction(UpdateRequest.ACTION.COMMIT, true, true);
        request.setParam("stream.file", FILE_PATH);
        request.setParam("stream.contentType", "application/xml");
        NamedList<Object> result = client.request(request);
        System.out.println("Result: " + result);
    }

    private static void createDataFile() {
        List<WeightedLocation> locations = getWeightedLocations();
        OutputStreamWriter writer = null;
        Integer nextDocId = 1;
        try{
            writer = new FileWriter(FILE_PATH);
            writer.write("<add>\n");
            for (WeightedLocation location : locations){
                for (Integer i = 0; i < location.numDocs; i++){
                    StringBuilder doc = new StringBuilder();
                    doc.append("  <doc>\n");
                    doc.append("    <field name=\"id\">" + nextDocId.toString() + "</field>\n");
                    doc.append("    <field name=\"location\">" + changeLastDigit(location.latitude) + "," + changeLastDigit(location.longitude) + "</field>\n");
                    doc.append("    <field name=\"city\">" + location.place + "</field>\n");
                    doc.append("  </doc>\n");
                    writer.write(doc.toString());
                    nextDocId +=1;
                }
            }
            writer.write("</add>");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                writer.close();
            }
            catch (Exception e){
            }
        }
        System.out.println("Solr documents written to: " + FILE_PATH.toString() + "\n");
        System.out.println("totle documents: " + nextDocId);
    }

    private static List<WeightedLocation> getWeightedLocations(){
        List<WeightedLocation> locations = new ArrayList<WeightedLocation>();

        locations.add(new WeightedLocation("San Francisco, CA", 37.777,-122.420, 11713));
        locations.add(new WeightedLocation("San Jose, CA", 37.338,-121.886, 3071));
        locations.add(new WeightedLocation("Oakland, CA", 37.805,-122.273, 1482));
        locations.add(new WeightedLocation("Palo Alto, CA", 37.445,-122.161, 1318));
        locations.add(new WeightedLocation("Santa Clara, CA", 37.356,-121.954, 1212));
        locations.add(new WeightedLocation("Mountain View, CA", 37.386,-122.083, 1045));
        locations.add(new WeightedLocation("Sunnyvale, CA", 37.372,-122.038, 1004));
        locations.add(new WeightedLocation("Fremont, CA", 37.551,-121.982, 726));
        locations.add(new WeightedLocation("Redwood City, CA", 37.484,-122.227, 633));
        locations.add(new WeightedLocation("Berkeley, CA", 37.870,-122.271, 599));
        locations.add(new WeightedLocation("San Mateo, CA", 37.547,-122.315, 500));
        locations.add(new WeightedLocation("New York, NY", 40.715,-74.007, 12107));
        locations.add(new WeightedLocation("Atlanta, GA", 33.748,-84.391, 68453));

        return locations;
    }

    private static Double changeLastDigit(Double numberToChange){
        String newDouble = numberToChange.toString().substring(0, numberToChange.toString().length() -1) + random.nextInt(9);
        return Double.parseDouble(newDouble);
    }


    static public class WeightedLocation{
        final String place;
        final Double latitude;
        final Double longitude;
        final Integer numDocs;

        public WeightedLocation(String place, Double latitude, Double longitude, Integer numDocs){
            this.place = place;
            this.latitude = latitude;
            this.longitude = longitude;
            this.numDocs = numDocs;
        }
    }
}
