package com.dal.cabby.money;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.io.PredefinedInputs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// test cases for DriverEarnings class
public class DriverEarningsTest {
    @Test
    public void testDailyEarnings() {
        Inputs inputs = new PredefinedInputs();
        DriverEarnings driverEarnings = new DriverEarnings(inputs);
        Assertions.assertEquals(1000, 1000, "Daily Earning is not correct");
    }
    @Test
    public void testMonthlyEarnings() {
        Inputs inputs = new PredefinedInputs();
        DriverEarnings driverEarnings = new DriverEarnings(inputs);
        Assertions.assertEquals(5000, 5000, "Monthly Earning is not correct");
    }
    @Test
    public void testSpecificPeriodEarnings() {
        Inputs inputs = new PredefinedInputs();
        DriverEarnings driverEarnings = new DriverEarnings(inputs);
        Assertions.assertEquals(10000, 10000, "Earning is not correct for given period");
    }
}
