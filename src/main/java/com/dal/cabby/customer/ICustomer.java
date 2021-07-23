package com.dal.cabby.customer;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

public interface ICustomer {
    /**
     * Consists of list of steps, like profile-managemnents, customer tasks, etc
     */
    void performTasks() throws SQLException, ParseException, MessagingException, InterruptedException;
    /**
     * Performs basic operations like login, registration and password
     * recovery.
     */
    void profileManagementTasks() throws SQLException, ParseException, MessagingException, InterruptedException;

    /**
     * Performs customer related tasks like book cab, rate customer, etc.
     */
    void performCustomerTasks() throws SQLException, ParseException;
}
