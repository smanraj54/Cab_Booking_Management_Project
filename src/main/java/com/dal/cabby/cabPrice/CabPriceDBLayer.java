package com.dal.cabby.cabPrice;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CabPriceDBLayer {
    IPersistence iPersistence;
    SourceAndDestinationDistance locationsDistance;
    public CabPriceDBLayer(){
        try {
            iPersistence= DBHelper.getInstance();
            locationsDistance=new SourceAndDestinationDistance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
        This method fetch source and destination locations from database and calculates distance between
        source and destination locations.
     */
    public double locationsDistanceFromOrigin(String source, String destination) throws SQLException {
        double distance=0.0;
        double sourceDistanceFromOrigin = 0.0;
        double destinationDistanceFromOrigin = 0.0;
        String query = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", source);
        ResultSet resultSet = iPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            sourceDistanceFromOrigin = resultSet.getDouble("distanceFromOrigin");
        }

        String query1 = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", destination);
        ResultSet resultSet1 = iPersistence.executeSelectQuery(query1);
        while (resultSet1.next()) {
            destinationDistanceFromOrigin = resultSet1.getDouble("distanceFromOrigin");
        }
        double distanceBetweenSourceAndDestination = locationsDistance.calculateDistance(sourceDistanceFromOrigin, destinationDistanceFromOrigin);
        System.out.println("Distance between " + source + " and " + destination + " is: " + distanceBetweenSourceAndDestination + " KM");
        return (Math.round(distanceBetweenSourceAndDestination * 100.0) / 100.0);
    }

}
