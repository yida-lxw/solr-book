package com.yida.solr.book.examples.ch16.storm.wordcount.bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Constants;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.Config;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/19 19:47
 */
public class WordCountBolt extends BaseBasicBolt {
    private static final Logger logger = LogManager.getLogger(WordCountBolt.class);
    Map<String, Integer> counts = new HashMap<String, Integer>();
    private Integer emitFrequency;

    public WordCountBolt() {
        emitFrequency = 5;
    }

    public WordCountBolt(Integer frequency) {
        emitFrequency = frequency;
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config conf = new Config();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, emitFrequency);
        return conf;
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        if (tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
                && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID)) {
            for (String word : counts.keySet()) {
                Integer count = counts.get(word);
                collector.emit(new Values(word, count));
                System.out.println("[" + word + "]:" + count);
                //logger.info("[" + word + "]:" + count);
            }
        } else {
            String word = tuple.getStringByField("word");
            Integer count = counts.get(word);
            if (count == null) {
                count = 0;
            }
            count++;
            counts.put(word, count);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }
}
