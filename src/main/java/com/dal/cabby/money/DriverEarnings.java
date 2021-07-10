package com.dal.cabby.money;

import java.util.Scanner;

// this class will show the earning of driver for a specific period
public class DriverEarnings {

    public void earningsPage() {
        while (true) {
            System.out.println("\n**** Earnings Page ****");
            System.out.println("\t1. Daily earnings: ");
            System.out.println("\t2. Monthly earnings: ");
            System.out.println("\t3. Earning between a specific period: ");
            System.out.println("\t4. Return to the previous page: ");
            System.out.println("Please enter a selection: ");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    dailyEarnings();
                    break;
                case 2:
                    monthlyEarnings();
                    break;
                case 3:
                    specificPeriodEarnings();
                    break;
                default:
                    return;
            }
        }
    }

    public double dailyEarnings() {
        return 0.0;
    }

    public double monthlyEarnings() {
        return 0.0;
    }

    public double specificPeriodEarnings() {
        return 0.0;
    }

}
