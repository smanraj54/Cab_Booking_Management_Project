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
}
