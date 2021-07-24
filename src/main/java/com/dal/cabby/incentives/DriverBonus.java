package com.dal.cabby.incentives;

public class DriverBonus implements IDriverBonus {
    private final int FIVE_STAR_BONUS = 2;
    private final int FOUR_STAR_BONUS = 1;
    private final int LESS_THEN_FOUR_STAR_BONUS = 0;

    @Override
    public int giveDriverBonus(int rating) {
        if (rating == 5) {
            System.out.println("Congratulations! You are eligible for a 2% bonus on your last trip.");
            return FIVE_STAR_BONUS;
        } else if (rating == 4) {
            System.out.println("Congratulations! You are eligible for a 1% bonus on your last trip.");
            return FOUR_STAR_BONUS;
        } else {
            System.out.println("Trip Ended Successfully.");
            return LESS_THEN_FOUR_STAR_BONUS;
        }
    }
}

