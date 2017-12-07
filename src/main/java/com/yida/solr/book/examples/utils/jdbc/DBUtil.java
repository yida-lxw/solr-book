package com.yida.solr.book.examples.utils.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yida.solr.book.examples.utils.config.DefaultConfigurable;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * JDBC操作工具类
 * @since 1.0
 * @author  Lanxiaowei@citic-finance.com
 * @date    2015-6-18下午4:30:51
 *
 */
public class DBUtil extends DefaultConfigurable {
    private final QueryRunner runner = new QueryRunner();
    private String DATABASE_TYPE_DEFAULT;
    private String HOST_DEFAULT;
    private String PORT_DEFAULT;
    private String DATABASE_NAME_DEFAULT;
    private String USER_NAME_DEFAULT;
    private String PWD_DEFAULT;

    private DBUtil () {
        initialize();
    }

    private static class SingletonHolder {
        private static final DBUtil INSTANCE = new DBUtil();
    }

    public static final DBUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void initialize() {
        this.DATABASE_TYPE_DEFAULT = this.config.getDefaultDatabaseType();
        this.DATABASE_NAME_DEFAULT = this.config.getDefaultDatabaseName();
        this.HOST_DEFAULT = this.config.getDefaultHost();
        this.PORT_DEFAULT = this.config.getDefaultPort();
        this.USER_NAME_DEFAULT = this.config.getDefaultUserName();
        this.PWD_DEFAULT = this.config.getDefaultPassword();
    }

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     * @Author Lanxiaowei
     * @Title: openConn
     * @Description: 打开数据库连接(type: MySQL，Oracle，SQLServer)
     * @param type
     * @param host
     * @param port
     * @param name
     * @param username
     * @param password
     * @return
     * @return Connection
     * @throws
     */
    public Connection openConn(String type, String host, String port, String name, String username, String password) throws SQLException, ClassNotFoundException {
        String driver = null;
        String url = null;
        if (type.equalsIgnoreCase("MySQL")) {
            driver = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useUnicode=true&characterEncoding=utf-8";
        } else if (type.equalsIgnoreCase("Oracle")) {
            driver = "oracle.jdbc.driver.OracleDriver";
            url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + name;
        } else if (type.equalsIgnoreCase("SQLServer")) {
            driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
            url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + name;
        } else {
            throw new RuntimeException();
        }
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public Connection openConn(String databaseName) throws ClassNotFoundException, SQLException {
        return openConn(DATABASE_TYPE_DEFAULT, HOST_DEFAULT, PORT_DEFAULT, databaseName, USER_NAME_DEFAULT, PWD_DEFAULT);
    }

    /**
     * @throws SQLException
     * @throws ClassNotFoundException
     * @Author Lanxiaowei
     * @Title: openConn
     * @Description: 打开数据库链接
     * @return
     * @return Connection
     * @throws
     */
    public Connection openConn() throws ClassNotFoundException, SQLException {
        return openConn(DATABASE_TYPE_DEFAULT, HOST_DEFAULT, PORT_DEFAULT, DATABASE_NAME_DEFAULT, USER_NAME_DEFAULT, PWD_DEFAULT);
    }

    // 关闭数据库连接
    public void closeConn(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    // 查询（返回Array结果）
    public Object[] queryArray(Connection con, String sql, Object... params) throws SQLException {
        return runner.query(con, sql, new ArrayHandler(), params);
    }

    // 查询（返回ArrayList结果）
    public List<Object[]> queryArrayList(Connection con, String sql, Object... params) throws SQLException {
        return runner.query(con, sql, new ArrayListHandler(), params);
    }

    // 查询（返回Map结果）
    public Map<String, Object> queryMap(Connection con, String sql, Object... params) throws SQLException {
        return runner.query(con, sql, new MapHandler(), params);
    }

    // 查询（返回MapList结果）
    public List<Map<String, Object>> queryMapList(Connection con, String sql, Object... params) throws SQLException {
        return runner.query(con, sql, new MapListHandler(), params);
    }

    // 查询（返回Bean结果）
    public <T> T queryBean(Class<T> cls, Map<String, String> map, Connection con, String sql, Object... params) throws SQLException {
        if (map != null && !map.isEmpty()) {
            return runner.query(con, sql, new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
        }
        return runner.query(con, sql, new BeanHandler<T>(cls), params);
    }

    // 查询（返回BeanList结果）
    public <T> List<T> queryBeanList(Class<T> cls, Map<String, String> map, Connection con, String sql, Object... params) throws SQLException {
        if (map != null && !map.isEmpty()) {
            return runner.query(con, sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
        }
        return runner.query(con, sql, new BeanListHandler<T>(cls), params);
    }

    // 查询指定列名的值（单条数据）
    public <T> T queryColumn(String column, Connection con, String sql, Object... params) throws SQLException {
        return runner.query(con, sql, new ScalarHandler<T>(column), params);
    }

    // 查询指定列名的值（多条数据）
    public <T> List<T> queryColumnList(String column, Connection con, String sql, Object... params) throws SQLException {
        return runner.query(con, sql, new ColumnListHandler<T>(column), params);
    }

    // 查询指定列名对应的记录映射
    public <T> Map<T, Map<String, Object>> queryKeyMap(String column, Connection con, String sql, Object... params) throws SQLException {
        return runner.query(con, sql, new KeyedHandler<T>(column), params);
    }

    // 更新（包括UPDATE、INSERT、DELETE，返回受影响的行数）
    public int update(Connection con, String sql, Object... params) throws SQLException {
        return runner.update(con, sql, params);
    }
}