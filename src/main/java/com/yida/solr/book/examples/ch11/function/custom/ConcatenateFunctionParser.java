package com.yida.solr.book.examples.ch11.function.custom;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.solr.search.FunctionQParser;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.ValueSourceParser;

/**
 * Created by Lanxiaowei
 * 自定义Concatenate函数解析器
 */
public class ConcatenateFunctionParser extends ValueSourceParser {
    public ValueSource parse(FunctionQParser parser) throws SyntaxError {
        ValueSource value1 = parser.parseValueSource();
        ValueSource value2 = parser.parseValueSource();
        String delimiter = null;
        if (parser.hasMoreArguments()){
            delimiter = parser.parseArg();
        }
        return new ConcatenateFunction(value1, value2, delimiter);
    }
}