package com.dal.cabby;

import com.dal.cabby.prelogin.PreLoginPage;
import com.dal.cabby.profileManagement.Login;
import com.dal.cabby.profileManagement.Registration;

// Main starting class
public class Application {
    public static void main(String[] args) {
//        PreLoginPage preLoginPage = new PreLoginPage();
//        preLoginPage.start();

        Login login = new Login();
        login.attemptLogin();
    }
}
