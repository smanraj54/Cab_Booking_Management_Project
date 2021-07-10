package com.dal.cabby;

import com.dal.cabby.profileManagement.DB_Operations;
import com.dal.cabby.profileManagement.ForgotPassword;

import java.util.Scanner;

// Main starting class
public class Application {
    public static void main(String[] args) {
//        PreLoginPage preLoginPage = new PreLoginPage();
//        preLoginPage.start();

//        Login login = new Login();
//        login.attemptLogin();
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.passwordUpdateProcess(new Scanner(System.in), new DB_Operations());
    }
}
