package com.dal.cabby.money;

/**
 * This class calculates the percentage of commission that cab company
 * takes from the driver. The drivers has to pay less commission if they
 * achieve few targets.
 */
public class CommissionCalculation {

    private static final int defaultPercentage = 20; // percentage when no target achieved
    private static final int percentageOfferOne = 18;
    private static final int percentageOfferTwo = 16;
    private static final int percentageOfferThree = 15;

    /**
     * This method is calculating the percentage of commission
     * Parameters:
     * totalRides - the number of rides completed by the driver on particular day
     * totalDistance - the total distance covered by driver on particular day
     * totalTime - the total travel time on a single day
     * Return:
     * the commission percentage based on the set parameters
     */
    public int getCommissionPercentage(int totalRides, double totalDistance, double totalTime) {
        if (totalRides > 12 || totalDistance > 300 || totalTime > 8) {
            return percentageOfferThree;
        } else if (totalRides > 10 || totalDistance > 250 || totalTime > 7) {
            return percentageOfferTwo;
        } else if (totalRides > 8 || totalDistance > 200 || totalTime > 6) {
            return percentageOfferOne;
        } else {
            return defaultPercentage;
        }
    }
}
