package com.dal.cabby.incentives;

public class CustomerBonus {

    public int giveCustomerBonus(int rating) {
        if (rating == 5) {
            System.out.println("Congratulations! You are eligible for a 10% cashback on your last trip.");
            return 10;
        } else if (rating == 4) {
            System.out.println("Congratulations! You are eligible for a 5% cashback on your last trip.");
            return 5;
        } else {
            System.out.println("Trip Ended Successfully.");
            return 0;
        }
    }
}
