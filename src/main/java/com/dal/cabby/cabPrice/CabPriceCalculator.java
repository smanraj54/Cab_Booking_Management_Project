package com.dal.cabby.cabPrice;

import java.sql.Time;
import java.util.Scanner;



public class CabPriceCalculator {
    static double price=0.0;
    static int hour=java.time.LocalTime.now().getHour();
    static Scanner ss = new Scanner(System.in);

    public static int priceCalculation(String source, String destination, Boolean rideSharing,String sourceArea, Boolean isOfficeHours){
        System.out.println("*** Select your Preferences ***");
        System.out.println("1. Normal Booking");
        System.out.println("2. Want to share ride with co-passenger");
        System.out.println("3. Want to have Car TV and Wifi during ride");
        int userInput= ss.nextInt();
        switch(userInput){
            case 1:
                distanceFactor(sourceArea,isOfficeHours);
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return 0;
    }

    public static double distanceFactor(String rideArea,Boolean isPeakHour) {
        int totalDistance = 7;
        int shortDistance = 5;
        if (totalDistance <= shortDistance) {
            //For initial 5 kilometers fixed $5 would be charged per Km
            for (int initialKilometers = 1; initialKilometers < totalDistance; initialKilometers++) {
                price += 5;
            }
        } else {
            for (int lessKilometers = 1; lessKilometers <= shortDistance; lessKilometers++) {
                price += 5;
            }
            //Price per kilometer would be reduced for Long Journey after initial 5 kilometers
            for (int longKilometers = 6; longKilometers <= totalDistance; longKilometers++) {
                price += 3.5;
            }
        }
        // Extra 20% would be charged on base fare if ride timing would be from 9:00 PM till 5:00 AM or During office hours
        if((hour>21 && hour<24)||(hour>01 && hour<05) || isPeakHour){
            price+=(.20 * price);
        }

        // rides in urban area would be bit costlier
        if(rideArea=="urban"){
            price+=(.05 * price);
        }


        System.out.println("Total Price for the ride is: $" + price);
        return price;
    }
}
