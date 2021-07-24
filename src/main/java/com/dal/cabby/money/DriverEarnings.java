package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.util.DateOperations;

import java.sql.ResultSet;
import java.sql.SQLException;

// this class will show the earning of driver for a specific period
public class DriverEarnings {
    IPersistence iPersistence;
    DateOperations dateOperations;
    CommissionCalculation commission;
    int userID;
    Inputs inputs;

    public DriverEarnings(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        dateOperations = new DateOperations();
        commission = new CommissionCalculation();
        iPersistence = DBHelper.getInstance();
    }

    public String getEarnings(int driverID) throws SQLException {
        userID = driverID;
        return earnings();
    }

    private String earnings() throws SQLException {
        System.out.println("\n**** Earnings Page ****");
        System.out.println("1. Daily earnings: ");
        System.out.println("2. Monthly earnings: ");
        System.out.println("3. Earning between a specific period: ");
        System.out.println("4. Return to the previous page: ");
        System.out.print("Please enter a selection: ");
        int input = inputs.getIntegerInput();
        switch (input) {
            case 1:
                return dailyEarnings();
            case 2:
                return monthlyEarnings();
            case 3:
                return specificPeriodEarnings();
            case 4:
                return "";
            default:
                return "\nInvalid Selection";
        }
    }

    // method to calculate the daily earnings
    private String dailyEarnings() throws SQLException {
        System.out.print("Enter the date in DD/MM/YYYY format: ");
        String inputDate = inputs.getStringInput();
        if (dateOperations.validateDate(inputDate)) {
            String[] splitDate = inputDate.split("/");
            String date = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
            double earning = earningOnDate(userID, date);
            return "\nTotal earning on " + inputDate + " is $" + earning;
        } else {
            return "\nInvalid Input...";
        }
    }

    // method to calculate monthly earnings
    private String monthlyEarnings() throws SQLException {
        System.out.print("Enter the month in MM/YYYY format: ");
        String input = inputs.getStringInput();
        if (input.isEmpty() || (input.indexOf("/")!=2)) {
            return "\nInvalid Entry";
        } else {
            double earning = 0.0;
            String month = input.split("/")[0];
            String year = input.split("/")[1];
            String startDate = year + "-" + month + "-01";
            String endDate = dateOperations.getLastDayOfMonth(startDate);
            while (dateOperations.getDateDifference(startDate, endDate) > -1) {
                earning = earning + earningOnDate(userID, startDate);
                startDate = dateOperations.getNextDay(startDate);
            }
            return "\nThe total earnings in " + input + " is $" + earning;
        }
    }

    private String specificPeriodEarnings() throws SQLException {
        System.out.print("Enter the start date (DD/MM/YYYY): ");
        String startDate = inputs.getStringInput();
        System.out.print("Enter the end date (DD/MM/YYYY): ");
        String endDate = inputs.getStringInput();
        if (dateOperations.validateDate(startDate) && dateOperations.validateDate(endDate)) {
            double earning = 0.0;
            String[] splitStartDate = startDate.split("/");
            String startingDate = splitStartDate[2] + "-" + splitStartDate[1] + "-" + splitStartDate[0];
            String[] splitEndDate = endDate.split("/");
            String endingDate = splitEndDate[2] + "-" + splitEndDate[1] + "-" + splitEndDate[0];
            if (dateOperations.getDateDifference(startingDate, endingDate) < 0) {
                return "\nInvalid Entry. Start date is greater than end date...";
            } else {
                while (dateOperations.getDateDifference(startingDate, endingDate) > -1) {
                    earning = earning + earningOnDate(userID, startingDate);
                    startingDate = dateOperations.getNextDay(startingDate);
                }
                return "\nTotal earnings between " + startDate + " and " + endDate + " is $" + earning;
            }
        } else {
            return "\nInvalid Entry...";
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
        ResultSet output = iPersistence.executeSelectQuery(query);
        while (output.next()) {
            totalRides = output.getInt("total_rides");
            travelDistance = output.getDouble("total_distance_covered");
            travelTime = output.getDouble("total_travel_time");
            amountOfRides = output.getDouble("total_amount");
        }
        // getting commission percentage
        int commissionPercentage = commission.getCommissionPercentage(totalRides, travelDistance, travelTime);
        return (amountOfRides - ((amountOfRides * commissionPercentage)/100));
    }
}
