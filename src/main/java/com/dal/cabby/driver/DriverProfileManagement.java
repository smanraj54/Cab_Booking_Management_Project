package com.dal.cabby.driver;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;

import javax.mail.MessagingException;

public class DriverProfileManagement {

    private final DriverHelper driverHelper;
    private final Inputs inputs;

    public DriverProfileManagement(DriverHelper driverHelper, Inputs inputs) {
        this.driverHelper = driverHelper;
        this.inputs = inputs;
    }

    public boolean login() {
        System.out.println("Welcome to Driver login page");
        Login login = new Login(inputs);
        if (login.attemptLogin(UserType.DRIVER)) {
            System.out.println("Login successful");
            System.out.printf("LoggedID: %d, LoggedIn name: %s\n",
                    LoggedInProfile.getLoggedInId(), LoggedInProfile.getLoggedInName());
            return true;
        } else {
            return false;
        }
    }

    public boolean register() {
        System.out.println("Welcome to Driver registration page");
        Registration registration = new Registration(inputs);
        return registration.registerUser(UserType.DRIVER);
    }

    public boolean forgotPassword() throws MessagingException, InterruptedException {
        System.out.println("Welcome to Driver forgot password page");
        ForgotPassword forgotPassword = new ForgotPassword(inputs);
        return forgotPassword.passwordUpdateProcess(UserType.DRIVER);
    }

    boolean logout() {
        return new Logout(inputs).logout();
    }
}
