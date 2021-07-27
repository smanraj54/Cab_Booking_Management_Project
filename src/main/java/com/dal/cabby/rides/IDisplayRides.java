package com.dal.cabby.rides;

import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;
import java.util.List;

/**
 * This interface has all the methods to get the rides details for
 * different time periods.
 */
public interface IDisplayRides {

  /**
   * This method returns the rides on a particular day
   * Parameters:
   *   userID - id of the user
   *   userType - type of the user (CUSTOMER or DRIVER)
   *   date - date for which rides needs to be displayed
   * Returns:
   *   list of completed rides on a particular day
   */
  List<String> getDailyRides(int userID, UserType userType, String date) throws SQLException;

  /**
   * This method returns the rides in a particular month
   * Parameters:
   *   userID - id of the user
   *   userType - type of the user (CUSTOMER or DRIVER)
   *   monthInput - month in MM/YYYY format
   * Returns:
   *   list of completed rides in that particular month
   */
  List<String> getMonthlyRides(int userID, UserType userType, String monthInput) throws SQLException;

  /**
   * This method ask the user to enter the start date and end date
   * and return the ride details.
   *   userID - id of the user
   *   userType - type of the user (CUSTOMER or DRIVER)
   *   startDate - start date
   *   endDate - end date
   * Returns:
   *   list of completed rides between start date and end date
   */
  List<String> getSpecificPeriodRides(int userID, UserType userType, String startDate, String endDate) throws SQLException;
}
