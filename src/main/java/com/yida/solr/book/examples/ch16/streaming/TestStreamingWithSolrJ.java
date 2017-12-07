package com.yida.solr.book.examples.ch16.streaming;

import org.apache.solr.client.solrj.io.SolrClientCache;
import org.apache.solr.client.solrj.io.stream.StreamContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/23 10:35
 * 测试使用SolrJ发起Streaming请求
 */
public class TestStreamingWithSolrJ {
    public static void main(String[] args) {
        /*StreamContext context = new StreamContext()
        SolrClientCache cache = new SolrClientCache();
        context.setSolrClientCache(cache);

        Map topicQueryParams = new HashMap();
        topicQueryParams.put("q", "hello");  // The query for the topic
        topicQueryParams.put("rows", "500"); // How many rows to fetch during each run
        topicQueryParams.put("fl", "id, title"); // The field list to return with the documents

        TopicStream topicStream = new TopicStream(zkHost,        // Host address for the zookeeper service housing the collections
                "checkpoints",  // The collection to store the topic checkpoints
                "topicData",    // The collection to query for the topic records
                "topicId",      // The id of the topic
                -1,             // checkpoint every X tuples, if set -1 it will checkpoint after each run.
                topicQueryParams); // The query parameters for the TopicStream

        DaemonStream daemonStream = new DaemonStream(topicStream,             // The underlying stream to run.
                "daemonId",              // The id of the daemon
                1000,                    // The interval at which to run the internal stream
                500);                    // The internal queue size for the daemon stream. Tuples will be placed in the queue
        // as they are read by the internal internal thread.
        // Calling read() on the daemon stream reads records from the internal queue.
        daemonStream.setStreamContext(context);
        daemonStream.open();

        //Read until it's time to shutdown the DaemonStream. You can define the shutdown criteria.
        while (!shutdown()) {
            Tuple tuple = daemonStream.read() // This will block until tuples become available from the underlying stream (TopicStream)
            // The EOF tuple (signaling the end of the stream) will never occur until the DaemonStream has been shutdown.
            //Do something with the tuples
        }
        // Shutdown the DaemonStream.
        daemonStream.shutdown();

        //Read the DaemonStream until the EOF Tuple is found.
        //This allows the underlying stream to perform an orderly shutdown.

        while (true) {
            Tuple tuple = daemonStream.read();
            if (tuple.EOF) {
                break;
            } else {
                //Do something with the tuples.
            }
        }
        //Finally close the stream
        daemonStream.close();
        */
    }
}
