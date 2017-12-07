package com.yida.solr.book.examples.ch16.spark.scala

import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.common.SolrInputDocument
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import collection.JavaConversions._

/**
  * Created by Lanxiaowei 
  * Craated on 2016/11/21 8:50
  * 使用Scala提交索引至Solr
  */
object TestIndex {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:/hadoop-2.5.0")
    val zkHost: String = "linux.yida01.com:2181,linux.yida02.com:2181,linux.yida03.com:2181"
    val collection: String = "user-test"
    val queryStr: String = "*:*"
    val hdfsPath: String = "hdfs://linux.yida01.com:9000/tmp/text_file"
    val sparkConf: SparkConf = new SparkConf()
      .setAppName("myScalaSolrkApp")
      .setMaster("spark://linux.yida01.com:7077")
      .set("spark.default.parallelism", "1")
      .setJars(Array[String]("file:///E:/git-space/solr-book/target/scala-solr-test.jar"))
    val sc: SparkContext = new SparkContext(sparkConf)
    sc.addJar("libs/solr-solrj-6.2.1.jar")
    sc.addJar("libs/noggit-0.6.jar")
    sc.addJar("libs/httpmime-4.4.1.jar")

    val rdd: RDD[String] = sc.textFile(hdfsPath, 2)

    var rdd2:RDD[Array[String]] = rdd.map((i:String) => i.split(","))
    val docList = rdd2 map (x => {
            val id = x(0)
            val firstname = x(1)
            val lastname = x(2)
            val age = x(3).toInt
            val doc = new SolrInputDocument();
            doc.addField("id",id);
            doc.addField("firstname",firstname);
            doc.addField("lastname",lastname)
            doc.addField("age",age)
            doc
       }
    )
    var cloudSolrClient = new CloudSolrClient(zkHost)
    cloudSolrClient.add(docList.toJavaRDD().toLocalIterator)
    cloudSolrClient.setDefaultCollection(collection);
    cloudSolrClient.commit(true, true);
    cloudSolrClient.close();
  }
}
