package com.yida.solr.book.examples.ch16.spark.solr;

import com.lucidworks.spark.rdd.SolrJavaRDD;
import com.lucidworks.spark.util.SolrSupport;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.rdd.RDD;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.concurrent.LinkedBlockingDeque;

/**
  * Created by Lanxiaowei
  * Craated on 2016/11/20 15:48
  * 测试在Spark程序中读取HDFS上的文件并将其索引数据至Solr中
  */
public class TestIndex {
   public static void main(String[] args) throws Exception {
     System.setProperty("hadoop.home.dir", "E:/hadoop-2.5.0");
     String zkHost = "linux.yida01.com:2181,linux.yida02.com:2181,linux.yida03.com:2181";
     String collection = "user-test";
     String queryStr = "*:*";
     String hdfsPath = "hdfs://linux.yida01.com:9000/tmp/text_file";
     SparkConf sparkConf = new SparkConf()
             .setAppName("mySparSolrkApp")
             .setMaster("spark://linux.yida01.com:7077")
             .set("spark.default.parallelism", "1")
             .setJars(new String[] {"file:///E:/git-space/solr-book/target/spark-solr-test.jar"});
     SparkContext sc = new SparkContext(sparkConf);

     //读取输入文件
     RDD rdd = sc.textFile(hdfsPath,2);
     JavaRDD<String> javaRDD = rdd.toJavaRDD();
     JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
     javaSparkContext.addJar("libs/spark-solr-3.0.1-SNAPSHOT.jar");
     javaSparkContext.addJar("libs/solr-solrj-6.2.1.jar");
     javaSparkContext.addJar("libs/noggit-0.6.jar");
     javaSparkContext.addJar("libs/httpmime-4.4.1.jar");

     JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(500));
     JavaRDD<String> input = jssc.sparkContext().parallelize(javaRDD.collect(),1);
     LinkedBlockingDeque<JavaRDD<String>> queue = new LinkedBlockingDeque<JavaRDD<String>>();
     queue.add(input);

     JavaDStream<SolrInputDocument> docs = jssc.queueStream(queue).map(
         new Function<String, SolrInputDocument>() {
           public SolrInputDocument call(String row) {
             String[] fields = row.split(",");
             SolrInputDocument doc = new SolrInputDocument();
             doc.setField("id", fields[0]);
             doc.setField("firstname", fields[1]);
             doc.setField("lastname", fields[2]);
             doc.setField("age", Integer.valueOf(fields[2]));
             return doc;
           }
         }
     );

     // 开始请求Solr
     SolrSupport.indexDStreamOfDocs(zkHost, collection, 1, docs.dstream());
     jssc.start();

     Thread.sleep(5000);

     SolrJavaRDD solrRDD = SolrJavaRDD.get(zkHost, collection, jssc.sparkContext().sc());
     JavaRDD<SolrDocument> resultsRDD =
             solrRDD.queryShards(new SolrQuery(queryStr));

     long numFound = resultsRDD.count();
     System.out.println();
   }

}