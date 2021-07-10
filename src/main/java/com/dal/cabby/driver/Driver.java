package com.dal.cabby.driver;

import com.dal.cabby.pojo.Booking;
import com.dal.cabby.profileManagement.Logout;
import com.dal.cabby.util.Common;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Driver {
    DriverHelper driverHelper;
    int driverId = 1;

    public Driver() throws SQLException, ParseException {
        driverHelper = new DriverHelper();
        driverPage1();
    }

    private void driverPage1() throws SQLException, ParseException {
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

    public void login() throws SQLException, ParseException {
        // Call login method.
        System.out.println("Login successful.");
        page2();
    }

    public void register() {
        System.out.println("Welcome to Driver registration page");
        System.out.println("Feature not implemented yet.");
    }

    public void forgotPassword() {
        System.out.println("Welcome to Driver forgot password page");
        System.out.println("Feature not implemented yet.");
    }

    public void page2() throws SQLException, ParseException {
        while (true) {
            System.out.println("1. Start trip");
            System.out.println("2. View previous rides");
            System.out.println("3. View incomes");
            System.out.println("4. Rate customer for the trip:");
            System.out.println("5. Logout");
            int input = getInput();
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
                default:
                    logout();
                    break;
            }
        }
    }

    private void logout() {
        new Logout().logout();
    }

    private int getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
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
        int input = getInput();
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

    private void rateCustomer() {
        System.out.println("Rating customer for the completed trip is " +
                "mandatory in the Cabby. It helps us to improve our services." +
                "Hence please rate the customer for the trips");
        System.out.println("Enter customer id:");
        int cust_id = getInput();
    }
}
