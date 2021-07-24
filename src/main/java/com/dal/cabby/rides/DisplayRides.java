package com.dal.cabby.rides;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayRides {
    IPersistence iPersistence;
    private UserType requesterType;
    private int requesterID;
    Inputs inputs;
    public DisplayRides(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        iPersistence = DBHelper.getInstance();
    }

    public List<String> getRides(UserType userType, int userID) throws SQLException {
        requesterType = userType;
        requesterID = userID;
        return ridesPage();
    }

    // method to display ride page options and get input from the user
    private List<String> ridesPage() throws SQLException {
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

    // method to get daily rides
    private List<String> getDailyRides() throws SQLException {
        System.out.print("Enter the date in DD/MM/YYYY format: ");
        String inputDate = inputs.getStringInput().trim();
        if (!validateDate(inputDate)) {
            return Collections.singletonList("Invalid Input");
        } else {
            String date = getFormattedDate(inputDate);
            return getRidesFromDb(date, date, requesterType, requesterID);
        }
    }

    // method to get monthly rides
    private List<String> getMonthlyRides() throws SQLException {
        System.out.print("Enter the month in MM/YYYY format: ");
        String input = inputs.getStringInput().trim();
        if (input.length() == 7 && input.indexOf("/") == 2) {
            String[] splitInput = input.split("/");
            String month = splitInput[0];
            String year = splitInput[1];
            String startDate = year + "-" + month + "-01";
            String endDate = getLastDay(startDate);
            return getRidesFromDb(startDate, endDate, requesterType, requesterID);
        } else {
            return Collections.singletonList("Invalid Input");
        }
    }

    // method to get rides between specific time period
    private List<String> getSpecificPeriodRides() throws SQLException {
        System.out.print("Enter the start date (DD/MM/YYYY): ");
        String startDate = inputs.getStringInput().trim();
        System.out.print("Enter the end date (DD/MM/YYYY): ");
        String endDate = inputs.getStringInput().trim();
        if (validateDate(startDate) && validateDate(endDate)) {
            String startingDate = getFormattedDate(startDate);
            String endingDate = getFormattedDate(endDate);
            if (getDateDifference(startingDate, endingDate) < 0) {
                return Collections.singletonList("Invalid Input. Start date is " +
                    "greater than end date.");
            } else {
                return getRidesFromDb(startingDate, endingDate, requesterType, requesterID);
            }
        } else {
            return Collections.singletonList("Invalid Input");
        }
    }

    // method to get rides from the database
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
            return listOfRides;
        } else {
            return listOfRides;
        }
    }

    // method for date validation
    private boolean validateDate(String date) {
        if (date != null && date.length() == 10 && date.indexOf("/") == 2 && date.lastIndexOf("/") == 5) {
            String[] splitDate = date.split("/");
            String day = splitDate[0];
            String month = splitDate[1];
            String year = splitDate[2];
            return !day.equals("00") && !month.equals("00") && !year.equals("0000");
        }
        return false;
    }

    // method to get the date in required format
    private String getFormattedDate(String inputDate) {
        String[] splitDate = inputDate.split("/");
        String day = splitDate[0];
        String month = splitDate[1];
        String year = splitDate[2];
        return (year + "-" + month + "-" + day);
    }

    // method to get the last day of month
    private String getLastDay(String inputDate) throws SQLException {
        String date = "";
        String query = String.format("select last_day('%s') as last_date", inputDate);
        ResultSet result = iPersistence.executeSelectQuery(query);
        while (result.next()) {
            date = result.getString("last_date");
        }
        return date;
    }

    // method to get the difference between two dates
    private int getDateDifference(String startDate, String endDate) throws SQLException {
        int dateDifference = 0;
        String query = String.format("select datediff('%s','%s') as date_difference", endDate, startDate);
        ResultSet result = iPersistence.executeSelectQuery(query);
        while (result.next()) {
            dateDifference = result.getInt("date_difference");
        }
        return dateDifference;
    }

    // method to get the column name for user category
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
