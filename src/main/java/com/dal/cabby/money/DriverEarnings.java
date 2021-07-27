package com.dal.cabby.money;

import com.dal.cabby.util.DateOperations;
import com.dal.cabby.util.IDateOperations;

import java.sql.SQLException;

/**
 * This class will return the earnings of the driver for daily,
 * monthly, and for specific period of time
 */
public class DriverEarnings implements IDriverEarnings {
    private IDateOperations dateOperations;
    private IDBDriverOperations dbDriverOperations;

    /**
     * Constructor of DriverEarnings class
     */
    public DriverEarnings() throws SQLException {
        dateOperations = new DateOperations();
        dbDriverOperations = new DBDriverOperations();
    }

    /**
     * This method will provide the earnings on particular date
     * Parameters:
     *   userID - id of the user
     *   date - date for which earning needs to be displayed
     * Returns:
     *   a string with earning details of that day
     */
    public String getDailyEarnings(int userID, String date) throws SQLException {
        if (dateOperations.validateDate(date)) {
            String inputDate = dateOperations.getFormattedDate(date);
            double earning = dbDriverOperations.earningOnDate(userID, inputDate);
            return "\nTotal earning on " + date + " is $" + String.format("%.2f", earning);
        } else {
            return "\nInvalid Input...";
        }
    }

    /**
     * This method will provide the earnings for a specific month
     * Parameters:
     *   userID - id of the user
     *   monthInput - value of month in MM/YYYY format
     * Returns:
     *   a string with earning details of that month
     */
    public String getMonthlyEarnings(int userID, String monthInput) throws SQLException {
        if (!dateOperations.validateMonthInput(monthInput)) {
            return "\nInvalid Entry";
        } else {
            double earning = 0.0;
            String month = monthInput.split("/")[0];
            String year = monthInput.split("/")[1];
            String startDate = year + "-" + month + "-01";
            String endDate = dateOperations.getLastDateOfMonth(startDate);
            int days = dateOperations.getDateDifference(startDate, endDate);
            for (int i = 0; i <= days; i++) {
                if (i >= 0 && i < 9) {
                    startDate = startDate.substring(0, startDate.length()-1) + (i + 1);
                } else {
                    startDate = startDate.substring(0, startDate.length()-2) + (i + 1);
                }
                earning = earning + dbDriverOperations.earningOnDate(userID, startDate);
            }
            return "\nThe total earnings in " + monthInput + " is $" + String.format("%.2f", earning);
        }
    }

    /**
     * This method will will provide the earnings between start date and end date
     * Returns:
     *   a string with earning details between specific period
     */
    public String getSpecificPeriodEarnings(int userID, String startDate, String endDate) throws SQLException {
        if (dateOperations.validateDate(startDate) && dateOperations.validateDate(endDate)) {
            double earning = 0.0;
            String startingDate = dateOperations.getFormattedDate(startDate);
            String endingDate = dateOperations.getFormattedDate(endDate);
            if (dateOperations.getDateDifference(startingDate, endingDate) < 0) {
                return "\nInvalid Entry. Start date is greater than end date...";
            } else {
                while (dateOperations.getDateDifference(startingDate, endingDate) > -1) {
                    earning = earning + dbDriverOperations.earningOnDate(userID, startingDate);
                    startingDate = dateOperations.getNextDay(startingDate);
                }
                return "\nTotal earnings between " + startDate + " and " + endDate +
                    " is $" + String.format("%.2f", earning);
            }
        } else {
            return "\nInvalid Entry...";
        }
    }
}
