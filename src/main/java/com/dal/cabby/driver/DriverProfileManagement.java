package com.dal.cabby.driver;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;

import javax.mail.MessagingException;

class DriverProfileManagement {

    private final DriverHelper driverHelper;
    private final Inputs inputs;

    public DriverProfileManagement(DriverHelper driverHelper, Inputs inputs) {
        this.driverHelper = driverHelper;
        this.inputs = inputs;
    }

    public boolean login() throws InterruptedException {
        System.out.println("Welcome to Driver login page");
        ILogin ILogin = new Login(inputs);
        if (ILogin.attemptLogin(UserType.DRIVER)) {
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
        IRegistration IRegistration = new Registration(inputs);
        return IRegistration.registerUser(UserType.DRIVER);
    }

    public boolean forgotPassword() throws MessagingException, InterruptedException {
        System.out.println("Welcome to Driver forgot password page");
        IForgotPassword IForgotPassword = new ForgotPassword(inputs);
        return IForgotPassword.passwordUpdateProcess(UserType.DRIVER);
    }

    boolean logout() {
        return new Logout(inputs).logout();
    }
}
