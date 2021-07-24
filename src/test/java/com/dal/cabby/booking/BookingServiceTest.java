package com.dal.cabby.booking;

import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class BookingServiceTest {

    @BeforeAll
    static void saveBooking() throws SQLException {
        IBookingService iBookingService = new BookingService();
        Booking booking = new Booking();
        booking.setDriverId(1);
        booking.setCustomerId(1);
        booking.setSource("Halifax");
        booking.setDestination("Toronto");
        booking.setPrice(105.5);
        booking.setTravelTime("08/06/2021 02:30");
        booking.setCabId(1);
        try {
            iBookingService.saveBooking(booking);
        } catch (SQLException throwables) {
            Assertions.fail(throwables.getMessage());
        }
    }

    @Test
    void getBooking() throws SQLException {
        IBookingService iBookingService = new BookingService();
        try {
            Booking booking = iBookingService.getBooking(1);
            Assertions.assertEquals(1, booking.getBookingId(), "Wrong booking id");
        } catch (SQLException throwables) {
            Assertions.fail(throwables.getMessage());
        }
    }

    @Test
    void cancelBooking() throws SQLException {
        IBookingService iBookingService = new BookingService();
        iBookingService.cancelBooking(1, UserType.DRIVER);
        Booking booking = iBookingService.getBooking(1);
        Assertions.assertEquals(true, booking.isCancelled(), "Wrong value");
    }

    @Test
    void getCustomerOpenBooking() throws SQLException {
        IBookingService iBookingService = new BookingService();
        Booking booking = iBookingService.getCustomerOpenBooking(1);
        Assertions.assertNotNull(booking, "Wrong value");
    }

    @Test
    void getDriverOpenBookings() throws SQLException {
        IBookingService iBookingService = new BookingService();
        List<Booking> bookingList = iBookingService.getDriverOpenBookings(1);
        Assertions.assertTrue(bookingList.size() >= 1, "Wrong value");
    }

    @Test
    void getCustomerTotalBookings() throws SQLException {
        IBookingService iBookingService = new BookingService();
        int totalBooking = iBookingService.getCustomerTotalBookings(1);
        Assertions.assertTrue(totalBooking >= 1, "Wrong value");
    }

    @Test
    void getDriverTotalBookings() throws SQLException {
        IBookingService iBookingService = new BookingService();
        int totalBooking = iBookingService.getDriverTotalBookings(1);
        Assertions.assertTrue(totalBooking >= 1, "Wrong value");
    }
}