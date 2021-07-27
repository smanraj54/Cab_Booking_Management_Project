package com.dal.cabby.cabPrice;

import java.sql.SQLException;
/*
    This interface holds Business logic for calculating price of ride in case customer avails extra
    amenities during ride.
 */
public interface ICabPriceWithAmenities {
    /*
        This method returns price of cab when user avails extra amenities. Based on duration of ride
        extra price is calculated.
     */
    double amenities(String source, double distance, int cabCategory, double hour) throws SQLException;
}
