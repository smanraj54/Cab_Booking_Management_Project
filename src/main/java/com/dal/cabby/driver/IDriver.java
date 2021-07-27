package com.dal.cabby.driver;

import com.dal.cabby.user.User;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * This interface acts as Presentation layer for the Driver layer.
 */
public interface IDriver extends User {
    /**
     * Performs drivers related tasks like start trip, rate customer, etc.
     */
    void performDriverTasks() throws SQLException, ParseException;
}
