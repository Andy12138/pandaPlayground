package com.zmg20200111.panda.utils.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

@Slf4j
public class JdbcUtils {

    private static final int FORMAT_LEN = 20;

    /**
     * 获取mysql连接
     * @return
     */
    public Connection getMysqlConnection() {
        Connection connection = null;
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/zmg_db?useUnicode=true&characterEncoding=utf8";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "root");
        info.setProperty("serverTimezone", "Asia/Shanghai");
        try {
            connection = DriverManager.getConnection(jdbcUrl, info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection... connections) {
        for (Connection connection : connections) {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("关闭jdbc connection 失败");
            }
        }
    }

    public void scanResultSet(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("------------------->");
            System.out.println("结果集：");
            for (int i = 1; i <= columnCount; i++) {
                String format = getFormat(columnCount, i);
                System.out.format(format, metaData.getColumnLabel(i));
            }
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = getColumnValue(resultSet, i);
                    String format = getFormat(columnCount, i);
                    System.out.format(format, value);
                }
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getColumnValue(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        if (value != null) {
            if (value.length() > FORMAT_LEN) {
                value = value.substring(0, FORMAT_LEN - 4) + "...";
            }
        }
        return value;
    }

    private String getFormat(int columnCount, int i) {
        String prefix = "";
        String suffix = "";
        if (i != 1) {
            prefix = "|";
        }
        if (i == columnCount) {
            suffix = "\n";
        }
        StringJoiner format = new StringJoiner("", prefix, suffix);
        format.add("%-"+ FORMAT_LEN +"s");
        return format.toString();
    }
}
