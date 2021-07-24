package com.dal.cabby.customer;

import com.dal.cabby.booking.BookingService;
import com.dal.cabby.cabSelection.CabSelectionService;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.incentives.DriverBonus;
import com.dal.cabby.incentives.IDriverBonus;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.money.BuyCoupons;
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
import java.util.Date;
import java.util.List;

class CustomerTasks {
    private final Inputs inputs;
    private final IPersistence iPersistence;
    private final IRatings iRatings;

    public CustomerTasks(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        this.iPersistence = DBHelper.getInstance();
        iRatings = new Ratings();
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
        iRatings.addCustomerRating(driver_id, trip_id, rating);

        IDriverBonus driverBonus = new DriverBonus();
        int bonus = driverBonus.giveDriverBonus(rating);
        ConsolePrinter.printSuccessMsg(String.format("Driver with id: %d is eligible for bonus of %d percentage",
                driver_id, bonus));
    }

    void bookRides() throws SQLException, ParseException {
        int custId = LoggedInProfile.getLoggedInId();
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        System.out.println("Select travel time(MM/dd/yyyy HH:mm):");
        String travelTime = inputs.getStringInput();
        double hour = 0.0;
        try {
            Date date = Common.parseDate(travelTime, "MM/dd/yyyy HH:mm");
            hour = date.getHours();
            System.out.println("Awesome, Your travel date and time is " + date.toLocaleString());
        } catch (Exception e) {
            ConsolePrinter.printErrorMsg(e.getMessage());
            ConsolePrinter.printErrorMsg("Your cab booking failed due to wrong date entered. Please try again");
            return;
        }
        Booking booking = cabSelectionService.preferredCab(custId, hour);
        booking.setCustomerId(custId);
        booking.setTravelTime(travelTime);
        BookingService bookingService = new BookingService();
        bookingService.saveBooking(booking);
        ConsolePrinter.printSuccessMsg("Congratulations!. Your booking is confirmed!");
        String bookingDetails = String.format("Booking details: Source: %s , Destination: %s , Travel time: %s, Fare: %f",
                booking.getSource(), booking.getDestination(), booking.getTravelTime(), booking.getPrice());
        ConsolePrinter.printOutput(bookingDetails);
    }

    void cancelBooking() throws SQLException {
        BookingService bookingService = new BookingService();
        Booking booking = bookingService.getCustomerOpenBooking(LoggedInProfile.getLoggedInId());
        if (booking == null) {
            ConsolePrinter.printOutput("You have no booking to cancel.");
            return;
        }
        System.out.println("Do you want to cancel this booking?(y/n)");
        String bookingDetails = String.format("Booking details: Source: %s , Destination: %s , Travel time: %s, Fare: %f",
                booking.getSource(), booking.getDestination(), booking.getTravelTime(), booking.getPrice());
        ConsolePrinter.printOutput(bookingDetails);
        String input = inputs.getStringInput();
        if (!input.equalsIgnoreCase("y")) {
            return;
        }
        bookingService.cancelBooking(booking.getBookingId(), UserType.CUSTOMER);
        ConsolePrinter.printSuccessMsg("Your booking is cancelled successfully");
    }

    void showRides() throws SQLException {
        DisplayRides displayRides = new DisplayRides(inputs);
        List<String> rides = displayRides.getRides(UserType.CUSTOMER, LoggedInProfile.getLoggedInId());
        System.out.println();
        for (String ride : rides) {
            System.out.println(ride);
        }
    }

    void viewRatings() throws SQLException {
        double avgRating = iRatings.getAverageRatingOfCustomer(LoggedInProfile.getLoggedInId());
        if (avgRating == 0) {
            System.out.println("You don't have any rating at the moment");
            return;
        }
        System.out.println("Your average rating is: " + avgRating);
    }

    void buyCoupons() throws SQLException {
        BuyCoupons coupons = new BuyCoupons(inputs);
        System.out.println(coupons.getCoupons(LoggedInProfile.getLoggedInId(), UserType.CUSTOMER));
    }
}
