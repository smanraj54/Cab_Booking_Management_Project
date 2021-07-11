package com.dal.cabby;

import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.prelogin.PreLoginPage;
import com.dal.cabby.profileManagement.Login;
import com.dal.cabby.profileManagement.Registration;

// Main starting class
public class Application {
    public static void main(String[] args) {
        Inputs inputs = new InputFromUser();
        PreLoginPage preLoginPage = new PreLoginPage(inputs);
        preLoginPage.start();
    }
}
