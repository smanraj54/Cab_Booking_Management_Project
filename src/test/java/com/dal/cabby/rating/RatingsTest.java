package com.dal.cabby.rating;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RatingsTest {

    @Test
    void addDriverRating() throws SQLException {
        IRatings ratings = new Ratings();
        int driverId = 1;
        int tripId = 1;
        int rating = 5;
        ratings.addDriverRating(driverId, tripId, rating);
    }


    @Test
    void addCustomerRating() {
    }

    @Test
    void getAverageRatingOfDriver() {
    }

    @Test
    void getAverageRatingOfCustomer() {

    }
}