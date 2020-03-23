package com.zmg20200111.panda.utils.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMetadataUtils {

    private JdbcUtils jdbcUtils = new JdbcUtils();

    public void showSchemas() {
        Connection connection = jdbcUtils.getMysqlConnection();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getCatalogs();
            resultSet.setFetchSize(100);
            jdbcUtils.scanResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.closeConnection(connection);
        }
    }

    public void showTables(){
        Connection connection = jdbcUtils.getMysqlConnection();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables("zmg_db", "zmg_db", "%", null);
            resultSet.setFetchSize(100);
            jdbcUtils.scanResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.closeConnection(connection);
        }
    }




}
