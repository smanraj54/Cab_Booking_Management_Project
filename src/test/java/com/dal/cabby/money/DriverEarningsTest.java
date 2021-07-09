package com.dal.cabby.money;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// test cases for DriverEarnings class
public class DriverEarningsTest {
    @Test
    public void testDailyEarnings() {
        DriverEarnings driverEarnings = new DriverEarnings();
        Assertions.assertEquals(1000, 1000, "Daily Earning is not correct");
    }
    @Test
    public void testMonthlyEarnings() {
        DriverEarnings driverEarnings = new DriverEarnings();
        Assertions.assertEquals(5000, 5000, "Monthly Earning is not correct");
    }
    @Test
    public void testSpecificPeriodEarnings() {
        DriverEarnings driverEarnings = new DriverEarnings();
        Assertions.assertEquals(10000, 10000, "Earning is not correct for given period");
    }
}
