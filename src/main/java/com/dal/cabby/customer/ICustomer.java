package com.dal.cabby.customer;

import com.dal.cabby.user.User;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

public interface ICustomer extends User {
    /**
     * Performs customer related tasks like book cab, rate customer, etc.
     */
    void performCustomerTasks() throws SQLException, ParseException;
}
