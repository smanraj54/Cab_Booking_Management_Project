package com.dal.cabby.cabPrice;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;

import java.sql.SQLException;

public class CabPriceRideSharing {
    Inputs inputs;
    IPersistence iPersistence;
    CabPriceNormalBooking cabPriceNormalBooking;

    //CabPriceCalculator cabPriceCalculator;
    public CabPriceRideSharing(Inputs inputs) {
        this.inputs = inputs;
        cabPriceNormalBooking = new CabPriceNormalBooking(inputs);
        //cabPriceCalculator=new CabPriceCalculator(inputs);
        try {
            iPersistence = DBHelper.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double rideSharing(String source, double distance, int cabCategory, double hour) throws SQLException {
        System.out.println("Choose number of co-passengers: ");
        System.out.println("One co-passenger");
        System.out.println("Two co-passengers");
        int input = inputs.getIntegerInput();
        double basicPrice = cabPriceNormalBooking.distanceFactor(source, distance, cabCategory, hour);
        System.out.println("Price without Co-passenger: $" + String.format("%.2f", basicPrice));
        double priceWithCoPassenger = basicPrice;
        double discount;
        switch (input) {
            case 1:
                priceWithCoPassenger -= (.10 * basicPrice);
                break;
            case 2:
                priceWithCoPassenger -= (.15 * basicPrice);
                break;
        }
        discount = basicPrice - priceWithCoPassenger;
        System.out.println("You got a discount of $" + String.format("%.2f", discount) + " on sharing ride with co-passenger");
        System.out.println("Total Price for this ride is: $" + String.format("%.2f", priceWithCoPassenger));
        return (Math.round(priceWithCoPassenger * 100.0) / 100.0);
    }
}
