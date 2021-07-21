package com.dal.cabby.cabSelection;

import com.dal.cabby.cabPrice.CabPriceCalculator;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CabSelectionService {
    private DBHelper dbHelper;
    private Inputs inputs;
    private CabPriceCalculator cabPriceCalculator;
    private String sourceLocation, destinationLocation;
    private double sourceDistance = 0.0;
    private ArrayList<CabSelectionDAO> cabDetails = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();
    private String Query;
    private ResultSet resultSet;

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

    public Booking preferredCab(int custId, double hour) throws SQLException {
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
        double sourceDistance = fetchSourceLocation();
        ArrayList<CabSelectionDAO> cabs = getAllNearbyCabs();
        CabSelectionDAO bestCab = bestNearbyCab();
        double price = cabPriceCalculator.priceCalculation(sourceLocation, destinationLocation, cabType);
        Booking booking = new Booking(-1, custId, bestCab.driver_Id, -1, sourceLocation, destinationLocation, hour + "", price);
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
        Query = String.format("Select cabName, cabDistanceFromOrigin, driver_id, routeTrafficDensity, " +
                        "cabSpeedOnRoute from cabs where cabDistanceFromOrigin BETWEEN '%f' AND '%f'"
                , lowerRangeOfCabs, upperRangeOfCabs);
        resultSet = dbHelper.executeSelectQuery(Query);
        while (resultSet.next()) {
            CabSelectionDAO cabDetail = new CabSelectionDAO(resultSet.getString("cabName"),
                    resultSet.getDouble("cabDistanceFromOrigin"),
                    resultSet.getInt("driver_id"),
                    resultSet.getString("routeTrafficDensity"),
                    resultSet.getInt("cabSpeedOnRoute"));
            cabDetails.add(cabDetail);
        }
        System.out.println("List of nearby Cabs:");
        for (int i = 0; i < cabDetails.size(); i++) {
            System.out.println(cabDetails.get(i).toString());
        }

        for (int i = 0; i < cabDetails.size(); i++) {
            arrayList.add(cabDetails.get(i).cabName);
        } /*
        Created this arrayList to store names of Nearby cabs which will be passed to a function along with
        sourceLocation to calculate distance between cabs and source location.
        */

        for (int i = 0; i < arrayList.size(); i++) {
            cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation, arrayList.get(i));
        }
        return cabDetails;
    }

    public CabSelectionDAO bestNearbyCab() {
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
        cabSelectionService.preferredCab(1, 12);
    }
}

