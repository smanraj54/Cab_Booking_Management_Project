package com.dal.cabby.profiles;


// This class wll work on the scores of driver and customers based on rating and ride performance.
public class Scorer {

    public double calculateDriverScore(int stars, int eta_pickup, int actualArrivalTime, double initialScore, boolean hasCancelled) {

        double score = initialScore;
        if (score >= 5) {
            return 5.0;
        }
        int diff = actualArrivalTime - eta_pickup;
        if (diff < 0) {
            score += 0.2;
        } else if (diff > 0) {
            score -= 0.1;
        } else {
            score += 1;
        }
        if (hasCancelled) {
            score -= 0.3;
        }
        if (stars == 5) {
            score += 0.3;
        } else if (stars == 4) {
            score += 0.1;
        } else if (stars == 3) {
            score -= 0.1;
        } else if (stars == 2) {
            score -= 0.3;
        } else if (stars == 1) {
            score -= 0.5;
        }
        return score;
    }

    public double calculateCustomerScore() {


        return 0.0;
    }
}
