package com.dal.cabby.prelogin;

import com.dal.cabby.admin.Admin;
import com.dal.cabby.customer.Customer;
import com.dal.cabby.driver.Driver;
import com.dal.cabby.util.Common;

import java.util.Scanner;

// Prelogin page when user visit the Cabby app
public class PreLoginPage {
    /* Starting point of Prelogin page. */
    public void start() {
        welcomeMessage();
        page1();
    }

    /* Show welcome message when user vigit Cabby app page */
    public void welcomeMessage() {
        System.out.println("***** Cabby App: A one stop app for your cab booking *****\n");
    }
    public void page1() {
        System.out.println("Are you: \n1: Admin\n2: Driver\n3: Customer");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        switch (input) {
            case 1:
                Admin admin = new Admin();
                break;
            case 2:
                Driver driver = new Driver();
                break;
            case 3:
                Customer customer = new Customer();
                break;
            default:
                System.out.println("Invalid input: " + input);
                return;
        }
    }
}
