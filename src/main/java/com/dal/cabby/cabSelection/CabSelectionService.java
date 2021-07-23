package com.dal.cabby.cabSelection;
import com.dal.cabby.cabPrice.CabPriceCalculator;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Booking;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CabSelectionService {
    CabSelectionDAO bestCab;
    private IPersistence IPersistence;
    private Inputs inputs;
    private CabPriceCalculator cabPriceCalculator;
    private String sourceLocation, destinationLocation;
    private ArrayList<CabSelectionDAO> cabDetails = new ArrayList<>();

    public CabSelectionService(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        cabPriceCalculator = new CabPriceCalculator(inputs);
        try {
            IPersistence = DBHelper.getInstance();
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
        fetchSourceLocation();
        getAllNearbyCabs();
        double price = cabPriceCalculator.priceCalculation(sourceLocation, destinationLocation, cabType, hour);
        Booking booking = new Booking(-1, custId, bestCab.driver_Id, -1, sourceLocation, destinationLocation, "", price, false);
        return booking;
    }

    private double fetchSourceLocation() throws SQLException {
        double sourceDistance=0.0;
        String query = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", sourceLocation);
        ResultSet resultSet = IPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            sourceDistance = resultSet.getDouble("distanceFromOrigin");
        }
        return sourceDistance;
    }

    private ArrayList<CabSelectionDAO> getAllNearbyCabs() throws SQLException {
        double lowerRangeOfCabs = (fetchSourceLocation() - 5);
        double upperRangeOfCabs = (fetchSourceLocation() + 5);
        String query = String.format("Select cabName, cabId, cabDistanceFromOrigin, driver_id, routeTrafficDensity, " +
                        "cabSpeedOnRoute,driverGender from cabs where cabDistanceFromOrigin BETWEEN '%f' AND '%f'"
                , lowerRangeOfCabs, upperRangeOfCabs);
        ResultSet resultSet = IPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            CabSelectionDAO cabDetail = new CabSelectionDAO(resultSet.getString("cabName"),
                    resultSet.getInt("cabId"), resultSet.getDouble("cabDistanceFromOrigin"),
                    resultSet.getInt("driver_id"), resultSet.getString("routeTrafficDensity"),
                    resultSet.getInt("cabSpeedOnRoute"), resultSet.getString("driverGender"));
            cabDetails.add(cabDetail);
        }
        System.out.println("Unfiltered List of nearby Cabs:");
        for (CabSelectionDAO cabDetail : cabDetails) {
            System.out.println(cabDetail.toString());
        }
        System.out.println("Do you want to book cab based on Gender of Cab Driver??");
        System.out.println("1. YES ");
        System.out.println("2. No ");
        int input = inputs.getIntegerInput();
        switch (input) {
            case 1:
                bestCab = withGenderPreference();
                break;
            case 2:
                bestCab = withoutGenderPreference();
        }
        return cabDetails;
    }

    private CabSelectionDAO withGenderPreference() throws SQLException {
        ArrayList<String> maleArrayList = new ArrayList<>();
        ArrayList<String> femaleArrayList = new ArrayList<>();
        String gender;
        System.out.println("Select your gender preference");
        System.out.println("1. Male ");
        System.out.println("2. Female ");
        int input = inputs.getIntegerInput();
        switch (input) {
            case 1:
                for (CabSelectionDAO cabDetail : cabDetails) {
                    gender = cabDetail.driverGender;
                    if (gender.equals("Male")) {
                        maleArrayList.add(cabDetail.cabName);
                    }
                }
                for (String s : maleArrayList) {
                    cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation, s);
                }
                return bestNearbyCabOfMaleDriver();
            case 2:
                for (CabSelectionDAO cabDetail : cabDetails) {
                    gender = cabDetail.driverGender;
                    if (gender.equals("Female")) {
                        femaleArrayList.add(cabDetail.cabName);
                    }
                }
                for (String s : femaleArrayList) {
                    cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation, s);
                }
                return bestNearbyCabOfFemaleDriver();
            default:
                System.out.println("Invalid input: " + input);
                return null;
        }
    }

    private CabSelectionDAO withoutGenderPreference() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        for (CabSelectionDAO cabDetail : cabDetails) {
            arrayList.add(cabDetail.cabName);
        } /*
        Created this arrayList to store names of Nearby cabs which will be passed to a function along with
        sourceLocation to calculate distance between cabs and source location.
        */

        for (String s : arrayList) {
            cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation, s);
        }
        return bestNearbyCabWithoutFilter();
    }

    private CabSelectionDAO bestNearbyCabOfMaleDriver() throws SQLException {
        ArrayList<Double> maleDriverTimeToReach = new ArrayList<>();
        String gender;
        CabSelectionDAO selectedCab = null;
        double min = Double.MAX_VALUE;
        for (CabSelectionDAO cabDetail : cabDetails) {
            gender = cabDetail.driverGender;
            if (gender.equals("Male")) {
                double timeOfCab = (cabDetail.cabDistanceFromOrigin) / (cabDetail.cabSpeedOnRoute);
                maleDriverTimeToReach.add(timeOfCab);
                if (timeOfCab < min) {
                    selectedCab = cabDetail;
                    min = timeOfCab;
                }
            }
        }
        System.out.println("Estimated Arrival time of each Cab:" + maleDriverTimeToReach);
        System.out.println("Fastest cab is reaching your location in " + String.format("%.2f", min) + " minutes");
        return selectedCab;
    }

    private CabSelectionDAO bestNearbyCabOfFemaleDriver() throws SQLException {
        ArrayList<Double> femaleDriverTimeToReach = new ArrayList<>();
        String gender;
        CabSelectionDAO selectedCab = null;
        double min = Double.MAX_VALUE;
        for (CabSelectionDAO cabDetail : cabDetails) {
            gender = cabDetail.driverGender;
            if (gender.equals("Female")) {
                double timeOfCab = (cabDetail.cabDistanceFromOrigin) / (cabDetail.cabSpeedOnRoute);
                femaleDriverTimeToReach.add(timeOfCab);
                if (timeOfCab < min) {
                    selectedCab = cabDetail;
                    min = timeOfCab;
                }
            }
        }
        System.out.println("Estimated Arrival time of each Cab:" + femaleDriverTimeToReach);
        System.out.println("Fastest cab is reaching your location in " + String.format("%.2f", min) + " minutes");
        return selectedCab;
    }

    private CabSelectionDAO bestNearbyCabWithoutFilter() {
        ArrayList<Double> timeToReach = new ArrayList<>();
        CabSelectionDAO selectedCab = null;
        double min = Double.MAX_VALUE;
        for (CabSelectionDAO cabDetail : cabDetails) {
            double timeOfCab = (cabDetail.cabDistanceFromOrigin) / (cabDetail.cabSpeedOnRoute);
            timeToReach.add(timeOfCab);
            if (timeOfCab < min) {
                selectedCab = cabDetail;
                min = timeOfCab;
            }
        }
        System.out.println("Estimated Arrival time of each Cab:" + timeToReach);
        System.out.println("Fastest cab is reaching your location in " + String.format("%.2f", min) + " minutes");
        return selectedCab;
    }

    public static void main(String[] args) throws SQLException {
        CabSelectionService cabSelectionService = new CabSelectionService(new InputFromUser());
        cabSelectionService.preferredCab(1, 20);
    }
}

