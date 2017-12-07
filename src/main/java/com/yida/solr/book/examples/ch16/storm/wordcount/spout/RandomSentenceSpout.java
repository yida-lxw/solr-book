package com.yida.solr.book.examples.ch16.storm.wordcount.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/19 19:38
 * 自定义Spout实现
 */
public class RandomSentenceSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Random random;

    private String[] sentences = new String[]{
            "the cow jumped over the moon",
            "an apple a day keeps the doctor away",
            "four score and seven years ago",
            "snow white and the seven dwarfs",
            "i am at two with nature" };

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector = spoutOutputCollector;
        random = new Random();
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);
        String sentence = sentences[random.nextInt(sentences.length)];
        collector.emit(new Values(sentence));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }
}
