package com.dal.cabby.driver;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;
import com.dal.cabby.profiles.Ratings;
import com.dal.cabby.util.Common;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Driver {
    DriverHelper driverHelper;
    int driverId = 1;
    private final Inputs inputs;

    public Driver(Inputs inputs) throws SQLException, ParseException {
        this.inputs = inputs;
        driverHelper = new DriverHelper();
        driverPage1();
    }

    private void driverPage1() throws SQLException, ParseException {
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

    public void login() throws SQLException, ParseException {
        System.out.println("Welcome to Driver login page");
        Login login = new Login(inputs);
        if (login.attemptLogin(UserType.DRIVER)) {
            System.out.println("Login successful");
            System.out.printf("LoggedID: %d, LoggedIn name: %s\n",
                    LoggedInProfile.getLoggedInId(), LoggedInProfile.getLoggedInName());
        } else {
            return;
        }
        page2();
    }

    public void register() {
        System.out.println("Welcome to Driver registration page");
        Registration registration = new Registration(inputs);
        registration.registerUser(UserType.DRIVER);
    }

    public void forgotPassword() {
        System.out.println("Welcome to Driver forgot password page");
        ForgotPassword forgotPassword = new ForgotPassword(inputs);
        forgotPassword.passwordUpdateProcess(UserType.DRIVER);
    }

    public void page2() throws SQLException, ParseException {
        while (true) {
            System.out.println("1. Start trip");
            System.out.println("2. View previous rides");
            System.out.println("3. View incomes");
            System.out.println("4. Rate customer for the trip:");
            System.out.println("5. Logout");
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    startTrip();
                    break;
                case 2:
                    viewIncomes();
                    break;
                case 3:
                    viewRides();
                    break;
                case 4:
                    rateCustomer();
                    break;
                case 5:
                    boolean isLogoutSuccessful = logout();
                    if (isLogoutSuccessful) {
                        return;
                    }
                    break;
            }
        }
    }

    private boolean logout() {
        return new Logout(inputs).logout();
    }

    private void startTrip() throws SQLException, ParseException {
        List<Booking> bookingsList = driverHelper.getUnfinishedBookingLists(driverId);
        if (bookingsList.size() == 0) {
            System.out.println("You have no new bookings\n");
            return;
        }
        System.out.println("List of bookings: ");
        for (Booking b : bookingsList) {
            System.out.printf("BookingId: %d, CustomerId: %d, Source: %s, Destination: %s, Travel-Time: %s\n",
                    b.getBookingId(), b.getCustomerId(), b.getSource(), b.getDestination(), b.getTravelTime());
        }
        System.out.println("Enter the bookingId for which you want to start the trip: ");
        int input = inputs.getIntegerInput();
        driverHelper.markBookingComplete(input);
        Common.simulateCabTrip();
        for (Booking b : bookingsList) {
            if (b.getBookingId() == input) {
                driverHelper.completeTrip(input, driverId, b.getCustomerId(), 5.6, 9.8,
                        "2021-01-24 12:35:16", "2021-01-24 12:55:16");
            }
        }
    }

    private void viewIncomes() {
        System.out.println("Incomes displayed");
    }

    private void viewRides() {
        System.out.println("Rides displayed");
    }

    private void rateCustomer() throws SQLException {
        System.out.println("Rating customer for the completed trip is " +
                "mandatory in the Cabby. It helps us to improve our services." +
                "Hence please rate the customer for the trips");
        System.out.println("Enter customer id:");
        int cust_id = inputs.getIntegerInput();
        System.out.println("Enter trip id:");
        int trip_id = inputs.getIntegerInput();
        System.out.println("Enter the rating between 1-5:");
        int rating = inputs.getIntegerInput();
        Ratings ratings = new Ratings();
        ratings.addCustomerRating(cust_id, trip_id, rating);
    }
}