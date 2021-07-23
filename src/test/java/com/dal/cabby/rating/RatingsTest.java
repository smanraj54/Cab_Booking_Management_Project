package com.dal.cabby.rating;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class RatingsTest {

    @BeforeAll
    static void addDriverRating() throws SQLException {
        IRatings ratings = new Ratings();
        int driverId = 1;
        int tripId = 1;
        int rating = 5;
        ratings.addDriverRating(driverId, tripId, rating);
    }

    @Test
    void addCustomerRating() throws SQLException {
        IRatings ratings = new Ratings();
        int driverId = 1;
        int tripId = 1;
        int rating = 5;
        ratings.addCustomerRating(driverId, tripId, rating);
    }

    @Test
    void addInvalidDriverRating() throws SQLException {
        IRatings ratings = new Ratings();
        int driverId = 1;
        int customerId = 1;
        int rating = 7;
        // Expect a runtime exception because valid rating is between 1 to 5
        Assertions.assertThrows(RuntimeException.class, () ->ratings.addDriverRating(driverId, customerId, rating));
    }

    @Test
    void addInvalidCustomerRating() throws SQLException {
        IRatings ratings = new Ratings();
        int driverId = 1;
        int customerId = 1;
        int rating = -1;
        // Expect a runtime exception because valid rating is between 1 to 5
        Assertions.assertThrows(RuntimeException.class, () ->ratings.addDriverRating(driverId, customerId, rating));
    }

    @Test
    void getAverageRatingOfDriver() throws SQLException {
        IRatings ratings = new Ratings();
        double average_rating = ratings.getAverageRatingOfDriver(1);
        Assertions.assertTrue(average_rating <= 5 && average_rating >= 1, "Wrong rating of driver");
    }

    @Test
    void getAverageRatingOfCustomer() throws SQLException {
        IRatings ratings = new Ratings();
        double average_rating = ratings.getAverageRatingOfCustomer(1);
        Assertions.assertTrue(average_rating <= 5 && average_rating >= 1, "Wrong rating of customer");
    }

}