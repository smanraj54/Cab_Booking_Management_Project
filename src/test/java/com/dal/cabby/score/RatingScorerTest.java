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
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    void calculateDriverScoreWithTwoStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 3.1;
        double actualScore = ratingScorer.calculateDriverScore(2, 250, 258, 3.5);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    void calculateDriverScoreWithThreeStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 4.5;
        double actualScore = ratingScorer.calculateDriverScore(3, 258, 258, 4.5);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    void calculateDriverScoreWithFourStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 4.6;
        double actualScore = ratingScorer.calculateDriverScore(4, 258, 258, 4.4);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    void calculateDriverScoreWithFiveStars() {
        RatingScorer ratingScorer = new RatingScorer();
        double expectedScore = 5.0;
        double actualScore = ratingScorer.calculateDriverScore(5, 258, 258, 4.7);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }



    @Test
    void calculateCustomerScorewithOneStar() {

    }


}