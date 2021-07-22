package com.dal.cabby.cabSelection;

import com.dal.cabby.cabPrice.CabPriceCalculator;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class CabSelectionService {
    private DBHelper dbHelper;
    private Inputs inputs;
    private CabPriceCalculator cabPriceCalculator;
    private String sourceLocation, destinationLocation;
    private double sourceDistance = 0.0;
    private ArrayList<CabSelectionDAO> cabDetails = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> femaleArrayList=new ArrayList<>();
    ArrayList<String> maleArrayList=new ArrayList<>();
    private String Query;
    private ResultSet resultSet;
    String gender;
    CabSelectionDAO bestCab;

    public CabSelectionService(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        cabPriceCalculator = new CabPriceCalculator(inputs);
        dbHelper = new DBHelper();
        try {
            dbHelper.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Booking preferredCab(int custId) throws SQLException {
        System.out.println("Enter your Cab preference");
        System.out.println("1. Micro and Mini");
        System.out.println("2. Prime Sedan");
        System.out.println("3. Prime SUV");
        System.out.println("4. Luxury Class");
        int cabType = inputs.getIntegerInput();
        System.out.println("Enter Source location");
        sourceLocation = inputs.getStringInput();
        System.out.println("Enter Destination location");
        destinationLocation = inputs.getStringInput();
        fetchSourceLocation();
        getAllNearbyCabs();
        double price = cabPriceCalculator.priceCalculation(sourceLocation, destinationLocation, cabType);
        Booking booking = new Booking(-1, custId, bestCab.driver_Id, -1, sourceLocation, destinationLocation, "", price);
        return booking;
    }

    public double fetchSourceLocation() throws SQLException {
        Query = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", sourceLocation);
        resultSet = dbHelper.executeSelectQuery(Query);
        while (resultSet.next()) {
            sourceDistance = resultSet.getDouble("distanceFromOrigin");
        }
        return sourceDistance;
    }

    public ArrayList<CabSelectionDAO> getAllNearbyCabs() throws SQLException {
        double lowerRangeOfCabs = (sourceDistance - 5);
        double upperRangeOfCabs = (sourceDistance + 5);
        Query = String.format("Select cabName, cabId, cabDistanceFromOrigin, driver_id, routeTrafficDensity, " +
                        "cabSpeedOnRoute,driverGender from cabs where cabDistanceFromOrigin BETWEEN '%f' AND '%f'"
                , lowerRangeOfCabs, upperRangeOfCabs);
        resultSet = dbHelper.executeSelectQuery(Query);
        while (resultSet.next()) {
            CabSelectionDAO cabDetail = new CabSelectionDAO(resultSet.getString("cabName"),
                    resultSet.getInt("cabId"),
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
        int input = inputs.getIntegerInput();
        switch (input){
            case 1:
                bestCab = withGenderPreference();
                break;
            case 2:
                bestCab = withoutGenderPreference();
        }
        return cabDetails;
    }

    public CabSelectionDAO withGenderPreference() throws SQLException {
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
                return bestNearbyCabOfMaleDriver();
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
                return bestNearbyCabOfFemaleDriver();
            default:
                System.out.println("Invalid input: " + input);
                return null;
        }
    }

    public CabSelectionDAO withoutGenderPreference() throws SQLException{
        for(int i=0;i<cabDetails.size();i++){
            arrayList.add(cabDetails.get(i).cabName);
        } /*
        Created this arrayList to store names of Nearby cabs which will be passed to a function along with
        sourceLocation to calculate distance between cabs and source location.
        */

        for(int i=0;i<arrayList.size();i++) {
            cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation,arrayList.get(i));
        }
        return bestNearbyCabWithoutFilter();
    }

    public CabSelectionDAO bestNearbyCabOfMaleDriver() throws SQLException {
        ArrayList<Double> maleDriverTimeToReach=new ArrayList<>();
        CabSelectionDAO selectedCab = null;
        double min = Double.MAX_VALUE;
        for (int i=0;i<cabDetails.size();i++){
            gender=cabDetails.get(i).driverGender;
            if(gender.equals("Male")) {
                double timeOfCab = (cabDetails.get(i).cabDistanceFromOrigin) / (cabDetails.get(i).cabSpeedOnRoute);
                maleDriverTimeToReach.add(timeOfCab);
                if(timeOfCab < min) {
                    selectedCab = cabDetails.get(i);
                    min = timeOfCab;
                }
            }
        }
        System.out.println("Estimated Arrival time of each Cab:" + maleDriverTimeToReach);
        System.out.println("Fastest cab is reaching your location in "+ String.format("%.2f", min) + " minutes");
        return selectedCab;
    }

    public CabSelectionDAO bestNearbyCabOfFemaleDriver() throws SQLException {
        ArrayList<Double> femaleDriverTimeToReach=new ArrayList<>();
        CabSelectionDAO selectedCab = null;
        double min = Double.MAX_VALUE;
        for (int i=0;i<cabDetails.size();i++){
            gender=cabDetails.get(i).driverGender;
            if(gender.equals("Female")) {
                double timeOfCab = (cabDetails.get(i).cabDistanceFromOrigin) / (cabDetails.get(i).cabSpeedOnRoute);
                femaleDriverTimeToReach.add(timeOfCab);
                if(timeOfCab < min) {
                    selectedCab = cabDetails.get(i);
                    min = timeOfCab;
                }
            }
        }
        System.out.println("Estimated Arrival time of each Cab:" + femaleDriverTimeToReach);
        System.out.println("Fastest cab is reaching your location in "+ String.format("%.2f", min) + " minutes");
        return selectedCab;
    }

    public CabSelectionDAO bestNearbyCabWithoutFilter() {
        ArrayList<Double> timeToReach = new ArrayList<>();
        CabSelectionDAO selectedCab = null;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < cabDetails.size(); i++) {
            double timeOfCab = (cabDetails.get(i).cabDistanceFromOrigin) / (cabDetails.get(i).cabSpeedOnRoute);
            timeToReach.add(timeOfCab);
            if (timeOfCab < min) {
                selectedCab = cabDetails.get(i);
                min = timeOfCab;
            }
        }
        System.out.println("Estimated Arrival time of each Cab:" + timeToReach);
        System.out.println("Fastest cab is reaching your location in " + String.format("%.2f", min) + " minutes");
        return selectedCab;
    }

    public static void main(String[] args) throws SQLException {
        CabSelectionService cabSelectionService = new CabSelectionService(new InputFromUser());
        cabSelectionService.preferredCab(1);
    }
}

