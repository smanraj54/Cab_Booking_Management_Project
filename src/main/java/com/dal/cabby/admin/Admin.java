package com.dal.cabby.admin;

import com.dal.cabby.util.Common;

import java.util.Scanner;

public class Admin {

    public Admin() {
        adminPage1();
    }

    private void adminPage1() {
        int input = Common.page1Options();

        switch (input) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                forgotPassword();
                break;
            default:
                System.out.println("Invalid input: " + input);
                return;
        }
    }

    public void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Admin login page. Please enter username and password.");
        System.out.println("Username: ");
        String userName = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();
        if (!userName.equals("root") || !password.equals("root123")) {
            System.out.println("Invalid username or password. Please retry");
            return;
        }
        System.out.println("Login successful.");
    }

    public void register() {
        System.out.println("Welcome to Admin registration page");
        System.out.println("Feature not implemented yet.");
    }

    public void forgotPassword() {
        System.out.println("Welcome to Admin forgot password page");
        System.out.println("Feature not implemented yet.");
    }
}
