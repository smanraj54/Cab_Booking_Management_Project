package com.dal.cabby.incentives;

public class DriverBonus implements IDriverBonus {

    @Override
    public int giveDriverBonus(int rating) {
        if (rating == 5) {
            System.out.println("Congratulations! You are eligible for a 2% bonus on your last trip.");
            return 2;
        } else if (rating == 4) {
            System.out.println("Congratulations! You are eligible for a 1% bonus on your last trip.");
            return 1;
        } else {
            System.out.println("Trip Ended Successfully.");
            return 0;
        }
    }
}

