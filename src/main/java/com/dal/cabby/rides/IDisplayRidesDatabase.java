package com.dal.cabby.rides;

import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;
import java.util.List;

public interface IDisplayRidesDatabase {

    /**
     * This method gets the ride details from the database
     * Parameters:
     *   startDate - start date
     *   endDate - end date
     *   userID - id of the user
     *   userType - type of user (Customer or Driver)
     * Returns:
     *   list of completed rides between start date and end date
     */
    List<String> getRidesFromDb(String startDate, String endDate, int userID, UserType userType) throws SQLException;
}
