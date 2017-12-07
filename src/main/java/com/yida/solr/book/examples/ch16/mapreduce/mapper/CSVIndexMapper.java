package com.yida.solr.book.examples.ch16.mapreduce.mapper;

import com.yida.solr.book.examples.ch16.mapreduce.Params;
import com.yida.solr.book.examples.ch16.mapreduce.parser.CSVParser;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/15 22:35
 * CSV格式的文件创建索引的Mapper实现类
 */
public class CSVIndexMapper extends IndexMapper {
    @Override
    protected void setup(Context context) throws IOException,
            InterruptedException {
        super.setup(context);
        Configuration conf = context.getConfiguration();
        this.parser = new CSVParser(',', '"');
        if(null == this.parser) {
            throw new RuntimeException("Parser MUST not be null.");
        }
        String header = conf.get(Params.CSV_HEADERS);
        if(null == header || "".equals(header)) {
            throw new RuntimeException("header MUST not be null.");
        }
        this.parser.setHeaders(header.split(","));
    }
}
