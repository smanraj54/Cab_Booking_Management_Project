package com.dal.cabby.profiles;

import com.dal.cabby.dbHelper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ratings {
    DBHelper dbHelper;

    Ratings() {
        dbHelper = new DBHelper();
    }

    void addDriverRating(int driverId, int tripId, int rating) throws SQLException {
        String q = String.format("insert into driver_ratings(driver_id, trip_id, rating) values (%d, %d, %d))",
                driverId, tripId, rating);
        dbHelper.executeCreateOrUpdateQuery(q);
    }

    void addCustomerRating(int userId, int tripId, int rating) throws SQLException {
        String q = String.format("insert into customer_ratings(cust_id, trip_id, rating) values (%d, %d, %d))",
                userId, tripId, rating);
        dbHelper.executeCreateOrUpdateQuery(q);
    }

    double getAverageRatingOfDriver() throws SQLException {
        String q = "select avg(rating) as avg_rating from driver_ratings";
        ResultSet resultSet = dbHelper.executeSelectQuery(q);
        return resultSet.getDouble("avg_rating");
    }

    double getAverageRatingOfCustomer() throws SQLException {
        String q = "select avg(rating) as avg_rating from customer_ratings";
        ResultSet resultSet = dbHelper.executeSelectQuery(q);
        return resultSet.getDouble("avg_rating");
    }

}
