package com.dal.cabby.score;

public interface IRatingScorer {
    double calculateDriverScore(int stars, int eta_pickup, int actualArrivalTime, double initialScore);

    double calculateCustomerScore(int stars, double initialScore, int actualArrivalTime, int boardTime);
}
