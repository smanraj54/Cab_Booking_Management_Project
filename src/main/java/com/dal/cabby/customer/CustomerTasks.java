package com.dal.cabby.customer;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.money.BuyCoupons;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.LoggedInProfile;
import com.dal.cabby.rating.IRatings;
import com.dal.cabby.rating.Ratings;
import com.dal.cabby.rides.DisplayRides;

import java.sql.SQLException;

public class CustomerTasks {
    private final Inputs inputs;

    public CustomerTasks(Inputs inputs) {
        this.inputs = inputs;
    }

    void rateDriver() throws SQLException {
        System.out.println("Rating driver for the completed trip is " +
                "mandatory in the Cabby. It helps us to improve our services." +
                "Hence please rate the driver for the trips");
        System.out.println("Enter driver id:");
        int driver_id = inputs.getIntegerInput();
        System.out.println("Enter trip id:");
        int trip_id = inputs.getIntegerInput();
        System.out.println("Enter the rating between 1-5:");
        int rating = inputs.getIntegerInput();
        IRatings IRatings = new Ratings();
        IRatings.addCustomerRating(driver_id, trip_id, rating);
    }

    void bookRides() {
        System.out.println("Ride Booked");
    }

    void showRides() throws SQLException {
        DisplayRides rides = new DisplayRides(inputs);
        rides.getRides(UserType.CUSTOMER, LoggedInProfile.getLoggedInId());
    }

    void viewRatings() {
        System.out.println("You current rating is: <NA>");
    }

    void buyCoupons() throws SQLException {
        BuyCoupons coupons = new BuyCoupons(inputs);
        coupons.getCoupons(LoggedInProfile.getLoggedInId(), UserType.CUSTOMER);
    }
}
