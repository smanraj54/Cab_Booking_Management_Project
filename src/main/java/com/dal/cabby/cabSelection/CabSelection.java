package com.dal.cabby.cabSelection;
import com.dal.cabby.cabPrice.CabPriceCalculator;

import java.sql.ResultSet;
import java.sql.SQLException;
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
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        cabPriceCalculator.priceCalculation(sourceLocation,destinationLocation,false,"urban",true, input);
    }

    public double fetchSource() throws SQLException {
        String sourceLocationQuery= String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'",sourceLocation);// with sourceLocation find it's distance fro, origin
        ResultSet sourceDistanceFromOrigin= dbHelper.executeSelectQuery(sourceLocationQuery);
        while (sourceDistanceFromOrigin.next()) {
            sourceDistance = sourceDistanceFromOrigin.getDouble("distanceFromOrigin");
        }
        //System.out.println("Source Distance from origin:"+sourceDistance);
        return sourceDistance;
    }
}

