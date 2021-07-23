package com.dal.cabby.dbHelper;

import java.sql.*;

public class DBHelper implements IPersistence {
    String database;
    String user;
    String password;
    Connection connection;
    String connUrl;
    String url = "jdbc:mysql://%s:3306/%s?useSSL=false&allowPublicKeyRetrieval=true";
    private String DEFAULT_MYSQL_USERNAME = "root";
    private String DEFAULT_MYSQL_PASSWORD = "root@123";
    private String DEFAULT_MYSQL_DATABASE = "cabby";

    public DBHelper() {
        this.database = DEFAULT_MYSQL_DATABASE;
        this.user = DEFAULT_MYSQL_USERNAME;
        this.password = DEFAULT_MYSQL_PASSWORD;
        connUrl = String.format(url, "localhost", this.database);
    }

    public DBHelper(String user, String password) {
        this.database = "cabby";
        this.user = user;
        this.password = password;
        connUrl = String.format(url, "localhost", this.database);
    }

    @Override
    public void initialize() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection(connUrl, user, password);
        }
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    @Override
    public void executeCreateOrUpdateQuery(String query) throws SQLException {
        if (connection == null) {
            throw new RuntimeException("Please call initialize method in DBHelper before calling this method.");
        }
        Statement st = connection.createStatement();
        st.executeUpdate(query);
    }

    @Override
    public ResultSet executeSelectQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
