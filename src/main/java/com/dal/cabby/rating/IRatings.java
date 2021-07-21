package com.dal.cabby.rating;

import java.sql.SQLException;

public interface IRatings {
    void addDriverRating(int driverId, int tripId, int rating) throws SQLException;

    void addCustomerRating(int userId, int tripId, int rating) throws SQLException;

    double getAverageRatingOfDriver() throws SQLException;

    double getAverageRatingOfCustomer() throws SQLException;
}
