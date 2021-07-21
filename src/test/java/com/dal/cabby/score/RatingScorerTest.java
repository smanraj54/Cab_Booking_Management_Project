package com.dal.cabby.score;

import com.dal.cabby.score.RatingScorer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RatingScorerTest {

    @Test
    void calculateDriverScoreWithOneStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 2.9;
        double actualScore = ratingScorer.calculateDriverScore(1, 250, 258, 3.5);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateDriverScoreWithTwoStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 3.1;
        double actualScore = ratingScorer.calculateDriverScore(2, 250, 258, 3.5);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateDriverScoreWithThreeStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 4.5;
        double actualScore = ratingScorer.calculateDriverScore(3, 258, 258, 4.5);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateDriverScoreWithFourStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 4.6;
        double actualScore = ratingScorer.calculateDriverScore(4, 258, 258, 4.4);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateDriverScoreWithFiveStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 5.0;
        double actualScore = ratingScorer.calculateDriverScore(5, 258, 258, 4.7);
        Assertions.assertEquals(expectedScore, actualScore,  "Calculate driver scorer method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithOneStar() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 3.5;
        double actualScore = ratingScorer.calculateCustomerScore(1, 4.2, 250, 252);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithTwoStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 4.3;
        double actualScore = ratingScorer.calculateCustomerScore(2, 4.5, 250, 251);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithThreeStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 4.8;
        double actualScore = ratingScorer.calculateCustomerScore(3, 4.8, 250, 251);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithFourStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 5.0;
        double actualScore = ratingScorer.calculateCustomerScore(4, 4.9, 250, 251);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }

    @Test
    void calculateCustomerScorewithFiveStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 4.6;
        double actualScore = ratingScorer.calculateCustomerScore(5, 4.5, 250, 253);
        Assertions.assertEquals(expectedScore, actualScore, "Calculate customer score method is not working correctly.");
    }


}