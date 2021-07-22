package com.dal.cabby.driver;

import com.dal.cabby.booking.BookingService;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.LoggedInProfile;

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

    DriverHelper(DBHelper dbHelper) throws SQLException {
        this.dbHelper = dbHelper;
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
            bookingsList.add(new Booking(bookingId, custId, driverId, cabId, source, destination, travelTime, price, false));
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

    void cancelBooking() throws SQLException {
        BookingService bookingService = new BookingService(dbHelper);
        Booking booking = bookingService.getDriverOpenBookings(LoggedInProfile.getLoggedInId());
        if (booking == null) {
            System.out.println("You have no booking to cancel.");
            return;
        }
        bookingService.cancelBooking(booking.getBookingId(), UserType.DRIVER);
    }
}
