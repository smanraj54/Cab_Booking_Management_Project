package com.dal.cabby.admin;

import com.dal.cabby.user.User;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

/*
This interface acts as presentation layer for the Admin user.
 */
public interface IAdmin extends User {
    /**
     * Performs admin related tasks like approve profile, regesiter profiles, etc.
     */
    void performAdminTasks() throws SQLException, ParseException;
}
