package com.yida.solr.book.examples.ch16.storm.wordcount.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.text.BreakIterator;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/19 19:44
 */
public class SplitSentenceBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String sentence = tuple.getStringByField("sentence");
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(sentence);
        int start = boundary.first();
        for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
            String word = sentence.substring(start, end);
            word = word.replaceAll("\\s+", "");
            if (!word.equals("")) {
                basicOutputCollector.emit(new Values(word));
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }
}
