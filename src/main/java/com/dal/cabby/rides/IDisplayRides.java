package com.dal.cabby.rides;

import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;
import java.util.List;

public interface IDisplayRides {

  /**
   * This method will return the list of rides.
   * Parameters:
   * userType - whether the user is driver or customer
   * userID - the id of the user
   * Returns:
   * list of rides
   */
  public List<String> getRides(UserType userType, int userID) throws SQLException;
}
