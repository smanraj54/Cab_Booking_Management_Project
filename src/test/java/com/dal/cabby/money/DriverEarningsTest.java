package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

// test cases for DriverEarnings class
public class DriverEarningsTest {

    IPersistence iPersistence = DBHelper.getInstance();

    public DriverEarningsTest() throws SQLException {
    }

    @Test
    public void testDailyEarnings() throws SQLException {

        // cleaning data for testing
        iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (201,202);");
        iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (201,202);");

        // inserting data for testing in booking table
        iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
            "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
            "(201, '2010-07-24 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto'), " +
            "(202, '2010-07-24 20:00:00', 1, 2, 2, 150, 'Winnipeg', 'Toronto');");

        // inserting data for testing in trips table
        iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
            "booking_id, trip_amount, distance_covered, created_at) values " +
            "(201, 1, 1, 201, 150, 1600, '2010-07-24 20:00:00'), " +
            "(202, 1, 2, 202, 80.5, 200, '2010-07-24 20:00:00');");

        DriverEarnings driverEarnings = new DriverEarnings();

        /*
         * Earning will be 150 + 80.5 = 230.5
         * 230.5 - commission (15% of total - distance is greater than 300)
         * 230.5 - 34.575 = 195.925
         */
        assertEquals("\nTotal earning on 24/07/2010 is $195.93",
            driverEarnings.getDailyEarnings(1, "24/07/2010"), "Driver's earning is not " +
                "calculated correctly");
    }

    @Test
    public void testMonthlyEarnings() throws SQLException {

        // cleaning data for testing
        iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (201, 202, 203, 204);");
        iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (201, 202, 203, 204);");

        // inserting data for testing in booking table
        iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
            "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
            "(201, '2010-07-01 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto'), " +
            "(202, '2010-07-01 20:00:00', 1, 2, 1, 150, 'Winnipeg', 'Toronto'), " +
            "(203, '2010-07-31 20:00:00', 1, 3, 1, 150, 'Halifax', 'Toronto'), " +
            "(204, '2010-07-31 20:00:00', 1, 4, 1, 150, 'Winnipeg', 'Toronto');");

        // inserting data for testing in trips table
        iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
            "booking_id, trip_amount, distance_covered, created_at) values " +
            "(201, 1, 1, 201, 150, 1600, '2010-07-01 20:00:00'), " +
            "(202, 1, 2, 202, 80.5, 200, '2010-07-01 20:00:00'), " +
            "(203, 1, 3, 203, 150, 1600, '2010-07-31 20:00:00'), " +
            "(204, 1, 4, 204, 80.5, 200, '2010-07-31 20:00:00');");

        /*
         * Earning will be (150 + 80.5 = 230.5) * 2
         * 230.5 - commission (15% of total - distance is greater than 300)
         * (230.5 - 34.575) * 2 = 195.925 * 2 = 391.85
         */
        DriverEarnings driverEarnings = new DriverEarnings();
        assertEquals("\nThe total earnings in 07/2010 is $391.85",
            driverEarnings.getMonthlyEarnings(1, "07/2010"), "Driver's " +
                "earning is not calculated correctly");
    }

    @Test
    public void testSpecificPeriodEarnings() throws SQLException {

        // cleaning data for testing
        iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (201, 202, 203, 204);");
        iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (201, 202, 203, 204);");

        // inserting data for testing in booking table
        iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
            "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
            "(201, '2010-12-28 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto'), " +
            "(202, '2010-12-29 20:00:00', 1, 2, 1, 150, 'Winnipeg', 'Toronto'), " +
            "(203, '2010-12-30 20:00:00', 1, 3, 1, 150, 'Halifax', 'Toronto'), " +
            "(204, '2010-12-31 20:00:00', 1, 4, 1, 150, 'Winnipeg', 'Toronto');");

        // inserting data for testing in trips table
        iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
            "booking_id, trip_amount, distance_covered, created_at) values " +
            "(201, 1, 1, 201, 150, 1600, '2010-12-28 20:00:00'), " +
            "(202, 1, 2, 202, 80.5, 200, '2010-12-28 20:00:00'), " +
            "(203, 1, 3, 203, 150, 1600, '2010-12-31 20:00:00'), " +
            "(204, 1, 4, 204, 80.5, 200, '2010-12-31 20:00:00');");

        /*
         * Earning on 28th will be (150 + 80.5 = 230.5)
         * Earning on 31st will be (150 + 80.5 = 230.5)
         * 230.5 - commission (15% of total because distance is greater than 300)
         * (230.5 - 34.575) * 2 = 195.925 * 2 = 391.85
         */
        DriverEarnings driverEarnings = new DriverEarnings();
        assertEquals("\nTotal earnings between 28/12/2010 and 31/12/2010 is $391.85",
            driverEarnings.getSpecificPeriodEarnings(1, "28/12/2010", "31/12/2010"),
            "Driver's earning is not calculated correctly");
    }

    @Test
    public void testCommission1() throws SQLException {

        // cleaning data for testing
        iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (201, 202);");
        iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (201, 202);");

        // inserting data for testing in booking table
        iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
            "created_at, driver_id, cust_id, cab_id, estimated_price,  source, destination) values " +
            "(201, '2010-07-24 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto');");

        // inserting data for testing in trips table
        iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
            "booking_id, trip_amount, distance_covered, trip_start_time, trip_end_time, created_at) values " +
            "(201, 1, 1, 201, 150, 100, '2010-07-24 20:00:00', '2010-07-24 21:00:00', '2010-07-24 20:00:00');");

        /*
         * total rides: 1, total distance: 100, total time: 1, So 20% commission will be deducted
         * Total Amount: 150, After deduction of 20% commission: 120
         */
        DriverEarnings earnings = new DriverEarnings();
        assertEquals("\nTotal earning on 24/07/2010 is $120.00",
            earnings.getDailyEarnings(1, "24/07/2010"),
            "Earning is not calculated correctly");
    }

    @Test
    public void testCommission2() throws SQLException {

        // cleaning data for testing
        iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (201, 202);");
        iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (201, 202);");

        // inserting data for testing in booking table
        iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
            "created_at, driver_id, cust_id, cab_id, estimated_price,  source, destination) values " +
            "(201, '2010-07-24 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto');");

        // inserting data for testing in trips table
        iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
            "booking_id, trip_amount, distance_covered, trip_start_time, trip_end_time, created_at) values " +
            "(201, 1, 1, 201, 150, 250, '2010-07-24 20:00:00', '2010-07-24 21:00:00', '2010-07-24 20:00:00');");

        /*
         * total rides: 1, total distance: 250, total time: 1 hr, So 18% commission will be deducted
         * Total Amount: 150, After deduction of 18% commission: 123
         */
        DriverEarnings earnings = new DriverEarnings();
        assertEquals("\nTotal earning on 24/07/2010 is $123.00",
            earnings.getDailyEarnings(1, "24/07/2010"),
            "Earning is not calculated correctly");
    }

    @Test
    public void testCommission3() throws SQLException {

        // cleaning data for testing
        iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (201, 202);");
        iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (201, 202);");

        // inserting data for testing in booking table
        iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
            "created_at, driver_id, cust_id, cab_id, estimated_price,  source, destination) values " +
            "(201, '2010-07-24 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto');");

        // inserting data for testing in trips table
        iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
            "booking_id, trip_amount, distance_covered, trip_start_time, trip_end_time, created_at) values " +
            "(201, 1, 1, 201, 150, 251, '2010-07-24 20:00:00', '2010-07-24 21:00:00', '2010-07-24 20:00:00');");

        /*
         * total rides: 1, total distance: 251, total time: 1 hr, So 16% commission will be deducted
         * Total Amount: 150, After deduction of 18% commission: 126
         */
        DriverEarnings earnings = new DriverEarnings();
        assertEquals("\nTotal earning on 24/07/2010 is $126.00",
            earnings.getDailyEarnings(1, "24/07/2010"),
            "Earning is not calculated correctly");
    }

    @Test
    public void testCommission4() throws SQLException {

        // cleaning data for testing
        iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (201, 202);");
        iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (201, 202);");

        // inserting data for testing in booking table
        iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
            "created_at, driver_id, cust_id, cab_id, estimated_price,  source, destination) values " +
            "(201, '2010-07-24 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto');");

        // inserting data for testing in trips table
        iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
            "booking_id, trip_amount, distance_covered, trip_start_time, trip_end_time, created_at) values " +
            "(201, 1, 1, 201, 150, 350, '2010-07-24 20:00:00', '2010-07-24 21:00:00', '2010-07-24 20:00:00');");

        /*
         * total rides: 1, total distance: 350, total time: 1 hr, So 15% commission will be deducted
         * Total Amount: 150, After deduction of 18% commission: 127.5
         */
        DriverEarnings earnings = new DriverEarnings();
        assertEquals("\nTotal earning on 24/07/2010 is $127.50",
            earnings.getDailyEarnings(1, "24/07/2010"),
            "Earning is not calculated correctly");
    }
}
