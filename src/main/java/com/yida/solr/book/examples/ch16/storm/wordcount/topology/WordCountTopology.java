package com.yida.solr.book.examples.ch16.storm.wordcount.topology;

import com.yida.solr.book.examples.ch16.storm.wordcount.bolt.SplitSentenceBolt;
import com.yida.solr.book.examples.ch16.storm.wordcount.bolt.WordCountBolt;
import com.yida.solr.book.examples.ch16.storm.wordcount.spout.RandomSentenceSpout;
import com.yida.solr.book.examples.utils.JarUtils;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.io.File;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/19 19:53
 * 使用Storm实现经典的Word Count示例
 */
public class WordCountTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new RandomSentenceSpout(), 5);
        builder.setBolt("split", new SplitSentenceBolt(), 8).shuffleGrouping("spout");
        builder.setBolt("count", new WordCountBolt(), 12).fieldsGrouping("split", new Fields("word"));

        Config conf = new Config();
        conf.setDebug(false);
        //args = new String[] {"work-count-toplogy1"};
        //Storm集群提交模式
        if (args != null && args.length > 0) {
            File jarFile = JarUtils.createTempJar(WordCountTopology.class.getClassLoader()
                    .getResource("").getPath());
            ClassLoader classLoader = JarUtils.getClassLoader();
            Thread.currentThread().setContextClassLoader(classLoader);

            //System.setProperty("storm.jar", Class.forName("com.xx.xxx.RemoteRunningTopology").getProtectionDomain().getCodeSource().getLocation().getPath());
            System.setProperty("storm.jar", jarFile.toString());
            conf.setNumWorkers(3);
            conf.put(Config.NIMBUS_HOST, "linux.yida01.com");
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        }
        //本地运行模式
        else {
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
            Thread.sleep(60000);
            cluster.shutdown();
        }
    }
}
