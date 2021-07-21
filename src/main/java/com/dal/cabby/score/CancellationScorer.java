package com.dal.cabby.score;

public class CancellationScorer {

    public double driverCancelled(double initialScore, boolean hasCancelled) {
        double score = initialScore;
        if (hasCancelled) {
            score -= 0.3;
        }
        else {
            System.out.println("Driver has not cancelled.");
            return initialScore;}
        return score;

    }

    public double customerCancelled(double initialScore, boolean hasArrived, boolean hasCancelled) {
        double score = initialScore;
        if (hasCancelled) {
            if (hasArrived) {
                score -= 0.5;
            } else {
                score -= 0.2;
            }
        }
        else {
            System.out.println("Customer has not cancelled.");
            return initialScore;
        }
        return score;

    }
}
