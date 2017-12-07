package com.yida.solr.book.examples.utils.config;

import com.yida.solr.book.examples.utils.jdbc.JDBCConfig;

/**
 * @ClassName: DefaultConfigurable
 * @Description: 可配置接口默认实现
 * @author Lanxiaowei(736031305@qq.com)
 * @date 2015年9月29日 上午9:23:21
 *
 */
public abstract class DefaultConfigurable implements Configurable {
    protected JDBCConfig config;

    public DefaultConfigurable() {
        initConfig();
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: initConfig
     * @Description: 初始化爬虫配置对象
     * @return void
     * @throws
     */
    public JDBCConfig initConfig() {
        this.config = JDBCConfig.getInstance();
        return this.config;
    }

    public JDBCConfig getConfig() {
        return config;
    }

    public void setConfig(JDBCConfig config) {
        this.config = config;
    }
}

