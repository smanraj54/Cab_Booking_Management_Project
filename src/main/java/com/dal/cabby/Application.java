package com.dal.cabby;

import com.dal.cabby.prelogin.PreLoginPage;
import com.dal.cabby.profileManagement.Registration;

// Main starting class
public class Application {
    public static void main(String[] args) {
//        PreLoginPage preLoginPage = new PreLoginPage();
//        preLoginPage.start();

        Registration registration = new Registration();
        registration.RegisterUser();
    }
}
