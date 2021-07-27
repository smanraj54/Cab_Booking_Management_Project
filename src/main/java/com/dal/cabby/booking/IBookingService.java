package com.dal.cabby.booking;

import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/*
This interface lists down all the methods for the creating new bookings and
managing the existing bookings.
 */
public interface IBookingService {

    /**
     * Create new booking.
     * @param booking - Booking parameter which conatins all information about the booking.
     * @throws SQLException
     */
    void saveBooking(Booking booking) throws SQLException;

    /**
     *
     * @param booking_id - booking_id by which booking will be fetched.
     * @return Return the booking for the given booking id.
     * @throws SQLException
     */
    Booking getBooking(int booking_id) throws SQLException;

    /**
     * Cancel the existing booking.
     * @param booking_id - booking id which need to be cancelled.
     * @param cancelledBy - User who cancelled the booking: Driver or Customer.
     * @throws SQLException
     */
    void cancelBooking(int booking_id, UserType cancelledBy) throws SQLException;

    /**
     * Get the customer open booking.
     * @param cust_id - customer_id for which booking need to be fetched.
     * @return - Return the booking which is cancelled.
     * @throws SQLException
     */
    Booking getCustomerOpenBooking(int cust_id) throws SQLException;

    /**
     * Get the driver open booking.
     * @param driver_id - driver id for which booking need to be fetched.
     * @return - Return the booking which is cancelled.
     * @throws SQLException
     */
    List<Booking> getDriverOpenBookings(int driver_id) throws SQLException;

    /**
     * Fetch total booking done by the customer.
     * @param cust_id - customer_id for which number of total booking done.
     * @return - return the total booking done by the customer.
     * @throws SQLException
     */
    int getCustomerTotalBookings(int cust_id) throws SQLException;

    /**
     * Fetch total booking done by the driver.
     * @param driver_id - driver_id for which number of total booking done.
     * @return - return the total booking done by the driver.
     * @throws SQLException
     */
    int getDriverTotalBookings(int driver_id) throws SQLException;

    /**
     * Mark the booking complete, if it got converetd into trips.
     * @param bookingId - booking_id which got completed.
     * @throws SQLException
     */
    void markBookingComplete(int bookingId) throws SQLException;

    /**
     * Complete the given booking
     * @param bookingId - booking id of the booking.
     * @param driverId - driver id of the booking.
     * @param custId - customer id of the booking.
     * @param tripAmount - total fare of the booking.
     * @param distanceCovered - total distance between source and destination of the booking.
     * @param tripStartTime - start time of the booking.
     * @param tripEndTime - end time of the booking
     * @throws SQLException
     * @throws ParseException
     */
    void completeTrip(int bookingId, int driverId, int custId, double tripAmount, double distanceCovered,
                      String tripStartTime, String tripEndTime) throws SQLException, ParseException;
}
