package com.yida.solr.book.examples.ch16.mapreduce.parser;

import com.yida.solr.book.examples.ch16.mapreduce.fieldmapping.FieldMapping;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/15 19:58
 * Solr Document解析器，用于将外部数据源转换成Solr中的SolrInputDocument
 */
public interface DocumentParser {
    /**
     * 将Hadoop读取的每一行数据转换成SolrInputDocument
     * @return
     */
    public SolrInputDocument parse(String text) throws IOException;

    /**
     * 设置域名称映射器
     * @param mapping
     */
    public void setFieldMapper(FieldMapping mapping);

    public void setHeaders(String[] headers);
}
