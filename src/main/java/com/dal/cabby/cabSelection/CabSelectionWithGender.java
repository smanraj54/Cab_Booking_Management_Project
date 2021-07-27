package com.dal.cabby.cabSelection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dal.cabby.cabPrice.CabPriceCalculator;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.rating.IRatings;
import com.dal.cabby.rating.Ratings;

public class CabSelectionWithGender {
    IPersistence iPersistence;
    Inputs inputs;
    CabSelection cabSelection;
    CabSelectionDBLayer cabSelectionDBLayer;
    CabPriceCalculator cabPriceCalculator;

    /*
        This is the constructor of class which interacts with DB Layer to fetch Nearby Cabs
    */
    public CabSelectionWithGender(Inputs inputs, CabSelection cabSelection){
        this.inputs=inputs;
        this.cabSelection = cabSelection;
        cabPriceCalculator = new CabPriceCalculator(inputs);
        try {
            iPersistence=DBHelper.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        cabSelectionDBLayer=new CabSelectionDBLayer(inputs, cabSelection);
    }

    /*
        This method pass names of all nearby cabs of a specific gender chosen by customer to DB Layer
         to calculate distance between Source Location and Cab location which will be used as one of the
         parameter two parameters (2nd one is Traffic Density) in Fetching optimal cab.
    */
    public CabSelectionDAO withGenderPreference() throws SQLException {
        List<CabSelectionDAO> mainArrayList = cabSelectionDBLayer.getAllNearbyCabs();
        List<String> maleArrayList = new ArrayList<>();
        List<String> femaleArrayList = new ArrayList<>();
        String gender;
        System.out.println("Select your gender preference");
        System.out.println("1. Male ");
        System.out.println("2. Female ");
        int input = inputs.getIntegerInput();
        try {
            System.out.println("Great! We are searching the best cab for you. Please hold on......");
            for(int i=5;i>0;i--) {
                Thread.sleep(1000);
                System.out.println(i + "....");
            }
            System.out.println("Hey! We have found the best cab based on your preferences.");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        switch (input) {
            case 1:
                for (CabSelectionDAO cabDetail : mainArrayList) {
                    gender = cabDetail.driverGender;
                    if (gender.equals("Male")) {
                        maleArrayList.add(cabDetail.cabName);
                    }
                }
                for (String s : maleArrayList) {
                    cabSelectionDBLayer.locationAndCabDistanceFromOrigin(cabSelection.sourceLocation, s);
                }
                return bestNearbyCabOfMaleDriver();
            case 2:
                for (CabSelectionDAO cabDetail : mainArrayList) {
                    gender = cabDetail.driverGender;
                    if (gender.equals("Female")) {
                        femaleArrayList.add(cabDetail.cabName);
                    }
                }
                for (String s : femaleArrayList) {
                    cabSelectionDBLayer.locationAndCabDistanceFromOrigin(cabSelection.sourceLocation, s);
                }
                return bestNearbyCabOfFemaleDriver();

            default:
                System.out.println("Invalid input: " + input);
                return null;
        }
    }

    /*
        This method return best possible cab/ optimal cab of MALE driver after checking Traffic density
        factor on routes.
     */
    private CabSelectionDAO bestNearbyCabOfMaleDriver() throws SQLException {
        List<CabSelectionDAO> mainArrayList = cabSelectionDBLayer.getAllNearbyCabs();
        List<Double> maleDriverTimeToReach = new ArrayList<>();
        String gender;
        CabSelectionDAO selectedCab = null;
        double min = Double.MAX_VALUE;
        IRatings iRatings = new Ratings();
        for (CabSelectionDAO cabDetail : mainArrayList) {
            gender = cabDetail.driverGender;
            if (gender.equals("Male")) {
                double timeOfCab = (cabDetail.cabDistanceFromOrigin) / (cabDetail.cabSpeedOnRoute);
                maleDriverTimeToReach.add(timeOfCab);
                int driverId = cabDetail.driver_Id;
                double ratings = iRatings.getAverageRatingOfDriver(driverId);
                System.out.printf("Driver rating for driver_id: %d, Rating: %f\n", driverId, ratings);
                if (timeOfCab < min) {
                    selectedCab = cabDetail;
                    min = timeOfCab;
                }
            }
        }
        System.out.println("Estimated Arrival time of each Cab:");
        for (Double driverTimeToReach : maleDriverTimeToReach) {
            System.out.println(String.format("%.2f", driverTimeToReach));
        }
        System.out.println("Fastest cab is reaching your location in " + String.format("%.2f", min) + " minutes");
        return selectedCab;
    }

    /*
        This method return best possible cab/ optimal cab of FEMALE driver after checking Traffic density
        factor on routes.
     */
    private CabSelectionDAO bestNearbyCabOfFemaleDriver() throws SQLException {
        List<CabSelectionDAO> mainArrayList = cabSelectionDBLayer.getAllNearbyCabs();
        List<Double> femaleDriverTimeToReach = new ArrayList<>();
        String gender;
        CabSelectionDAO selectedCab = null;
        double min = Double.MAX_VALUE;
        IRatings iRatings = new Ratings();
        for (CabSelectionDAO cabDetail : mainArrayList) {
            gender = cabDetail.driverGender;
            if (gender.equals("Female")) {
                double timeOfCab = (cabDetail.cabDistanceFromOrigin) / (cabDetail.cabSpeedOnRoute);
                femaleDriverTimeToReach.add(timeOfCab);
                int driverId = cabDetail.driver_Id;
                double ratings = iRatings.getAverageRatingOfDriver(driverId);
                if (timeOfCab < min) {
                    selectedCab = cabDetail;
                    min = timeOfCab;
                }
            }
        }
        System.out.println("Estimated Arrival time of each Cab:");
        for (Double driverTimeToReach : femaleDriverTimeToReach) {
            System.out.println(String.format("%.2f", driverTimeToReach));
        }
        System.out.println("Fastest cab is reaching your location in " + String.format("%.2f", min) + " minutes");
        return selectedCab;
    }
}
