package com.dal.cabby.driver;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Booking;
import com.dal.cabby.profileManagement.LoggedInProfile;
import com.dal.cabby.profiles.Ratings;
import com.dal.cabby.util.Common;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class DriverTasks {
    private DriverHelper driverHelper;
    private Inputs inputs;
    private int driverId;

    public DriverTasks(DriverHelper driverHelper, Inputs inputs) {
        this.driverHelper = driverHelper;
        this.inputs = inputs;
        driverId = LoggedInProfile.getLoggedInId();
    }

    void startTrip() throws SQLException, ParseException {
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

    void viewIncomes() {
        System.out.println("Incomes displayed");
    }

    void viewRides() {
        System.out.println("Rides displayed");
    }

    void rateCustomer() throws SQLException {
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
