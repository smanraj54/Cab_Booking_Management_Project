package com.dal.cabby.incentives;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverBonusTest {

    @Test
    void giveDriverBonusFiveStars() {
        DriverBonus driverBonus = new DriverBonus();
        int bonus = driverBonus.giveDriverBonus(5);
        Assertions.assertEquals(2, bonus, "Incentive not correct.");
    }

    @Test
    void giveDriverBonusFourStars() {
        DriverBonus driverBonus = new DriverBonus();
        int bonus = driverBonus.giveDriverBonus(4);
        Assertions.assertEquals(1, bonus, "Incentive not correct.");
    }
}