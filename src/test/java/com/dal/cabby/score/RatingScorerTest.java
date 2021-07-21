package com.dal.cabby.score;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RatingScorerTest {

    @Test
    void calculateDriverScoreWithOneStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 2.9;
        double actualScore = IRatingScorer.calculateDriverScore(1, 250, 258, 3.5);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateDriverScoreWithTwoStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 3.1;
        double actualScore = IRatingScorer.calculateDriverScore(2, 250, 258, 3.5);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateDriverScoreWithThreeStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 4.5;
        double actualScore = IRatingScorer.calculateDriverScore(3, 258, 258, 4.5);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateDriverScoreWithFourStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 4.6;
        double actualScore = IRatingScorer.calculateDriverScore(4, 258, 258, 4.4);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateDriverScoreWithFiveStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 5.0;
        double actualScore = IRatingScorer.calculateDriverScore(5, 258, 258, 4.7);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithOneStar() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 3.5;
        double actualScore = IRatingScorer.calculateCustomerScore(1, 4.2, 250, 252);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithTwoStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 4.3;
        double actualScore = IRatingScorer.calculateCustomerScore(2, 4.5, 250, 251);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithThreeStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 4.8;
        double actualScore = IRatingScorer.calculateCustomerScore(3, 4.8, 250, 251);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithFourStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 5.0;
        double actualScore = IRatingScorer.calculateCustomerScore(4, 4.9, 250, 251);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithFiveStars() {
        IRatingScorer IRatingScorer = new RatingScorer();
        double expectedScore = 4.6;
        double actualScore = IRatingScorer.calculateCustomerScore(5, 4.5, 250, 253);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }


}