package com.yida.solr.book.examples.ch16.mapreduce.fieldmapping;

import java.util.Map;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/15 20:47
 * 外部标题/字段与Solr的Schema中定义的域名称之间的映射关系
 */
public interface FieldMapping {
    public Map<String,String> mapping(String[] header);
}
