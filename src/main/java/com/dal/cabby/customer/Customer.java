package com.dal.cabby.customer;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.util.Common;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

public class Customer implements ICustomer {
    private final Inputs inputs;
    private CustomerTasks customerTasks;
    private CustomerProfileManagement customerProfileManagement;
    private DBHelper dbHelper;

    public Customer(Inputs inputs, DBHelper dbHelper) throws SQLException {
        this.inputs = inputs;
        this.dbHelper = dbHelper;
        initialize();
    }

    private void initialize() {
        customerTasks = new CustomerTasks(inputs, dbHelper);
        customerProfileManagement = new CustomerProfileManagement(inputs);
    }

    @Override
    public void performTasks() throws SQLException, ParseException, MessagingException, InterruptedException {
        profileManagementTasks();
    }

    @Override
    public void profileManagementTasks() throws SQLException, ParseException, MessagingException, InterruptedException {
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
    public void performCustomerTasks() throws SQLException {
        while (true) {
            System.out.println("1. Book Cabs");
            System.out.println("2. View previous Rides");
            System.out.println("3. Rate driver for the trip");
            System.out.println("4. View your current rating");
            System.out.println("5. Buy Coupons");
            System.out.println("6. Logout");
            System.out.println("7. Cancel booking");
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
                    customerTasks.buyCoupons();
                    break;
                case 6:
                    if (customerProfileManagement.logout()) {
                        return;
                    }
                    break;
                case  7:
                    customerTasks.cancelBooking();
                    break;
                default:
                    System.out.println("\nInvalid Input");
                    break;
            }
        }
    }
}
