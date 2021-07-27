package com.dal.cabby.money;

import java.sql.SQLException;

/**
 * This interface has all the methods required to get the earning details for
 * different time spans
 */
public interface IDriverEarnings {

    /**
     * This method will provide the earnings on particular date
     * Parameters:
     *   userID - id of the user
     *   date - date for which earning needs to be displayed
     * Returns:
     *   a string with earning details of that day
     */
    String getDailyEarnings(int userID, String date) throws SQLException;

    /**
     * This method will provide the earnings for a specific month
     * Parameters:
     *   userID - id of the user
     *   monthInput - value of month in MM/YYYY format
     * Returns:
     *   a string with earning details of that month
     */
    String getMonthlyEarnings(int userID, String monthInput) throws SQLException;

    /**
     * This method will will provide the earnings between start date and end date
     * Returns:
     *   a string with earning details between specific period
     */
    String getSpecificPeriodEarnings(int userID, String startDate, String endDate) throws SQLException;
}
