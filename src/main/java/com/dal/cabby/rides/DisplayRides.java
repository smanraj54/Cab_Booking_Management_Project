package com.dal.cabby.rides;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.util.DateOperations;
import com.dal.cabby.util.IDateOperations;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * This class provides the rides completed by user (Driver and Customer). This
 * class provides the option to display daily rides, monthly rides, and rides
 * between specific period.
 */
public class DisplayRides implements IDisplayRides {
    private IPersistence iPersistence;
    private IDateOperations dateOperations;
    private IDisplayRidesDatabase database;

    /**
     * Constructor of class DisplayRides
     */
    public DisplayRides() throws SQLException {
        iPersistence = DBHelper.getInstance();
        dateOperations = new DateOperations();
        database = new DisplayRidesDatabase();
    }

    /**
     * This method returns the rides on a particular day
     * Parameters:
     *   userID - id of the user
     *   userType - type of the user (CUSTOMER or DRIVER)
     *   date - date for which rides needs to be displayed
     * Returns:
     *   list of completed rides on a particular day
     */
    public List<String> getDailyRides(int userID, UserType userType, String date) throws SQLException {
        if (!dateOperations.validateDate(date)) {
            return Collections.singletonList("Invalid Input");
        } else {
            String inputDate = dateOperations.getFormattedDate(date);
            return database.getRidesFromDb(inputDate, inputDate, userID, userType);
        }
    }

    /**
     * This method returns the rides in a particular month
     * Parameters:
     *   userID - id of the user
     *   userType - type of the user (CUSTOMER or DRIVER)
     *   monthInput - month in MM/YYYY format
     * Returns:
     *   list of completed rides in that particular month
     */
    public List<String> getMonthlyRides(int userID, UserType userType, String monthInput) throws SQLException {

        if (dateOperations.validateMonthInput(monthInput)) {
            String[] splitInput = monthInput.split("/");
            String month = splitInput[0];
            String year = splitInput[1];
            String startDate = year + "-" + month + "-01";
            String endDate = dateOperations.getLastDateOfMonth(startDate);
            return database.getRidesFromDb(startDate, endDate, userID, userType);
        } else {
            return Collections.singletonList("Invalid Input");
        }
    }

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
    public List<String> getSpecificPeriodRides(int userID, UserType userType, String startDate, String endDate) throws SQLException {

        if (dateOperations.validateDate(startDate) && dateOperations.validateDate(endDate)) {
            String startingDate = dateOperations.getFormattedDate(startDate);
            String endingDate = dateOperations.getFormattedDate(endDate);
            if (dateOperations.getDateDifference(startingDate, endingDate) < 0) {
                return Collections.singletonList("Invalid Input. Start date is " +
                        "greater than end date.");
            } else {
                return database.getRidesFromDb(startingDate, endingDate, userID, userType);
            }
        } else {
            return Collections.singletonList("Invalid Input");
        }
    }
}
