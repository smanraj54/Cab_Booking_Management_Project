package com.dal.cabby.rides;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.util.DateOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class displays the rides completed by user (Driver and Customer). This
 * class provides the option to display daily rides, monthly rides, and rides
 * between specific period.
 */
public class DisplayRides {
    IPersistence iPersistence;
    Inputs inputs;
    DateOperations dateOperations;
    private UserType requesterType;
    private int requesterID;

    /**
     * Constructor of class DisplayRides
     */
    public DisplayRides(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        dateOperations = new DateOperations();
        iPersistence = DBHelper.getInstance();
    }

    /**
     * This method will return the list of rides.
     * Parameters:
     * userType - whether the user is driver or customer
     * userID - the id of the user
     * Returns:
     * list of rides
     */
    public List<String> getRides(UserType userType, int userID) throws SQLException {
        requesterType = userType;
        requesterID = userID;
        return rides();
    }

    /**
     * This method provides the display options to select from and returns
     * the appropriate result
     * Returns:
     * list of rides
     */
    private List<String> rides() throws SQLException {
        List<String> totalRides = new ArrayList<>();
        System.out.println("\n**** Rides Page ****");
        System.out.println("1. Daily rides");
        System.out.println("2. Monthly rides");
        System.out.println("3. Rides between a specific period");
        System.out.println("4. Return to the previous page");
        System.out.print("Please enter a selection: ");
        int selection = inputs.getIntegerInput();
        switch (selection) {
            case 1:
                return getDailyRides();
            case 2:
                return getMonthlyRides();
            case 3:
                return getSpecificPeriodRides();
            case 4:
                return totalRides;
            default:
                return Collections.singletonList("Invalid selection");
        }
    }

    /**
     * This method ask the user to enter the date and return the ride
     * details.
     * Returns:
     * list of completed rides on a particular day
     */
    private List<String> getDailyRides() throws SQLException {
        System.out.print("Enter the date in DD/MM/YYYY format: ");
        String inputDate = inputs.getStringInput().trim();
        if (!dateOperations.validateDate(inputDate)) {
            return Collections.singletonList("Invalid Input");
        } else {
            String date = dateOperations.getFormattedDate(inputDate);
            return getRidesFromDb(date, date, requesterType, requesterID);
        }
    }

    /**
     * This method ask the user to enter the month details and return the
     * ride details.
     * Returns:
     * list of completed rides in that particular month
     */
    private List<String> getMonthlyRides() throws SQLException {
        System.out.print("Enter the month in MM/YYYY format: ");
        String input = inputs.getStringInput().trim();
        if (input.length() == 7 && input.indexOf("/") == 2) {
            String[] splitInput = input.split("/");
            String month = splitInput[0];
            String year = splitInput[1];
            String startDate = year + "-" + month + "-01";
            String endDate = dateOperations.getLastDay(startDate);
            return getRidesFromDb(startDate, endDate, requesterType, requesterID);
        } else {
            return Collections.singletonList("Invalid Input");
        }
    }

    /**
     * This method ask the user to enter the start date and end date
     * and return the ride details.
     * Returns:
     * list of completed rides between start date and end date
     */
    private List<String> getSpecificPeriodRides() throws SQLException {
        System.out.print("Enter the start date (DD/MM/YYYY): ");
        String startDate = inputs.getStringInput().trim();
        System.out.print("Enter the end date (DD/MM/YYYY): ");
        String endDate = inputs.getStringInput().trim();
        if (dateOperations.validateDate(startDate) && dateOperations.validateDate(endDate)) {
            String startingDate = dateOperations.getFormattedDate(startDate);
            String endingDate = dateOperations.getFormattedDate(endDate);
            if (dateOperations.getDateDifference(startingDate, endingDate) < 0) {
                return Collections.singletonList("Invalid Input. Start date is " +
                        "greater than end date.");
            } else {
                return getRidesFromDb(startingDate, endingDate, requesterType, requesterID);
            }
        } else {
            return Collections.singletonList("Invalid Input");
        }
    }

    /**
     * This method gets the ride details from the database
     * Parameters:
     * startDate - start date
     * endDate - end date
     * userType - type of user (Customer or Driver)
     * userID - id of the user
     * Returns:
     * list of completed rides between start date and end date
     */
    private List<String> getRidesFromDb(String startDate, String endDate, UserType userType, int userID) throws SQLException {
        List<String> listOfRides = new ArrayList<>();
        listOfRides.add("Ride Details ->");
        String query = String.format("select \n" +
                "bookings.booking_id,\n" +
                "bookings.source,\n" +
                "bookings.destination,\n" +
                "trips.trip_amount\n" +
                "from bookings inner join trips\n" +
                "on bookings.booking_id = trips.booking_id\n" +
                "where cast(trips.created_at as date) between '%s' and '%s' \n" +
                "and trips.%s = %d\n" +
                "order by trips.booking_id;", startDate, endDate, getColumnName(userType), userID);
        ResultSet result = iPersistence.executeSelectQuery(query);
        while (result.next()) {
            String bookingID = result.getString("booking_id");
            String pickupLocation = result.getString("source");
            String dropLocation = result.getString("destination");
            double rideAmount = result.getDouble("trip_amount");
            String rideDetail = "BookingID: " + bookingID + ", Pickup: " + pickupLocation +
                    ", Destination: " + dropLocation + ", Price: " + rideAmount +
                    ", Status: Completed";
            listOfRides.add(rideDetail);
        }
        if (listOfRides.size() == 1) {
            listOfRides.add(("No rides to display"));
        }
        return listOfRides;
    }

    /**
     * method to get the column name for user category
     */
    private String getColumnName(UserType userType) {
        if (userType == UserType.DRIVER) {
            return "driver_id";
        } else if (userType == UserType.CUSTOMER) {
            return "cust_id";
        } else {
            throw new RuntimeException("Usertype invalid: " + userType);
        }
    }
}
