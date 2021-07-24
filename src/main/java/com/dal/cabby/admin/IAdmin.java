package com.dal.cabby.admin;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

public interface IAdmin {
    /**
     * Consists of list of steps, like profile-managemnents, admin tasks, etc
     */
    void performTasks() throws SQLException, ParseException, MessagingException, InterruptedException;

    /**
     * Performs basic operations like login, registration and password
     * recovery.
     */
    void profileManagementTasks() throws SQLException, ParseException, MessagingException, InterruptedException;

    /**
     * Performs admin related tasks like approve profile, regesiter profiles, etc.
     */
    void performAdminTasks() throws SQLException, ParseException;
}
