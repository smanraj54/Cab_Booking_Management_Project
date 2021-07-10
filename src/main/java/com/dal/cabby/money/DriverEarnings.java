package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// this class will show the earning of driver for a specific period
public class DriverEarnings {
    DBHelper dbHelper;
    int userID;

    public DriverEarnings() {
        dbHelper = new DBHelper();
        try {
            dbHelper.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getEarnings(int driverID) throws SQLException {
        userID = driverID;
        earningsPage();
    }

    public void earningsPage() throws SQLException {
        while (true) {
            System.out.println("\n**** Earnings Page ****");
            System.out.println("\t1. Daily earnings: ");
            System.out.println("\t2. Monthly earnings: ");
            System.out.println("\t3. Earning between a specific period: ");
            System.out.println("\t4. Return to the previous page: ");
            System.out.print("Please enter a selection: ");
            int input = Integer.parseInt(getInput());
            switch (input) {
                case 1:
                    dailyEarnings();
                    break;
                case 2:
                    monthlyEarnings();
                    break;
                case 3:
                    specificPeriodEarnings();
                    break;
                default:
                    return;
            }
        }
    }

    // method to calculate the daily earnings
    public void dailyEarnings() throws SQLException {
        System.out.print("Enter the date: ");
        String date = getInput();
        double earning = earningOnDate(userID, date);
        System.out.println("\nTotal earning on "+date+" is $"+earning);
    }

    // method to calculate monthly earnings
    public void monthlyEarnings() throws SQLException {
        System.out.print("Enter the month in MM/YYYY format: ");
        String input = getInput();
        if (input.isEmpty() || (input.indexOf("/")!=2)) {
            System.out.println("Invalid Entry");
        } else {
            double earning = 0.0;
            String month = input.split("/")[0];
            String year = input.split("/")[1];
            String startDate = year + "-" + month + "-01";
            String endDate = getLastDayOfMonth(startDate);
            while (getDateDifference(startDate, endDate) > -1) {
                earning = earning + earningOnDate(userID, startDate);
                startDate = getNextDay(startDate);
            }
            System.out.println("\nThe total earnings in "+input+" is $"+earning);
        }
    }

    private void specificPeriodEarnings() throws SQLException {
    }

    // method to calculate the percentage of commission deducted
    private int commissionPercentage(int totalRides, double totalDistance, double totalTime) {
        if (totalRides > 12 || totalDistance > 300 || totalTime > 8) {
            return 15;
        } else if (totalRides > 10 || totalDistance > 250 || totalTime > 7) {
            return 16;
        } else if (totalRides > 8 || totalDistance > 200 || totalTime > 6) {
            return 18;
        } else {
            return 20;
        }
    }

    private String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private double earningOnDate(int driverID, String date) throws SQLException {
        int totalRides = 0;
        double travelDistance = 0.0;
        double travelTime = 0.0;
        double amountOfRides = 0.0;
        String query = String.format("select \n" +
                "count(trip_id) as total_rides,\n" +
                "sum(distance_covered) as total_distance_covered,\n" +
                "sum(timestampdiff(second,trip_start_time,trip_end_time))/3600 as total_travel_time,\n" +
                "sum(trip_amount) as total_amount\n" +
                "from trips \n" +
                "where driver_id = %d and cast(created_at as date) = '%s'\n" +
                "group by driver_id", driverID, date);
        ResultSet output = dbHelper.executeSelectQuery(query);
        while (output.next()) {
            totalRides = output.getInt("total_rides");
            travelDistance = output.getDouble("total_distance_covered");
            travelTime = output.getDouble("total_travel_time");
            amountOfRides = output.getDouble("total_amount");
        }
        // getting commission percentage
        int commissionPercentage = commissionPercentage(totalRides, travelDistance, travelTime);
        return (amountOfRides - ((amountOfRides * commissionPercentage)/100));
    }

    private int getDateDifference(String startDate, String endDate) throws SQLException {
        int dateDifference = 0;
        String query = String.format("select datediff('%s','%s') as date_difference", endDate, startDate);
        ResultSet result = dbHelper.executeSelectQuery(query);
        while (result.next()) {
            dateDifference = result.getInt("date_difference");
        }
        return dateDifference;
    }

    private String getNextDay(String inputDate) throws SQLException {
        String date = "";
        String query = String.format("select adddate('%s',1) as next_day", inputDate);
        ResultSet result = dbHelper.executeSelectQuery(query);
        while (result.next()){
            date = result.getString("next_day");
        }
        return date;
    }

    private String getLastDayOfMonth(String inputDate) throws SQLException {
        String date = "";
        String query = String.format("select last_day('%s') as last_date", inputDate);
        ResultSet result = dbHelper.executeSelectQuery(query);
        while (result.next()) {
            date = result.getString("last_date");
        }
        return date;
    }
}
