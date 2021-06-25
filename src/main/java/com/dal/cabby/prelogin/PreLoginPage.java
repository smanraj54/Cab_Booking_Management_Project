package com.dal.cabby.prelogin;

import java.util.Scanner;

// Prelogin page when user visit the Cabby app
public class PreLoginPage {
    /* Starting point of Prelogin page. */
    public void start() {
        welcomeMessage();
        showOptions();
    }

    /* Show welcome message when user vigit Cabby app page */
    public void welcomeMessage() {
        System.out.println("***** Cabby App: A one stop app for your cab booking *****\n");
    }

    /* Show three options: Login, Registration and Reset password */
    public void showOptions() {
        System.out.println("\nPlease enter one of the options:");
        System.out.println("1: Login\n2:New user registration\n3: Forgot password?\n");
        int inputByUser = new Scanner(System.in).nextInt();
        System.out.println("\n\nThanks for your input:" + inputByUser +", the feature is yet to be implemented.");
    }
}
