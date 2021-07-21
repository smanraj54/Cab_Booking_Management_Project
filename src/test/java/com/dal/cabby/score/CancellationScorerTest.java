package com.dal.cabby.score;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CancellationScorerTest {

    @Test
    public void driverCancelledTrueTest() {
        CancellationScorer cancellationScorer = new CancellationScorer();
        double initialScore = 4.0;
        double newScore = cancellationScorer.driverCancelled(initialScore, true);
        Assertions.assertEquals(3.7, newScore, "Incorrect new score");
    }

    @Test
    public void driverCancelledFalseTest() {
        CancellationScorer cancellationScorer = new CancellationScorer();
        double initialScore = 3.9;
        double newScore = cancellationScorer.driverCancelled(initialScore, false);
        Assertions.assertEquals(initialScore, newScore, "Incorrect new score");
    }

    @Test
    void customerCancelledTrue_hasArrivedTrueTest() {
        CancellationScorer cancellationScorer = new CancellationScorer();
        double initialScore = 3.9;
        double newScore = cancellationScorer.customerCancelled(initialScore, true, true);
        Assertions.assertEquals(3.4, newScore, "Incorrect new score");
    }
}