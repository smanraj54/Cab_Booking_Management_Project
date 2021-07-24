package com.dal.cabby.driver;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

public interface IDriver {

    /**
     * Consists of list of steps, like profile-managemnents, driver tasks, etc
     */
    void performTasks() throws SQLException, ParseException, MessagingException, InterruptedException;
    /**
     * Performs basic operations like login, registration and password
     * recovery.
     */
    void profileManagementTasks() throws SQLException, ParseException, MessagingException, InterruptedException;

    /**
     * Performs drivers related tasks like start trip, rate customer, etc.
     */
    void performDriverTasks() throws SQLException, ParseException;
}
