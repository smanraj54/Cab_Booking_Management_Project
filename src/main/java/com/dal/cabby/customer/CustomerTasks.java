package com.dal.cabby.customer;

import com.dal.cabby.booking.BookingService;
import com.dal.cabby.cabSelection.CabSelectionService;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.money.BuyCoupons;
import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.LoggedInProfile;
import com.dal.cabby.rating.IRatings;
import com.dal.cabby.rating.Ratings;
import com.dal.cabby.rides.DisplayRides;

import java.sql.SQLException;

public class CustomerTasks {
    private final Inputs inputs;
    IPersistence iPersistence;

    public CustomerTasks(Inputs inputs, IPersistence iPersistence) {
        this.inputs = inputs;
        this.iPersistence = iPersistence;
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

    void bookRides() throws SQLException {
        int custId = LoggedInProfile.getLoggedInId();
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        System.out.println("Select travel time:");
        String travelTime = inputs.getStringInput();
        //0 to 23:59
        double hour = 13;
        Booking booking = cabSelectionService.preferredCab(custId, hour);
        booking.setCustomerId(custId);
        booking.setTravelTime(travelTime);
        BookingService bookingService = new BookingService();
        bookingService.saveBooking(booking);
    }

    void cancelBooking() throws SQLException {
        BookingService bookingService = new BookingService();
        Booking booking = bookingService.getCustomerOpenBooking(LoggedInProfile.getLoggedInId());
        if (booking == null) {
            System.out.println("You have no booking to cancel.");
            return;
        }
        bookingService.cancelBooking(booking.getBookingId(), UserType.CUSTOMER);
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
