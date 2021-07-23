package com.dal.cabby.rating;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ratings implements IRatings {
    IPersistence IPersistence;

    public Ratings() throws SQLException {
        IPersistence = new DBHelper();
        IPersistence.initialize();
    }

    @Override
    public void addDriverRating(int driverId, int tripId, int rating) throws SQLException {
        if (rating<1 || rating>5) {
            throw new RuntimeException("Rating can only be in the range of 1 to 5");
        }
        String q = String.format("insert into driver_ratings(driver_id, trip_id, rating) values (%d, %d, %d)",
                driverId, tripId, rating);
        IPersistence.executeCreateOrUpdateQuery(q);
    }

    @Override
    public void addCustomerRating(int userId, int tripId, int rating) throws SQLException {
        if (rating<1 || rating>5) {
            throw new RuntimeException("Rating can only be in the range of 1 to 5");
        }
        String q = String.format("insert into customer_ratings(cust_id, trip_id, rating) values (%d, %d, %d)",
                userId, tripId, rating);
        IPersistence.executeCreateOrUpdateQuery(q);
    }

    @Override
    public double getAverageRatingOfDriver(int driver_id) throws SQLException {
        String q = String.format("select avg(rating) as avg_rating from driver_ratings where driver_id=%d", driver_id);
        ResultSet resultSet = IPersistence.executeSelectQuery(q);
        while (resultSet.next()) {
            return resultSet.getDouble("avg_rating");
        }
        return 0.0;
    }

    @Override
    public double getAverageRatingOfCustomer(int cust_id) throws SQLException {
        String q = String.format("select avg(rating) as avg_rating from customer_ratings where cust_id=%d",cust_id);
        ResultSet resultSet = IPersistence.executeSelectQuery(q);
        while(resultSet.next()) {
            return resultSet.getDouble("avg_rating");
        }
       return 0.0;
    }
}
