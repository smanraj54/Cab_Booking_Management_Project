package com.dal.cabby.cabSelection;
import com.dal.cabby.cabPrice.CabPriceCalculator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import com.dal.cabby.dbHelper.DBHelper;

public class CabSelection {
    DBHelper dbHelper;
    public CabSelection() throws SQLException {
        dbHelper=new DBHelper();
        try {
            dbHelper.initialize();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    static Scanner ss=new Scanner(System.in);
    CabPriceCalculator cabPriceCalculator=new CabPriceCalculator();
    public String sourceLocation, destinationLocation;
    double sourceDistance = 0.0;
    ArrayList<CabDAO> cabDetails=new ArrayList<>();

    public void preferredCab() throws SQLException {
        System.out.println("Enter your Cab preference");
        System.out.println("1. Micro and Mini");
        System.out.println("2. Prime Sedan");
        System.out.println("3. Prime SUV");
        System.out.println("4. Luxury Class");
        int input=ss.nextInt();
        System.out.println("Enter Source location");
        sourceLocation= ss.next();
        System.out.println("Enter Destination location");
        destinationLocation= ss.next();
        switch (input){
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
        cabPriceCalculator.priceCalculation(sourceLocation,destinationLocation,false,"urban", input);
    }

    public double fetchSourceLocation() throws SQLException {
        String sourceLocationQuery= String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'",sourceLocation);
        ResultSet resultSet= dbHelper.executeSelectQuery(sourceLocationQuery);
        while (resultSet.next()) {
            sourceDistance = resultSet.getDouble("distanceFromOrigin");
        }
        return sourceDistance;
    }

    public ArrayList<CabDAO> getAllNearbyCabs() throws SQLException {
        double lowerRangeOfCabs = (sourceDistance-5);
        double upperRangeOfCabs = (sourceDistance+5);
        String Query= String.format("Select cabName, cabDistanceFromOrigin, driver_id, routeTrafficDensity, " +
                "cabSpeedOnRoute from cabs where cabDistanceFromOrigin BETWEEN '%f' AND '%f'"
                ,lowerRangeOfCabs,upperRangeOfCabs);
        ResultSet resultSet= dbHelper.executeSelectQuery(Query);
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

        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<cabDetails.size();i++){
            arrayList.add(cabDetails.get(i).cabName);
        }

        for(int i=0;i<arrayList.size();i++) {
            cabPriceCalculator.locationAndCabDistanceFromOrigin(sourceLocation,arrayList.get(i));
        }
        return cabDetails;
    }
}

