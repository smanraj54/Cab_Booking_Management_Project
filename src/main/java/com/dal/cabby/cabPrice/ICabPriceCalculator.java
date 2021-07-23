package com.dal.cabby.cabPrice;

import java.sql.SQLException;

public interface ICabPriceCalculator {
    /*
        This is the starting method to perform all the price calculation operations for Normal bookings,
        price for sharing ride, price while availing extra amenities.
     */
    double priceCalculation(String source, String destination, int cabType, double hour) throws SQLException;

    /*
        This method calculate distance between Source and Destination place. It takes two parameters.
     */
    double locationsDistanceFromOrigin(String source,String destination) throws SQLException;

    /*
        This method calculate distance between Source i.e, (location from where user is booking cab)
        and Nearby Cabs. It takes two parameters i.e, source location and names of nearby cabs.
     */
    double locationAndCabDistanceFromOrigin(String source,String destination) throws SQLException;

}


