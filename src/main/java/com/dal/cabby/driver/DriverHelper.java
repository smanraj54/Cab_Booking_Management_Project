package com.dal.cabby.driver;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.pojo.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriverHelper {
    DBHelper dbHelper;

    DriverHelper() throws SQLException {
        dbHelper = new DBHelper();
        dbHelper.initialize();
    }

    public static void main(String[] args) throws SQLException, ParseException {
        DriverHelper driverHelper = new DriverHelper();
        driverHelper.completeTrip(1, 1, 1, 5.6, 9.8,
                "2021-01-24 12:35:16", "2021-01-24 12:55:16");
    }

    List<Booking> getUnfinishedBookingLists(int driverId) throws SQLException {
        String q = String.format("select booking_id, cust_id, travel_time, estimated_price, source, destination from bookings where driver_id=%d and is_trip_done=false",
                driverId);
        ResultSet resultSet = dbHelper.executeSelectQuery(q);
        List<Booking> bookingsList = new ArrayList<>();
        while (resultSet.next()) {
            int bookingId = resultSet.getInt("booking_id");
            int custId = resultSet.getInt("cust_id");
            int cabId = resultSet.getInt("cab_id");
            double price = resultSet.getDouble("estimated_price");
            String travelTime = resultSet.getDate("travel_time").toString();
            String source = resultSet.getString("source");
            String destination = resultSet.getString("destination");
            bookingsList.add(new Booking(bookingId, custId, driverId, cabId, source, destination, travelTime, price));
        }
        return bookingsList;
    }

    void markBookingComplete(int bookingId) throws SQLException {
        String q = String.format("update bookings set is_trip_done=true where booking_id=%d", bookingId);
        dbHelper.executeCreateOrUpdateQuery(q);
    }

    void completeTrip(int bookingId, int driverId, int custId, double tripAmount, double distanceCovered,
                      String tripStartTime, String tripEndTime) throws SQLException, ParseException {
        java.sql.Date startTime = getSQLFormatDate(tripStartTime);
        java.sql.Date endTime = getSQLFormatDate(tripEndTime);
        String q = String.format("insert into trips(" +
                        "driver_id, cust_id, booking_id, trip_amount, distance_covered, " +
                        "trip_start_time, trip_end_time) values(%d, %d, %d, %f, %f, '%s', '%s')",
                driverId, custId, bookingId, tripAmount, distanceCovered, tripStartTime, tripEndTime);
        dbHelper.executeCreateOrUpdateQuery(q);
    }

    java.sql.Date getSQLFormatDate(String dateInStr) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(dateInStr);
        return new java.sql.Date(date.getTime());
    }
}
