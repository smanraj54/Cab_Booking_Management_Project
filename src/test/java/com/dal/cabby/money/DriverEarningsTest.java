package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.io.PredefinedInputs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

// test cases for DriverEarnings class
public class DriverEarningsTest {

    IPersistence iPersistence = DBHelper.getInstance();

    public DriverEarningsTest() throws SQLException {
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
        Assertions.assertEquals(5000, 5000, "Monthly Earning is not correct");
    }
    @Test
    public void testSpecificPeriodEarnings() {
        Inputs inputs = new PredefinedInputs();
        DriverEarnings driverEarnings = new DriverEarnings(inputs);
        Assertions.assertEquals(10000, 10000, "Earning is not correct for given period");
    }
}
