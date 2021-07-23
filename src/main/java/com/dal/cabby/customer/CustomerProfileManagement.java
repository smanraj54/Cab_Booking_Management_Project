package com.dal.cabby.customer;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;

import javax.mail.MessagingException;

class CustomerProfileManagement {
    final private Inputs inputs;

    public CustomerProfileManagement(Inputs inputs) {
        this.inputs = inputs;
    }

    boolean login() throws InterruptedException {
        System.out.println("Welcome to Customer login page");
        ILogin ILogin = new Login(inputs);
        if (ILogin.attemptLogin(UserType.CUSTOMER)) {
            System.out.println("Login successful");
            System.out.printf("LoggedID: %d, LoggedIn name: %s\n",
                    LoggedInProfile.getLoggedInId(), LoggedInProfile.getLoggedInName());
            return true;
        } else {
            return false;
        }
    }

    boolean register() {
        System.out.println("Welcome to Customer registration page");
        IRegistration IRegistration = new Registration(inputs);
        return IRegistration.registerUser(UserType.CUSTOMER);
    }

    boolean forgotPassword() throws MessagingException, InterruptedException {
        System.out.println("Welcome to Customer forgot password page");
        IForgotPassword IForgotPassword = new ForgotPassword(inputs);
        return IForgotPassword.passwordUpdateProcess(UserType.CUSTOMER);
    }

    boolean logout() {
        return new Logout(inputs).logout();
    }
}
