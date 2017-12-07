package com.yida.solr.book.examples.ch12.queryparser;

import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.ExtendedDismaxQParser;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SyntaxError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lanxiaowei
 * 前缀匹配或精确匹配时自动增加权重
 */
public class PrefixBoostQParser extends QParser {
    //public static final String NAME = "prefixBoost";
    public static final String PREFIX_BOOST = "pb";
    public static final String STUFFIX_BOOST = "sb";
    public static final String EXACT_BOOST = "eb";

    //前缀匹配时默认添加的权重
    public static final String DEFAULT_PREFIX_BOOST = "10";
    public static final String DEFAULT_STUFFIX_BOOST = "2";
    public static final String DEFAULT_EXACT_BOOST = "100";

    //占位符
    public static final String PLACE_HOLDER = "ph";
    //前缀匹配时人为添加的前缀字符，但是你需要注意有些分词器可能会将此字符当作停用词，
    //你需要将此字符从默认停用词中排除掉
    //public static final String PLACE_HOLDER_CHAR = "ǣ";
    //空格字符
    public static final String Blank_SPACE_CHAR = " ";
    //双引号
    public static final String DOUBLE_QUOT_CHAR = "\"";

    public static final String TC_CHAR = "^";

    //是否精确匹配
    public static final String EXACT_MATCH = "exactMatch";
    //默认不精确匹配
    public static final boolean DEFAULT_EXACT_MATCH = false;

    public static final String PARSER_PREFIX = "{!prefixBoost";

    public static final String PREFIX_TAG = "\"^";
    public static final String STUFFIX_TAG = "^\"";

    public static final String AND = "AND";
    public static final String OR = "OR";

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\"[^\"]*\"\\^\\d+\\s{1}");
        String s = "\"^iphone 6S\"^10 \"iphone 6S Plus\"^20 \"Apple iphone 6S\"^30 ";
        Matcher mather = pattern.matcher(s);
        while (mather.find()) {
            String t = mather.group();
            System.out.println(t);
        }
    }

    public PrefixBoostQParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        super(qstr, localParams, params, req);
    }
    public Query parse() throws SyntaxError {
        if (qstr != null) {
            if(qstr.startsWith(AND) || qstr.startsWith(AND.toLowerCase())) {
                qstr = qstr.substring(qstr.indexOf(AND) + AND.length()).trim();
            }
            if(qstr.startsWith(OR) || qstr.startsWith(OR.toLowerCase())) {
                qstr = qstr.substring(qstr.indexOf(OR) + OR.length()).trim();
            }
            if(qstr.endsWith(AND) || qstr.endsWith(AND.toLowerCase())) {
                qstr = qstr.substring(0,qstr.indexOf(AND)).trim();
            }
            if(qstr.endsWith(OR) || qstr.endsWith(OR.toLowerCase())) {
                qstr = qstr.substring(0,qstr.indexOf(OR)).trim();
            }
            if(qstr.startsWith("\"")) {
                String prefixBoost = localParams.get(PREFIX_BOOST,null);
                if(prefixBoost == null || "".equals(prefixBoost)) {
                    prefixBoost = params.get(PREFIX_BOOST,DEFAULT_PREFIX_BOOST);
                }
                String stuffixBoost = localParams.get(STUFFIX_BOOST,null);
                if(stuffixBoost == null || "".equals(stuffixBoost)) {
                    stuffixBoost = params.get(STUFFIX_BOOST,DEFAULT_STUFFIX_BOOST);
                }
                String exactBoost = localParams.get(EXACT_BOOST,null);
                if(exactBoost == null || "".equals(exactBoost)) {
                    exactBoost = params.get(EXACT_BOOST,DEFAULT_EXACT_BOOST);
                }

                //获取占位符
                /*String placeHolder = localParams.get(PLACE_HOLDER,PLACE_HOLDER_CHAR);
                if(placeHolder == null || "".equals(placeHolder)) {
                    placeHolder = params.get(PLACE_HOLDER,PLACE_HOLDER_CHAR);
                }*/
                if(qstr.startsWith(AND)) {
                    qstr = qstr.substring(qstr.indexOf(AND) + AND.length()).trim();
                }
                if(qstr.startsWith(OR)) {
                    qstr = qstr.substring(qstr.indexOf(OR) + OR.length()).trim();
                }
                if(qstr.endsWith(AND)) {
                    qstr = qstr.substring(0,qstr.indexOf(AND)).trim();
                }
                if(qstr.endsWith(OR)) {
                    qstr = qstr.substring(0,qstr.indexOf(OR)).trim();
                }
                //String[] array = qstr.split(Blank_SPACE_CHAR);
                List<String> list = new ArrayList<String>();
                String regex = "";
                if(qstr.indexOf("\"^") != -1) {
                    regex = "\"[^\"]*\"\\^\\d+\\s{1}";

                } else {
                    regex = "\"[^\"]*\"\\s{1}";
                }
                Pattern pattern = Pattern.compile(regex);
                Matcher mather = pattern.matcher(qstr + " ");
                while (mather.find()) {
                    String t = mather.group();
                    list.add(t);
                }
                String[] array = list.toArray(new String[]{});
                StringBuffer qBuffer = new StringBuffer();
                //当前字符串是否是Boolean操作符AND/OR
                boolean currentOp = false;
                for(int i = 0; i < array.length; i++) {
                    String str = array[i];
                    if("".equals(str) || " ".equals(str)) {
                        continue;
                    }
                    if((str.equalsIgnoreCase(AND) || str.equalsIgnoreCase(OR)) && !currentOp) {
                        currentOp = true;
                        if(i != 0 && i != array.length - 1) {
                            qBuffer.append(Blank_SPACE_CHAR + str.toUpperCase() + Blank_SPACE_CHAR);
                        }
                        continue;
                    }
                    currentOp = false;
                    String op = req.getSchema().getQueryParserDefaultOperator();

                    if(haveBoost(str)) {
                        //如果是精确匹配
                        if(str.startsWith(PREFIX_TAG) && str.endsWith(STUFFIX_TAG)) {
                            str = str.replace(PREFIX_TAG,DOUBLE_QUOT_CHAR)
                                    .replace(STUFFIX_TAG, DOUBLE_QUOT_CHAR);
                        } else if(str.startsWith(PREFIX_TAG)) {
                            str = str.replace(PREFIX_TAG,DOUBLE_QUOT_CHAR);
                        } else if(str.endsWith(STUFFIX_TAG)) {
                            str = str.replace(STUFFIX_TAG, DOUBLE_QUOT_CHAR);
                        }
                    } else {
                        //如果是精确匹配
                        if(str.startsWith(PREFIX_TAG) && str.endsWith(STUFFIX_TAG)) {
                            str = setBoost(str,exactBoost);
                            str = str.replace(PREFIX_TAG,DOUBLE_QUOT_CHAR)
                                    .replace(STUFFIX_TAG, DOUBLE_QUOT_CHAR);
                        } else if(str.startsWith(PREFIX_TAG)) {
                            str = setBoost(str,prefixBoost);
                            str = str.replace(PREFIX_TAG,DOUBLE_QUOT_CHAR);
                        } else if(str.endsWith(STUFFIX_TAG)) {
                            str = setBoost(str,stuffixBoost);
                            str = str.replace(STUFFIX_TAG, DOUBLE_QUOT_CHAR);
                        }
                    }
                    qBuffer.append(str).append(Blank_SPACE_CHAR).append(op).append(Blank_SPACE_CHAR);
                }
                qstr = qBuffer.toString();
                System.out.println(qstr);
            }
        }
        return new ExtendedDismaxQParser(qstr,localParams,params,req).parse();
    }

    private boolean haveBoost(String str) {
        return Pattern.matches("[^\\^]+\\^\\d+$",str);
    }

    private String setBoost(String str,String boost) {
        return str += str + "^" + boost;
    }
}
