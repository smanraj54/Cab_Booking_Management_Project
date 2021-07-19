package com.dal.cabby.driver;

import java.sql.SQLException;
import java.text.ParseException;

public interface IDriver {

    /**
     * Consists of list of steps, like profile-managemnents, driver tasks, etc
     */
    void performTasks() throws SQLException, ParseException;
    /**
     * Performs basic operations like login, registration and password
     * recovery.
     */
    void profileManagementTasks() throws SQLException, ParseException;

    /**
     * Performs drivers related tasks like start trip, rate customer, etc.
     */
    void performDriverTasks() throws SQLException, ParseException;
}
