package com.dal.cabby.rating;

import com.dal.cabby.dbHelper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ratings {
    DBHelper dbHelper;

    public Ratings() throws SQLException {
        dbHelper = new DBHelper();
        dbHelper.initialize();
    }

    public void addDriverRating(int driverId, int tripId, int rating) throws SQLException {
        if (rating<1 || rating>5) {
            throw new RuntimeException("Rating can only be in the range of 1 to 5");
        }
        String q = String.format("insert into driver_ratings(driver_id, trip_id, rating) values (%d, %d, %d)",
                driverId, tripId, rating);
        dbHelper.executeCreateOrUpdateQuery(q);
    }

    public void addCustomerRating(int userId, int tripId, int rating) throws SQLException {
        if (rating<1 || rating>5) {
            throw new RuntimeException("Rating can only be in the range of 1 to 5");
        }
        String q = String.format("insert into customer_ratings(cust_id, trip_id, rating) values (%d, %d, %d)",
                userId, tripId, rating);
        dbHelper.executeCreateOrUpdateQuery(q);
    }

    public double getAverageRatingOfDriver() throws SQLException {
        String q = "select avg(rating) as avg_rating from driver_ratings";
        ResultSet resultSet = dbHelper.executeSelectQuery(q);
        return resultSet.getDouble("avg_rating");
    }

    public double getAverageRatingOfCustomer() throws SQLException {
        String q = "select avg(rating) as avg_rating from customer_ratings";
        ResultSet resultSet = dbHelper.executeSelectQuery(q);
        return resultSet.getDouble("avg_rating");
    }
}
