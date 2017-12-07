package com.yida.solr.book.examples.ch13.jsonrequest;

import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.ResponseParser;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.util.NamedList;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * 响应信息JSON解析器
 */
public class JSONResponseParser extends ResponseParser {
    private String writerType = "json";
    private static final String CONTENT_TYPE = "application/json; charset=utf8";
    public JSONResponseParser() {
    }

    public JSONResponseParser(String writerType) {
        this.writerType = writerType;
    }

    public NamedList<Object> processResponse(InputStream body, String encoding) {
        try {
            StringWriter e = new StringWriter();
            IOUtils.copy(body, e, encoding);
            String output = e.toString();
            NamedList list = new NamedList();
            list.add("response", output);
            return list;
        } catch (IOException var6) {
            throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "parsing error", var6);
        }
    }

    public NamedList<Object> processResponse(Reader reader) {
        try {
            StringWriter e = new StringWriter();
            IOUtils.copy(reader, e);
            String output = e.toString();
            NamedList list = new NamedList();
            list.add("response", output);
            return list;
        } catch (IOException var5) {
            throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "parsing error", var5);
        }
    }

    private Map<String,LinkedHashMap> toMap(String json) {
        if(null == json || "".equals(json)) {
            return new HashMap<String,LinkedHashMap>();
        }
        Map<String,LinkedHashMap> map = new HashMap<String,LinkedHashMap>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(json, new TypeReference<HashMap>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    public String getWriterType() {
        return this.writerType;
    }

    public void setWriterType(String writerType) {
        this.writerType = writerType;
    }
}
