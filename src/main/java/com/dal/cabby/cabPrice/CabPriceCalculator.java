package com.dal.cabby.cabPrice;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CabPriceCalculator implements ICabPriceCalculator {
    IPersistence iPersistence;
    Inputs inputs;
    double distance = 0.0;
    CabPriceDistanceFactor cabPriceDistanceFactor;
    CabPriceRideSharing cabPriceRideSharing;

    //CabPriceAmenities cabPriceAmenities;
    public CabPriceCalculator(Inputs inputs) {
        this.inputs = inputs;
        cabPriceDistanceFactor = new CabPriceDistanceFactor(inputs);
        cabPriceRideSharing = new CabPriceRideSharing(inputs);
        try {
            iPersistence = DBHelper.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        double num = 2.45678;
        double num1 = (double) Math.round(num * 100) / 100;

        System.out.println(num1);
        System.out.println(Math.round(num * 100.0) / 100.0);
    }

    @Override
    public double priceCalculation(String source, String destination, int cabType, double hour) throws SQLException {
        System.out.println("*** Select your Preferences ***");
        System.out.println("1. Normal Booking");
        System.out.println("2. Want to share ride with co-passenger");
        System.out.println("3. Want to have Car TV and Wifi during ride");
        int userInput = inputs.getIntegerInput();
        distance = locationsDistanceFromOrigin(source, destination);
        switch (userInput) {
            case 1:
                return cabPriceDistanceFactor.distanceFactor(source, distance, cabType, hour);
            case 2:
                return cabPriceRideSharing.rideSharing(source, distance, cabType, hour);
            case 3:
                //return cabPriceAmenities.amenities(source,distance,cabType,hour);
            case 4:
                System.out.println("Invalid option selected");
        }
        return -1.0;
    }

    @Override
    public double locationsDistanceFromOrigin(String source, String destination) throws SQLException {
        double sourceDistanceFromOrigin = 0.0;
        double destinationDistanceFromOrigin = 0.0;
        String query = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", source);
        ResultSet resultSet = iPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            sourceDistanceFromOrigin = resultSet.getDouble("distanceFromOrigin");
        }

        String query1 = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", destination);
        ResultSet resultSet1 = iPersistence.executeSelectQuery(query1);
        while (resultSet1.next()) {
            destinationDistanceFromOrigin = resultSet1.getDouble("distanceFromOrigin");
        }
        double distanceBetweenSourceAndDestination = calculateDistance(sourceDistanceFromOrigin, destinationDistanceFromOrigin);
        System.out.println("Distance between " + source + " and " + destination + " is: " + distance + " KM");
        return (Math.round(distanceBetweenSourceAndDestination * 100.0) / 100.0);
    }

    @Override
    public double locationAndCabDistanceFromOrigin(String source, String destination) throws SQLException {
        double sourceDistanceFromOrigin = 0.0;
        double cabDistanceFromOrigin = 0.0;
        String query = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", source);
        ResultSet resultSet = iPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            sourceDistanceFromOrigin = resultSet.getDouble("distanceFromOrigin");
        }

        String query1 = String.format("Select cabDistanceFromOrigin from cabs where cabName='%s'", destination);
        ResultSet resultSet1 = iPersistence.executeSelectQuery(query1);
        while (resultSet1.next()) {
            cabDistanceFromOrigin = resultSet1.getDouble("cabDistanceFromOrigin");
        }
        double distanceBetweenSourceAndCab = calculateDistance(sourceDistanceFromOrigin, cabDistanceFromOrigin);
        //System.out.println("Distance between "+ source + " and "+ destination +" is: " + distance+" KM");
        return (Math.round(distanceBetweenSourceAndCab * 100.0) / 100.0);
    }

//    private double amenities(String source,double distance, int cabCategory, double hour) throws SQLException {
//        // For every 30 minutes of ride we are charging extra $2 per amenity.
//        System.out.println("Choose amenities:");
//        System.out.println("1. CarTV");
//        System.out.println("2. Wifi");
//        System.out.println("3. Both");
//        int input= inputs.getIntegerInput();
//        double basicPrice= distanceFactor(source,distance, cabCategory,hour);
//        System.out.println("Price without amenities: $"+String.format("%.2f",basicPrice));
//        double priceWithAmenities= basicPrice;
//        double extraCharge=0;
//        double speed=0.0;
//        String query=String.format("Select averageSpeed from price_Calculation where sourceName='%s'",source);
//        ResultSet resultSet= iPersistence.executeSelectQuery(query);
//        while(resultSet.next()){
//            speed=resultSet.getDouble("averageSpeed");
//        }
//
//        double time=(distance/speed)*60;  //Converted hours into minutes
//        double rideInMinutes=(time/30);
//        switch (input){
//            case 1:
//                extraCharge=(2*rideInMinutes);
//                System.out.println("Extra charges: $"+String.format("%.2f",extraCharge));
//                priceWithAmenities+=extraCharge;
//                break;
//            case 2:
//                extraCharge=(2*rideInMinutes);
//                System.out.println("Extra charges: $"+String.format("%.2f",extraCharge));
//                priceWithAmenities+=extraCharge;
//                break;
//            case 3:
//                extraCharge=(2*(2*rideInMinutes));
//                System.out.println("Extra charges: $"+String.format("%.2f",extraCharge));
//                priceWithAmenities+=extraCharge;
//                break;
//        }
//        System.out.println("Total Price for this ride is: $"+ String.format("%.2f",priceWithAmenities));
//        return (Math.round(priceWithAmenities*100.0)/100.0);
//    }

    private double calculateDistance(Double source, Double destination) throws SQLException {
        if (source > 0 && destination > 0) {
            if (destination < source) {
                distance = source - destination;
            } else {
                distance = destination - source;
            }
        } else if (source < 0 && destination < 0) {
            if (destination < source) {
                distance = source - destination;
            } else {
                distance = destination - source;
            }
        } else if (source < 0 && destination > 0) {
            distance = destination - source;
        } else if (source > 0 && destination < 0) {
            distance = source - destination;
        }
        return (Math.round(distance * 100.0) / 100.0);
    }

}
