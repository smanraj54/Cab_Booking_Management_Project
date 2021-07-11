package com.dal.cabby.cabPrice;

import java.sql.Time;
import java.util.Scanner;

public class CabPriceCalculator {
    double price=0.0;
    int hour=java.time.LocalTime.now().getHour();
    Scanner ss = new Scanner(System.in);

    public int priceCalculation(String source, String destination, Boolean rideSharing, String sourceArea, Boolean isOfficeHours, int cabType){
        System.out.println("*** Select your Preferences ***");
        System.out.println("1. Normal Booking");
        System.out.println("2. Want to share ride with co-passenger");
        System.out.println("3. Want to have Car TV and Wifi during ride");
        int userInput= ss.nextInt();
        switch(userInput){
            case 1:
                distanceFactor(sourceArea,isOfficeHours,cabType);
                System.out.println("Total Price for the ride is: $" + String.format("%.2f",price));
                break;
            case 2:
                rideSharing(cabType);
                break;
            case 3:
                amenities(cabType);
                break;
        }
        return 0;
    }

    public double distanceFactor(String rideArea,Boolean isPeakHour,int cabCategory){
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

        if(cabCategory==2) {
            price+=(.1*price);  //10% price would be higher for Prime Sedan category of Cabs
        }
        if(cabCategory==3){
            price+=(.25*price);  //25% price would be higher for Prime SUV category of Cabs
        }
        if(cabCategory==4) {
            price += (.40 * price);  //40% price would be higher for PLuxury Class Cabs
        }
        return price;
    }

    public void rideSharing(int cabCategory){
        System.out.println("Choose number of co-passengers: ");
        System.out.println("One co-passenger");
        System.out.println("Two co-passengers");
        int input= ss.nextInt();
        double basicPrice=distanceFactor("urban",true,cabCategory);
        System.out.println("Price without Co-passenger: $"+String.format("%.2f",basicPrice));
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
        System.out.println("You got a discount of $"+ String.format("%.2f",discount)+" on sharing ride with co-passenger");
        System.out.println("Total Price for this ride is: $"+String.format("%.2f",priceWithCoPassenger));
    }

    public void amenities(int cabCategory){
        // For every 30 minutes of ride we are charging extra $2 per amenity.
        //For dummy data we have taken total time of ride as 90 minutes.
        System.out.println("Choose amenities:");
        System.out.println("1. CarTV");
        System.out.println("2. Wifi");
        System.out.println("3. Both");
        int input= ss.nextInt();
        double basicPrice= distanceFactor("urban",true, cabCategory);
        System.out.println("Price without amenities: $"+String.format("%.2f",basicPrice));
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
        System.out.println("Total Price for this ride is: $"+ String.format("%.2f",priceWithAmenities));
    }
}
