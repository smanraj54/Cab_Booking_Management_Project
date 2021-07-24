package com.dal.cabby.incentives;

public class CustomerBonus implements ICustomerBonus {
    private final int FIVE_STAR_BONUS = 10;
    private final int FOUR_STAR_BONUS = 5;
    private final int LESS_THEN_FOUR_STAR_BONUS = 0;

    @Override
    public int giveCustomerBonus(int rating) {
        if (rating == 5) {
            System.out.println("Congratulations! You are eligible for a 10% cashback on your last trip.");
            return FIVE_STAR_BONUS;
        } else if (rating == 4) {
            System.out.println("Congratulations! You are eligible for a 5% cashback on your last trip.");
            return FOUR_STAR_BONUS;
        } else {
            System.out.println("Trip Ended Successfully.");
            return LESS_THEN_FOUR_STAR_BONUS;
        }
    }
}
