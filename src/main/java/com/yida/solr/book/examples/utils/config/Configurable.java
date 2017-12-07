package com.yida.solr.book.examples.utils.config;

import com.yida.solr.book.examples.utils.jdbc.JDBCConfig;

/**
 * 可配置接口
 *
 * @since 1.0
 * @author Lanxiaowei@citic-finance.com
 * @date 2015-9-28上午9:33:53
 *
 */
public interface Configurable {
    public JDBCConfig initConfig();
}
