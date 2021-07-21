package com.dal.cabby.cabSelection;
import com.dal.cabby.cabPrice.CabPriceCalculator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;

public class CabSelectionService {
    DBHelper dbHelper;
    Inputs inputs;
    public CabSelectionService(Inputs inputs) throws SQLException {
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
    ArrayList<CabSelectionDAO> cabDetails=new ArrayList<>();
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> femaleArrayList=new ArrayList<>();
    ArrayList<String> maleArrayList=new ArrayList<>();
    String Query;
    ResultSet resultSet;
    String gender;

    public void preferredCab() throws SQLException {
        System.out.println("Enter your Cab preference");
        System.out.println("1. Micro and Mini");
        System.out.println("2. Prime Sedan");
        System.out.println("3. Prime SUV");
        System.out.println("4. Luxury Class");
        int cabType=inputs.getIntegerInput();
        System.out.println("Enter Source location");
        sourceLocation= inputs.getStringInput();
        System.out.println("Enter Destination location");
        destinationLocation= inputs.getStringInput();
        switch (cabType){
            case 1:
                fetchSourceLocation();
                getAllNearbyCabs();
                break;
            case 2:
                fetchSourceLocation();
                getAllNearbyCabs();
                break;
            case 3:
                fetchSourceLocation();
                getAllNearbyCabs();
                break;
            case 4:
                fetchSourceLocation();
                getAllNearbyCabs();
                break;
        }
        cabPriceCalculator.priceCalculation(sourceLocation,destinationLocation,cabType);
    }

    public double fetchSourceLocation() throws SQLException {
        Query= String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'",sourceLocation);
        resultSet= dbHelper.executeSelectQuery(Query);
        while (resultSet.next()) {
            sourceDistance = resultSet.getDouble("distanceFromOrigin");
        }
        return sourceDistance;
    }

    public ArrayList<CabSelectionDAO> getAllNearbyCabs() throws SQLException {
        double lowerRangeOfCabs = (sourceDistance-5);
        double upperRangeOfCabs = (sourceDistance+5);
        Query= String.format("Select cabName, cabDistanceFromOrigin, driver_id, routeTrafficDensity, " +
                "cabSpeedOnRoute,driverGender from cabs where cabDistanceFromOrigin BETWEEN '%f' AND '%f'"
                ,lowerRangeOfCabs,upperRangeOfCabs);
        resultSet= dbHelper.executeSelectQuery(Query);
        while (resultSet.next()) {
            CabSelectionDAO cabDetail = new CabSelectionDAO(resultSet.getString("cabName"),
                    resultSet.getDouble("cabDistanceFromOrigin"),
                    resultSet.getInt("driver_id"),
                    resultSet.getString("routeTrafficDensity"),
                    resultSet.getInt("cabSpeedOnRoute"),
                    resultSet.getString("driverGender"));
            cabDetails.add(cabDetail);
        }
        System.out.println("Unfiltered List of nearby Cabs:");
        for(int i=0;i<cabDetails.size();i++){
            System.out.println(cabDetails.get(i).toString());
        }
        System.out.println("Do you want to book cab based on Gender of Cab Driver??");
        System.out.println("1. YES ");
        System.out.println("2. No ");
        int input =inputs.getIntegerInput();
        switch (input){
            case 1:
                withGenderPreference();
                break;
            case 2:
                withoutGenderPreference();
                break;
        }
        return cabDetails;
    }

    public void withGenderPreference() throws SQLException {
        System.out.println("Select your gender preference");
        System.out.println("1. Male ");
        System.out.println("2. Female ");
        int input =inputs.getIntegerInput();
        switch (input){
            case 1:
                for(int i=0;i<cabDetails.size();i++){
                    gender=cabDetails.get(i).driverGender;
                    if(gender.equals("Male")){
                        maleArrayList.add(cabDetails.get(i).cabName);
                    }
                }
                for (int i=0;i<maleArrayList.size();i++){
                    cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation,maleArrayList.get(i));
                }
                bestNearbyCabOfMaleDriver();
                break;
            case 2:
                for (int i=0;i<cabDetails.size();i++){
                    gender=cabDetails.get(i).driverGender;
                    if(gender.equals("Female")){
                        femaleArrayList.add(cabDetails.get(i).cabName);
                    }
                }
                for (int i = 0; i < femaleArrayList.size(); i++) {
                    cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation, femaleArrayList.get(i));
                }
                bestNearbyCabOfFemaleDriver();
                break;
        }
    }

    public void withoutGenderPreference() throws SQLException{
        for(int i=0;i<cabDetails.size();i++){
            arrayList.add(cabDetails.get(i).cabName);
        } /*
        Created this arrayList to store names of Nearby cabs which will be passed to a function along with
        sourceLocation to calculate distance between cabs and source location.
        */

        for(int i=0;i<arrayList.size();i++) {
            cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation,arrayList.get(i));
        }
        bestNearbyCabWithoutFilter();
    }

    public void bestNearbyCabOfMaleDriver() throws SQLException {
        ArrayList<Double> maleDriverTimeToReach=new ArrayList<>();
        for (int i=0;i<cabDetails.size();i++){
            gender=cabDetails.get(i).driverGender;
            if(gender.equals("Male")) {
                maleDriverTimeToReach.add((cabDetails.get(i).cabDistanceFromOrigin) / (cabDetails.get(i).cabSpeedOnRoute));
            }
        }
        System.out.println("Estimated Arrival time of each Cab:");
        for (int i = 0; i < maleDriverTimeToReach.size(); i++) {
            System.out.println(String.format("%.2f",maleDriverTimeToReach.get(i)));
        }
        double min= Collections.min(maleDriverTimeToReach);
        System.out.println("Fastest cab is reaching your location in "+ String.format("%.2f", min) + " minutes");
    }

    public void bestNearbyCabOfFemaleDriver() throws SQLException {
        ArrayList<Double> femaleDriverTimeToReach=new ArrayList<>();
        for (int i=0;i<cabDetails.size();i++){
            gender=cabDetails.get(i).driverGender;
            if(gender.equals("Female")) {
                femaleDriverTimeToReach.add((cabDetails.get(i).cabDistanceFromOrigin) / (cabDetails.get(i).cabSpeedOnRoute));
            }
        }
        System.out.println("Estimated Arrival time of each Cab:");
        for (int i = 0; i < femaleDriverTimeToReach.size(); i++) {
            System.out.println(String.format("%.2f",femaleDriverTimeToReach.get(i)));
        }
        double min= Collections.min(femaleDriverTimeToReach);
        System.out.println("Fastest cab is reaching your location in "+ String.format("%.2f",min) + " minutes");
    }


    public void bestNearbyCabWithoutFilter() throws SQLException {
        ArrayList<Double> timeToReach=new ArrayList<>();
        for (int i=0;i<cabDetails.size();i++){
            timeToReach.add((cabDetails.get(i).cabDistanceFromOrigin)/(cabDetails.get(i).cabSpeedOnRoute));
        }
        System.out.println("Estimated Arrival time of each Cab:"+ String.format("%.2f",timeToReach));
        double min= Collections.min(timeToReach);
        System.out.println("Fastest cab is reaching your location in "+ String.format("%.2f",min) + " minutes");
    }
}

