package com.dal.cabby.dbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IPersistence {

    /**
     * Closes any open Database coneections and database statements.
     * @throws SQLException
     */
    void close() throws SQLException;

    /**
     * Execute DML query
     * @param query - query which need to be executed.
     * @throws SQLException
     */
    void executeCreateOrUpdateQuery(String query) throws SQLException;

    /**
     * Execute select query.
     * @param query - query which need to be executed.
     * @return - return the ResultSet
     * @throws SQLException
     */
    ResultSet executeSelectQuery(String query) throws SQLException;
}
