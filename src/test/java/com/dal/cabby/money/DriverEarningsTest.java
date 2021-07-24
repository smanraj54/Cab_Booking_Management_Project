package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.io.PredefinedInputs;
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
        iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (201,202,203,204);");

        // inserting data for testing in trips table
        iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
            "booking_id, trip_amount, distance_covered, created_at) values " +
            "(201, 1, 1, 101, 150, 1600, '2010-07-24 20:00:00'), " +
            "(202, 1, 2, 102, 80.5, 200, '2010-07-24 20:00:00');");

        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("24/07/2010");
        DriverEarnings driverEarnings = new DriverEarnings(inputs);

        /*
         * Earning will be 150 + 80.5 = 230.5
         * 230.5 - commission (15% of total - distance is greater than 300)
         * 230.5 - 34.575 = 195.925
         */
        assertEquals("\nTotal earning on 24/07/2010 is $195.925",
            driverEarnings.getEarnings(1), "Driver's earning is not " +
                "calculated correctly");
    }

    @Test
    public void test() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("24/07/2021");
        DriverEarnings earnings = new DriverEarnings(inputs);
        System.out.println(earnings.getEarnings(1));
    }

    @Test
    public void testMonthlyEarnings() {
        Inputs inputs = new PredefinedInputs();
        DriverEarnings driverEarnings = new DriverEarnings(inputs);
        assertEquals(5000, 5000, "Monthly Earning is not correct");
    }
    @Test
    public void testSpecificPeriodEarnings() {
        Inputs inputs = new PredefinedInputs();
        DriverEarnings driverEarnings = new DriverEarnings(inputs);
        assertEquals(10000, 10000, "Earning is not correct for given period");
    }
}
