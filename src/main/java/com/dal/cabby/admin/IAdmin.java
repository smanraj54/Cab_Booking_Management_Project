package com.dal.cabby.admin;

import java.sql.SQLException;
import java.text.ParseException;

public interface IAdmin {
    /**
     * Consists of list of steps, like profile-managemnents, admin tasks, etc
     */
    void performTasks() throws SQLException, ParseException;
    /**
     * Performs basic operations like login, registration and password
     * recovery.
     */
    void profileManagementTasks() throws SQLException, ParseException;

    /**
     * Performs admin related tasks like approve profile, regesiter profiles, etc.
     */
    void performAdminTasks() throws SQLException, ParseException;
}
