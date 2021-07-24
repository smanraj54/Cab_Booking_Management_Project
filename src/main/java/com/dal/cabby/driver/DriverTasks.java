package com.dal.cabby.driver;

import com.dal.cabby.booking.BookingService;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.money.BuyCoupons;
import com.dal.cabby.money.DriverEarnings;
import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.LoggedInProfile;
import com.dal.cabby.rating.IRatings;
import com.dal.cabby.rating.Ratings;
import com.dal.cabby.rides.DisplayRides;
import com.dal.cabby.util.Common;
import com.dal.cabby.util.ConsolePrinter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

class DriverTasks {
    private final Inputs inputs;
    private IRatings iRatings;
    private BookingService bookingService;

    public DriverTasks(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        iRatings = new Ratings();
        bookingService = new BookingService();
    }

    void startTrip() throws SQLException, ParseException {
        List<Booking> bookingsList = bookingService.getDriverOpenBookings(LoggedInProfile.getLoggedInId());
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
        bookingService.markBookingComplete(input);
        Common.simulateCabTrip();
        for (Booking b : bookingsList) {
            if (b.getBookingId() == input) {
                bookingService.completeTrip(input, LoggedInProfile.getLoggedInId(), b.getCustomerId(), 5.6, 9.8,
                        "2021-01-24 12:35:16", "2021-01-24 12:55:16");
            }
        }
    }

    void viewRides() throws SQLException {
        DisplayRides displayRides = new DisplayRides(inputs);
        List<String> rides = displayRides.getRides(UserType.DRIVER, LoggedInProfile.getLoggedInId());
        System.out.println();
        for (String ride : rides) {
            System.out.println(ride);
        }
    }

    void viewIncomes() throws SQLException {
        DriverEarnings driverEarnings = new DriverEarnings(inputs);
        driverEarnings.getEarnings(LoggedInProfile.getLoggedInId());
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
        IRatings IRatings = new Ratings();
        IRatings.addCustomerRating(cust_id, trip_id, rating);
    }

    void viewRatings() throws SQLException {
        double avgRating = iRatings.getAverageRatingOfDriver(LoggedInProfile.getLoggedInId());
        if (avgRating == 0) {
            System.out.println("You don't have any rating at the moment");
            return;
        }
        System.out.println("Your average rating is: " + avgRating);
    }

    void buyCoupons() throws SQLException {
        BuyCoupons buyCoupons = new BuyCoupons(inputs);
        buyCoupons.getCoupons(LoggedInProfile.getLoggedInId(), UserType.DRIVER);
    }

    void cancelBooking() throws SQLException {
        BookingService bookingService = new BookingService();
        List<Booking> bookingList = bookingService.getDriverOpenBookings(LoggedInProfile.getLoggedInId());
        if (bookingList.size() == 0) {
            ConsolePrinter.printOutput("You have no booking to cancel.");
            return;
        }
        System.out.println("These are new bookings:");
        for (Booking booking : bookingList) {
            System.out.printf("BookingId: %d, Source: %s, Destination: %s\n",
                    booking.getBookingId(), booking.getSource(), booking.getDestination());
        }
        System.out.println("Now, select the booking-id you want to cancel:");
        int bookingId = inputs.getIntegerInput();
        bookingService.cancelBooking(bookingId, UserType.DRIVER);
        ConsolePrinter.printSuccessMsg(
                String.format("Your booking with bookingId: %d is cancelled", bookingId));
    }
}
