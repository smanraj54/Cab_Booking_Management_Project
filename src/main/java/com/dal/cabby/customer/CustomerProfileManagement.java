package com.dal.cabby.customer;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;

public class CustomerProfileManagement {
    final private Inputs inputs;

    public CustomerProfileManagement(Inputs inputs) {
        this.inputs = inputs;
    }

    boolean login() {
        System.out.println("Welcome to Customer login page");
        Login login = new Login(inputs);
        if (login.attemptLogin(UserType.CUSTOMER)) {
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
        Registration registration = new Registration(inputs);
        return registration.registerUser(UserType.CUSTOMER);
    }

    boolean forgotPassword() {
        System.out.println("Welcome to Customer forgot password page");
        ForgotPassword forgotPassword = new ForgotPassword(inputs);
        return forgotPassword.passwordUpdateProcess(UserType.CUSTOMER);
    }

    boolean logout() {
        return new Logout(inputs).logout();
    }
}
