package com.yida.solr.book.examples.utils;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * properties配置文件读取工具类
 *
 * @author Lanxiaowei
 * @create 2012.12.18 16:23:56
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class PropertiesUtils {
    /** Properties配置文件容器 */
    private static Hashtable<String, Properties> proContainer = new Hashtable<String, Properties>();

    /**
     * 从文件系统加载Properties(默认会先从缓存中获取)
     *
     * @param propertyFilePath
     * @return
     */
    public final static Properties getPropertiesFromFS(String propertyFilePath) {
        if (propertyFilePath == null) {
            return null;
        }
        Properties pro = proContainer.get(propertyFilePath);
        if (null == pro) {
            pro = loadPropertyFromFileSystem(propertyFilePath);
            if (null != pro) {
                proContainer.put(propertyFilePath, pro);
            }
        }
        return pro;
    }

    /**
     * 加载Properties配置文件(默认先查找缓存，若没有然后查找类路径，最后查找文件系统，最后更新缓存)
     *
     * @param propertyFilePath
     * @return
     */
    public final static Properties getProperties(String propertyFilePath) {
        if (propertyFilePath == null) {
            return null;
        }
        if (!propertyFilePath.startsWith("/")) {
            propertyFilePath = "/" + propertyFilePath;
        }
        if (!propertyFilePath.endsWith(".properties")) {
            propertyFilePath = propertyFilePath + ".properties";
        }
        Properties pro = proContainer.get(propertyFilePath);
        if (pro == null) {
            pro = loadPropertyFile(propertyFilePath);
        }
        if (pro != null) {
            proContainer.put(propertyFilePath, pro);
        }
        return pro;
    }

    /**
     * 加载Properties(默认从类路径下查找，没找到才会根据提供的文件路径去文件系统中查找)
     *
     * @param propertyFilePath
     * @return
     */
    public static Properties loadPropertyFile(String propertyFilePath) {
        return loadPropertyFile(propertyFilePath, true);
    }

    /**
     * 加载Properties(默认从类路径下查找，没找到才会根据提供的文件路径去文件系统中查找)
     *
     * @param propertyFilePath
     * @return
     */
    public static Properties loadPropertyFile(String propertyFilePath, boolean debug) {
        InputStream is = null;
        if (debug) {
            is = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertyFilePath);
        } else {
            if (!propertyFilePath.startsWith("/")) {
                propertyFilePath = "/" + propertyFilePath;
            }
            is = PropertiesUtils.class.getResourceAsStream(propertyFilePath);
        }
        if (is == null) {
            return loadPropertyFromFileSystem(System.getProperty("user.dir") + propertyFilePath);
        }
        Properties ppts = new Properties();
        try {
            ppts.load(is);
            return ppts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;//
        }
    }

    /**
     * 从文件系统加载Properties
     *
     * @param propertyFilePath
     * @return
     */
    public static Properties loadPropertyFromFileSystem(final String propertyFilePath) {
        try {
            Properties pro = new Properties();
            pro.load(new java.io.FileInputStream(propertyFilePath));
            return pro;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 往一存在的Properties配置文件中追加键值对、注释(默认先查找文件系统，然后查找类路径)
     *
     * @param propertyFilePath
     *            properties配置文件路径
     * @param keyValue
     *            多个键值对
     * @param comment
     *            注释
     * @return 是否添加成功
     */
    public final static boolean appendKeyValue(String propertyFilePath, Hashtable<String, String> keyValue, String comment) {
        Properties pro = getProperties(propertyFilePath);
        if (pro == null || keyValue == null) {
            return false;
        }
        pro.putAll(keyValue);
        java.io.OutputStream stream = null;
        try {
            stream = new FileOutputStream(propertyFilePath);
        } catch (FileNotFoundException e) {
            String path = PropertiesUtils.class.getResource(propertyFilePath).getPath();
            try {
                stream = new java.io.FileOutputStream(path);
            } catch (FileNotFoundException e1) {
                return false;
            }
        }

        if (stream == null) {
            return false;
        }
        try {
            pro.store(stream, comment != null ? comment : "This is a comment.");
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 往一存在的Properties配置文件中追加键值对,不加注释(重载)
     *
     * @param propertyFilePath
     *            properties配置文件路径
     * @param keyValue
     *            多个键值对
     * @return 是否添加成功
     */
    public final static boolean appendKeyValue(String propertyFilePath, Hashtable<String, String> keyValue) {
        return appendKeyValue(propertyFilePath, keyValue, null);
    }

    /**
     * 在文件系统创建Properties配置文件
     *
     * @param propertyFilePath
     *            properties配置文件路径
     * @param keyValue
     *            多个键值对(Hashtable同步)
     * @return
     */
    public final static boolean createPropertiesFile(String propertyFilePath, Hashtable<String, String> keyValue) {
        File file = new File(propertyFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return appendKeyValue(propertyFilePath, keyValue);
    }

    /**
     * Properties对象追加单个键值对(注意：并未持久化到文件)
     *
     * @param propertyFilePath
     * @param key
     * @param value
     * @return
     */
    public final static boolean setValue(String propertyFilePath, String key, String value) {
        Properties pro = getProperties(propertyFilePath);
        if (pro == null) {
            return false;
        }
        pro.put(key, value);
        return true;
    }

    /**
     * Properties对象持久化至文件系统
     *
     * @param properties
     *            properties对象
     * @param propertyFilePath
     *            properties文件保存路径
     * @param comment
     *            注释
     */
    public final static void saveProperties(Properties properties, String propertyFilePath, String comment) {
        try {
            OutputStream stream = new FileOutputStream(propertyFilePath);
            properties.store(stream, comment);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    /**
     * 删除Properties对象中的一对键值对
     *
     * @param properties
     * @param key
     * @return 返回删除的值
     */
    public final static String deleteValueByKey(Properties properties, String key) {
        if (properties == null) {
            return null;
        }
        return (String) properties.remove(key);
    }

    /**
     * 删除Properties对象中的多对键值对
     *
     * @param properties
     * @param keys
     *            键数组
     */
    public final static void deleteValueByKeys(Properties properties, String[] keys) {
        if (keys == null) {
            return;
        }
        if (properties == null) {
            return;
        }
        for (String key : keys) {
            properties.remove(key);
        }
    }

    /**
     * 删除Properties对象中的多对键值对并持久化至文件系统
     *
     * @param propertyFilePath
     *            properties文件路径
     * @param keys
     *            键数组
     * @return
     */
    public final static boolean deleteValueAndSave(String propertyFilePath, String[] keys) {
        Properties pro = getProperties(propertyFilePath);
        if (pro == null) {
            return false;
        }
        deleteValueByKeys(pro, keys);
        if (pro == null) {
            return false;
        }
        saveProperties(pro, propertyFilePath, null);
        return true;
    }

    /**
     * 更新properties属性文件中某个key的value
     *
     * @param propertyFilePath
     * @param key
     * @param newValue
     * @return boolean 是否更新成功
     */
    public final static boolean updateValue(String propertyFilePath, String key, String newValue) {
        if (key == null || newValue == null) {
            return false;
        }
        Hashtable<String, String> table = new Hashtable<String, String>();
        table.put(key, newValue);
        return appendKeyValue(propertyFilePath, table, null);
    }

    /**
     * 批量更新properties属性文件中多个键值对
     *
     * @param propertyFilePath
     * @param table
     * @return boolean 是否更新成功
     */
    public final static boolean batchUpdateValue(String propertyFilePath, Hashtable<String, String> table) {
        if (propertyFilePath == null || table == null) {
            return false;
        }
        return appendKeyValue(propertyFilePath, table, null);
    }

    /**
     * Properties容器中移除指定properties文件
     *
     * @param propertyFilePath
     * @return 返回删除的Properties对象
     */
    public final static Properties removePropertyFile(String propertyFilePath) {
        return proContainer.remove(propertyFilePath);
    }

    /**
     * Properties容器中添加指定properties文件
     *
     * @param propertyFilePath
     * @return 返回添加的Properties对象
     */
    public final static Properties addPropertyFile(String propertyFilePath) {
        Properties properties = getProperties(propertyFilePath);
        if (null == properties) {
            return null;
        }
        return proContainer.put(propertyFilePath, properties);
    }

    /**
     * 重新加载指定的Properties文件(先清理缓存重新加载再更新缓存)
     *
     * @param propertyFilePath
     */
    public final static Properties reloadPropertyFile(String propertyFilePath) {
        removePropertyFile(propertyFilePath);
        return getProperties(propertyFilePath);
    }

    /**
     * 获取Properties的完整包路径
     *
     * @param packageName
     *            包名
     * @param propertyFileName
     *            properties文件名(不含目录)
     * @return
     */
    public final static String getPpropertyFilePath(String packageName, String propertyFileName) {
        packageName = packageName == null ? "" : packageName.replaceAll("\\.", "/");
        propertyFileName = propertyFileName.endsWith(".properties") ? propertyFileName : (propertyFileName + ".properties");
        StringBuffer fileName = new StringBuffer("/");
        fileName.append(packageName).append("/").append(propertyFileName);
        return fileName.toString();
    }

    /**
     * Properties对象转换成HashMap对象
     *
     * @param properties
     * @return
     */
    public final static HashMap<String, Object> properties2Map(Properties properties) {
        if (null == properties) {
            return null;
        }
        Iterator it = properties.entrySet().iterator();
        HashMap<String, Object> map = new HashMap<String, Object>();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            map.put(key, value);
        }
        return map;
    }

    /**
     * 根据key获取value
     *
     * @param pro
     * @param key
     * @return
     */
    public final static String getProperty(Properties pro, String key) {
        if (null == pro) {
            return null;
        }
        return pro.getProperty(key);
    }

    /**
     * @Title: native2asiiProperties
     * @Description: properties内容进行unicode编码
     * @param @param originalProperties 原Properties文件加载路径
     * @param @param propertiesName 新Properties文件名称
     * @return void
     * @throws
     */
    public final static void native2asiiProperties(String originalProperties, String propertiesName) {
        Properties properties = loadPropertyFile(originalProperties);
        String path = FileUtils.getAbsolutePath(originalProperties);
        File proFile = new File(path + propertiesName);
        StringBuffer content = new StringBuffer();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            content.append(key);
            content.append("=");
            content.append(StringUtils.string2Unicode(val, "UTF-8"));
            // content.append(val);
            content.append("\n");
        }
        String filePath = (path + propertiesName).replace("/bin", "/src");
        if (content.length() > 0 && content.toString().endsWith("\n")) {
            FileUtils.writeFile(GerneralUtils.replaceEndsWith(content.toString(), "\n$"), filePath, "UTF-8", false);
        } else {
            FileUtils.writeFile(content.toString(), filePath, "UTF-8", false);
        }
    }

    /*************************根据Key提取属性值，并转换成相应数据类型************************/
    public static String getStringProperty(Properties properties,String key, String defaultValue) {
        if (properties == null || properties.getProperty(key) == null) {
            return defaultValue;
        }
        return properties.getProperty(key);
    }

    public static int getIntProperty(Properties properties,String key, int defaultValue) {
        if (properties == null || properties.getProperty(key) == null) {
            return defaultValue;
        }
        return Integer.parseInt(properties.getProperty(key));
    }

    public static short getShortProperty(Properties properties,String key, short defaultValue) {
        if (properties == null || properties.getProperty(key) == null) {
            return defaultValue;
        }
        return Short.parseShort(properties.getProperty(key));
    }

    public static long getLongProperty(Properties properties,String key, long defaultValue) {
        if (properties == null || properties.getProperty(key) == null) {
            return defaultValue;
        }
        return Long.parseLong(properties.getProperty(key));
    }

    public static float getFloatProperty(Properties properties,String key, float defaultValue) {
        if (properties == null || properties.getProperty(key) == null) {
            return defaultValue;
        }
        return Float.parseFloat(properties.getProperty(key));
    }

    public static double getDoubleProperty(Properties properties,String key, double defaultValue) {
        if (properties == null || properties.getProperty(key) == null) {
            return defaultValue;
        }
        return Double.parseDouble(properties.getProperty(key));
    }

    public static boolean getBooleanProperty(Properties properties,String key, boolean defaultValue) {
        if (properties == null || properties.getProperty(key) == null) {
            return defaultValue;
        }
        return properties.getProperty(key).toLowerCase().trim().equals("true");
    }

    public static String getStringProperty(Properties properties,String key) {
        return getStringProperty(properties, key, null);
    }

    public static int getIntProperty(Properties properties,String key) {
        return getIntProperty(properties, key, 0);
    }

    public static short getShortProperty(Properties properties,String key) {
        return getShortProperty(properties, key, (short)0);
    }

    public static long getLongProperty(Properties properties,String key) {
        return getLongProperty(properties, key, 0L);
    }

    public static float getFloatProperty(Properties properties,String key) {
        return getFloatProperty(properties, key, 0F);
    }

    public static double getDoubleProperty(Properties properties,String key) {
        return getDoubleProperty(properties, key, 0);
    }

    public static boolean getBooleanProperty(Properties properties,String key) {
        return getBooleanProperty(properties, key, false);
    }
    /************************根据Key提取属性值，并转换成相应数据类型  End******************/
}
