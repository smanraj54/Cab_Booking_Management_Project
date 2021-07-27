package com.dal.cabby.cabPrice;

import java.sql.SQLException;
/*
    This interface holds Business logic for calculating price of ride in Normal category.
 */
public interface ICabPriceNormalBooking {
    /*
        This method return price from source to destination based on few parameters like distance between
        source and destination,type of cab, price during night and office hours, price based on
        source area (Rural and Urban).
     */
    double distanceFactor(String source, double distance, int cabType, double hour) throws SQLException;
}
