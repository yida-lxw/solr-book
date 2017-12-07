package com.yida.solr.book.examples.ch13.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Lanxiaowei
 * 采用MapReduce从HDFS上读取文件创建索引
 */
public class SolrIndexMapReduce {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        Configuration conf  = new Configuration();
        conf.set("fs.defaultFS", "hdfs://linux.yida08.com:9000");
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        Job job = Job.getInstance(conf);

        job.setJobName("SolrIndex");
        job.setJarByClass(SolrIndexMapReduce.class);

        job.setMapperClass(SolrIndexMapReduce.SolrIndexMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("/user/hadoop/mapreduce/output/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("/user/hadoop/mapreduce/output2"));
        boolean success = job.waitForCompletion(true);
        if(success){
            System.out.println("任务执行成功");
        }
    }

    static class SolrIndexMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
        private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/mapreduce";
        private SolrInputDocument doc;
        private HttpSolrClient client;
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            client = new HttpSolrClient(SOLR_INSTANT_CORE);
            client.setRequestWriter(new BinaryRequestWriter());
        }

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            doc = new SolrInputDocument();
            String[] words = value.toString().split("\t");
            doc.addField("id", UUID.randomUUID().toString());
            doc.addField("word",words[0]);
            String count = words[1];
            doc.addField("count",null == count? 0 : Integer.valueOf(count));
            try {
                client.add(doc);
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            try {
                client.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
        }
    }
}
