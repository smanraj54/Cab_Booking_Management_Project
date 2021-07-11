package com.dal.cabby.rides;

import java.util.Scanner;

public class DisplayRides {

    public DisplayRides() {
    }

    public void getRides() {
        ridesPage();
    }

    private void ridesPage() {
        while (true) {
            System.out.println("\n**** Rides Page ****");
            System.out.println("\t1. Daily rides");
            System.out.println("\t2. Monthly rides");
            System.out.println("\t3. Rides between a specific period");
            System.out.println("\t4. Return to the previous page");
            System.out.println("Please enter a selection: ");
            Scanner sc = new Scanner(System.in);
            int selection = sc.nextInt();
            switch (selection) {
                case 1:
                    getDailyRides();
                    break;
                case 2:
                    getMonthlyRides();
                    break;
                case 3:
                    getSpecificPeriodRides();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\nInvalid selection");
            }
        }
    }

    // method to get daily rides
    public void getDailyRides() {
    }

    // method to get monthly rides
    public void getMonthlyRides() {
    }

    // method to get rides between specific time period
    public void getSpecificPeriodRides() {
    }
}
