package com.dal.cabby.profiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScorerTest {

    @Test
    void calculateDriverScoreWithOneStars() {
        Scorer scorer = new Scorer();
        double expectedScore = 2.9;
        double actualScore = scorer.calculateDriverScore(1, 250, 258, 3.5);

        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    public void driverCancelledTrueTest() {
        Scorer scorer = new Scorer();
        double initialScore = 4.0;
        double newScore = scorer.driverCancelled(initialScore, true);
        Assertions.assertEquals(3.7, newScore, "Incorrect new score");
    }

    @Test
    public void driverCancelledFalseTest() {
        Scorer scorer = new Scorer();
        double initialScore = 3.9;
        double newScore = scorer.driverCancelled(initialScore, false);
        Assertions.assertEquals(initialScore, newScore, "Incorrect new score");
    }

    @Test
    void calculateCustomerScore() {

    }
}