package com.dal.cabby.cabPrice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;

public class CabPriceCalculator {
    DBHelper dbHelper;
    Inputs inputs;
    public CabPriceCalculator(Inputs inputs){
        this.inputs=inputs;
        dbHelper = new DBHelper();
        try {
            dbHelper.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    double price=0.0;
    int hour=java.time.LocalTime.now().getHour();
    double sourceDistanceFromOrigin = 0.0;
    double destinationDistanceFromOrigin= 0.0;
    double cabDistanceFromOrigin=0.0;
    double distance=0.0;

    public int priceCalculation(String source, String destination, Boolean rideSharing, String sourceArea, int cabType) throws SQLException {
        System.out.println("*** Select your Preferences ***");
        System.out.println("1. Normal Booking");
        System.out.println("2. Want to share ride with co-passenger");
        System.out.println("3. Want to have Car TV and Wifi during ride");
        int userInput= inputs.getIntegerInput();
        distance=locationsDistanceFromOrigin(source,destination);
        switch(userInput){
            case 1:
                distanceFactor(sourceArea,cabType,distance);
                System.out.println("Total Price for the ride is: $" + String.format("%.2f",price));
                break;
            case 2:
                rideSharing(cabType,distance);
                break;
            case 3:
                amenities(source, cabType,distance);
                break;
        }
        return 0;
    }

    public double locationsDistanceFromOrigin(String source,String destination) throws SQLException {
        String sourceLocationQuery = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", source);
        ResultSet resultSet = dbHelper.executeSelectQuery(sourceLocationQuery);
        while (resultSet.next()) {
            sourceDistanceFromOrigin = resultSet.getDouble("distanceFromOrigin");
        }

        String destinationLocationQuery = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", destination);
        ResultSet resultSet1 = dbHelper.executeSelectQuery(destinationLocationQuery);
        while (resultSet1.next()) {
            destinationDistanceFromOrigin = resultSet1.getDouble("distanceFromOrigin");
        }
        double distanceBetweenSourceAndDestination= calculateDistance(sourceDistanceFromOrigin,destinationDistanceFromOrigin);
        System.out.println("Distance between "+ source + " and "+ destination +" is: " + distance+" KM");
        return distanceBetweenSourceAndDestination;
    }

    public double locationAndCabDistanceFromOrigin(String source,String destination) throws SQLException {
        String sourceLocationQuery = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", source);
        ResultSet resultSet = dbHelper.executeSelectQuery(sourceLocationQuery);
        while (resultSet.next()) {
            sourceDistanceFromOrigin = resultSet.getDouble("distanceFromOrigin");
        }

        String cabLocationQuery= String.format("Select cabDistanceFromOrigin from cabs where cabName='%s'",destination);
        ResultSet resultSet1= dbHelper.executeSelectQuery(cabLocationQuery);
        while (resultSet1.next()) {
            cabDistanceFromOrigin = resultSet1.getDouble("cabDistanceFromOrigin");
        }
        double distanceBetweenSourceAndCab=calculateDistance(sourceDistanceFromOrigin,cabDistanceFromOrigin);
        System.out.println("Distance between "+ source + " and "+ destination +" is: " + distance+" KM");
        return distanceBetweenSourceAndCab;
    }

    public double calculateDistance(Double source,Double destination) throws SQLException {
        if(source >0 && destination>0) {
            if (destination < source) {
                distance = source - destination;
            } else {
                distance = destination - source;
            }
        }
        else if (source< 0 && destination < 0) {
            if (destination < source) {
                distance = source - destination;
            } else {
                distance = destination - source;
            }
        }
        else if (source < 0 && destination > 0) {
            distance = destination-source;
        }
        else if (source > 0 && destination < 0) {
            distance = source - destination;
        }
        return distance;
    }

    public double distanceFactor(String rideArea,int cabCategory,double distance){
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

    public void rideSharing(int cabCategory,double distance){
        System.out.println("Choose number of co-passengers: ");
        System.out.println("One co-passenger");
        System.out.println("Two co-passengers");
        int input= inputs.getIntegerInput();
        double basicPrice=distanceFactor("urban",cabCategory,distance);
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

    public void amenities(String source,int cabCategory,double distance) throws SQLException {
        // For every 30 minutes of ride we are charging extra $2 per amenity.
        System.out.println("Choose amenities:");
        System.out.println("1. CarTV");
        System.out.println("2. Wifi");
        System.out.println("3. Both");
        int input= inputs.getIntegerInput();
        double basicPrice= distanceFactor("urban", cabCategory,distance);
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
