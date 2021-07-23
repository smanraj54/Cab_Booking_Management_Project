package com.dal.cabby.admin;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class AdminProfileManagement {
    private final AdminHelper adminHelper;
    private final Inputs inputs;

    public AdminProfileManagement(AdminHelper adminHelper, Inputs inputs) {
        this.adminHelper = adminHelper;
        this.inputs = inputs;
    }

    boolean login() throws SQLException {
        System.out.println("Welcome to Admin login page");
        Login login = new Login(inputs);
        if (login.attemptLogin(UserType.ADMIN)) {
            System.out.println("Login successful");
            System.out.printf("LoggedID: %d, LoggedIn name: %s\n",
                    LoggedInProfile.getLoggedInId(), LoggedInProfile.getLoggedInName());
            return true;
        } else {
            return false;
        }
    }

    boolean register() {
        System.out.println("Welcome to Admin registration page");
        Registration registration = new Registration(inputs);
        return registration.registerUser(UserType.ADMIN);
    }

    boolean forgotPassword() throws MessagingException, InterruptedException {
        System.out.println("Welcome to Admin forgot password page");
        ForgotPassword forgotPassword = new ForgotPassword(inputs);
        return forgotPassword.passwordUpdateProcess(UserType.ADMIN);
    }

    boolean logout() {
        return new Logout(inputs).logout();
    }
}
