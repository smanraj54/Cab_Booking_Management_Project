package com.dal.cabby.cabPrice;

import java.sql.SQLException;
/*
This interface holds Business logic for ride-sharing feature of our application.
 */
public interface ICabPriceRideSharing {
    /*
        This method returns cab price when user shares ride with others. Separate percent of price reduction is
        there based on number of co-passengers.
     */
    double rideSharing(String source, double distance, int cabCategory, double hour) throws SQLException;
}
