package com.dal.cabby.cabPrice;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.dbHelper.IPersistence;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CabPriceWithAmenities {
    Inputs inputs;
    IPersistence iPersistence;
    CabPriceDistanceFactor cabPriceDistanceFactor;
    public CabPriceWithAmenities(Inputs inputs){
        this.inputs=inputs;
        cabPriceDistanceFactor=new CabPriceDistanceFactor(inputs);
        try {
            iPersistence= DBHelper.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public double amenities(String source,double distance, int cabCategory, double hour) throws SQLException {
        // For every 30 minutes of ride we are charging extra $2 per amenity.
        System.out.println("Choose amenities:");
        System.out.println("1. CarTV");
        System.out.println("2. Wifi");
        System.out.println("3. Both");
        int input= inputs.getIntegerInput();
        double basicPrice= cabPriceDistanceFactor.distanceFactor(source,distance, cabCategory,hour);
        System.out.println("Price without amenities: $"+String.format("%.2f",basicPrice));
        double priceWithAmenities= basicPrice;
        double extraCharge=0;
        double speed=0.0;
        String query=String.format("Select averageSpeed from price_Calculation where sourceName='%s'",source);
        ResultSet rs= iPersistence.executeSelectQuery(query);
        while(rs.next()){
            speed=rs.getDouble("averageSpeed");
        }
        double time=(distance/speed)*60;   //Converted hours into minutes
        double rideInMinutes=(time/30);
        switch (input){
            case 1:
                extraCharge=(2*rideInMinutes);
                System.out.println("Extra charges: $"+String.format("%.2f",extraCharge));
                priceWithAmenities+=extraCharge;
                break;
            case 2:
                extraCharge=(2*rideInMinutes);
                System.out.println("Extra charges: $"+String.format("%.2f",extraCharge));
                priceWithAmenities+=extraCharge;
                break;
            case 3:
                extraCharge=(2*(2*rideInMinutes));
                System.out.println("Extra charges: $"+String.format("%.2f",extraCharge));
                priceWithAmenities+=extraCharge;
                break;
        }
        System.out.println("Total Price for this ride is: $"+ String.format("%.2f",priceWithAmenities));
        return (Math.round(priceWithAmenities*100.0)/100.0);
    }
}


