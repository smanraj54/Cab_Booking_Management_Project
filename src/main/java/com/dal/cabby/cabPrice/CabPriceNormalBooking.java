package com.dal.cabby.cabPrice;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CabPriceNormalBooking {
    IPersistence iPersistence;
    Inputs inputs;
    public CabPriceNormalBooking(Inputs inputs){
        this.inputs=inputs;
        try {
            iPersistence = DBHelper.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public double distanceFactor(String source, double distance, int cabType, double hour) throws SQLException {
        double shortDistance = 5; //For initial few kilometers 5 dollars would be charged per Km
        String rideArea = null;
        double price = 0.0;
        if (distance <= shortDistance) {
            for (int initialKilometers = 1; initialKilometers <= distance; initialKilometers++) {
                price += 5;
            }
        } else {
            for (int lessKilometers = 1; lessKilometers <= shortDistance; lessKilometers++) {
                price += 5;
            }
            //Price per kilometer would be reduced for Long Journey
            for (int longKilometers = 6; longKilometers <= distance; longKilometers++) {
                price += 3.5;
            }
        }
        // Extra 20% would be charged on base fare if ride timing would be from 9:00 PM till 5:00 AM or During office hours
        if ((hour >= 21 && hour <= 24) || (hour >= 00 && hour < 05) || (hour >= 17 && hour < 19)) {
            price += (.20 * price);
        }
        // rides in urban area would be bit costlier
        String query = String.format("Select sourceArea from price_Calculation where sourceName='%s'", source);
        ResultSet resultSet = iPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            rideArea = resultSet.getString("sourceArea");
        }
        if (rideArea.equals("urban")) {
            price += (.05 * price);
        }

        if (cabType == 2) {
            price += (.1 * price);  //10% price would be higher for Prime Sedan category of Cabs
        }
        if (cabType == 3) {
            price += (.25 * price);  //25% price would be higher for Prime SUV category of Cabs
        }
        if (cabType == 4) {
            price += (.40 * price);  //40% price would be higher for PLuxury Class Cabs
        }
        return (Math.round(price * 100.0) / 100.0);
    }

}
