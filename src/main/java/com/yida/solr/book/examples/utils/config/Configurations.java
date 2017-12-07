package com.yida.solr.book.examples.utils.config;

import com.yida.solr.book.examples.utils.PropertiesUtils;

import java.util.Properties;

/**
 * JDBC配置文件读取类
 * @since 1.0
 * @author  Lanxiaowei@citic-finance.com
 * @date    2015-9-28上午9:55:33
 *
 */
public class Configurations {
    private static final boolean DEBUG = true;
    private static Properties prop = new Properties();

    static {
        // 读取爬虫配置文件
        prop = PropertiesUtils.loadPropertyFile("jdbc.properties", DEBUG);
        if (null == prop) {
            throw new ConfigInitialException("An exception occurs as initialize the jdbc configuration file.");
        }
    }

    /**
     * 根据key读取字符串类型的配置项
     * @param key
     * @param defaultValue  默认值
     * @return
     */
    public static String getStringProperty(String key, String defaultValue) {
        if (prop == null || prop.getProperty(key) == null) {
            return defaultValue;
        }
        return prop.getProperty(key);
    }

    public static String getStringProperty(String key) {
        if (prop == null || prop.getProperty(key) == null) {
            return null;
        }
        return prop.getProperty(key);
    }

    public static int getIntProperty(String key, int defaultValue) {
        if (prop == null || prop.getProperty(key) == null) {
            return defaultValue;
        }
        return Integer.parseInt(prop.getProperty(key));
    }

    public static int getIntProperty(String key) {
        if (prop == null || prop.getProperty(key) == null) {
            return 0;
        }
        return Integer.parseInt(prop.getProperty(key));
    }

    public static short getShortProperty(String key, short defaultValue) {
        if (prop == null || prop.getProperty(key) == null) {
            return defaultValue;
        }
        return Short.parseShort(prop.getProperty(key));
    }

    public static short getShortProperty(String key) {
        if (prop == null || prop.getProperty(key) == null) {
            return (short) 0;
        }
        return Short.parseShort(prop.getProperty(key));
    }

    public static long getLongProperty(String key, long defaultValue) {
        if (prop == null || prop.getProperty(key) == null) {
            return defaultValue;
        }
        return Long.parseLong(prop.getProperty(key));
    }

    public static long getLongProperty(String key) {
        if (prop == null || prop.getProperty(key) == null) {
            return (long) 0;
        }
        return Long.parseLong(prop.getProperty(key));
    }

    public static float getFloatProperty(String key, float defaultValue) {
        if (prop == null || prop.getProperty(key) == null) {
            return defaultValue;
        }
        return Float.parseFloat(prop.getProperty(key));
    }

    public static float getFloatProperty(String key) {
        if (prop == null || prop.getProperty(key) == null) {
            return 0f;
        }
        return Float.parseFloat(prop.getProperty(key));
    }

    public static double getDoubleProperty(String key, double defaultValue) {
        if (prop == null || prop.getProperty(key) == null) {
            return defaultValue;
        }
        return Double.parseDouble(prop.getProperty(key));
    }

    public static double getDoubleProperty(String key) {
        if (prop == null || prop.getProperty(key) == null) {
            return 0.00D;
        }
        return Double.parseDouble(prop.getProperty(key));
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        if (prop == null || prop.getProperty(key) == null) {
            return defaultValue;
        }
        return prop.getProperty(key).toLowerCase().trim().equals("true");
    }

    public static boolean getBooleanProperty(String key) {
        if (prop == null || prop.getProperty(key) == null) {
            return false;
        }
        return prop.getProperty(key).toLowerCase().trim().equals("true");
    }
}
