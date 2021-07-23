package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;

import java.sql.ResultSet;
import java.sql.SQLException;

// this class will show the earning of driver for a specific period
public class DriverEarnings {
    IPersistence IPersistence;
    int userID;
    Inputs inputs;

    public DriverEarnings(Inputs inputs) {
        this.inputs = inputs;
        IPersistence = new DBHelper();
        try {
            IPersistence.initialize();
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
            int input = inputs.getIntegerInput();
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
        System.out.print("Enter the date in DD/MM/YYYY format: ");
        String inputDate = inputs.getStringInput();
        if (validateDate(inputDate)) {
            String[] splitDate = inputDate.split("/");
            String date = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
            double earning = earningOnDate(userID, date);
            System.out.println("\nTotal earning on " + inputDate + " is $" + earning);
        } else {
            System.out.println("\nInvalid Input...");
        }
    }

    // method to calculate monthly earnings
    public void monthlyEarnings() throws SQLException {
        System.out.print("Enter the month in MM/YYYY format: ");
        String input = inputs.getStringInput();
        if (input.isEmpty() || (input.indexOf("/")!=2)) {
            System.out.println("\nInvalid Entry");
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
            System.out.println("\nThe total earnings in " + input + " is $" + earning);
        }
    }

    private void specificPeriodEarnings() throws SQLException {
        System.out.print("Enter the start date (DD/MM/YYYY): ");
        String startDate = inputs.getStringInput();
        System.out.print("Enter the end date (DD/MM/YYYY): ");
        String endDate = inputs.getStringInput();
        if (validateDate(startDate) && validateDate(endDate)) {
            double earning = 0.0;
            String[] splitStartDate = startDate.split("/");
            String startingDate = splitStartDate[2] + "-" + splitStartDate[1] + "-" + splitStartDate[0];
            String[] splitEndDate = endDate.split("/");
            String endingDate = splitEndDate[2] + "-" + splitEndDate[1] + "-" + splitEndDate[0];
            if (getDateDifference(startingDate, endingDate) < 0) {
                System.out.println("\nInvalid Entry. Start date is greater than end date...");
            } else {
                while (getDateDifference(startingDate, endingDate) > -1) {
                    earning = earning + earningOnDate(userID, startingDate);
                    startingDate = getNextDay(startingDate);
                }
                System.out.println("\nTotal earnings between " + startDate + " and " + endDate + " is $" + earning);
            }
        } else {
            System.out.println("\nInvalid Entry...");
        }
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
        ResultSet output = IPersistence.executeSelectQuery(query);
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
        ResultSet result = IPersistence.executeSelectQuery(query);
        while (result.next()) {
            dateDifference = result.getInt("date_difference");
        }
        return dateDifference;
    }

    private String getNextDay(String inputDate) throws SQLException {
        String date = "";
        String query = String.format("select adddate('%s',1) as next_day", inputDate);
        ResultSet result = IPersistence.executeSelectQuery(query);
        while (result.next()){
            date = result.getString("next_day");
        }
        return date;
    }

    private String getLastDayOfMonth(String inputDate) throws SQLException {
        String date = "";
        String query = String.format("select last_day('%s') as last_date", inputDate);
        ResultSet result = IPersistence.executeSelectQuery(query);
        while (result.next()) {
            date = result.getString("last_date");
        }
        return date;
    }

    // method to validate date
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
}
