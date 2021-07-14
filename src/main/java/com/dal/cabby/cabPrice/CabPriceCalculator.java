package com.dal.cabby.cabPrice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;
import com.dal.cabby.dbHelper.DBHelper;

public class CabPriceCalculator {
    DBHelper dbHelper;
    public CabPriceCalculator(){
        dbHelper = new DBHelper();
        try {
            dbHelper.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    double price=0.0;
    int hour=java.time.LocalTime.now().getHour();
    Scanner ss = new Scanner(System.in);
    double distance=0.0;

    public int priceCalculation(String source, String destination, Boolean rideSharing, String sourceArea, int cabType) throws SQLException {
        System.out.println("*** Select your Preferences ***");
        System.out.println("1. Normal Booking");
        System.out.println("2. Want to share ride with co-passenger");
        System.out.println("3. Want to have Car TV and Wifi during ride");
        int userInput= ss.nextInt();
        distance=calculateDistance(source,destination);
        switch(userInput){
            case 1:
                distanceFactor(source,destination,sourceArea,cabType,distance);
                System.out.println("Total Price for the ride is: $" + String.format("%.2f",price));
                break;
            case 2:
                rideSharing(source,destination,cabType,distance);
                break;
            case 3:
                amenities(source,destination,cabType,distance);
                break;
        }
        return 0;
    }

    public double calculateDistance(String source,String destination) throws SQLException {
        double sourceDistance = 0.0;
        double destinationDistance= 0.0;
        double distance = 0.0;

        String sourceLocationQuery= String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'",source);// with sourceLocation find it's distance fro, origin
        ResultSet sourceDistanceFromOrigin= dbHelper.executeSelectQuery(sourceLocationQuery);
        while (sourceDistanceFromOrigin.next()) {
            sourceDistance = sourceDistanceFromOrigin.getDouble("distanceFromOrigin");
        }

        String destinationLocationQuery= String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'",destination);// with sourceLocation find it's distance from origin
        ResultSet destinationDistanceFromOrigin= dbHelper.executeSelectQuery(destinationLocationQuery);
        while (destinationDistanceFromOrigin.next()) {
            destinationDistance = destinationDistanceFromOrigin.getDouble("distanceFromOrigin");
        }

        if(sourceDistance >0 && destinationDistance>0) {
            if (destinationDistance < sourceDistance) {
                distance = sourceDistance - destinationDistance;
            } else {
                distance = destinationDistance - sourceDistance;
            }
        }
        else if (sourceDistance< 0 && destinationDistance < 0) {
            if (destinationDistance < sourceDistance) {
                distance = sourceDistance - destinationDistance;
            } else {
                distance = destinationDistance - sourceDistance;
            }
        }
        else if (sourceDistance < 0 && destinationDistance > 0) {
            distance = destinationDistance-sourceDistance;
        }
        else if (sourceDistance > 0 && destinationDistance < 0) {
            distance = sourceDistance - destinationDistance;
        }
        System.out.println("Distance between two locations:"+distance);
        return distance;
    }

    public double distanceFactor(String source, String destination, String rideArea,int cabCategory,double distance){
        double shortDistance=5; //For initial few kilometers 5 dollars would be charged per Km
        if(distance <= shortDistance){
            for (int initialKilometers=1; initialKilometers<=distance; initialKilometers++){
                price+=5;
            }
        }
        else{
            for(int lessKilometers=1;lessKilometers<=shortDistance;lessKilometers++){
                price+=5;
            }
            //Price per kilometer would be reduced for Long Journey
            for (int longKilometers=6;longKilometers<=distance;longKilometers++ ){
                price+=3.5;
            }
        }
        // Extra 20% would be charged on base fare if ride timing would be from 9:00 PM till 5:00 AM or During office hours
        if((hour>=21 && hour<24)||(hour>=01 && hour<05) || (hour>=17 && hour<19)){
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

    public void rideSharing(String source, String destination, int cabCategory,double distance){
        System.out.println("Choose number of co-passengers: ");
        System.out.println("One co-passenger");
        System.out.println("Two co-passengers");
        int input= ss.nextInt();
        double basicPrice=distanceFactor(source,destination,"urban",cabCategory,distance);
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

    public void amenities(String source, String destination,int cabCategory,double distance) throws SQLException {
        // For every 30 minutes of ride we are charging extra $2 per amenity.
        System.out.println("Choose amenities:");
        System.out.println("1. CarTV");
        System.out.println("2. Wifi");
        System.out.println("3. Both");
        int input= ss.nextInt();
        double basicPrice= distanceFactor(source,destination,"urban", cabCategory,distance);
        System.out.println("Price without amenities: $"+String.format("%.2f",basicPrice));
        double priceWithAmenities= basicPrice;
        double extraCharge=0;
        double speed=0.0;
        String rideSpeed=String.format("Select averageSpeed from price_Calculation where sourceName='%s'",source);
        ResultSet rideDuration=dbHelper.executeSelectQuery(rideSpeed);
        while(rideDuration.next()){
            speed=rideDuration.getDouble("averageSpeed");
        }
        double time=(distance/speed)*60;  //Converted hours into minutes
        double rideInMinutes=(time/30);
        switch (input){
            case 1:
                extraCharge=(2*rideInMinutes);
                System.out.println("Extra charges: $"+extraCharge);
                priceWithAmenities+=extraCharge;
                break;
            case 2:
                extraCharge=(2*rideInMinutes);
                System.out.println("Extra charges: $"+extraCharge);
                priceWithAmenities+=extraCharge;
                break;
            case 3:
                extraCharge=(2*(2*rideInMinutes));
                System.out.println("Extra charges: $"+extraCharge);
                priceWithAmenities+=extraCharge;
                break;
        }
        System.out.println("Total Price for this ride is: $"+ String.format("%.2f",priceWithAmenities));
    }
}
