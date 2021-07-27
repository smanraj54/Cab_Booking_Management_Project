package com.dal.cabby.cabPrice;

import java.sql.SQLException;

public interface ICabPriceNormalBooking {
    double distanceFactor(String source, double distance, int cabType, double hour) throws SQLException;
}
