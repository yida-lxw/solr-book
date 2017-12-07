package com.yida.solr.book.examples.ch16.sql.jdbc;

import java.sql.*;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/21 22:03
 * 使用JDBC进行Solr SQL查询示例代码
 */
public class TestJDBCSQL {
    public static void main(String[] args) {
        String zkHost = "linux.yida01.com:2181,linux.yida02.com:2181,linux.yida03.com:2181";
        String collectionName = "logs";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection("jdbc:solr://" + zkHost + "?collection=" + collectionName +
                    "&aggregationMode=map_reduce&numWorkers=2");
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id,log_level,log_msg FROM " + collectionName + " limit 10");
            while(rs.next()) {
                String id = rs.getString("id");
                String log_level = rs.getString("log_level");
                String log_msg = rs.getString("log_msg");
                System.out.println("[" + log_level + "]:" + log_msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
