package com.yida.solr.book.examples.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * 通用函数工具类
 *
 * @author Lanxiaowei
 */
@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
public class GerneralUtils {
    private static final Map<String, Integer> monthMap = new HashMap<String, Integer>();

    private static Random random = new Random();
    static {
        monthMap.put("January", 0);
        monthMap.put("February", 1);
        monthMap.put("March", 2);
        monthMap.put("April", 3);
        monthMap.put("May", 4);
        monthMap.put("June", 5);
        monthMap.put("July", 6);
        monthMap.put("August", 7);
        monthMap.put("September", 8);
        monthMap.put("October", 9);
        monthMap.put("November", 10);
        monthMap.put("December", 11);
    }

    /** 基本数据类型注册 */
    private final static List<Class<?>> PRIMITIVE_CLASSES = new ArrayList<Class<?>>() {
        {
            add(Long.class);
            add(Integer.class);
            add(Short.class);
            add(String.class);
            add(Float.class);
            add(Double.class);
            add(Boolean.class);
            add(java.util.Date.class);
            add(java.sql.Date.class);
            add(java.sql.Timestamp.class);
        }
    };

    public static Map<String, Integer> getMonthmap() {
        return monthMap;
    }

    /**
     * 判断字符串为空(包含null和"")
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        return (null == str || "".equals(str));
    }

    /**
     * 判断字符串非空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmptyString(String str) {
        return !isEmptyString(str);
    }

    /**
     * 判断集合非空
     *
     * @param collection
     * @return
     */
    public static boolean isEmptyCollection(Collection collection) {
        return (null == collection || collection.isEmpty());
    }

    /**
     * 判断集合非空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmptyCollection(Collection collection) {
        return !isEmptyCollection(collection);
    }

    /**
     * 判断某集合内是否包含给定元素
     *
     * @param targetCollection
     * @param original
     * @return
     */
    public static boolean isContains(Collection targetCollection, Object original) {
        return targetCollection.contains(original);
    }

    /**
     * 判断Map为空
     *
     * @param map
     * @return
     */
    public static boolean isEmptyMap(Map map) {
        return (null == map || map.isEmpty());
    }

    /**
     * 判断Map非空
     *
     * @param map
     * @return
     */
    public static boolean isNotEmptyMap(Map map) {
        return !isEmptyMap(map);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmptyArray(Object[] array) {
        return null == array || array.length == 0;
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmptyArray(Object[] array) {
        return !isEmptyArray(array);
    }

    /**
     * 删除集合中指定元素
     *
     * @param collection
     * @return
     */
    public static boolean deleteElementFromCollection(Collection<Object> collection, Object targetEl) {
        if (isEmptyCollection(collection)) {
            return false;
        }
        for (Iterator<Object> it = collection.iterator(); it.hasNext();) {
            Object obj = it.next();
            if (obj.equals(targetEl)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * 删除Map中指定key对应的键值对
     *
     * @param map
     */
    public static boolean deleteElementFromMap(Map<String, Object> map, String targetKey) {
        if (isEmptyMap(map) || null == targetKey) {
            return false;
        }
        for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext();) {
            String key = it.next().getKey();
            if (key.equals(targetKey)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * 删除数组某索引位置的元素并返回删除后的新数组
     *
     * @param array
     * @param delItem
     * @return
     */
    public static Object[] deleteElementFromArray(Object[] array, int delItem) {
        if (isEmptyArray(array) || delItem < 0 || delItem >= array.length) {
            return array;
        }
        for (int i = delItem; i < array.length - 1; ++i) {
            array[i] = array[i + 1];
        }
        Object[] arrayTemp = new Object[array.length - 1];
        System.arraycopy(array, 0, arrayTemp, 0, array.length - 1);
        array = null;
        return arrayTemp;
    }

    /**
     * 将集合中所有元素按照指定的连接符拼接成一个字符串 如果不指定连接符，默认设置为英文逗号,
     *
     * @param collection
     * @param delimiter
     * @return
     */
    public static String joinCollection(Collection collection, String delimiter) {
        if (isEmptyCollection(collection)) {
            return null;
        }
        if (isEmptyString(delimiter)) {
            delimiter = ",";
        }
        StringBuffer text = new StringBuffer();
        for (Object element : collection) {
            String str = "";
            if (null != element) {
                str = element.toString();
            }
            text.append(str).append(delimiter);
        }
        return text.toString().replaceAll(delimiter + "$", "");
    }

    /**
     * 将集合中所有元素按照指定的连接符拼接成一个字符串(重载) 如果不指定连接符，默认设置为英文逗号,
     *
     * @param collection
     * @return
     */
    public static String joinCollection(Collection collection) {
        return joinCollection(collection, null);
    }

    /**
     * List集合N等分<br/>
     * 若不能整除，则最后余数算一份
     *
     * @param list
     * @param splitCOunt
     *            分割个数即分成splitCOunt份
     * @return
     */
    public static List<List> splitList(List list, int splitCOunt) {
        int start = 0;
        List<List> params = new ArrayList<List>();
        if (list.size() <= splitCOunt) {
            params.add(list);
            return params;
        }
        int t = list.size() / splitCOunt;
        int pageSize = list.size() % splitCOunt == 0 ? t : t + 1;
        while (start < list.size() - 1) {
            int n = list.size() - start;
            List subList = null;
            if (n > splitCOunt) {
                int end = start + pageSize;
                if (end > list.size()) {
                    end = list.size();
                }
                subList = list.subList(start, end);
            } else {
                subList = list.subList(start, start + n);
            }
            params.add(subList);
            start += pageSize;
        }
        return params;
    }

    /**
     * 生成指定区间[min-max)之间的随机数
     *
     * @param max
     * @param min
     * @return
     */
    public static int generateRandomNumber(int max, int min) {
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 判断指定类型是否是基本数据类型
     *
     * @param clazz
     * @return
     */
    public final static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || PRIMITIVE_CLASSES.contains(clazz);
    }

    /**
     * 从get/set方法名中提取出属性名称
     *
     * @return
     */
    public static String getPropertyNameFromGetSet(String methodName) {
        if (null == methodName) {
            return null;
        }
        if (methodName.length() <= 3 || (!methodName.startsWith("get") && !methodName.startsWith("set"))) {
            return methodName;
        }
        return StringUtils.lowerFirstLetter(methodName.substring(3));
    }

    /**
     * 骆驼命名法转换成数据库字段命名法，如studentName-->student_name
     *
     * @param propertyName
     *            属性名称
     * @param prefix
     *            添加前缀
     * @param stuffix
     *            添加后缀
     * @return
     */
    public static String splitCamelName(String propertyName, String prefix, String stuffix) {
        if (GerneralUtils.isEmptyString(propertyName)) {
            return propertyName;
        }
        char[] dest = new char[propertyName.length()];
        propertyName.getChars(0, propertyName.length(), dest, 0);
        StringBuilder builder = new StringBuilder();
        if (isNotEmptyString(prefix)) {
            builder.append(prefix).append("_");
        }
        for (int i = 0; i < dest.length; i++) {
            if (i == 0) {
                builder.append(Character.toLowerCase(dest[i]));
                continue;
            }
            if (Character.isUpperCase(dest[i])) {
                builder.append("_").append(Character.toLowerCase(dest[i]));
            } else {
                builder.append(dest[i]);
            }
        }
        if (isNotEmptyString(stuffix)) {
            builder.append("_").append(stuffix);
        }
        return builder.toString();
    }

    /**
     * 骆驼命名法转换成数据库字段命名法(重载)，如studentName-->student_name
     *
     * @param propertyName
     *            属性名称
     * @return
     */
    public static String splitCamelName(String propertyName) {
        return splitCamelName(propertyName, null, null);
    }

    /**
     * 数据库字段名称转换成类属性名，如stu_name-->stuName
     *
     * @param fieldName
     *            数据库字段名称
     * @param prefix
     *            前缀
     * @param stuffix
     *            后缀
     * @return
     */
    public static String splitDBFieldName(String fieldName, String prefix, String stuffix) {
        if (GerneralUtils.isEmptyString(fieldName)) {
            return fieldName;
        }
        if (isNotEmptyString(prefix)) {
            if (prefix.endsWith("_")) {
                fieldName = fieldName.replaceAll("^" + prefix + "(.*)", "$1");
            } else {
                fieldName = fieldName.replaceAll("^" + prefix + "_(.*)", "$1");
            }
        } else {
            fieldName = fieldName.replaceAll("^_(.*)", "$1");
        }
        if (isNotEmptyString(stuffix)) {
            if (stuffix.startsWith("_")) {
                fieldName = fieldName.replaceAll("(.*)" + stuffix + "$", "$1");
            } else {
                fieldName = fieldName.replaceAll("(.*)_" + stuffix + "$", "$1");
            }
        } else {
            fieldName = fieldName.replaceAll("(.*)_$", "$1");
        }
        if (fieldName.indexOf("_") == -1) {
            return fieldName;
        }
        String[] array = fieldName.split("_");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                builder.append(array[i].toLowerCase());
            } else {
                builder.append(StringUtils.upperFirstLetter(array[i]));
            }
        }
        return builder.toString();
    }

    /**
     * 数据库字段名称转换成类属性名(重载)，如stu_name-->stuName
     *
     * @param fieldName
     *            数据库字段名称
     * @return
     */
    public static String splitDBFieldName(String fieldName) {
        return splitDBFieldName(fieldName, null, null);
    }

    /**
     * 对象转换成属性Map键值对
     *
     * @param object
     *            目标对象
     * @param toDB
     *            Map的key是否转换成数据库字段形式
     * @return
     */
    public static Map<String, Object> object2Map(Object object, boolean toDB) {
        if (null == object) {
            return null;
        }
        List<Field> fieldList = new ArrayList<Field>();
        ReflectionUtils.getFields(fieldList, object.getClass(), true);
        if (GerneralUtils.isEmptyCollection(fieldList)) {
            return null;
        }
        // 使用LinkedHashMap是为了保证元素插入顺序
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (Field field : fieldList) {
            // 排除静态属性
            if (Modifier.isStatic(field.getModifiers()) || field.equals("serialVersionUID")) {
                continue;
            }
            if (toDB) {
                map.put(splitCamelName(field.getName()), ReflectionUtils.getFieldValue(object, field.getName()));
            } else {
                map.put(field.getName(), ReflectionUtils.getFieldValue(object, field.getName()));
            }
        }
        return map;
    }

    /**
     * 对象转换成属性Map键值对(重载)
     *
     * @param object
     *            目标对象
     * @return
     */
    public static Map<String, Object> object2Map(Object object) {
        return object2Map(object, false);
    }

    /**
     * Object转换成属性值数组
     *
     * @param object
     *            待转换的对象
     * @param filterFields
     *            需要过滤掉不转换的属性集合
     * @return
     */
    public static Object[] object2Array(Object object, Set<String> filterFields) {
        if (null == object) {
            return null;
        }
        List<Field> fieldList = new ArrayList<Field>();
        ReflectionUtils.getFields(fieldList, object.getClass(), true);
        if (GerneralUtils.isEmptyCollection(fieldList)) {
            return null;
        }
        List<Object> objList = new ArrayList<Object>();
        for (Field field : fieldList) {
            if (GerneralUtils.isNotEmptyCollection(filterFields)) {
                if (filterFields.contains(field.getName())) {
                    continue;
                }
            }
            Object val = ReflectionUtils.getFieldValue(object, field.getName());
            objList.add(val);
        }
        return objList.toArray();
    }

    /**
     * Object转换成属性值数组(重载)
     *
     * @param object
     *            待转换的对象
     * @return
     */
    public static Object[] object2Array(Object object) {
        return object2Array(object, null);
    }

    /**
     * Map键值对转换成对象
     *
     * @param map
     * @param target
     * @return
     */
    public static Object map2Object(Map<String, Object> map, Class target) {
        if (GerneralUtils.isEmptyMap(map) || null == target) {
            return null;
        }
        Object object = null;
        try {
            object = target.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String propertyName = splitDBFieldName(key);
            if (!ReflectionUtils.hasThisField(target, propertyName)) {
                continue;
            }
            Object value = entry.getValue();
            if (value instanceof String) {
                if (StringUtils.isDate(value.toString())) {
                    value = StringUtils.string2Date(value.toString());
                }
            }
            ReflectionUtils.setFieldValue(object, propertyName, value);
        }
        return object;
    }

    /**
     * Map结构纵向改成横向
     *
     * @param map
     * @return
     */
    public static List<Map<Integer, Object>> mapVertical2Horizontal(Map<String, List<Object>> map) {
        if (GerneralUtils.isEmptyMap(map)) {
            return null;
        }
        // 列索引
        int index = 0;
        List<Map<Integer, Object>> mapList = new ArrayList<Map<Integer, Object>>();
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            List<Object> list = entry.getValue();
            for (int j = 0; j < list.size(); j++) {
                Object val = list.get(j);
                Map<Integer, Object> rowMap = mapList.get(index);
                if (null == rowMap) {
                    rowMap = new HashMap<Integer, Object>();
                    rowMap.put(index, val);
                    mapList.add(rowMap);
                } else {
                    rowMap.put(index, val);
                }
            }
            index++;
        }
        return mapList;
    }

    /**
     * Map结构纵向改成横向(重载)
     *
     * @param map
     * @return
     */
    public static List<Map<String, Object>> mapVerticalToHorizontal(Map<String, List<Object>> map) {
        if (GerneralUtils.isEmptyMap(map)) {
            return null;
        }
        // 列索引
        int index = 0;
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<Object> list = entry.getValue();
            for (int j = 0; j < list.size(); j++) {
                Object val = list.get(j);
                Map<String, Object> rowMap = mapList.get(index);
                if (null == rowMap) {
                    rowMap = new LinkedHashMap<String, Object>();
                    rowMap.put(key, val);
                    mapList.add(rowMap);
                } else {
                    rowMap.put(key, val);
                }
            }
            index++;
        }
        return mapList;
    }

    /**
     * Map结构横向改成纵向
     * @return
     */
    public static Map<String, List<Object>> mapHorizontal2Vertical(List<Map<String, Object>> mapList) {
        if (GerneralUtils.isEmptyCollection(mapList)) {
            return null;
        }
        Map<String, List<Object>> keywordMap = new HashMap<String, List<Object>>();
        for (Map<String, Object> map : mapList) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();
                String cellVal = val == null ? "" : val.toString();
                if (cellVal.length() == 0) {
                    continue;
                }
                List<Object> columnList = keywordMap.get(key);
                if (GerneralUtils.isEmptyCollection(columnList)) {
                    columnList = new ArrayList<Object>();
                    columnList.add(cellVal);
                    keywordMap.put(key, columnList);
                } else {
                    keywordMap.get(key).add(cellVal);
                }
            }
        }
        return keywordMap;
    }

    /**
     * Map结构横向改成纵向
     *
     * @return
     */
    public static Map<Integer, List<Object>> mapHorizontalToVertical(List<Map<Integer, Object>> mapList) {
        if (GerneralUtils.isEmptyCollection(mapList)) {
            return null;
        }
        Map<Integer, List<Object>> keywordMap = new HashMap<Integer, List<Object>>();
        for (Map<Integer, Object> map : mapList) {
            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                Integer key = entry.getKey();
                Object val = entry.getValue();
                String cellVal = val == null ? "" : val.toString();
                if (cellVal.length() == 0) {
                    continue;
                }
                List<Object> columnList = keywordMap.get(key.intValue());
                if (GerneralUtils.isEmptyCollection(columnList)) {
                    columnList = new ArrayList<Object>();
                    columnList.add(cellVal);
                    keywordMap.put(key.intValue(), columnList);
                } else {
                    keywordMap.get(key.intValue()).add(cellVal);
                }
            }
        }
        return keywordMap;
    }

    /**
     * 对象集合转换成二维数组，用于批量插入或更新参数
     *
     * @param beanList
     * @return
     */
    public static Object[][] beanListToArrays(Collection beanList, Set<String> filterFields) {
        if (GerneralUtils.isEmptyCollection(beanList)) {
            return null;
        }
        List<Object[]> params = new ArrayList<Object[]>();
        for (Object object : beanList) {
            Object[] arr = object2Array(object, filterFields);
            params.add(arr);
        }
        return list2Array(params);
    }

    /**
     * 对象集合转换成二维数组，用于批量插入或更新参数(重载)
     *
     * @param beanList
     * @return
     */
    public static Object[][] beanListToArrays(Collection beanList) {
        return beanListToArrays(beanList, null);
    }

    /**
     * List<Object[]>转换成二维数组
     *
     * @param list
     * @return
     */
    public static Object[][] list2Array(List<Object[]> list) {
        if (GerneralUtils.isEmptyCollection(list)) {
            return null;
        }
        Object[][] params = new Object[list.size()][list.get(0).length];
        for (int i = 0; i < list.size(); i++) {
            params[i] = list.get(i);
        }
        return params;
    }

    /**
     * 追加参数(将whereParams里的参数值追加至params二维数组每项的末尾)
     *
     * @param params
     * @param whereParams
     * @return
     */
    public static Object[][] appendParams(Object[][] params, Map<String, Object> whereParams) {
        if (GerneralUtils.isEmptyArray(params)) {
            return null;
        }
        if (GerneralUtils.isEmptyMap(whereParams)) {
            return params;
        }
        List<Object> list = new ArrayList<Object>();
        for (Map.Entry<String, Object> entry : whereParams.entrySet()) {
            Object val = entry.getValue();
            list.add(val);
        }
        Object[] appendParam = list.toArray();
        for (Object[] param : params) {
            param = ArrayUtils.mergeArray(param, appendParam);
        }
        return params;
    }

    /**
     * 结果集转换成List<Map<String,Object>>(columnName:columnValue)
     *
     * @param rs
     *            结果集
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> resultSet2MapList(ResultSet rs) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                int columnCount = rsmd.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    map.put(columnName, rs.getObject(i + 1));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取内网IP地址数值
     *
     * @param ipAddress
     * @return
     */
    public static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);
        return a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
    }

    /**
     * 判断内网IP是否在指定区段内
     *
     * @param userIp
     * @param begin
     * @param end
     * @return
     */
    public static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

    /**
     * 判断是否为内网IP
     *
     * @param ipAddress
     * @return
     */
    public static boolean isInnerIP(String ipAddress) {
        long ipNum = getIpNum(ipAddress);
        /**
         * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类
         * 192.168.0.0-192.168.255.255 当然，还有127.0.0.1这个网段是环回地址
         **/
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        return isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || ipAddress.equals("127.0.0.1");
    }

    /**
     * 获取当前机器的所有内网IP
     *
     * @return
     * @throws SocketException
     */
    public static List<String> getAllIntranetIp() throws SocketException {
        List<String> ipList = null;
        Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
        if (null != netInterfaces) {
            ipList = new ArrayList<String>();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration cardipaddress = ni.getInetAddresses();
                InetAddress ip = null;
                String allipaddress = "";
                while (cardipaddress.hasMoreElements()) {
                    ip = (InetAddress) cardipaddress.nextElement();
                    if (ip.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                        continue;
                    }
                    allipaddress = ip.getHostAddress();
                    if (!"".equals(allipaddress)) {
                        boolean isInnerIp = true;
                        try {
                            isInnerIp = isInnerIP(allipaddress);
                        } catch (NumberFormatException e) {
                            isInnerIp = false;
                        }
                        if (isInnerIp) {
                            ipList.add(allipaddress);
                        }
                    }
                }
            }
        }
        return ipList;
    }

    /**
     * 获取update的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            SQL语句限制条件参数(字段名称--参数值)
     * @param filterFields
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getUpdateSql(Class targetClass, Map<String, Object> params, Set<String> filterFields, String tablePrefix, String tableStuf, String tableName) {
        String table = getTableName(targetClass, tablePrefix, tableStuf, tableName);
        if (GerneralUtils.isEmptyString(table)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("update ").append(table).append(" set ");
        List<Field> fieldList = new ArrayList<Field>();
        ReflectionUtils.getFields(fieldList, targetClass, true);
        if (GerneralUtils.isEmptyCollection(fieldList)) {
            return null;
        }
        int count = 0;
        int index = 0;
        for (Field field : fieldList) {
            String fieldName = GerneralUtils.splitCamelName(field.getName());
            index++;
            if (GerneralUtils.isEmptyString(fieldName) || fieldName.equals("serial_version_u_i_d") || fieldName.equals("serialVersionUID")) {
                continue;
            }
            if (GerneralUtils.isNotEmptyCollection(filterFields)) {
                if (filterFields.contains(fieldName)) {
                    continue;
                }
            }
            if (index == fieldList.size()) {
                builder.append(fieldName).append("=?");
            } else {
                builder.append(fieldName).append("=?,");
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        if (GerneralUtils.isNotEmptyMap(params)) {
            builder.append(" where 1=1");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();
                if (null != val) {
                    // 若key为类属性名称,则转换成数据库表字段名形式
                    if (key.indexOf("_") == -1) {
                        builder.append(" and ").append(splitCamelName(key)).append("=?");
                    } else {
                        builder.append(" and ").append(key).append("=?");
                    }
                }
            }
        }
        return builder.toString().replace("?, where", "? where");
    }

    /**
     * 获取update的SQL语句( 重载1)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            SQL语句限制条件参数(字段名称--参数值)
     * @param filterFields
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @return
     */
    public static String getUpdateSql(Class targetClass, Map<String, Object> params, Set<String> filterFields, String tablePrefix, String tableStuf) {
        return getUpdateSql(targetClass, params, filterFields, tablePrefix, tableStuf, null);
    }

    /**
     * 获取update的SQL语句(重载2)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            SQL语句限制条件参数(字段名称--参数值)
     * @param filterFields
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getUpdateSql(Class targetClass, Map<String, Object> params, Set<String> filterFields, String tableName) {
        return getUpdateSql(targetClass, params, filterFields, null, null, tableName);
    }

    /**
     * 获取update的SQL语句(重载3)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            SQL语句限制条件参数(字段名称--参数值)
     * @param filterFields
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @return
     */
    public static String getUpdateSql(Class targetClass, Map<String, Object> params, Set<String> filterFields) {
        return getUpdateSql(targetClass, params, filterFields, null, null, null);
    }

    /**
     * 获取update的SQL语句(重载4)
     *
     * @param targetClass
     *            目标类型
     * @param filterFields
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @return
     */
    public static String getUpdateSql(Class targetClass, Set<String> filterFields) {
        return getUpdateSql(targetClass, null, filterFields, null, null, null);
    }

    /**
     * 获取update的SQL语句(重载5)
     *
     * @param targetClass
     *            目标类型
     * @return
     */
    public static String getUpdateSql(Class targetClass) {
        return getUpdateSql(targetClass, null, null, null, null, null);
    }

    /**
     * 获取insert的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param filterFields
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @param tableName
     *            自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getInsertSql(Class targetClass, Set<String> filterFields, String tablePrefix, String tableStuf, String tableName) {
        String table = getTableName(targetClass, tablePrefix, tableStuf, tableName);
        if (GerneralUtils.isEmptyString(table)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("insert into ").append(table).append("(");
        List<Field> fieldList = new ArrayList<Field>();
        ReflectionUtils.getFields(fieldList, targetClass, true);
        if (GerneralUtils.isEmptyCollection(fieldList)) {
            return null;
        }
        int count = 0;
        int index = 0;
        for (Field field : fieldList) {
            String fieldName = GerneralUtils.splitCamelName(field.getName());
            index++;
            if (GerneralUtils.isEmptyString(fieldName)) {
                continue;
            }
            if (GerneralUtils.isNotEmptyCollection(filterFields)) {
                if (filterFields.contains(fieldName)) {
                    continue;
                }
            }
            if (index == fieldList.size()) {
                builder.append(fieldName);
            } else {
                builder.append(fieldName).append(",");
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        builder.append(") values (");
        for (int i = 0; i < count; i++) {
            if (i != count - 1) {
                builder.append("?,");
            } else {
                builder.append("?");
            }
        }
        builder.append(")");
        return builder.toString().replace(",)", ")");
    }

    /**
     * 获取insert的SQL语句(重载1)
     *
     * @param targetClass
     *            目标类型
     * @param filterFields
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @param tableName
     *            自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getInsertSql(Class targetClass, Set<String> filterFields, String tableName) {
        return getInsertSql(targetClass, filterFields, null, null, tableName);
    }

    /**
     * 获取insert的SQL语句(重载2)
     *
     * @param targetClass
     *            目标类型
     * @param filterFields
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @return
     */
    public static String getInsertSql(Class targetClass, Set<String> filterFields) {
        return getInsertSql(targetClass, filterFields, null, null, null);
    }

    /**
     * 获取insert的SQL语句(重载3)
     *
     * @param targetClass
     *            目标类型
     *            需要过滤掉的字段(比如主键自增长字段不需要插入)
     * @return
     */
    public static String getInsertSql(Class targetClass) {
        return getInsertSql(targetClass, null, null, null, null);
    }

    /**
     * 获取根据Key主键查询的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            Key主键字段名称
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getByKeySql(Class targetClass, String keyFieldName, String tablePrefix, String tableStuf, String tableName) {
        if (GerneralUtils.isEmptyString(keyFieldName)) {
            keyFieldName = "id"; // 默认设置主键ID字段为id
        }
        String table = getTableName(targetClass, tablePrefix, tableStuf, tableName);
        if (GerneralUtils.isEmptyString(table)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("select * from ").append(table);
        builder.append(" where ").append(keyFieldName).append("=?");
        return builder.toString();
    }

    /**
     * 获取根据Key主键查询的SQL语句(重载1)
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            Key主键字段名称
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @return
     */
    public static String getByKeySql(Class targetClass, String keyFieldName, String tablePrefix, String tableStuf) {
        return getByKeySql(targetClass, keyFieldName, tablePrefix, tableStuf, null);
    }

    /**
     * 获取根据Key主键查询的SQL语句(重载2)
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            Key主键字段名称
     * @param tableName
     *            自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getByKeySql(Class targetClass, String keyFieldName, String tableName) {
        return getByKeySql(targetClass, keyFieldName, null, null, tableName);
    }

    /**
     * 获取根据Key主键查询的SQL语句(重载3)
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            Key主键字段名称
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getByKeySql(Class targetClass, String keyFieldName) {
        return getByKeySql(targetClass, keyFieldName, null, null, null);
    }

    /**
     * 获取根据Key字段删除的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            唯一约束字段名称
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getDeleteByKeySql(Class targetClass, String keyFieldName, String tablePrefix, String tableStuf, String tableName) {
        if (GerneralUtils.isEmptyString(keyFieldName)) {
            keyFieldName = "id"; // 默认设置主键ID字段为id
        }
        String table = getTableName(targetClass, tablePrefix, tableStuf, tableName);
        if (GerneralUtils.isEmptyString(table)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("delete from ").append(table);
        builder.append(" where ").append(keyFieldName).append("=?");
        return builder.toString();
    }

    /**
     * 获取根据Key字段删除的SQL语句(重载1)
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            唯一约束字段名称
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @return
     */
    public static String getDeleteByKeySql(Class targetClass, String keyFieldName, String tablePrefix, String tableStuf) {
        return getDeleteByKeySql(targetClass, keyFieldName, tablePrefix, tableStuf, null);
    }

    /**
     * 获取根据Key字段删除的SQL语句(重载2)
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            唯一约束字段名称
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getDeleteByKeySql(Class targetClass, String keyFieldName, String tableName) {
        return getDeleteByKeySql(targetClass, keyFieldName, null, null, tableName);
    }

    /**
     * 获取根据Key字段删除的SQL语句(重载3)
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            唯一约束字段名称
     * @return
     */
    public static String getDeleteByKeySql(Class targetClass, String keyFieldName) {
        return getDeleteByKeySql(targetClass, keyFieldName, null, null, null);
    }

    /**
     * 获取根据Key字段删除的SQL语句--(重载4) 不指定唯一约束字段名称，默认认为是id
     *
     * @param targetClass
     *            目标类型
     * @return
     */
    public static String getDeleteByKeySql(Class targetClass) {
        return getDeleteByKeySql(targetClass, "id", null, null, null);
    }

    /**
     * 获取根据Key字段删除多条记录的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            唯一约束字段名称
     * @param paramsCount
     *            参数个数
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getDeleteByKeysSql(Class targetClass, String keyFieldName, int paramsCount, String tablePrefix, String tableStuf, String tableName) {
        if (GerneralUtils.isEmptyString(keyFieldName)) {
            keyFieldName = "id"; // 默认设置主键ID字段为id
        }
        String table = getTableName(targetClass, tablePrefix, tableStuf, tableName);
        if (GerneralUtils.isEmptyString(table) || paramsCount <= 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("delete from ").append(table);
        builder.append(" where ").append(keyFieldName).append(" in(");
        for (int i = 0; i < paramsCount; i++) {
            if (i != paramsCount - 1) {
                builder.append("?,");
            } else {
                builder.append("?");
            }
        }
        builder.append(")");
        return builder.toString();
    }

    /**
     * 获取根据Key字段删除多条记录的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            唯一约束字段名称
     * @param paramsCount
     *            参数个数
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @return
     */
    public static String getDeleteByKeysSql(Class targetClass, String keyFieldName, int paramsCount, String tablePrefix, String tableStuf) {
        return getDeleteByKeysSql(targetClass, keyFieldName, paramsCount, tablePrefix, tableStuf, null);
    }

    /**
     * 获取根据Key字段删除多条记录的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            唯一约束字段名称
     * @param paramsCount
     *            参数个数
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getDeleteByKeysSql(Class targetClass, String keyFieldName, int paramsCount, String tableName) {
        return getDeleteByKeysSql(targetClass, keyFieldName, paramsCount, null, null, tableName);
    }

    /**
     * 获取根据Key字段删除多条记录的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param keyFieldName
     *            唯一约束字段名称
     * @param paramsCount
     *            参数个数
     * @return
     */
    public static String getDeleteByKeysSql(Class targetClass, String keyFieldName, int paramsCount) {
        return getDeleteByKeysSql(targetClass, keyFieldName, paramsCount, null, null, null);
    }

    /**
     * 获取根据Key字段删除多条记录的SQL语句
     *
     * @param targetClass
     *            目标类型
     * @param paramsCount
     *            参数个数
     * @return
     */
    public static String getDeleteByKeysSql(Class targetClass, int paramsCount) {
        return getDeleteByKeysSql(targetClass, null, paramsCount, null, null, null);
    }

    /**
     * 获取含多限制条件参数的删除SQL语句 (注意：暂只支持and、=等于形式的限制条件，or like between in > >= <
     * <=后续再扩展)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            Where部分参数
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getDeleteSqlWithConditions(Class targetClass, Map<String, Object> params, String tablePrefix, String tableStuf, String tableName) {
        if (GerneralUtils.isEmptyMap(params) || null == targetClass) {
            return null;
        }
        String table = getTableName(targetClass, tablePrefix, tableStuf, tableName);
        if (GerneralUtils.isEmptyString(table)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("delete from ").append(table).append(" where 1=1");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (null != val) {
                // 若key为类属性名称,则转换成数据库表字段名形式
                if (key.indexOf("_") == -1) {
                    builder.append(" and ").append(splitCamelName(key)).append("=?");
                } else {
                    builder.append(" and ").append(key).append("=?");
                }
            }
        }
        return builder.toString();
    }

    /**
     * 获取含多限制条件参数的删除SQL语句 (注意：暂只支持and、=等于形式的限制条件，or like between in > >= <
     * <=后续再扩展)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            Where部分参数
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getDeleteSqlWithConditions(Class targetClass, Map<String, Object> params, String tableName) {
        return getDeleteSqlWithConditions(targetClass, params, null, null, tableName);
    }

    /**
     * 获取含多限制条件参数的删除SQL语句 (注意：暂只支持and、=等于形式的限制条件，or like between in > >= <
     * <=后续再扩展)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            Where部分参数
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getDeleteSqlWithConditions(Class targetClass, Map<String, Object> params) {
        return getDeleteSqlWithConditions(targetClass, params, null, null, null);
    }

    /**
     * 获取Select查询语句 (不指定显示字段，默认会显示表的所有字段)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            查询参数
     * @param displayFields
     *            显示字段
     * @return
     */
    public static String getQuerySqlWithConditions(Class targetClass, Map<String, Object> params, List<String> displayFields, String tablePrefix, String tableStuf, String tableName) {
        if (null == targetClass) {
            return null;
        }
        String table = getTableName(targetClass, tablePrefix, tableStuf, tableName);
        if (GerneralUtils.isEmptyString(table)) {
            return null;
        }
        StringBuilder builder = new StringBuilder("select ");
        if (GerneralUtils.isEmptyCollection(displayFields)) {
            builder.append("*");
        } else {
            for (String displayField : displayFields) {
                if (displayField.indexOf("_") != -1) {
                    builder.append(displayField).append(",");
                } else {
                    builder.append(splitCamelName(displayField)).append(",");
                }
            }
        }
        builder.append(" from ").append(table);
        if (GerneralUtils.isNotEmptyMap(params)) {
            builder.append(" where 1=1");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();
                if (null == val) {
                    continue;
                }
                if (key.indexOf("_") != -1) {
                    builder.append(" and ").append(key).append("=?");
                } else {
                    builder.append(" and ").append(splitCamelName(key)).append("=?");
                }
            }
        }
        return builder.toString().replace(", from", " from");
    }

    /**
     * 获取Select查询语句 (不指定显示字段，默认会显示表的所有字段)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            查询参数
     * @param displayFields
     *            显示字段
     * @return
     */
    public static String getQuerySqlWithConditions(Class targetClass, Map<String, Object> params, List<String> displayFields, String tableName) {
        return getQuerySqlWithConditions(targetClass, params, displayFields, null, null, tableName);
    }

    /**
     * 获取Select查询语句 (不指定显示字段，默认会显示表的所有字段)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            查询参数
     * @param displayFields
     *            显示字段
     * @return
     */
    public static String getQuerySqlWithConditions(Class targetClass, Map<String, Object> params, List<String> displayFields) {
        return getQuerySqlWithConditions(targetClass, params, displayFields, null, null, null);
    }

    /**
     * 获取Select查询语句 (不指定显示字段，默认会显示表的所有字段)
     *
     * @param targetClass
     *            目标类型
     * @param params
     *            查询参数
     * @return
     */
    public static String getQuerySqlWithConditions(Class targetClass, Map<String, Object> params) {
        return getQuerySqlWithConditions(targetClass, params, null, null, null, null);
    }

    /**
     * 获取Select查询语句 (不指定显示字段，默认会显示表的所有字段) (不指定查询参数，默认为整表查询)
     *
     * @param targetClass
     *            目标类型
     * @return
     */
    public static String getQuerySqlWithConditions(Class targetClass) {
        return getQuerySqlWithConditions(targetClass, null, null, null, null, null);
    }

    /**
     * 获取查询所有的SQL语句
     *
     * @param targetClass
     * @return
     */
    public static String getQueryAllSql(Class targetClass) {
        String table = getTableName(targetClass, null, null, null);
        StringBuffer buffer = new StringBuffer("select * from ");
        buffer.append(table);
        return buffer.toString();
    }

    /**
     * 获取查询所有的SQL语句
     *
     * @return
     */
    public static String getQueryAllSql(String tableName) {
        StringBuffer buffer = new StringBuffer("select * from ");
        buffer.append(tableName);
        return buffer.toString();
    }

    /**
     * 获取表名
     *
     * @param targetClass
     *            目标类型
     * @param tablePrefix
     *            表名前缀
     * @param tableStuf
     *            表名后缀
     * @param tableName
     *            用户自定义表名(当类名称和表名称没有规律时请指定此参数)
     * @return
     */
    public static String getTableName(Class targetClass, String tablePrefix, String tableStuf, String tableName) {
        if (null == targetClass && GerneralUtils.isEmptyString(tableName)) {
            return null;
        }
        String table = "";
        if (GerneralUtils.isEmptyString(tableName)) {
            table = GerneralUtils.splitCamelName(targetClass.getSimpleName(), tablePrefix, tableStuf);
        } else {
            table = tableName;
        }
        return table;
    }

    /**
     * 统计集合中某元素出现的次数
     *
     * @param coll
     *            集合
     * @param element
     *            集合中某一元素
     * @return
     */
    public static int getOccurCountOfElInColl(Collection coll, Object element) {
        if (GerneralUtils.isEmptyCollection(coll)) {
            return 0;
        }
        return Collections.frequency(coll, element);
    }

    /**
     * 根据value对map进行排序
     *
     * @param map
     */
    public static List<Map.Entry<String, Integer>> sortMapByValue(Map<String, Integer> map, final boolean asc) {
        if (isEmptyMap(map)) {
            return null;
        }
        List<Map.Entry<String, Integer>> entrys = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(entrys, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                if (asc) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        return entrys;
    }

    /**
     * 根据value对map进行排序(默认升序)
     *
     * @param map
     */
    public static List<Map.Entry<String, Integer>> sortMapByValue(Map<String, Integer> map) {
        return sortMapByValue(map, true);
    }

    /**
     * 查找两个字符串最大长度的相同部分
     *
     * @param s1
     * @param s2
     * @return
     */
    public static String searchMaxLenSame(String s1, String s2) {
        if (null == s1 || s2 == null) {
            return "";
        }
        if (s1.length() > s2.length()) {
            String temp = s1;
            s1 = s2;
            s2 = temp;
        }
        int n = s1.length();
        int index = 0;
        ok: for (; n > 0; n--) {
            for (int i = 0; i < s1.length() - n + 1; i++) {
                String s = s1.substring(i, i + n);
                if (s2.indexOf(s) != -1) {
                    index = i;
                    break ok;
                }
            }
        }
        return s1.substring(index, index + n);
    }

    /**
     * 替换HTML5的文档声明
     *
     * @param html5
     * @return
     */
    public static String replaceDocType(String html5) {
        if (GerneralUtils.isEmptyString(html5)) {
            return html5;
        }
        if (html5.indexOf("<!doctype html>") == -1) {
            return html5;
        }
        String html = html5.replace("<!doctype html>", Constant.HTML_DOCTYPY);
        html = html.replaceAll("<html\\s+[^>]+>", "<html>");
        return html;
    }

    /**
     * 集合转换成数组
     *
     * @param collection
     * @return
     */
    public static Object[] collection2Array(Collection<Object> collection) {
        if (GerneralUtils.isEmptyCollection(collection)) {
            return null;
        }
        return collection.toArray();
    }

    /**
     * 数组转换成List
     *
     * @param array
     * @return
     */
    public static List array2List(Object[] array) {
        if (GerneralUtils.isEmptyArray(array)) {
            return null;
        }
        return Arrays.asList(array);
    }

    /**
     * 数组转换成Set
     *
     * @param array
     * @return
     */
    public static Set array2Set(Object[] array) {
        if (GerneralUtils.isEmptyArray(array)) {
            return null;
        }
        return new LinkedHashSet(array2List(array));
    }

    /**
     * 剔除结尾字符(如最后一个换行符、最后一个逗号等等)
     *
     * @param str
     *            待处理字符串
     * @param regular
     *            正则表达式
     * @return 返回剔除后的字符
     */
    public static String replaceEndsWith(String str, String regular) {
        if (isEmptyString(str)) {
            return null;
        }
        if (GerneralUtils.isEmptyString(regular)) {
            return str;
        }
        return str.replaceAll(regular, "");
    }

    /*
     * 生成随机文件名
     */
    public static String gernerateRandomFilename() {
        Random rand = new Random();
        long random = rand.nextInt() + System.currentTimeMillis();
        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        StringBuffer buffer = new StringBuffer();
        buffer.append(intYear).append("-").append(intMonth);
        buffer.append("-").append(intDay).append("-").append(random);
        return buffer.toString();
    }

    /**
     * @Title: execCommand
     * @Description: 执行命令(dos或者shell命令) 如果需要执行多条命令，请编写BAT批处理文件，然后调用execBat()
     * @param @param command dos或者shell命令
     * @param @return
     * @return String
     * @throws
     */
    public static String execCommand(String command) {
        String sResult = "", sErrorResult = "";
        StringBuffer buffer = new StringBuffer("cmd /c ");
        buffer.append(command);
        Process process = null;
        try {
            Runtime run = Runtime.getRuntime();
            process = run.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                sResult += line + "\n";
            }
            String errorLine = null;
            while ((errorLine = error.readLine()) != null) {
                sErrorResult += errorLine + "\n";
            }
            if (sErrorResult != null && sErrorResult.startsWith("ERROR")) {
                sResult = sErrorResult;
            }
            input.close();
            error.close();
        } catch (Exception e) {
            e.printStackTrace();
            sResult = "";
        } finally {
            try {
                process.waitFor(); // 主进程阻塞，直到当前进程执行完毕后，主进程才会继续向下执行(注意是进程，不是线程)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // process.destroy();
            // //打开子进程后，主进程立即销毁退出，若主进程需要子进程执行返回的结果,那么就请使用waitFor函数
        }
        return sResult;
    }

    /**
     * @Title: execBat
     * @Description: 调用执行BAT文件
     * @param @param batFile 批处理文件路径
     * @param @return
     * @return String
     * @throws
     */
    public static String execBat(String batFile) {
        String sResult = "", sErrorResult = "";
        StringBuffer buffer = new StringBuffer("cmd /c ");
        buffer.append("\"").append(batFile).append("\"");
        Process process = null;
        try {
            Runtime run = Runtime.getRuntime();
            process = run.exec(buffer.toString());
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                sResult += line + "\n";
            }
            String errorLine = null;
            while ((errorLine = error.readLine()) != null) {
                sErrorResult += errorLine + "\n";
            }
            if (sErrorResult != null && sErrorResult.startsWith("ERROR")) {
                sResult = sErrorResult;
            }
            input.close();
            error.close();
        } catch (Exception e) {
            e.printStackTrace();
            sResult = "";
        } finally {
			/*
			 * try { process.waitFor();
			 * //主进程阻塞，直到当前进程执行完毕后，主进程才会继续向下执行(注意是进程，不是线程) } catch
			 * (InterruptedException e) { e.printStackTrace(); }
			 */
            process.destroy(); // 打开子进程后，主进程立即销毁退出，若主进程需要子进程执行返回的结果,那么就请使用waitFor函数
        }
        return sResult;
    }

    /**
     * long转换成字节数组
     *
     * @param l
     */
    public static byte[] long2ByteArray(long l) {
        byte[] array = new byte[8];
        int i, shift;
        for (i = 0, shift = 56; i < 8; i++, shift -= 8) {
            array[i] = (byte) (0xFF & (l >> shift));
        }
        return array;
    }

    /**
     * 字节数组转换成long
     * @param bytearray
     * @return
     */
    public static long byteArray2Long(byte[] bytearray) {
        return byteArray2Long(bytearray, 0);
    }

    /**
     * 字节数组转换成long
     * @param bytearray
     * @param offset   偏移量
     * @return
     */
    public static long byteArray2Long(byte[] bytearray, int offset) {
        long result = 0;
        for (int i = offset; i < 8; i++) {
            result = (result << 8) | (0xff & (byte) (bytearray[i] & 0xff));
        }
        return result;
    }

    /**
     * int转换成字节数组
     * @param value
     * @return
     */
    public static byte[] int2ByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }

    /**
     * 字节数组转换成int
     * @param b
     * @return
     */
    public static int byteArray2Int(byte[] b) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }

    /**
     * 字节数组转换成long
     * @param b
     * @return
     */
    public static long byteArraytoLong(byte[] b) {
        int value = 0;
        for (int i = 0; i < 8; i++) {
            int shift = (8 - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public static int compareLong(long o1, long o2) {
        if (o1 < o2) {
            return -1;
        } else if (o1 == o2) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * @param <T>
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: mapEquals
     * @Description: 判断两个Map是否相等
     * @param @param m1
     * @param @param m2
     * @param @return
     * @return boolean
     * @throws
     */
    public static <T> boolean mapEquals(Map<String,T> m1,Map<String,T> m2) {
        if(m1 == null && m2 == null) {
            return true;
        }
        if(m1 == null && m2 != null) {
            return false;
        }
        if(m2 == null && m1 != null) {
            return false;
        }
        if(m1.keySet().size() != m2.keySet().size()) {
            return false;
        }
        boolean diff = false;
        for(Map.Entry<String,T> entry : m1.entrySet()) {
            String key = entry.getKey();
            T v1 = entry.getValue();
            T v2 = m2.get(key);
            if(v1 == null && v2 == null) {
                continue;
            }
            if((v1 == null && v2!= null) || (v2 == null && v1!= null)) {
                diff = true;
                break;
            }
            if(!v1.equals(v2)) {
                diff = true;
                break;
            }
        }
        return !diff;
    }
}
