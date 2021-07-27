package com.dal.cabby.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class performs the test cases for commission calculation
 */
public class CommissionTest {

    /**
     * These test cases are used to test the correct percentage output from commission calculation.
     * Below is the logic to calculate commission percentage:
     * No target completed -> 20%
     * If total rides > 8 or Distance travelled > 200 KM/Day or Hours of trip > 6 Hr/day -> 18%
     * If total rides > 10 or Distance travelled > 250 KM/Day or Hours of trip > 7 Hr/day -> 16%
     * If total rides > 12 or Distance travelled > 300 KM/Day or Hours of trip > 8 Hr/day -> 15%
     *
     * Below are the test cases to check each scenario
     */
    @Test
    public void commissionTest1() {
        ICommissionCalculation commission = new CommissionCalculation();

        // all are below first level
        int rides = 7;
        double distance = 150;
        double time = 5;

        assertEquals(20, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest2() {
        ICommissionCalculation commission = new CommissionCalculation();

        int rides = 9;
        double distance = 150;
        double time = 5;

        assertEquals(18, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest3() {
        ICommissionCalculation commission = new CommissionCalculation();
        int rides = 11;
        double distance = 150;
        double time = 5;

        assertEquals(16, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest4() {
        ICommissionCalculation commission = new CommissionCalculation();
        int rides = 13;
        double distance = 150;
        double time = 5;

        assertEquals(15, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest5() {
        ICommissionCalculation commission = new CommissionCalculation();
        int rides = 7;
        double distance = 201;
        double time = 5;

        assertEquals(18, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest6() {
        ICommissionCalculation commission = new CommissionCalculation();
        int rides = 7;
        double distance = 251;
        double time = 5;

        assertEquals(16, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest7() {
        ICommissionCalculation commission = new CommissionCalculation();
        int rides = 7;
        double distance = 301;
        double time = 5;

        assertEquals(15, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest8() {
        ICommissionCalculation commission = new CommissionCalculation();
        int rides = 7;
        double distance = 150;
        double time = 6.1;

        assertEquals(18, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest9() {
        ICommissionCalculation commission = new CommissionCalculation();
        int rides = 7;
        double distance = 150;
        double time = 7.1;

        assertEquals(16, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }

    @Test
    public void commissionTest10() {
        ICommissionCalculation commission = new CommissionCalculation();
        int rides = 7;
        double distance = 150;
        double time = 8.5;

        assertEquals(15, commission.getCommissionPercentage(rides, distance, time),
            "incorrect commission");
    }
}
