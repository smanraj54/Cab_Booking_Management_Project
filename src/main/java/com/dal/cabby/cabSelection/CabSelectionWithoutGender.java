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
    CabSelectionDBLayer cabSelectionDBLayer;
    CabSelection cabSelection;
    CabPriceCalculator cabPriceCalculator;

    /*
        This is the constructor of class which interacts with DB Layer to fetch Nearby Cabs
     */
    public CabSelectionWithoutGender(Inputs inputs, CabSelection cabSelection){
        this.inputs=inputs;
        this.cabSelection = cabSelection;
        cabPriceCalculator=new CabPriceCalculator(inputs);
        try {
            iPersistence=DBHelper.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        cabSelectionDBLayer=new CabSelectionDBLayer(inputs, cabSelection);
    }

    /*
        This method pass names of all nearby cabs to price Calculation class to calculate distance between
        Source Location and Cab location which is further used in calculating price.
     */
    public CabSelectionDAO withoutGenderPreference() throws SQLException {
        List<CabSelectionDAO> mainArrayList;
        mainArrayList = cabSelectionDBLayer.getAllNearbyCabs();
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
        for (CabSelectionDAO cabDetail : mainArrayList) {
            arrayList.add(cabDetail.cabName);
        } /*
        Created this arrayList to store names of Nearby cabs which will be passed to a function along with
        sourceLocation to calculate distance between cabs and source location.
        */

        for (String s : arrayList) {
            cabPriceCalculator.locationAndCabDistanceFromOrigin(cabSelection.sourceLocation, s);
        }
        return bestNearbyCabWithoutFilter();
    }

    /*
    This method return best possible cab/ optimal cab based on customer's preference after
    checking Traffic density on routes.
     */
    private CabSelectionDAO bestNearbyCabWithoutFilter() throws SQLException {
        List<Double> timeToReach = new ArrayList<>();
        CabSelectionDAO selectedCab = null;
        IRatings iRatings = new Ratings();
        double min = Double.MAX_VALUE;
        for (CabSelectionDAO cabDetail : cabSelectionDBLayer.cabDetails) {
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
