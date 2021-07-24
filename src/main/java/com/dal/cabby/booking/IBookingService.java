package com.dal.cabby.booking;

import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface IBookingService {
    void saveBooking(Booking booking) throws SQLException;

    Booking getBooking(int booking_id) throws SQLException;

    void cancelBooking(int booking_id, UserType cancelledBy) throws SQLException;

    Booking getCustomerOpenBooking(int cust_id) throws SQLException;

    List<Booking> getDriverOpenBookings(int driver_id) throws SQLException;

    int getCustomerTotalBookings(int cust_id) throws SQLException;

    int getDriverTotalBookings(int driver_id) throws SQLException;

    void markBookingComplete(int bookingId) throws SQLException;

    void completeTrip(int bookingId, int driverId, int custId, double tripAmount, double distanceCovered,
                      String tripStartTime, String tripEndTime) throws SQLException, ParseException;
}
