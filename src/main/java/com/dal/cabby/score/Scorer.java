package com.dal.cabby.score;


// This class wll work on the scores of driver and customers based on rating and ride performance.
public class Scorer {

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


    public double calculateDriverScore(int stars, int eta_pickup, int actualArrivalTime, double initialScore) {

        double score = initialScore;

        int diff = actualArrivalTime - eta_pickup;
        if (diff < 0) {
            score += 0.2;
        } else if (diff > 0) {
            score -= 0.1;
        } else {
            score += 0.1;
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
        if (score >= 5) {
            return 5.0;
        }

        return score;
    }

    public double calculateCustomerScore(int stars, double initialScore, int actualArrivalTime, int boardTime) {

        int diff = actualArrivalTime - boardTime;
        double score = initialScore;

        if (diff < 0 && diff > -1) {
            score += 0.1;
        } else if (diff == 0) {
            score += 0.2;
        } else {
            score -= 0.2;
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
}
