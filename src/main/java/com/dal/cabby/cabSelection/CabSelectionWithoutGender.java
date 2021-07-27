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

public class CabSelectionWithoutGender {
    IPersistence iPersistence;
    Inputs inputs;
    CabSelectionService cabSelectionService;
    CabPriceCalculator cabPriceCalculator;

    public CabSelectionWithoutGender(Inputs inputs,CabSelectionService cabSelectionService){
        this.inputs=inputs;
        this.cabSelectionService=cabSelectionService;
        cabPriceCalculator=new CabPriceCalculator(inputs);
        try {
            iPersistence=DBHelper.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public CabSelectionDAO withoutGenderPreference() throws SQLException {
        try {
            System.out.println("Great! We are searching the best cab for you. Please hold on......");
            for (int i = 5; i > 0; i--) {
                Thread.sleep(1000);
                System.out.println(i + "....");
            }
            System.out.println("Hey! We have found the best cab based on your preferences.");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        List<String> arrayList = new ArrayList<>();
        for (CabSelectionDAO cabDetail : cabSelectionService.cabDetails) {
            arrayList.add(cabDetail.cabName);
        } /*
        Created this arrayList to store names of Nearby cabs which will be passed to a function along with
        sourceLocation to calculate distance between cabs and source location.
        */

        for (String s : arrayList) {
            cabPriceCalculator.locationAndCabDistanceFromOrigin(cabSelectionService.sourceLocation, s);
        }
        return bestNearbyCabWithoutFilter();
    }

    private CabSelectionDAO bestNearbyCabWithoutFilter() throws SQLException {
        List<Double> timeToReach = new ArrayList<>();
        CabSelectionDAO selectedCab = null;
        IRatings iRatings = new Ratings();
        double min = Double.MAX_VALUE;
        for (CabSelectionDAO cabDetail : cabSelectionService.cabDetails) {
            double timeOfCab = (cabDetail.cabDistanceFromOrigin) / (cabDetail.cabSpeedOnRoute);
            timeToReach.add(timeOfCab);
            int driverId = cabDetail.driver_Id;
            double ratings = iRatings.getAverageRatingOfDriver(driverId);
            if (timeOfCab < min) {
                selectedCab = cabDetail;
                min = timeOfCab;
            }
        }
        System.out.println("Estimated Arrival time of each Cab:");
        for (Double driverTimeToReach : timeToReach) {
            System.out.println(String.format("%.2f", driverTimeToReach));
        }
        System.out.println("Fastest cab is reaching your location in " + String.format("%.2f", min) + " minutes");
        return selectedCab;
    }
}
