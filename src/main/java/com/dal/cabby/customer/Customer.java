package com.dal.cabby.customer;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.ForgotPassword;
import com.dal.cabby.profileManagement.Login;
import com.dal.cabby.profileManagement.Registration;
import com.dal.cabby.profiles.Ratings;
import com.dal.cabby.util.Common;

import java.sql.SQLException;

public class Customer {
    private final Inputs inputs;
    public Customer(Inputs inputs) {
        this.inputs = inputs;
        customerPage1();
    }

    private void customerPage1() {
        Common.page1Options();
        int input = inputs.getIntegerInput();
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
        System.out.println("Welcome to Customer login page");
        Login login = new Login(inputs);
        login.attemptLogin(UserType.CUSTOMER);
        System.out.println("Login successful.");
    }

    public void register() {
        System.out.println("Welcome to Customer registration page");
        Registration registration = new Registration(inputs);
        registration.registerUser(UserType.CUSTOMER);
    }

    public void forgotPassword() {
        System.out.println("Welcome to Customer forgot password page");
        ForgotPassword forgotPassword = new ForgotPassword(inputs);
        forgotPassword.passwordUpdateProcess(UserType.CUSTOMER);
    }

    private void rateDriver() throws SQLException {
        System.out.println("Rating driver for the completed trip is " +
                "mandatory in the Cabby. It helps us to improve our services." +
                "Hence please rate the driver for the trips");
        System.out.println("Enter driver id:");
        int driver_id = inputs.getIntegerInput();
        System.out.println("Enter trip id:");
        int trip_id = inputs.getIntegerInput();
        System.out.println("Enter the rating between 1-5:");
        int rating = inputs.getIntegerInput();
        Ratings ratings = new Ratings();
        ratings.addCustomerRating(driver_id, trip_id, rating);
    }
}
