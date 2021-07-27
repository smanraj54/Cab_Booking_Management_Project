package com.dal.cabby.cabPrice;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import java.sql.SQLException;

public class CabPriceCalculator implements ICabPriceCalculator {
    IPersistence iPersistence;
    Inputs inputs;
    ICabPriceNormalBooking cabPriceNormalBooking;
    ICabPriceRideSharing cabPriceRideSharing;
    ICabPriceWithAmenities cabPriceAmenities;
    CabPriceDBLayer cabPriceDBLayer;

    public CabPriceCalculator(Inputs inputs){
        this.inputs=inputs;
        cabPriceNormalBooking =new CabPriceNormalBooking(inputs);
        cabPriceRideSharing=new CabPriceRideSharing(inputs);
        cabPriceAmenities=new CabPriceWithAmenities(inputs);
        try {
            iPersistence = DBHelper.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cabPriceDBLayer=new CabPriceDBLayer();
    }

    /*
        This method servers as Presentation layer. Based on User's preference price will be calculated
        for a specific category.
     */
    @Override
    public double priceCalculation(String source, String destination, int cabType, double hour) throws SQLException {
        System.out.println("*** Select your Preferences ***");
        System.out.println("1. Normal Booking");
        System.out.println("2. Want to share ride with co-passenger");
        System.out.println("3. Want to have Car TV and Wifi during ride");
        int userInput = inputs.getIntegerInput();
        double distance = cabPriceDBLayer.locationsDistanceFromOrigin(source, destination);
        switch (userInput) {
            case 1:
                return cabPriceNormalBooking.distanceFactor(source, distance, cabType, hour);
            case 2:
                return cabPriceRideSharing.rideSharing(source, distance, cabType, hour);
            case 3:
                return cabPriceAmenities.amenities(source,distance,cabType,hour);
            case 4:
                System.out.println("Invalid option selected");
        }
        return -1.0;
    }
}
