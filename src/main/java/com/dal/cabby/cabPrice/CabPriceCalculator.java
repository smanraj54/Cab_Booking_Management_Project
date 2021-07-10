package com.dal.cabby.cabPrice;

import java.sql.Time;
import java.util.Scanner;



public class CabPriceCalculator {
    static double price=0.0;
    static Scanner ss = new Scanner(System.in);

    public static int priceCalculation(String source, String destination, Boolean rideSharing){
        System.out.println("*** Select your Preferences ***");
        System.out.println("1. Normal Booking");
        System.out.println("2. Want to share ride with co-passenger");
        System.out.println("3. Want to have Car TV and Wifi during ride");
        int userInput= ss.nextInt();
        switch(userInput){
            case 1:
                distanceFactor();
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return 0;
    }

    public static double distanceFactor() {
        int totalDistance = 7;
        int shortDistance = 5;
        if (totalDistance <= shortDistance) {
            for (int initialKilometers = 1; initialKilometers < totalDistance; initialKilometers++) {
                price += 5;                       //For initial few kilometers 5 dollars would be charged per Km
            }
        } else {
            for (int lessKilometers = 1; lessKilometers <= shortDistance; lessKilometers++) {
                price += 5;
            }
            for (int longKilometers = 6; longKilometers <= totalDistance; longKilometers++) {
                price += 3.5;                    //Price per kilometer would be reduced for Long Journey
            }
        }

        System.out.println("Total Price for the ride is: $" + price);
        return price;
    }
}
