package com.dal.cabby.customer;

import com.dal.cabby.booking.BookingService;
import com.dal.cabby.booking.IBookingService;
import com.dal.cabby.cabSelection.CabSelection;
import com.dal.cabby.incentives.DriverBonus;
import com.dal.cabby.incentives.IDriverBonus;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.money.BuyCouponsPage;
import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;
import com.dal.cabby.rating.IRatings;
import com.dal.cabby.rating.Ratings;
import com.dal.cabby.rides.DisplayRidesPage;
import com.dal.cabby.rides.IDisplayRidesPage;
import com.dal.cabby.score.CancellationScorer;
import com.dal.cabby.util.Common;
import com.dal.cabby.util.ConsolePrinter;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

class CustomerBusinessLayer {
    private final Inputs inputs;
    private double initialScore = 4.0;
    private final IRatings iRatings;

    public CustomerBusinessLayer(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        iRatings = new Ratings();
    }

    boolean login() throws InterruptedException {
        System.out.println("Welcome to Customer login page");
        ILogin ILogin = new Login(inputs);
        if (ILogin.attemptLogin(UserType.CUSTOMER)) {
            System.out.println("Login successful");
            System.out.printf("LoggedID: %d, LoggedIn name: %s\n",
                    LoggedInProfile.getLoggedInId(), LoggedInProfile.getLoggedInName());
            return true;
        } else {
            return false;
        }
    }

    boolean register() {
        System.out.println("Welcome to Customer registration page");
        IRegistration IRegistration = new Registration(inputs);
        return IRegistration.registerUser(UserType.CUSTOMER);
    }

    boolean forgotPassword() throws MessagingException, InterruptedException {
        System.out.println("Welcome to Customer forgot password page");
        IForgotPassword IForgotPassword = new ForgotPassword(inputs);
        return IForgotPassword.passwordUpdateProcess(UserType.CUSTOMER);
    }

    boolean logout() {
        return new Logout(inputs).logout();
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
        CabSelection cabSelection = new CabSelection(inputs);
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
        Booking booking = cabSelection.preferredCab(custId, hour);
        booking.setCustomerId(custId);
        booking.setTravelTime(travelTime);
        IBookingService iBookingService = new BookingService();
        iBookingService.saveBooking(booking);
        ConsolePrinter.printSuccessMsg("Congratulations!. Your booking is confirmed!");
        String bookingDetails = String.format("Booking details: Source: %s , Destination: %s , Travel time: %s, Fare: %f",
                booking.getSource(), booking.getDestination(), booking.getTravelTime(), booking.getPrice());
        ConsolePrinter.printOutput(bookingDetails);
    }

    void cancelBooking() throws SQLException {
        IBookingService iBookingService = new BookingService();
        Booking booking = iBookingService.getCustomerOpenBooking(LoggedInProfile.getLoggedInId());
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
        iBookingService.cancelBooking(booking.getBookingId(), UserType.CUSTOMER);
        ConsolePrinter.printSuccessMsg("Your booking is cancelled successfully");
        CancellationScorer cancellationScorer = new CancellationScorer();
        cancellationScorer.customerCancelled(initialScore, false, true);
    }

    void viewUpcomingTrip() throws SQLException {
        IBookingService iBookingService = new BookingService();
        Booking booking = iBookingService.getCustomerOpenBooking(LoggedInProfile.getLoggedInId());
        if (booking == null) {
            ConsolePrinter.printOutput("You have no booking. Please book your trip");
            return;
        }
        System.out.println("Please find below your upcoming trip:");
        String bookingDetails = String.format("Booking details: Source: %s , Destination: %s , Travel time: %s, Fare: %f",
                booking.getSource(), booking.getDestination(), booking.getTravelTime(), booking.getPrice());
        ConsolePrinter.printOutput(bookingDetails);
    }

    void showRides() throws SQLException {
        IDisplayRidesPage ridesPage = new DisplayRidesPage();
        ridesPage.ridesOptions(LoggedInProfile.getLoggedInId(), UserType.CUSTOMER);
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
        BuyCouponsPage couponsPage = new BuyCouponsPage();
        couponsPage.couponsPage(LoggedInProfile.getLoggedInId(), UserType.CUSTOMER);
    }
}
