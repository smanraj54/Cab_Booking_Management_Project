package com.dal.cabby.dbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IPersistence {

    void close() throws SQLException;

    void executeCreateOrUpdateQuery(String query) throws SQLException;

    ResultSet executeSelectQuery(String query) throws SQLException;
}
