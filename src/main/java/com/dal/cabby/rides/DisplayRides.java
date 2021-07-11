package com.dal.cabby.rides;

import com.dal.cabby.dbHelper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DisplayRides {
    DBHelper dbHelper;
    private String requesterType;
    private int requesterID;

    public DisplayRides() throws SQLException {
        dbHelper = new DBHelper();
        dbHelper.initialize();
    }

    public void getRides(String userType, int userID) throws SQLException {
        requesterType = userType;
        requesterID = userID;
        ridesPage();
    }

    private void ridesPage() throws SQLException {
        while (true) {
            System.out.println("\n**** Rides Page ****");
            System.out.println("\t1. Daily rides");
            System.out.println("\t2. Monthly rides");
            System.out.println("\t3. Rides between a specific period");
            System.out.println("\t4. Return to the previous page");
            System.out.print("Please enter a selection: ");
            int selection = Integer.parseInt(getInput());
            switch (selection) {
                case 1:
                    getDailyRides();
                    break;
                case 2:
                    getMonthlyRides();
                    break;
                case 3:
                    getSpecificPeriodRides();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\nInvalid selection");
            }
        }
    }

    // method to get daily rides
    private void getDailyRides() throws SQLException {
        System.out.print("Enter the date in DD/MM/YYYY format: ");
        String inputDate = getInput();
        if (validateDate(inputDate)) {
            String date = getFormattedDate(inputDate);
            String columnName = getColumnName(requesterType);
            String query = String.format("select\n" +
                    "bookings.booking_id,\n" +
                    "bookings.source,\n" +
                    "bookings.destination,\n" +
                    "trips.trip_amount\n" +
                    "from bookings inner join trips\n" +
                    "on bookings.booking_id = trips.booking_id\n" +
                    "where cast(trips.created_at as date) = '%s' and trips.%s = %d\n" +
                    "order by trips.booking_id;", date, columnName, requesterID);
            ResultSet result = dbHelper.executeSelectQuery(query);
            System.out.println("\nRide Details ->");
            while (result.next()) {
                String bookingId = result.getString("booking_id");
                String pickupLocation = result.getString("source");
                String dropLocation = result.getString("destination");
                double rideAmount = result.getDouble("trip_amount");
                System.out.println("BookingID: " + bookingId + ", Pickup: " + pickupLocation + ", Destination: " + dropLocation + ", Price: " + rideAmount + ", Status: Completed");
            }
        } else {
            System.out.println("\nInvalid Date");
        }
    }

    // method to get monthly rides
    private void getMonthlyRides() throws SQLException {
        System.out.print("Enter the month in MM/YYYY format: ");
        String input = getInput();
        if (input.length() == 0 || input.indexOf("/") != 2) {
            System.out.println("\nInvalid Date");
        } else {
            String[] splitInput = input.split("/");
            String month = splitInput[0];
            String year = splitInput[1];
            String startDate = year + "-" + month + "-01";
            String endDate = getLastDay(startDate);
            String columnName = getColumnName(requesterType);
            String query = String.format("select\n" +
                    "bookings.booking_id,\n" +
                    "bookings.source,\n" +
                    "bookings.destination,\n" +
                    "trips.trip_amount\n" +
                    "from bookings inner join trips\n" +
                    "on bookings.booking_id = trips.booking_id\n" +
                    "where cast(trips.created_at as date) between '%s' and '%s' \n" +
                    "and trips.%s = %d\n" +
                    "order by trips.booking_id;", startDate, endDate, columnName, requesterID);
            ResultSet result = dbHelper.executeSelectQuery(query);
            System.out.println("\nRide Details ->");
            while (result.next()) {
                String bookingId = result.getString("booking_id");
                String pickupLocation = result.getString("source");
                String dropLocation = result.getString("destination");
                double rideAmount = result.getDouble("trip_amount");
                System.out.println("BookingID: " + bookingId + ", Pickup: " + pickupLocation + ", Destination: " + dropLocation + ", Price: " + rideAmount + ", Status: Completed");
            }
        }
    }

    // method to get rides between specific time period
    private void getSpecificPeriodRides() {
    }

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

    private String getFormattedDate(String inputDate) {
        String[] splitDate = inputDate.split("/");
        String day = splitDate[0];
        String month = splitDate[1];
        String year = splitDate[2];
        return (year + "-" + month + "-" + day);
    }

    private String getLastDay(String inputDate) throws SQLException {
        String date = "";
        String query = String.format("select last_day('%s') as last_date", inputDate);
        ResultSet result = dbHelper.executeSelectQuery(query);
        while (result.next()) {
            date = result.getString("last_date");
        }
        return date;
    }

    private String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private String getColumnName(String userType) {
        if (userType.equalsIgnoreCase("DRIVER")) {
            return "driver_id";
        } else {
            return "cust_id";
        }
    }
}
