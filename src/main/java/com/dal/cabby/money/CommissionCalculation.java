package com.dal.cabby.money;

/**
 * This class calculates the percentage of commission that cab company
 * takes from the driver. The drivers has to pay less commission if they
 * achieve few targets.
 */
public class CommissionCalculation implements ICommissionCalculation {

    // default percentage
    private static final int defaultPercentage = 20;

    // values at level one
    private static final int percentageLevelOne = 18;
    private static final int ridesLevelOne = 8;
    private static final int distLevelOne = 200;
    private static final int timeLevelOne = 6;

    // values at level two
    private static final int percentageLevelTwo = 16;
    private static final int ridesLevelTwo = 10;
    private static final int distLevelTwo = 250;
    private static final int timeLevelTwo = 7;

    // values at level three
    private static final int percentageLevelThree = 15;
    private static final int ridesLevelThree = 12;
    private static final int distLevelThree = 300;
    private static final int timeLevelThree = 8;

    /**
     * This method is calculating the percentage of commission
     * Parameters:
     *   totalRides - the number of rides completed by the driver on particular day
     *   totalDistance - the total distance covered by driver on particular day
     *   totalTime - the total travel time on a single day
     * Return:
     *   the commission percentage based on the set parameters
     */
    public int getCommissionPercentage(int totalRides, double totalDistance, double totalTime) {
        if (totalRides > ridesLevelThree || totalDistance > distLevelThree || totalTime > timeLevelThree) {
            return percentageLevelThree;
        } else if (totalRides > ridesLevelTwo || totalDistance > distLevelTwo || totalTime > timeLevelTwo) {
            return percentageLevelTwo;
        } else if (totalRides > ridesLevelOne || totalDistance > distLevelOne || totalTime > timeLevelOne) {
            return percentageLevelOne;
        } else {
            return defaultPercentage;
        }
    }
}
