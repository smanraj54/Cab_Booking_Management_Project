package com.dal.cabby.booking;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class BookingServiceTest {
    @Test
    void saveBooking() throws SQLException {
        DBHelper dbHelper = new DBHelper();
        dbHelper.initialize();
        BookingService bookingService = new BookingService(dbHelper);
        Booking booking = new Booking();
        booking.setDriverId(1);
        booking.setCustomerId(1);
        booking.setSource("Halifax");
        booking.setDestination("Toronto");
        booking.setPrice(105.5);
        booking.setTravelTime("08/06/2021 02:30");
        booking.setCabId(1);
        try {
            bookingService.saveBooking(booking);
        } catch (SQLException throwables) {
            Assertions.fail(throwables.getMessage());
        }
    }

    @Test
    void getBooking() throws SQLException {
        DBHelper dbHelper = new DBHelper();
        dbHelper.initialize();
        BookingService bookingService = new BookingService(dbHelper);
        try {
            Booking booking = bookingService.getBooking(1);
            Assertions.assertEquals(1, booking.getBookingId(), "Wrong booking id");
        } catch (SQLException throwables) {
            Assertions.fail(throwables.getMessage());
        }
    }

    //@Test
    void cancelBooking() throws SQLException {
        DBHelper dbHelper = new DBHelper();
        dbHelper.initialize();
        BookingService bookingService = new BookingService(dbHelper);
        bookingService.cancelBooking(1, UserType.DRIVER);
        Booking booking = bookingService.getBooking(1);
        Assertions.assertEquals(true, booking.isCancelled(), "Wrong value");
    }

    @Test
    void getCustomerOpenBooking() throws SQLException {
        DBHelper dbHelper = new DBHelper();
        dbHelper.initialize();
        BookingService bookingService = new BookingService(dbHelper);
        Booking booking = bookingService.getCustomerOpenBooking(1);
        Assertions.assertNotNull(booking, "Wrong value");
    }

    @Test
    void getDriverOpenBookings() throws SQLException {
        DBHelper dbHelper = new DBHelper();
        dbHelper.initialize();
        BookingService bookingService = new BookingService(dbHelper);
        Booking booking = bookingService.getDriverOpenBookings(1);
        Assertions.assertNotNull(booking, "Wrong value");
    }

    @Test
    void getCustomerTotalBookings() throws SQLException {
        DBHelper dbHelper = new DBHelper();
        dbHelper.initialize();
        BookingService bookingService = new BookingService(dbHelper);
        int totalBooking = bookingService.getCustomerTotalBookings(1);
        Assertions.assertTrue(totalBooking >= 1, "Wrong value");
    }

    @Test
    void getDriverTotalBookings() throws SQLException {
        DBHelper dbHelper = new DBHelper();
        dbHelper.initialize();
        BookingService bookingService = new BookingService(dbHelper);
        int totalBooking = bookingService.getDriverTotalBookings(1);
        Assertions.assertTrue(totalBooking >= 1, "Wrong value");
    }
}