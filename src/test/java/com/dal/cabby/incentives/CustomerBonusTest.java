package com.dal.cabby.incentives;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBonusTest {

    @Test
    void giveCustomerBonusFiveStars() {
        CustomerBonus customerBonus = new CustomerBonus();
        int bonus = customerBonus.giveCustomerBonus(5);
        Assertions.assertEquals(10, bonus, "Cashback not correct.");
    }

    @Test
    void giveCustomerBonusFourStars() {
        CustomerBonus customerBonus = new CustomerBonus();
        int bonus = customerBonus.giveCustomerBonus(4);
        Assertions.assertEquals(5, bonus, "Cashback not correct.");
    }

    @Test
    void giveCustomerBonusThreeStars() {
        CustomerBonus customerBonus = new CustomerBonus();
        int bonus = customerBonus.giveCustomerBonus(3);
        Assertions.assertEquals(0, bonus, "Cashback not correct.");
    }

}
