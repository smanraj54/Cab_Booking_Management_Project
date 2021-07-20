package com.dal.cabby.cabSelection;
import com.dal.cabby.cabPrice.CabPriceCalculator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;

public class CabSelection {
    DBHelper dbHelper;
    Inputs inputs;
    public CabSelection(Inputs inputs) throws SQLException {
        this.inputs=inputs;
        dbHelper=new DBHelper();
        try {
            dbHelper.initialize();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    CabPriceCalculator cabPriceCalculator=new CabPriceCalculator(inputs);
    public String sourceLocation, destinationLocation;
    double sourceDistance = 0.0;
    ArrayList<CabDAO> cabDetails=new ArrayList<>();
    ArrayList<String> arrayList=new ArrayList<>();
    String Query;
    ResultSet resultSet;

    public void preferredCab() throws SQLException {
        System.out.println("Enter your Cab preference");
        System.out.println("1. Micro and Mini");
        System.out.println("2. Prime Sedan");
        System.out.println("3. Prime SUV");
        System.out.println("4. Luxury Class");
        int input=inputs.getIntegerInput();
        System.out.println("Enter Source location");
        sourceLocation= inputs.getStringInput();
        System.out.println("Enter Destination location");
        destinationLocation= inputs.getStringInput();
        switch (input){
            case 1:
                fetchSourceLocation();
                getAllNearbyCabs();
                bestNearbyCab();
                break;
            case 2:
                fetchSourceLocation();
                getAllNearbyCabs();
                bestNearbyCab();
                break;
            case 3:
                fetchSourceLocation();
                getAllNearbyCabs();
                bestNearbyCab();
                break;
            case 4:
                fetchSourceLocation();
                getAllNearbyCabs();
                bestNearbyCab();
                break;
        }
        cabPriceCalculator.priceCalculation(sourceLocation,destinationLocation,false,"urban", input);
    }

    public double fetchSourceLocation() throws SQLException {
        Query= String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'",sourceLocation);
        resultSet= dbHelper.executeSelectQuery(Query);
        while (resultSet.next()) {
            sourceDistance = resultSet.getDouble("distanceFromOrigin");
        }
        return sourceDistance;
    }

    public ArrayList<CabDAO> getAllNearbyCabs() throws SQLException {
        double lowerRangeOfCabs = (sourceDistance-5);
        double upperRangeOfCabs = (sourceDistance+5);
        Query= String.format("Select cabName, cabDistanceFromOrigin, driver_id, routeTrafficDensity, " +
                "cabSpeedOnRoute from cabs where cabDistanceFromOrigin BETWEEN '%f' AND '%f'"
                ,lowerRangeOfCabs,upperRangeOfCabs);
        resultSet= dbHelper.executeSelectQuery(Query);
        while (resultSet.next()) {
            CabDAO cabDetail = new CabDAO(resultSet.getString("cabName"),
                    resultSet.getDouble("cabDistanceFromOrigin"),
                    resultSet.getInt("driver_id"),
                    resultSet.getString("routeTrafficDensity"),
                    resultSet.getInt("cabSpeedOnRoute"));
            cabDetails.add(cabDetail);
        }
        System.out.println("List of nearby Cabs:");
        for(int i=0;i<cabDetails.size();i++){
            System.out.println(cabDetails.get(i).toString());
        }

        for(int i=0;i<cabDetails.size();i++){
            arrayList.add(cabDetails.get(i).cabName);
        } /*
        Created this arrayList to store names of Nearby cabs which will be passed to a function along with
        sourceLocation to calculate distance between cabs and source location.
        */

        for(int i=0;i<arrayList.size();i++) {
            cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation,arrayList.get(i));
        }
        return cabDetails;
    }

    public void bestNearbyCab() throws SQLException {
        ArrayList<Double> timeToReach=new ArrayList<>();
        for (int i=0;i<cabDetails.size();i++){
            timeToReach.add((cabDetails.get(i).cabDistanceFromOrigin)/(cabDetails.get(i).cabSpeedOnRoute));
        }
        System.out.println("Estimated Arrival time of each Cab:"+ timeToReach);
        double min= Collections.min(timeToReach);
        System.out.println("Fastest cab is reaching your location in "+ String.format("%.2f",min) + " minutes");
    }
}

