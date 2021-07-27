package com.dal.cabby.util;

import java.sql.SQLException;

/**
 * This interface list down all the methods related to the date operations
 */
public interface IDateOperations {

    /**
     * method for date validation
     * Parameters:
     *   date - input date
     * Returns:
     *   true if date is valid
     */
    boolean validateDate(String date);

    /**
     * method to validate month
     * Parameters:
     *   monthInput - input of month in MM/YYYY
     */
    boolean validateMonthInput(String monthInput);

    /**
     * method to get the difference between two dates
     * Parameters:
     *   startDate - start date
     *   endDate - end date
     * Returns:
     *   number of days difference
     */
    int getDateDifference(String startDate, String endDate) throws SQLException;

    /**
     * method to get the next day
     * Parameters:
     *   inputDate - date
     * Returns:
     *   next day in string format
     */
    String getNextDay(String inputDate) throws SQLException;

    /**
     * method to get the last day of month
     * Parameters:
     *   inputDate - date
     * Returns:
     *   last date of the month
     */
    String getLastDateOfMonth(String inputDate) throws SQLException;

    /**
     * method to get the date in required format
     * Parameters:
     *   inputDate - date
     * Returns:
     *   returns date in YYYY-MM-DD format
     */
    String getFormattedDate(String inputDate);
}
