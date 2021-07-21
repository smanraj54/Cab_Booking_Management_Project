package com.dal.cabby.profiles;

import com.dal.cabby.score.Scorer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScorerTest {

    @Test
    void calculateDriverScoreWithOneStars() {
        Scorer scorer = new Scorer();
        double expectedScore = 2.9;
        double actualScore = scorer.calculateDriverScore(1, 250, 258, 3.5);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    void calculateDriverScoreWithTwoStars() {
        Scorer scorer = new Scorer();
        double expectedScore = 3.1;
        double actualScore = scorer.calculateDriverScore(2, 250, 258, 3.5);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    void calculateDriverScoreWithThreeStars() {
        Scorer scorer = new Scorer();
        double expectedScore = 4.5;
        double actualScore = scorer.calculateDriverScore(3, 258, 258, 4.5);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    void calculateDriverScoreWithFourStars() {
        Scorer scorer = new Scorer();
        double expectedScore = 4.6;
        double actualScore = scorer.calculateDriverScore(4, 258, 258, 4.4);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }

    @Test
    void calculateDriverScoreWithFiveStars() {
        Scorer scorer = new Scorer();
        double expectedScore = 5.0;
        double actualScore = scorer.calculateDriverScore(5, 258, 258, 4.7);
        Assertions.assertEquals(actualScore, expectedScore, "Calculate drive score method is not working");
    }



    @Test
    void calculateCustomerScorewithOneStar() {

    }


}