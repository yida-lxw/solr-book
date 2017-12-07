package com.yida.solr.book.examples.utils.jdbc;

import com.yida.solr.book.examples.utils.config.Configurations;

/**
 * 配置对象
 *
 * @since 1.0
 * @author Lanxiaowei@citic-finance.com
 * @date 2015-9-28上午9:41:38
 *
 */
public class JDBCConfig {
    /**数据库类型*/
    private String defaultDatabaseType;
    /**主机名称或者主机IP地址*/
    private String defaultHost;
    /**端口号*/
    private String defaultPort;
    /**数据库名称*/
    private String defaultDatabaseName;
    /**登录帐号*/
    private String defaultUserName;
    /**登录密码*/
    private String defaultPassword;

    private JDBCConfig () {
        initialize();
    }

    private static class SingletonHolder {
        private static final JDBCConfig INSTANCE = new JDBCConfig();
    }

    public static final JDBCConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * @Author Lanxiaowei
     * @Title: initialize
     * @Description: 配置初始化
     * @return void
     * @throws
     */
    public void initialize() {
        /**数据库类型*/
        this.defaultDatabaseType = Configurations
                .getStringProperty("jdbc.default_database_type", "MYSQL");

        /**主机名称或者主机IP地址*/
        this.defaultHost = Configurations
                .getStringProperty("jdbc.default_host", "localhost");

        /**端口号*/
        this.defaultPort = Configurations
                .getStringProperty("jdbc.default_port", "3306");

        /**数据库名称*/
        this.defaultDatabaseName = Configurations
                .getStringProperty("jdbc.default_database_name", "test");

        /**登录帐号*/
        this.defaultUserName = Configurations
                .getStringProperty("jdbc.default_user_name", "root");

        /**登录密码*/
        this.defaultPassword = Configurations
                .getStringProperty("jdbc.default_password", "123");
    }

    public String getDefaultDatabaseType() {
        return defaultDatabaseType;
    }

    public void setDefaultDatabaseType(String defaultDatabaseType) {
        this.defaultDatabaseType = defaultDatabaseType;
    }

    public String getDefaultHost() {
        return defaultHost;
    }

    public void setDefaultHost(String defaultHost) {
        this.defaultHost = defaultHost;
    }

    public String getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(String defaultPort) {
        this.defaultPort = defaultPort;
    }

    public String getDefaultDatabaseName() {
        return defaultDatabaseName;
    }

    public void setDefaultDatabaseName(String defaultDatabaseName) {
        this.defaultDatabaseName = defaultDatabaseName;
    }

    public String getDefaultUserName() {
        return defaultUserName;
    }

    public void setDefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }
}