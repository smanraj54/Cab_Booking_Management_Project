package com.dal.cabby.money;

/**
 * This interface has the method to get the commission percentage
 */
public interface ICommissionCalculation {

    /**
     * This method is calculating the percentage of commission
     * Parameters:
     *   totalRides - the number of rides completed by the driver on particular day
     *   totalDistance - the total distance covered by driver on particular day
     *   totalTime - the total travel time on a single day
     * Return:
     *   the commission percentage based on the set parameters
     */
    public int getCommissionPercentage(int totalRides, double totalDistance, double totalTime);
}
