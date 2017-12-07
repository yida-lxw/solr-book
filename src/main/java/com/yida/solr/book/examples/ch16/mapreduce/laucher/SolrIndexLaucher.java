package com.yida.solr.book.examples.ch16.mapreduce.laucher;

import com.yida.solr.book.examples.ch16.mapreduce.Params;
import com.yida.solr.book.examples.ch16.mapreduce.mapper.CSVIndexMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/15 22:48
 * Solr索引创建运行类
 */
public class SolrIndexLaucher extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new SolrIndexLaucher(), args);
        System.exit(exitCode);
    }

    public int run(String[] args) throws Exception {
        Configuration conf = this.getConf();
        String hdfsInputFile = null;
        String hdfsInputDir = null;
        String hdfsOutputPath = null;
        if (args.length == 2) {
            hdfsInputFile = args[0];
            hdfsOutputPath = args[1];
        } else if (args.length == 1) {
            hdfsInputFile = args[0];
            hdfsOutputPath = conf.get(Params.HDFS_OUT_PATH);
            if(null == hdfsOutputPath || "".equals(hdfsOutputPath)) {
                printUsage();
                return -1;
            }
        } else {
            hdfsInputFile = conf.get(Params.HDFS_INPUT_PATH);
            hdfsInputDir = conf.get(Params.HDFS_INPUT_DIR);
            if((null == hdfsInputFile || "".equals(hdfsInputFile)) ||
                    null == hdfsInputDir || "".equals(hdfsInputDir)) {
                printUsage();
                return -1;
            }
        }
        System.setProperty("HADOOP_USER_NAME", conf.get(Params.HADOOP_USER,"hadoop"));
        Job job = new Job(conf);
        job.setJobName(conf.get(Params.MAPREDUCE_JOB_NAME,"Solr Index Job"));
        job.setJarByClass(SolrIndexLaucher.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(NullOutputFormat.class);
        if(null != hdfsInputDir && !"".equals(hdfsInputDir)) {
            FileInputFormat.setInputDirRecursive(job,true);
            FileInputFormat.setInputPaths(job, new Path(hdfsInputDir));
        } else {
            FileInputFormat.setInputPaths(job, new Path(hdfsInputFile));
        }
        FileSystem fileSystem  = FileSystem.get(conf);
        Path outdir = new Path(hdfsOutputPath);
        //若输出目录存在，则删除
        if(fileSystem.exists(outdir)){
            fileSystem.delete(outdir, true);
        }
        FileOutputFormat.setOutputPath(job, outdir);
        job.setMapperClass(CSVIndexMapper.class);
        job.setNumReduceTasks(0);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(NullWritable.class);

        if (job.waitForCompletion(true)) {
            return 0;
        }
        return 1;
    }

    private void printUsage() {
        System.err.printf(
                "Usage: %s [generic options] \n" +
                        "-D " + Params.CSV_HEADERS + "=<header-list>\n" +
                        "-D " + Params.SOLR_URL + "=<solr-url>\n" +
                        "-D " + Params.SOLR_REQUEST_QUEUE_SIZE + "=<request buffer size>\n" +
                        "-D " + Params.SOLR_REQUEST__THREADS + "=<request thread count>\n" +
                        "-D " + Params.SOLR_ID__FIELD + "=<Solr ID Field>\n" +
                        "-D " + Params.ZK_HOST + "=<Zookeeper Host>\n" +
                        "-D " + Params.ZK_CLIENT_TIMEOUT + "=<Zookeeper Client Timeout>\n" +
                        "-D " + Params.ZK_CONNECT_TIMEOUT + "=<Zookeeper Connect Timeout>\n" +
                        "-D " + Params.SOLR_DEFAULT__COLLECTION + "=<default collection>\n" +
                        "-D " + Params.HDFS_URL + "=<HDFS URL>\n" +
                        "-D " + Params.HDFS_INPUT_PATH + "=<HDFS Input File>\n" +
                        "-D " + Params.HDFS_INPUT_DIR + "=<HDFS Input Directory>\n" +
                        "-D " + Params.HDFS_OUT_PATH + "=<HDFS Output Path>\n" +
                        "-D " + Params.MAPREDUCE_TASKS + "=<Mapreduce Task Count>\n" +
                        "-D " + Params.MAPREDUCE_JOB_NAME + "=<Mapreduce Job Name>\n" +
                        "-D " + Params.HADOOP_USER + "=<Hadoop User>\n" +
                        "-" + Params.DEPEND_JARS + " <Thrid-Part jars>" +
                        " <HDFS Input File> <HDFS Output Path>\n",
                getClass().getSimpleName());
        ToolRunner.printGenericCommandUsage(System.err);
    }
}
