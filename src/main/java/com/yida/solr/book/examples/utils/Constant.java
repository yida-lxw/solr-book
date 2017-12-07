package com.yida.solr.book.examples.utils;

/**
 * 常量类
 * @since 1.0
 * @author  Lanxiaowei@citic-finance.com
 * @date    2015-9-28上午9:47:15
 *
 */
public class Constant {
    /**拉丁字符编码*/
    public static final String DEFAULT_CHARSET_ISO = "ISO-8859-1";
    /**默认读写缓冲区大小*/
    public static final int BUFFER_SIZE = 4096;

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String HTML_DOCTYPY = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:18.0) Gecko/20100101 Firefox/18.0";

    public static final String[] PATTERNS = new String[] {
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm",
            "yyyy/MM/dd HH:mm:ss",
            "M/dd/yyyy",
            "M/d/yyyy",
            "MM/d/yyyy",
            "MM/dd/yyyy",
            "MM/dd/yyyy HH:mm",
            "MM/dd/yyyy HH:mm:ss",
            "HH:mm:ss",
            "HH:mm",
    };
    /**每页大小*/
    public static final int PAGE_SIZE = 20;
    /**默认编码*/
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**HTML页面模版*/
    public static StringBuffer HTML_TEMPLATE = new StringBuffer();

    static{
        HTML_TEMPLATE.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">").append("\n");
        HTML_TEMPLATE.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").append("\n");
        HTML_TEMPLATE.append("<head>").append("\n");
        HTML_TEMPLATE.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />").append("\n");
        HTML_TEMPLATE.append("<title>").append("%2$s").append("</title>").append("\n");
        HTML_TEMPLATE.append("</head>").append("\n");
        HTML_TEMPLATE.append("<body>").append("\n");
        HTML_TEMPLATE.append("%1$s").append("\n");
        HTML_TEMPLATE.append("</body>").append("\n");
        HTML_TEMPLATE.append("</html>");
    }

    public static final String DEFAULT_DATABASE_NAME = "simujingli";//simujingli   test-db
}

