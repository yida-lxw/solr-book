package com.yida.solr.book.examples.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lanxiaowei
 * 字符串相关操作工具类
 */
public class StringUtils {
    private static final Pattern FIND_NUMBER = Pattern.compile("\\d+");

    public static final int INDEX_NOT_FOUND = -1;
    public static final String EMPTY = "";

    private static final String HEXES = "0123456789ABCDEF";
    private static final String[] escapeIgnore = new String[] { "*", "+", "-", ".", "/", "@", "_" };
    private static final String[] encodeURIIgnore = new String[] { "!", "#", "$", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "=", "?", "@", "_", "~" };
    private static final String[] encodeURIComponentIgnore = new String[] { "!", "'", "(", ")", "*", "-", ".", "_", "~" };

    private static final String[] padding = {"", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          "};

    private static char[] CODEC_TABLE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

    private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
            19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

    private static Pattern metaPattern = Pattern.compile("<meta\\s+([^>]*http-equiv\\s*=\\s*\"?content-type\"?[^>]*)>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static Pattern metaPattern_html5 = Pattern.compile("<meta\\s+[^>]*(charset\\s*=\\s*['\"_\\-0-9a-z]*)[^>]*>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static Pattern charsetPattern = Pattern.compile("charset\\s*=\\s*([_\\-0-9a-z]*)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static Pattern charsetPattern_html5 = Pattern.compile("charset\\s*=\\s*(['\"_\\-0-9a-z]*)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private static final Pattern patternForCharset = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private static final String[] REGEX_ESCAPES = {
            "$","(",")","*","+",".","?","\\",
            "/","^","{","}","|"
    };

    /**
     * 生成MD5串
     * @param msg
     * @return
     */
    public static final String getMD5Hash(final String msg) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            byte[] aby = md5.digest(msg.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer(32);
            for (int i = 0; i < aby.length; ++i) {
                sb.append(Integer.toHexString((aby[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * 提取数字
     * @param str
     * @return
     */
    public static String extractNumPart(String str) {
        Matcher numFinder =  FIND_NUMBER.matcher(str);
        return (numFinder.find()) ? numFinder.group() : null;
    }


    /**
     * @Title: encode
     * @Description: BASE64编码( JDK自带的BASE64Encoder会有每76个字符末尾添加\r\n换行,此方法不会换行,
     *               且BASE64Encoder编码性能极低,建议使用common-codec提供的BASE64编码函数或者使用此方法)
     * @param @param str 传入需要BASE64编码的原字符串
     * @param @return
     * @return String
     * @throws
     */
    public static String base64Encode(String str) {
        byte[] bt = str.getBytes();
        int totalBits = bt.length * 8;
        int nn = totalBits % 6;
        int curPos = 0;
        StringBuffer toReturn = new StringBuffer();
        while (curPos < totalBits) {
            int bytePos = curPos / 8;
            switch (curPos % 8) {
                case 0:
                    toReturn.append(CODEC_TABLE[(bt[bytePos] & 0xfc) >> 2]);
                    break;
                case 2:
                    toReturn.append(CODEC_TABLE[(bt[bytePos] & 0x3f)]);
                    break;
                case 4:
                    if (bytePos == bt.length - 1) {
                        toReturn.append(CODEC_TABLE[((bt[bytePos] & 0x0f) << 2) & 0x3f]);
                    } else {
                        int pos = (((bt[bytePos] & 0x0f) << 2) | ((bt[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
                        toReturn.append(CODEC_TABLE[pos]);
                    }
                    break;
                case 6:
                    if (bytePos == bt.length - 1) {
                        toReturn.append(CODEC_TABLE[((bt[bytePos] & 0x03) << 4) & 0x3f]);
                    } else {
                        int pos = (((bt[bytePos] & 0x03) << 4) | ((bt[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
                        toReturn.append(CODEC_TABLE[pos]);
                    }
                    break;
                default:
                    break;
            }
            curPos += 6;
        }
        if (nn == 2) {
            toReturn.append("==");
        } else if (nn == 4) {
            toReturn.append("=");
        }
        return toReturn.toString();
    }

    /**
     * @Title: base64Decode
     * @Description: BASE64解码
     * @param @param str 传入需要进行BASE64解码的原字符串
     * @param @return
     * @param @throws UnsupportedEncodingException
     * @return byte[]
     * @throws
     */
    public static byte[] base64Decode(String str) throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        byte[] data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {
			/* b1 */
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1)
                break;
			/* b2 */
            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1)
                break;
            buffer.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
			/* b3 */
            do {
                b3 = data[i++];
                if (b3 == 61)
                    return buffer.toString().getBytes("ISO-8859-1");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1)
                break;
            buffer.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
			/* b4 */
            do {
                b4 = data[i++];
                if (b4 == 61)
                    return buffer.toString().getBytes("ISO-8859-1");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1)
                break;
            buffer.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return buffer.toString().getBytes("ISO-8859-1");
    }

    /**
     * @Title: toHexString
     * @Description: 字符串进行十六进制的ASCII编码
     * @param @param s
     * @param @return
     * @return String
     * @throws
     */
    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * @Title: encode
     * @Description: URL编码(原理:将字符转换成16进制,然后前面加前缀%,对于0-255范围之外字符加前缀%u)
     *               不编码字符包括.-*_,注意空格会被编码成加号+,本函数已修复此BUG
     * @param @param url
     * @return void
     * @throws
     */
    public static String encode(String url) {
        if (GerneralUtils.isEmptyString(url)) {
            return url;
        }
        String result = URLEncoder.encode(url);
        result = result.replace("%3A", ":").replace("%2F", "/");
        result = result.replace("%3F", "?").replace("%3D", "=").replace("%26", "&");
        return result.replace("+", "%20");
    }

    /**
     * @Title: decode
     * @Description: URL解码(会将+加号解码成空格)
     * @param @param url
     * @return void
     * @throws
     */
    public static String decode(String url) {
        if (GerneralUtils.isEmptyString(url)) {
            return url;
        }
        return URLDecoder.decode(url);
    }

    /**
     * @Title: escape
     * @Description: URL中特殊字符编码(* + - . / @ _ 0-9 a-z A-Z不会被编码)
     * @param @param src
     * @param @return
     * @return String
     * @throws
     */
    public static String escape(String src) {
        if (GerneralUtils.isEmptyString(src)) {
            return src;
        }
        char j = 0;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (int i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            boolean flag = false;
            for (int k = 0; k < escapeIgnore.length; k++) {
                if (escapeIgnore[k].charAt(0) == j) {
                    tmp.append(j);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
                    tmp.append(j);
                } else if (j < 256) {
                    tmp.append("%");
                    if (j < 16) {
                        tmp.append("0");
                    }
                    tmp.append(Integer.toString(j, 16));
                } else {
                    tmp.append("%u");
                    tmp.append(Integer.toString(j, 16));
                }
            }
        }
        return tmp.toString();
    }

    /**
     * @Title: encodeURI
     * @Description: 对URI进行完整的编码(!#$&'()*+,-./:;=?@_~0-9a-zA-Z不会被编码)
     * @param @param uriString
     * @param @return
     * @return String
     * @throws
     */
    public static String encodeURI(String src) {
        if (GerneralUtils.isEmptyString(src)) {
            return src;
        }
        char j = 0;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (int i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            boolean flag = false;
            for (int k = 0; k < encodeURIIgnore.length; k++) {
                if (encodeURIIgnore[k].charAt(0) == j) {
                    tmp.append(j);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
                    tmp.append(j);
                } else if (j < 256) {
                    tmp.append("%");
                    if (j < 16) {
                        tmp.append("0");
                    }
                    tmp.append(Integer.toString(j, 16));
                } else {
                    tmp.append("%u");
                    tmp.append(Integer.toString(j, 16));
                }
            }
        }
        return tmp.toString();
    }

    /**
     * @Title: encodeURIComponent
     * @Description: 对URI参数部分进行编码(!'()*-._~0-9a-zA-Z不会被编码)
     *               主要适用于对URL传递的特殊字符或中文参数部分进行编码,而不是把整个URL传递给此函数
     *               eg:http://xxxx.do?qu=中文&r=50%,明显参数:中文和50%需要编码,
     *               因此你只需要把中文和50%传递给此函数进行编码
     * @param @param uriString
     * @param @return
     * @return String
     * @throws
     */
    public static String encodeURIComponent(String src) {
        if (GerneralUtils.isEmptyString(src)) {
            return src;
        }
        char j = 0;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (int i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            boolean flag = false;
            for (int k = 0; k < encodeURIComponentIgnore.length; k++) {
                if (encodeURIComponentIgnore[k].charAt(0) == j) {
                    tmp.append(j);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
                    tmp.append(j);
                } else if (j < 256) {
                    tmp.append("%");
                    if (j < 16) {
                        tmp.append("0");
                    }
                    tmp.append(Integer.toString(j, 16));
                } else {
                    tmp.append("%u");
                    tmp.append(Integer.toString(j, 16));
                }
            }
        }
        return tmp.toString();
    }

    /**
     * @Title: unescape
     * @Description: URL中特殊字符解码，与escape功能相反
     * @param @param src
     * @param @return
     * @return String
     * @throws
     */
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (-1 == pos) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     * 从字符串文本中提取日期字符串,如：xxxxxxx2/14/2013xxxxxxxx -->2/14/2013
     *
     * @param text
     * @return
     */
    public static String getDateTextFromString(String text) {
        if (GerneralUtils.isEmptyString(text)) {
            return null;
        }
        StringBuffer reg = new StringBuffer();
        reg.append("((1|2)\\d{3})(/|-|.)(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9]) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("([12][0-9]|3[01]|0?[1-9])(/|-|.)(1[012]|0?[1-9])(/|-|.)((1|2)\\d{3}) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9])(/|-|.)((1|2)\\d{3}) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("((1|2)\\d{3})(/|-|.)(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9]) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("((1|2)\\d{3})(/|-|.)([12][0-9]|3[01]|0?[1-9])(/|-|.)(1[012]|0?[1-9]) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9]):([1-5][0-9]|0?[0-9])|");

        reg.append("((1|2)\\d{3})(/|-|.)(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9]) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("([12][0-9]|3[01]|0?[1-9])(/|-|.)(1[012]|0?[1-9])(/|-|.)((1|2)\\d{3}) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9])(/|-|.)((1|2)\\d{3}) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("((1|2)\\d{3})(/|-|.)(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9]) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("((1|2)\\d{3})(/|-|.)([12][0-9]|3[01]|0?[1-9])(/|-|.)(1[012]|0?[1-9]) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9])|");
        reg.append("((1|2)\\d{3})(/|-|.)(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9])|");
        reg.append("([12][0-9]|3[01]|0?[1-9])(/|-|.)(1[012]|0?[1-9])(/|-|.)((1|2)\\d{3})|");
        reg.append("(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9])(/|-|.)((1|2)\\d{3})|");
        reg.append("((1|2)\\d{3})(/|-|.)(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9])|");
        reg.append("((1|2)\\d{3})(/|-|.)([12][0-9]|3[01]|0?[1-9])(/|-|.)(1[012]|0?[1-9])");
        Pattern pattern = Pattern.compile(reg.toString());
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * @Title: getDateTimeFromString
     * @Description: 从字符串文本中提取日期时间字符串
     * @param @param text
     * @param @return
     * @return String
     * @throws
     */
    public static String getDateTimeFromString(String text) {
        StringBuffer reg = new StringBuffer();
        reg.append("((1|2)\\d{3})(/|-|.)(1[012]|0?[1-9])(/|-|.)([12][0-9]|3[01]|0?[1-9]) (1[0-9]|2[0123]|0?[0-9]):([1-5][0-9]|0?[0-9]):([1-5][0-9]|0?[0-9])$");
        Pattern pattern = Pattern.compile(reg.toString());
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从字符串中提取出数字(可以提取整数和小数)
     *
     * @param text
     * @return
     */
    public static String getNumbericFromString(String text) {
        if (GerneralUtils.isEmptyString(text)) {
            return null;
        }
        StringBuffer reg = new StringBuffer();
        reg.append("0\\.\\d+|[1-9]{1}([0-9]+)?\\.\\d+|\\d+");
        Pattern pattern = Pattern.compile(reg.toString());
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从字符串中提取URL链接,<br/>
     * 如:http://www.google.com.hk
     * http://www.savings.com/popup/detail/coupon-723379.html
     *
     * @param text
     * @return
     */
    public static String getURLFromString(String text) {
        if (GerneralUtils.isEmptyString(text)) {
            return null;
        }
        StringBuffer reg = new StringBuffer();
        reg.append("\\b((ftp|http|https?)://[-\\w]+(\\.\\w[-\\w]*)+|(?i:[a-z0-9](?:[-a-z0-9]*[a-z0-9])?\\.)+");
        reg.append("(?-i:com\\b|edu\\b|biz\\b|gov\\b|in(?:t|fo)\\b|mil\\b|net\\b|org\\b|[a-z][a-z]\\b))");
        reg.append("(:\\d+)?(/[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]*(?:[.!,?]+[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]+)*)?");
        Pattern pattern = Pattern.compile(reg.toString());
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 将毫秒转换成多少小时分钟秒的格式 如：12410000-->3小时26分钟50秒
     *
     * @param time
     * @return 格式化后的时间格式
     *
     */
    public static String formatMillSecond(long time, String hourPlaceholder, String minuePlaceholder, String secondPlaceholder) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = new Long(time).intValue() / 1000;
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        StringBuffer buffer = new StringBuffer();
        if (hour > 0) {
            buffer.append(String.valueOf(hour)).append(hourPlaceholder);
        }
        if (minute > 0) {
            buffer.append(String.valueOf(minute)).append(minuePlaceholder);
        }
        if (second >= 0) {
            buffer.append(String.valueOf(second)).append(secondPlaceholder);
        }
        return buffer.toString();
    }

    /**
     * 将多少小时分钟秒的格式字符串转换成毫秒数(与formatMillSecond方法相反) 如：3小时26分钟50秒-->12410000
     *
     * @param str
     * @return
     */
    public static long unFormatMillSecond(String str) {
        if (null == str || str.length() == 0) {
            return 0;
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        if (list.size() != 3) {
            return 0;
        }
        int hour = Integer.parseInt(list.get(0));
        int minue = Integer.parseInt(list.get(1));
        int second = Integer.parseInt(list.get(2));
        return hour * 3600000 + minue * 60000 + second * 1000;
    }

    /**
     * @Title: cleanComment
     * @Description: 剔除JS/HTML注释
     * @param @param str
     * @param @return
     * @return String
     * @throws
     */
    public static String cleanComment(String str) {
        if (null == str || str.length() == 0) {
            return null;
        }
        // 剔除此类注释：<!-- 这里是注释 -->
        Pattern pattern = Pattern.compile("<!--[\\w\\W\r\\n]*?-->");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("");
        // 剔除此类注释：/**这里是注释 */
        pattern = Pattern.compile("/\\*[\\w\\W\r\\n]*?\\*/");
        matcher = pattern.matcher(str);
        str = matcher.replaceAll("");
        // 剔除此类注释：//这里是注释
        str = str.replaceAll("//[^\n]*\n", "");
        return str;
    }

    /**
     * @Title: cleanJavaScript
     * @Description: 剔除所有的JavaScript代码 对于<script></script>标签之间含有</script>字符串的情况，
     *               可能会有BUG，后续再完善 上述BUG已修复，支持<script>标签嵌套
     * @param @param str
     * @param @return
     * @return String
     * @throws
     */
    public static String cleanJavaScript(String str) {
		/*
		 * if (GerneralUtils.null == str || str.length() == 0) { return null; } return
		 * str.replaceAll("(?s)<script.*?>(.*?)</script>","");
		 */
        if (null == str || str.length() == 0) {
            return null;
        }
        str = str.toLowerCase().replaceAll("<script(?:[^<]++|<(?!/script>))*+</script>", "<script>");
        while (str.contains("</script>")) {
            str = str.replaceAll("<script(?:[^<]++|<(?!/script>))*+</script>", "");
        }
        return str;
    }

    /**
     * @Title: cleanCSS
     * @Description: 剔除<style></style>标签之间的所有CSS代码
     * @param @param str
     * @param @return
     * @return String
     * @throws
     */
    public static String cleanCSS(String str) {
        if (null == str || str.length() == 0) {
            return null;
        }
        return str.toLowerCase().replaceAll("(?s)<style.*?>(.*?)</style>", "");
    }

    /**
     * Unicode字符串转换成中文
     *
     * @param dataStr
     * @return
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    /**
     * 判断字符串是否以数字结尾
     *
     * @param str
     * @return
     */
    public static boolean endsWithNum(String str) {
        if (null == str || str.length() == 0) {
            return false;
        }
        String temp = str.replaceAll("\\d{1}$", "");
        return temp.length() != str.length();
    }

    /**
     * 查找字符串中第一个大写字母的索引位置
     *
     * @param str
     * @return
     */
    public static int findFirstUpperWordIndex(String str) {
        if (null == str || str.length() == 0) {
            return -1;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 字符串首字母转换成大写
     * @param str
     * @return
     */
    public static String upperFirstLetter(String str) {
        int strLen;
        if(str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return (new StringBuilder(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    /**
     * 字符串首字母转换成小写
     * @param str
     * @return
     */
    public static String lowerFirstLetter(String str) {
        int strLen;
        if(str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return (new StringBuilder(strLen)).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    /**
     * 剔除字符串结尾多余的换行符 如：I like java.\n\n\n\n --> I like java.
     *
     * @param str
     * @return
     */
    public static String removeBreakLineOfEnd(String str) {
        if (null == str || str.length() == 0) {
            return null;
        }
        return str.replaceAll("([^\n]*)\n+$", "$1");
    }

    /**
     * 将返回的JSON数据组装成一个HTML代码字符串
     *
     * @param data
     *            HTML代码片段
     * @param pageTitle
     *            HTML页面的title名称
     * @param template
     *            HTML模版
     * @return
     */
    public static String gernerateHTML(String data, String pageTitle, String template) {
        if (GerneralUtils.isEmptyString(data) || GerneralUtils.isEmptyString(template)) {
            return "";
        }
        if (GerneralUtils.isEmptyString(pageTitle)) {
            pageTitle = "New Page";
        }
        return String.format(template, data, pageTitle);
    }

    /**
     * 从网页meta标签中提取出页面编码，<br/>
     * 若页面未指定，默认返回null
     *
     * @param html
     * @return
     */
    public static String getCharsetFromMeta(String html) {
        html = html.replace("contenexType", "content-type");
        html = html.replace("charseexType", "charset");
        Matcher metaMatcher = metaPattern.matcher(html);
        String encoding = null;
        if (metaMatcher.find()) {
            Matcher charsetMatcher = charsetPattern.matcher(metaMatcher.group(1));
            if (charsetMatcher.find()) {
                encoding = new String(charsetMatcher.group(1));
            }
        } else {
            metaMatcher = metaPattern_html5.matcher(html);
            if (metaMatcher.find()) {
                Matcher charsetMatcher = charsetPattern_html5.matcher(metaMatcher.group(1));
                if (charsetMatcher.find()) {
                    encoding = new String(charsetMatcher.group(1));
                }
            }
        }
        if(null != encoding) {
            if(encoding.indexOf("'") != -1) {
                encoding = encoding.trim().substring(1,encoding.lastIndexOf("'"));
            } else if(encoding.indexOf("\"") != -1) {
                encoding = encoding.trim().substring(1,encoding.lastIndexOf("\""));
            }
        }
        return encoding;
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: getCharsetFromContentType
     * @Description: 从Content-Type中提取字符集编码
     * @param contentType  content-Type值
     * @return String
     * @throws
     */
    public static String getCharsetFromContentType(String contentType) {
        Matcher matcher = patternForCharset.matcher(contentType);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (Charset.isSupported(charset)) {
                return charset;
            }
        }
        return null;
    }

    /**
     * 替换HTML页面meta部分声明的charset编码
     *
     * @param html
     *            html页面内容
     * @param targetCharset
     *            目标编码
     * @return
     */
    public static String replaceHTMLCharsetFromMeta(String html, String targetCharset) {
        if (GerneralUtils.isEmptyString(html)) {
            return null;
        }
        if (GerneralUtils.isEmptyString(targetCharset)) {
            return html;
        }
        Pattern pattern = Pattern.compile("<meta\\s+http-equiv=\"Content-Type\"\\s+content=\"[\\s\\S]*?charset=(\\S+?)\"\\s*/>");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            String meta = matcher.group();
            String oldCharset = matcher.group(1);
            String newMeta = meta.replace(oldCharset, targetCharset);
            html = html.replace(meta, newMeta);
        }
        return html;
    }

    /**
     * @Title: md5
     * @Description: 生成MD5摘要
     * @param @param inStr
     * @param @return
     * @return String
     * @throws
     */
    public static String md5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * @Title: string2Unicode
     * @Description: 字符串转换成Unicode编码形式
     * @param @param str
     * @param @return
     * @return String
     * @throws
     */
    public static String string2Unicode(String str, String encoding) {
        try {
            str = new String(str.getBytes("ISO-8859-1"), encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        char[] utfBytes = str.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * @Title: enUnicode
     * @Description: 字符串Unicode编码
     * @param @param s
     * @param @return
     * @return String
     * @throws
     */
    public static String enUnicode(String s) {
        StringBuilder sb = new StringBuilder(s.length() * 3);
        for (char c : s.toCharArray()) {
            if (c < 256) {
                sb.append(c);
            } else {
                sb.append("\\u");
                sb.append(Character.forDigit((c >>> 12) & 0xf, 16));
                sb.append(Character.forDigit((c >>> 8) & 0xf, 16));
                sb.append(Character.forDigit((c >>> 4) & 0xf, 16));
                sb.append(Character.forDigit((c) & 0xf, 16));
            }
        }
        return sb.toString();
    }

    /**
     * @Title: unicode2String
     * @Description: Unicode编码字符串还原成String
     * @param @param unicodeStr
     * @param @return
     * @return String
     * @throws
     */
    public static String unicode2String(String str) {
        str = (str == null ? "" : str);
        if (str.indexOf("\\u") == -1) {
            return str;
        }
        StringBuffer buffer = new StringBuffer(1000);
        for (int i = 0; i < str.length() - 6;) {
            String strTemp = str.substring(i, i + 6);
            String value = strTemp.substring(2);
            int c = 0;
            for (int j = 0; j < value.length(); j++) {
                char tempChar = value.charAt(j);
                int t = 0;
                switch (tempChar) {
                    case 'a':
                        t = 10;
                        break;
                    case 'b':
                        t = 11;
                        break;
                    case 'c':
                        t = 12;
                        break;
                    case 'd':
                        t = 13;
                        break;
                    case 'e':
                        t = 14;
                        break;
                    case 'f':
                        t = 15;
                        break;
                    default:
                        t = tempChar - 48;
                        break;
                }
                c += t * ((int) Math.pow(16, (value.length() - j - 1)));
            }
            buffer.append((char) c);
            i = i + 6;
        }
        return buffer.toString();
    }

    /**
     * 判断是否为中文字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * @Title: isMessyCode
     * @Description: 判断是否乱码
     * @param @param strName
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.20008) {
            return true;
        }
        return false;
    }

    /**
     * @Title: reverseString
     * @Description: 逗号分割的字符倒序排列
     * @param @param str
     * @param @return
     * @return String
     * @throws
     */
    public static String reverseString(String str, String split) {
        if (!str.endsWith(",")) {
            str += ",";
        }
        String[] arrayData = str.split(split);
        List list = Arrays.asList(arrayData);
        Collections.reverse(list);
        return GerneralUtils.joinCollection(list).replace("\",{", "\"},{") + "}";
    }

    /**
     * 全角字符转换成半角字符
     * @param source
     * @return
     */
    public static String convertStringToNarrow(String source) {
        StringBuffer result = new StringBuffer();
        char[] ch = source.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == 12288) {
                result.append(' ');
            } else if (ch[i] == 12290) {
                result.append('.');
            } else if (ch[i] >= 65281 && ch[i] <= 65374) {
                result.append((char)(ch[i] - 65248));
            } else {
                result.append(ch[i]);
            }
        }
        return result.toString();
    }

    /**
     * 半角字符转换全角字符
     * @param source
     * @return
     */
    public static String convertStringToWide(String source) {
        StringBuffer result = new StringBuffer();
        char[] ch = source.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == 32) {
                result.append('　');
            } else if (ch[i] == 46) {
                result.append('。');
            } else if (ch[i] >= 33 && ch[i] <= 126) {
                result.append((char)(ch[i] + 65248));
            } else {
                result.append(ch[i]);
            }
        }
        return result.toString();
    }

    private static final int SESSION_ID_BYTES = 16;

    /**
     * @Title: generateSessionId
     * @Description: 生成随机的SessionId
     * @param @return
     * @return String
     * @throws
     */
    public static synchronized String generateSessionId() {
        Random random = new SecureRandom(); // 取随机数发生器, 默认是SecureRandom
        byte bytes[] = new byte[SESSION_ID_BYTES];
        random.nextBytes(bytes); // 产生16字节的byte
        bytes = getDigest().digest(bytes); // 取摘要,默认是"MD5"算法
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) { // 转化为16进制字符串
            byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
            byte b2 = (byte) (bytes[i] & 0x0f);
            if (b1 < 10)
                result.append((char) ('0' + b1));
            else
                result.append((char) ('A' + (b1 - 10)));
            if (b2 < 10)
                result.append((char) ('0' + b2));
            else
                result.append((char) ('A' + (b2 - 10)));
        }
        return (result.toString());
    }

    /******************String与其他基本数据类型之间的转换 Begin****************/
    public static int string2Int(String str) {
        if(null == str || str.length() == 0) {
            throw new IllegalArgumentException("str can't be null.");
        }
        return Integer.valueOf(str);
    }

    public static short string2Short(String str) {
        if(null == str || str.length() == 0) {
            throw new IllegalArgumentException("str can't be null.");
        }
        return Short.valueOf(str);
    }

    public static long string2Long(String str) {
        if(null == str || str.length() == 0) {
            throw new IllegalArgumentException("str can't be null.");
        }
        return Long.valueOf(str);
    }

    public static float string2Float(String str) {
        if(null == str || str.length() == 0) {
            throw new IllegalArgumentException("str can't be null.");
        }
        return Float.valueOf(str);
    }

    public static double string2Double(String str) {
        if(null == str || str.length() == 0) {
            throw new IllegalArgumentException("str can't be null.");
        }
        return Double.valueOf(str);
    }

    public static boolean string2Boolean(String str) {
        if(null == str || str.length() == 0) {
            throw new IllegalArgumentException("str can't be null.");
        }
        return Boolean.valueOf(str);
    }
    /******************String与其他基本数据类型之间的转换  End****************/

    /**
     * 字符串转换成util.Date
     */
    public static Date string2Date(String str) {
        if(null == str || str.length() == 0) {
            throw new IllegalArgumentException("str can't be null.");
        }
        DateFormat df = null;
        for(String pattern : Constant.PATTERNS) {
            df = new SimpleDateFormat(pattern);
            df.setLenient(false);
            Date date = null;
            try {
                date = df.parse(str);
                if (null != date) {
                    return date;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 字节数组转换成十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytes2Hex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder hex = new StringBuilder(2 * bytes.length);
        for (final byte b : bytes) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
                    HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    /**
     * 字符串转换成十六进制字符串
     *
     *            str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit = 0;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转换字符串
     *
     *            str Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n = 0;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 字节数组转换成十六进制字符串
     *
     * @param b byte数组
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * bytes字符串转换为字节数组
     *
     *            src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = Byte.decode("0x" + src.substring(i * 2, m)
                    + src.substring(m, n));
        }
        return ret;
    }

    /**
     * String的字符串转换成unicode的String
     *
     * strText 全角字符串
     * @return String 每个unicode之间无分隔符
     * @throws Exception
     */
    public static String strToUnicode(String strText){
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc = 0;
        String strHex = null;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128) {
                str.append("\\u" + strHex);
            } else { // 低位在前面补00
                str.append("\\u00" + strHex);
            }
        }
        return str.toString();
    }

    /**
     * @Author Lanxiaowei
     * @Title: unicode2Str
     * @Description: unicode字符解码
     * @param unicode
     * @return
     * @return String
     * @throws
     */
    public static String unicode2Str(String unicode) {
        String string = "";
        try {
            byte[] utf8 = unicode.getBytes("UTF-8");
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return string;
    }

    /**
     * unicode的String转换成String的字符串
     *
     *            hex 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串
     */
    public static String unicodeToString(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: encodeHex
     * @Description: 每个字节用两个十六进制字符表示,编码后字节数组长度会变成原始长度的2倍
     * @param @param data
     * @param @return
     * @return char[]
     * @throws
     */
    public static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4 ];
            out[j++] = DIGITS[ 0x0F & data[i] ];
        }
        return out;
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: md5Hex
     * @Description: 对十六进制字节进行MD5加密
     * @param @param data
     * @param @return
     * @return String
     * @throws
     */
    public static String md5Hex(byte[] data) {
        return new String(encodeHex(md5(data)));
    }

    public static String md5Hex(String data) {
        return md5Hex(data.getBytes());
    }

    public static byte[] md5(byte[] data) {
        return getMd5Digest().digest(data);
    }

    private static MessageDigest getMd5Digest() {
        return getDigest("MD5");
    }

    static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 判断传入字符串是否包含数字
     *
     * @param text
     * @return
     */
    public static boolean containsNum(String text) {
        boolean isNumber = false;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                isNumber = true;
                break;
            }
        }
        return isNumber;
    }

    /**
     * 判断字符串是否全部由大写字母组成
     * @param str
     * @return
     */
    public static boolean isAllUpperCase(String str){
        if(null == str || str.length() == 0){
            return false;
        }
        String temp = str.replaceAll("[A-Z]", "");
        return temp.length() == 0;
    }

    /**
     * 判断字符串是否全部由小写字母组成
     * @param str
     * @return
     */
    public static boolean isAllLowerCase(String str) {
        if(null == str || str.length() == 0){
            return false;
        }
        String temp = str.replaceAll("[a-z]", "");
        return temp.length() == 0;
    }

    /**
     * 判断字符串是否全部是数字(不含小数点)
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if(null == str || str.length() == 0 || (str.length() > 1 && str.startsWith("0"))){
            return false;
        }
        //JDK自带函数实现
        for (int i = str.length();--i >= 0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
	    /*//判断ASC码实现
	    for(int i=str.length();--i >= 0;){
	        int chr = str.charAt(i);
	        if(chr < 48 || chr > 57) {
	           return false;
	        }
	    }
	    return true;*/
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: isBlank
     * @Description: 判断字符串是否为空白:null, emtpy, or only whitespace (" ", \r\n, \t, etc)
     * @param @param string
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0) {
            return true;
        }
        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!Character.isWhitespace(string.codePointAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: padding
     * @Description: 返回指定宽度的空格字符
     * @param @param width
     * @param @return
     * @return String
     * @throws
     */
    public static String padding(int width) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be > 0");
        }

        if (width < padding.length) {
            return padding[width];
        }

        char[] out = new char[width];
        for (int i = 0; i < width; i++) {
            out[i] = ' ';
        }
        return String.valueOf(out);
    }

    /**
     * 判断字符串是否可以转换成小数
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        if(null == str || str.length() == 0 || str.indexOf(".") == -1 ||
                str.startsWith(".") || str.endsWith(".") ||
                (str.indexOf(".") != str.lastIndexOf("."))){
            return false;
        }
        //判断是否含有非数字字符
        String temp = str.replaceAll("[\\d|.]*","");
        if(temp.length() != 0) {
            return false;
        }
        //排除类似000.12形式
        if(str.indexOf(".") > 1) {
            String firstLetter = str.substring(0,1);
            if(firstLetter.equals("0")){
                return false;
            }
        }
        return true;
    }

    /**
     * 从字符串中提取数字(含小数),如：Was $365.00--365.00
     * @param str
     * @return
     */
    public static String getNumberFromString(String str){
        if(null == str || str.length() == 0) {
            throw new RuntimeException("Unable to convert " + str + " to a number.");
        }
        return str.replaceAll("[^\\d|.]*","");
    }

    /**
     * 字符串剔除重复项，如：aaaaabbbbbccccc-->abc
     * 注意：暂不支持交叉重复，如：aaaaabbbaaaccc-->bac而不是abac
     * @param str
     * @return
     */
    public static String excludeRepeat(String str) {
        if(null == str || str.length() == 0) {
            return str;
        }
        return str.replaceAll("(?s)(.)(?=.*\\1)", "");
    }

    /**
     * 判断某字符串是否包含中文字符
     *
     * @param text
     * @return
     */
    public static boolean containsChinese(String text) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    /**
     * 判断字符串是否为日期格式
     *
     * @param dateString
     * @return
     */
    public static boolean isDate(String dateString) {
        if (GerneralUtils.isEmptyString(dateString)) {
            return false;
        }
        DateFormat df = null;
        for (String pattern : Constant.PATTERNS) {
            df = new SimpleDateFormat(pattern);
            df.setLenient(false);
            Date date = null;
            try {
                date = df.parse(dateString);
                if (null != date) {
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        return false;
    }

    private static MessageDigest getDigest() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: ordinalIndexOf
     * @Description: 查找第N个searchStr的位置
     * @param @param str         源字符串
     * @param @param searchStr   待查找的字符串
     * @param @param ordinal     源字符串中第几个searchStr
     * @param @param lastIndex
     * @param @return
     * @return int
     * @throws
     */
    public static int ordinalIndexOf(String str, String searchStr, int ordinal, boolean lastIndex) {
        if (str == null || searchStr == null || ordinal <= 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return lastIndex ? str.length() : 0;
        }
        int found = 0;
        int index = lastIndex ? str.length() : INDEX_NOT_FOUND;
        do {
            if(lastIndex) {
                index = str.lastIndexOf(searchStr, index - 1);
            } else {
                index = str.indexOf(searchStr, index + 1);
            }
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < ordinal);
        return index;
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: ordinalIndexOf
     * @Description: 查找第N个searchStr的位置
     * @param @param str
     * @param @param searchStr
     * @param @param ordinal
     * @param @return
     * @return int
     * @throws
     */
    public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, false);
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: substring
     * @Description: 字符串截取
     * @param @param str
     * @param @param start
     * @param @param end
     * @param @return
     * @return String
     * @throws
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: substring
     * @Description: 字符串截取
     * @param @param str
     * @param @param start
     * @param @return
     * @return String
     * @throws
     */
    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY;
        }

        return str.substring(start);
    }

    public static int indexOf(String str, char searchChar) {
        if (str == null || str.length() == 0) {
            return INDEX_NOT_FOUND;
        }
        return str.indexOf(searchChar);
    }


    public static int indexOf(String str, char searchChar, int startPos) {
        if (str == null || str.length() == 0) {
            return INDEX_NOT_FOUND;
        }
        return str.indexOf(searchChar, startPos);
    }

    public static int indexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        // JDK1.2/JDK1.3 have a bug, when startPos > str.length for "", hence
        if (searchStr.length() == 0 && startPos >= str.length()) {
            return str.length();
        }
        return str.indexOf(searchStr, startPos);
    }

    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        return str.indexOf(searchStr);
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: isEmpty
     * @Description: 判断字符串是否为空或者为空字符串
     * @param @param str
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: isNotEmpty
     * @Description: isEmpty取反
     * @param @param str
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: countMatches
     * @Description: 判断子串在源串中出现的次数
     * @param @param str
     * @param @param sub
     * @param @return
     * @return int
     * @throws
     */
    public static int countMatches(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: normaliseWhitespace
     * @Description: 把多个空格替换成单个空格
     * @param @param string
     * @param @return
     * @return String
     * @throws
     */
    public static String normaliseWhitespace(String string) {
        StringBuilder sb = new StringBuilder(string.length());

        boolean lastWasWhite = false;
        boolean modified = false;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            int c = string.codePointAt(i);
            if (Character.isWhitespace(c)) {
                if (lastWasWhite) {
                    modified = true;
                    continue;
                }
                if (c != ' ')
                    modified = true;
                sb.append(' ');
                lastWasWhite = true;
            }
            else {
                sb.appendCodePoint(c);
                lastWasWhite = false;
            }
        }
        return modified ? sb.toString() : string;
    }

    public static String escapeRegex(String regex) {
        for(int j=0; j < REGEX_ESCAPES.length; j++) {
            if(regex.contains(REGEX_ESCAPES[j])) {
                regex = regex.replace(REGEX_ESCAPES[j], "\\" + REGEX_ESCAPES[j]);
            }
        }
        return regex;
    }

    public static void main(String[] args) {
        //String html = "<meta  content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\"/>";
        String html = "asdfadfa  <meta sdfasdcharset    = 'UTF-8' asdfas/>         asdfasdf";
        String charset = getCharsetFromMeta(html);
        System.out.println(charset);
        String s = normaliseWhitespace(html);
        System.out.println(s);

		/*String contentType = "text/html;charset=UTF-8";
		String charset = getCharsetFromContentType(contentType);
		System.out.println(charset);
		System.out.println(Charset.defaultCharset());*/

        String str = "(共250页)";
        String result = getNumberFromString(str);
        System.out.println(result);
        result = getNumbericFromString(str);
        System.out.println(result);

        String sss = "getproductlist('','http://shop.adidas.cn/specific/ajaxproductlist/productlist/?brand_series=60&sport_type=194&product_style=214','','',24,'');return false;";
        String url = getURLFromString(sss);
        System.out.println(url);
    }
}
