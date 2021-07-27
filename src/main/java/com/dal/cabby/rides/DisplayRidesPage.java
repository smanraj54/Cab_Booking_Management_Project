package com.dal.cabby.rides;

import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class displays the rides options to the user and get
 * the input from the user
 */
public class DisplayRidesPage implements IDisplayRidesPage {
    private Inputs inputs;
    private IDisplayRides displayRides;
    private List<String> rides;

    public DisplayRidesPage() {
        inputs = new InputFromUser();
    }

    /**
     * This method provides the display options to select from and returns
     * the appropriate result
     * Parameters:
     *   userID - id of the user
     *   userType - type of user (Customer or Driver)
     * Returns:
     *   list of rides
     */
    public void ridesOptions(int userID, UserType userType) throws SQLException {
        displayRides = new DisplayRides();
        rides = new ArrayList<>();
        System.out.println("\n**** Rides Page ****");
        System.out.println("1. Daily rides");
        System.out.println("2. Monthly rides");
        System.out.println("3. Rides between a specific period");
        System.out.println("4. Return to the previous page");
        System.out.print("Please enter a selection: ");
        int selection = inputs.getIntegerInput();
        switch (selection) {
            case 1:
                System.out.print("Enter the date in DD/MM/YYYY format: ");
                String inputDate = inputs.getStringInput().trim();
                rides = displayRides.getDailyRides(userID, userType, inputDate);
                System.out.println();
                for (String ride : rides) {
                    System.out.println(ride);
                }
                break;
            case 2:
                System.out.print("Enter the month in MM/YYYY format: ");
                String inputMonth = inputs.getStringInput().trim();
                rides = displayRides.getMonthlyRides(userID, userType, inputMonth);
                System.out.println();
                for (String ride : rides) {
                    System.out.println(ride);
                }
                break;
            case 3:
                System.out.print("Enter the start date (DD/MM/YYYY): ");
                String startDate = inputs.getStringInput().trim();
                System.out.print("Enter the end date (DD/MM/YYYY): ");
                String endDate = inputs.getStringInput().trim();
                rides = displayRides.getSpecificPeriodRides(userID, userType, startDate, endDate);
                System.out.println();
                for (String ride : rides) {
                    System.out.println(ride);
                }
                break;
            case 4:
                return;
            default:
                System.out.println("\nInvalid selection");
                break;
        }
    }
}
