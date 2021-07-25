package com.dal.cabby.dbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the database mocks which may be needed for the Unit
 * testing.
 */
public class DBMocks implements IPersistence {
    /**
     * Just a dummy implementation for the execute update query.
     *
     * @param query which need to be executed.
     * @throws SQLException
     */
    @Override
    public void executeCreateOrUpdateQuery(String query) throws SQLException {
        System.out.println("Executing the SQL query: " + query);
    }

    /**
     * Just a dummy implementation for the execute select query.
     *
     * @param query which need to be executed.
     * @return ResultSet after executing the select query.
     * @throws SQLException
     */
    @Override
    public ResultSet executeSelectQuery(String query) throws SQLException {
        return null;
    }

    /**
     * Closes the Database connections.
     *
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        System.out.println("Closing the Database connection");
    }
}