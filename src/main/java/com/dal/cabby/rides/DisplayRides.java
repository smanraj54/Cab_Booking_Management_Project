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
            Scanner sc = new Scanner(System.in);
            int selection = sc.nextInt();
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
        String tableColumn;
        if (requesterType.equalsIgnoreCase("DRIVER")) {
            tableColumn = "driver_id";
        } else {
            tableColumn = "cust_id";
        }
        System.out.print("Enter the date in DD/MM/YYYY format: ");
        Scanner sc = new Scanner(System.in);
        String inputDate = sc.nextLine();
        if (inputDate.length() == 10 && inputDate.indexOf("/") == 2 && inputDate.lastIndexOf("/") == 5) {
            String[] splitDate = inputDate.split("/");
            String date = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
            String query = String.format("select\n" +
                    "bookings.booking_id,\n" +
                    "bookings.source,\n" +
                    "bookings.destination,\n" +
                    "trips.trip_amount\n" +
                    "from bookings inner join trips\n" +
                    "on bookings.booking_id = trips.booking_id\n" +
                    "where cast(trips.created_at as date) = '%s' and trips.%s = %d\n" +
                    "order by trips.booking_id;", date, tableColumn, requesterID);
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

    // method to get monthly rides
    private void getMonthlyRides() {
    }

    // method to get rides between specific time period
    private void getSpecificPeriodRides() {
    }
}
