package com.dal.cabby.util;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to perform different operations on input date
 */
public class DateOperations implements IDateOperations {
    private final IPersistence iPersistence;

    public DateOperations() throws SQLException {
        iPersistence = DBHelper.getInstance();
    }

    /**
     * method for date validation
     * Parameters:
     *   date - input date
     * Returns:
     *   true if date is valid
     */
    public boolean validateDate(String date) {
        if (date != null && date.length() == 10 && date.indexOf("/") == 2 && date.lastIndexOf("/") == 5) {
            String[] splitDate = date.split("/");
            String day = splitDate[0];
            String month = splitDate[1];
            String year = splitDate[2];
            return !day.equals("00") && !month.equals("00") && !year.equals("0000");
        }
        return false;
    }

    /**
     * method to validate month
     * Parameters:
     *   monthInput - input of month in MM/YYYY
     */
    public boolean validateMonthInput(String monthInput) {
        return !monthInput.isEmpty() && (monthInput.indexOf("/") == 2) && monthInput.length() == 7;
    }

    /**
     * method to get the difference between two dates
     * Parameters:
     *   startDate - start date
     *   endDate - end date
     * Returns:
     *   number of days difference
     */
    public int getDateDifference(String startDate, String endDate) throws SQLException {
        int dateDifference = 0;
        String query = String.format("select datediff('%s','%s') as date_difference", endDate, startDate);
        ResultSet result = iPersistence.executeSelectQuery(query);
        while (result.next()) {
            dateDifference = result.getInt("date_difference");
        }
        return dateDifference;
    }

    /**
     * method to get the next day
     * Parameters:
     *   inputDate - date
     * Returns:
     *   next day in string format
     */
    public String getNextDay(String inputDate) throws SQLException {
        String date = "";
        String query = String.format("select adddate('%s',1) as next_day", inputDate);
        ResultSet result = iPersistence.executeSelectQuery(query);
        while (result.next()) {
            date = result.getString("next_day");
        }
        return date;
    }

    /**
     * method to get the last day of month
     * Parameters:
     *   inputDate - date
     * Returns:
     *   last date of the month
     */
    public String getLastDateOfMonth(String inputDate) throws SQLException {
        String date = "";
        String query = String.format("select last_day('%s') as last_date", inputDate);
        ResultSet result = iPersistence.executeSelectQuery(query);
        while (result.next()) {
            date = result.getString("last_date");
        }
        return date;
    }

    /**
     * method to get the date in required format
     * Parameters:
     *   inputDate - date
     * Returns:
     *   returns date in YYYY-MM-DD format
     */
    public String getFormattedDate(String inputDate) {
        String[] splitDate = inputDate.split("/");
        String day = splitDate[0];
        String month = splitDate[1];
        String year = splitDate[2];
        return (year + "-" + month + "-" + day);
    }
}
