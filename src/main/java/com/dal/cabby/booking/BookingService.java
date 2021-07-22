package com.dal.cabby.booking;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Booking;
import com.dal.cabby.pojo.Profile;
import com.dal.cabby.pojo.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingService {
    private DBHelper dbHelper;

    public BookingService(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void saveBooking(Booking booking) throws SQLException {
        String query = String.format("insert into bookings(driver_id, cust_id, cab_id, travel_time, estimated_price, source, destination) values(%d, %d, %d, '%s', %f, '%s', '%s')",
                booking.getDriverId(), booking.getCustomerId(), booking.getCabId(), booking.getTravelTime(), booking.getPrice(), booking.getSource(), booking.getDestination());
        dbHelper.executeCreateOrUpdateQuery(query);
        System.out.println("Congratulations!. Your booking is confirmed!");
    }

    public Booking getBooking(int booking_id) throws SQLException {
        String query = String.format("select * from bookings where booking_id=%d;", booking_id);
        ResultSet resultSet = dbHelper.executeSelectQuery(query);
        while (resultSet.next()) {
            int customerId = resultSet.getInt("cust_id");
            int driverId = resultSet.getInt("driver_id");
            int cabId = resultSet.getInt("cab_id");
            String source = resultSet.getString("source");
            String destination = resultSet.getString("destination");
            String travelTime = resultSet.getString("travel_time");
            double price = resultSet.getDouble("estimated_price");
            boolean isCancelled = resultSet.getBoolean("is_cancelled");
            boolean hasDriverCancelled = resultSet.getBoolean("has_driver_cancelled");
            boolean hasCustomerCancelled = resultSet.getBoolean("has_customer_cancelled");
            Booking booking = new Booking(booking_id, customerId, driverId, cabId, source, destination, travelTime, price, isCancelled);
            booking.setHasDriverCancelled(hasDriverCancelled);
            booking.setCancelled(hasCustomerCancelled);
            return booking;
        }
        return null;
    }

    public void cancelBooking(int booking_id, UserType cancelledBy) throws SQLException {
        boolean hasDriverCancelled = false;
        boolean hasCustomerCancelled = false;
        if (cancelledBy == UserType.DRIVER) {
            hasDriverCancelled = true;
        } else if (cancelledBy == UserType.CUSTOMER) {
            hasCustomerCancelled = true;
        } else {
            throw new RuntimeException("Wrong usertype: " + cancelledBy.toString());
        }
        String query = String.format("update bookings set is_cancelled=%b, has_driver_cancelled=%b, has_customer_cancelled=%b where booking_id=%d;",
                true, hasDriverCancelled, hasCustomerCancelled, booking_id);
        dbHelper.executeCreateOrUpdateQuery(query);
    }

    public Booking getCustomerOpenBooking(int cust_id) throws SQLException {
        String query = String.format("select * from bookings where cust_id=%d and is_trip_done=false;", cust_id);
        ResultSet resultSet = dbHelper.executeSelectQuery(query);
        while (resultSet.next()) {
            int bookingId = resultSet.getInt("booking_id");;
            int customerId = resultSet.getInt("cust_id");
            int driverId = resultSet.getInt("driver_id");
            int cabId = resultSet.getInt("cab_id");
            String source = resultSet.getString("source");
            String destination = resultSet.getString("destination");
            String travelTime = resultSet.getString("travel_time");
            double price = resultSet.getDouble("estimated_price");
            Booking booking = new Booking(bookingId, customerId, driverId, cabId, source, destination, travelTime, price, false);
            return booking;
        }
        return null;
    }

    public Booking getDriverOpenBookings(int driver_id) throws SQLException {
        String query = String.format("select * from bookings where driver_id=%d and is_trip_done=false;", driver_id);
        ResultSet resultSet = dbHelper.executeSelectQuery(query);
        while (resultSet.next()) {
            int bookingId = resultSet.getInt("booking_id");;
            int customerId = resultSet.getInt("cust_id");
            int driverId = resultSet.getInt("driver_id");
            int cabId = resultSet.getInt("cab_id");
            String source = resultSet.getString("source");
            String destination = resultSet.getString("destination");
            String travelTime = resultSet.getString("travel_time");
            double price = resultSet.getDouble("estimated_price");
            return new Booking(bookingId, customerId, driverId, cabId, source, destination, travelTime, price, false);
        }
        return null;
    }

    public int getCustomerTotalBookings(int cust_id) throws SQLException {
        String query = String.format("select count(*) from bookings where cust_id=%d;", cust_id);
        ResultSet resultSet = dbHelper.executeSelectQuery(query);
        while (resultSet.next()) {
            return resultSet.getInt(1);

        }
        return -1;
    }

    public int getDriverTotalBookings(int driver_id) throws SQLException {
        String query = String.format("select count(*) from bookings where driver_id=%d;", driver_id);
        ResultSet resultSet = dbHelper.executeSelectQuery(query);
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }
}
