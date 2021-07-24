package com.dal.cabby.dbHelper;

import java.sql.*;

public class DBHelper implements IPersistence {
    String database;
    String user;
    String password;
    private static Connection connection;
    private static Statement statement;
    String connUrl;
    String url = "jdbc:mysql://%s:3306/%s?useSSL=false&allowPublicKeyRetrieval=true";
    private final String DB_HOST = "35.193.102.29";
    private final String DEFAULT_MYSQL_USERNAME = "root";
    private final String DEFAULT_MYSQL_PASSWORD = "group15";
    private final String DEFAULT_MYSQL_DATABASE = "cabby";
    private static DBHelper dbHelper;

    public static DBHelper getInstance() throws SQLException {
        if(dbHelper == null) {
            dbHelper = new DBHelper();
            dbHelper.initialize();
        }
        return dbHelper;
    }

    private DBHelper() {
        this.database = DEFAULT_MYSQL_DATABASE;
        this.user = DEFAULT_MYSQL_USERNAME;
        this.password = DEFAULT_MYSQL_PASSWORD;
        connUrl = String.format(url, DB_HOST, this.database);
    }

    private void initialize() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection(connUrl, user, password);
        }
        if (statement == null) {
            statement = connection.createStatement();
        }
    }

    @Override
    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void executeCreateOrUpdateQuery(String query) throws SQLException {
        if (connection == null) {
            throw new RuntimeException("Please call initialize method in DBHelper before calling this method.");
        }
        statement.executeUpdate(query);
    }

    @Override
    public ResultSet executeSelectQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }
}
