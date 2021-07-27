package com.dal.cabby.cabPrice;

import java.sql.SQLException;

/*
This interface acts as a presentation layer for Price Calculation Feature
 */
public interface ICabPriceCalculator {
    /*
        This is the starting method to perform all the price calculation operations for Normal bookings,
        price for sharing ride, and price while availing extra amenities.
     */
    double priceCalculation(String source, String destination, int cabType, double hour) throws SQLException;

}


