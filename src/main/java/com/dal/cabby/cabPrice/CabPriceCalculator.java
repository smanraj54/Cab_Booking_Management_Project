package com.dal.cabby.cabPrice;

import java.sql.Time;
import java.util.Scanner;

public class CabPriceCalculator {
    static double price=0.0;
    static int hour=java.time.LocalTime.now().getHour();
    static Scanner ss = new Scanner(System.in);

    public static void main(String[] args) {
        priceCalculation("Halifax","Montreal",false,"urban",true);
    }

    public static int priceCalculation(String source, String destination, Boolean rideSharing, String sourceArea, Boolean isOfficeHours){
        System.out.println("*** Select your Preferences ***");
        System.out.println("1. Normal Booking");
        System.out.println("2. Want to share ride with co-passenger");
        System.out.println("3. Want to have Car TV and Wifi during ride");
        int userInput= ss.nextInt();
        switch(userInput){
            case 1:
                distanceFactor(sourceArea,isOfficeHours);
                System.out.println("Total Price for the ride is: $"+price);
                break;
            case 2:
                rideSharing();
                break;
            case 3:
                amenities();
                break;
        }
        return 0;
    }

    public static double distanceFactor(String rideArea,Boolean isPeakHour){
        int totalDistance=70;  //Distance in KM
        int shortDistance=5;
        if(totalDistance <= shortDistance){
            //For initial few kilometers 5 dollars would be charged per Km
            for (int initialKilometers=1; initialKilometers<totalDistance; initialKilometers++){
                price+=5;
            }
        }
        else{
            for(int lessKilometers=1;lessKilometers<=shortDistance;lessKilometers++){
                price+=5;
            }
            //Price per kilometer would be reduced for Long Journey
            for (int longKilometers=6;longKilometers<=totalDistance;longKilometers++ ){
                price+=3.5;
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
        return price;
    }

    public static void rideSharing(){
        System.out.println("Choose number of co-passengers: ");
        System.out.println("One co-passenger");
        System.out.println("Two co-passengers");
        int input= ss.nextInt();
        double basicPrice=distanceFactor("urban",true);
        double priceWithCoPassenger=basicPrice;
        double discount;
        switch (input){
            case 1:
                priceWithCoPassenger -=(.10*basicPrice);
                break;
            case 2:
                priceWithCoPassenger -=(.15*basicPrice);
                break;
        }
        discount=basicPrice-priceWithCoPassenger;
        System.out.println("You got a discount of: $"+ String.format("%.2f",discount));
        System.out.println("Total Price for this ride is: $"+priceWithCoPassenger);
    }

    public static void amenities(){
        // For every 30 minutes of ride we are charging extra $2 per amenity.

        //For dummy data we have taken total time of ride as 90 minutes.
        System.out.println("Choose amenities:");
        System.out.println("1. CarTV");
        System.out.println("2. Wifi");
        System.out.println("3. Both");
        int input= ss.nextInt();
        double basicPrice= distanceFactor("urban",true);
        System.out.println("Price without amenities: $"+basicPrice);
        double priceWithAmenities= basicPrice;
        double extraCharge=0;

        switch (input){
            case 1:
                extraCharge=((2*(90/30)));
                System.out.println("Extra charges: $"+extraCharge);
                priceWithAmenities+=extraCharge;
                break;
            case 2:
                extraCharge=((2*(90/30)));
                System.out.println("Extra charges: $"+extraCharge);
                priceWithAmenities+=extraCharge;
                break;
            case 3:
                extraCharge=(2*(2*(90/30)));
                System.out.println("Extra charges: $"+extraCharge);
                priceWithAmenities+=extraCharge;
                break;
        }
        System.out.println("Total Price for this ride is: $"+priceWithAmenities);
    }

}
