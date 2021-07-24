package com.dal.cabby.money;

public class CommissionCalculation {

  private static final int defaultPercentage = 20; // percentage when no target achieved
  private static final int percentageOfferOne = 18;
  private static final int percentageOfferTwo = 16;
  private static final int percentageOfferThree = 15;

  // method to calculate the percentage of commission deducted
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
