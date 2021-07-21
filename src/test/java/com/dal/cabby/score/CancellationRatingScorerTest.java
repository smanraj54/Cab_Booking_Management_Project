package com.dal.cabby.score;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CancellationRatingScorerTest {

    @Test
    public void driverCancelledTrueTest() {
        ICancellationScorer ICancellationScorer = new CancellationScorer();
        double initialScore = 4.0;
        double newScore = ICancellationScorer.driverCancelled(initialScore, true);
        Assertions.assertEquals(3.7, newScore, "Incorrect new score");
    }

    @Test
    public void driverCancelledFalseTest() {
        ICancellationScorer ICancellationScorer = new CancellationScorer();
        double initialScore = 3.9;
        double newScore = ICancellationScorer.driverCancelled(initialScore, false);
        Assertions.assertEquals(initialScore, newScore, "Incorrect new score");
    }

    @Test
    void customerCancelledTrue_hasArrivedTrueTest() {
        ICancellationScorer ICancellationScorer = new CancellationScorer();
        double initialScore = 3.9;
        double newScore = ICancellationScorer.customerCancelled(initialScore, true, true);
        Assertions.assertEquals(3.4, newScore, "Incorrect new score");
    }
}