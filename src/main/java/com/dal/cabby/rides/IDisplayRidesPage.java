package com.dal.cabby.rides;

import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;

public interface IDisplayRidesPage {

    /**
     * This method provides the display options to select from and returns
     * the appropriate result
     * Parameters:
     *   userID - id of the user
     *   userType - type of user (Customer or Driver)
     * Returns:
     *   list of rides
     */
    void ridesOptions(int userID, UserType userType) throws SQLException;
}
