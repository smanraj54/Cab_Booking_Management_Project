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

    boolean login() throws InterruptedException {
        System.out.println("Welcome to Admin login page");
        ILogin ILogin = new Login(inputs);
        if (ILogin.attemptLogin(UserType.ADMIN)) {
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
        IRegistration IRegistration = new Registration(inputs);
        return IRegistration.registerUser(UserType.ADMIN);
    }

    boolean forgotPassword() throws MessagingException, InterruptedException {
        System.out.println("Welcome to Admin forgot password page");
        IForgotPassword IForgotPassword = new ForgotPassword(inputs);
        return IForgotPassword.passwordUpdateProcess(UserType.ADMIN);
    }

    boolean logout() {
        return new Logout(inputs).logout();
    }
}
