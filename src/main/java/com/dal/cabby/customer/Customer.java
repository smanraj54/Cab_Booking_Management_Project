package com.dal.cabby.customer;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;
import com.dal.cabby.profiles.Ratings;
import com.dal.cabby.rides.DisplayRides;
import com.dal.cabby.util.Common;

import java.sql.SQLException;
import java.text.ParseException;

public class Customer implements ICustomer {
    private final Inputs inputs;
    private CustomerTasks customerTasks;
    private CustomerProfileManagement customerProfileManagement;

    public Customer(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        initialize();
    }

    private void initialize() {
        customerTasks = new CustomerTasks(inputs);
        customerProfileManagement = new CustomerProfileManagement(inputs);
    }

    @Override
    public void performTasks() throws SQLException, ParseException {
        profileManagementTasks();
    }

    @Override
    public void profileManagementTasks() throws SQLException, ParseException {
        while (true) {
            Common.page1Options();
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    boolean isLoginSuccessful = customerProfileManagement.login();
                    if (isLoginSuccessful) {
                        System.out.println("Login successful");
                        performCustomerTasks();
                    }
                    break;
                case 2:
                    boolean isRegistered = customerProfileManagement.register();
                    if(!isRegistered) {
                        System.out.println("Registration failed!");
                    }
                    break;
                case 3:
                    boolean recoveryStatus = customerProfileManagement.forgotPassword();
                    if(recoveryStatus) {
                        System.out.println("Password reset successful. Please login with new credentials");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input: " + input);
            }
        }
    }

    @Override
    public void performCustomerTasks() throws SQLException, ParseException {
        while (true) {
            System.out.println("1. Book Cabs");
            System.out.println("2. View previous Rides");
            System.out.println("3. Rate driver for the trip");
            System.out.println("4. View your current rating");
            System.out.println("5. Logout");
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    customerTasks.bookRides();
                    break;
                case 2:
                    customerTasks.showRides();
                    break;
                case 3:
                    customerTasks.rateDriver();
                    break;
                case 4:
                    customerTasks.viewRatings();
                    break;
                case 5:
                    if (customerProfileManagement.logout()) {
                        return;
                    }
                    break;
                default:
                    System.out.println("\nInvalid Input");
                    break;
            }
        }
    }
}
