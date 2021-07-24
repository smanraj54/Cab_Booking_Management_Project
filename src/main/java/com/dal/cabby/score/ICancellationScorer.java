package com.dal.cabby.score;

public interface ICancellationScorer {
    double driverCancelled(double initialScore, boolean hasCancelled);

    double customerCancelled(double initialScore, boolean hasArrived, boolean hasCancelled);
}
