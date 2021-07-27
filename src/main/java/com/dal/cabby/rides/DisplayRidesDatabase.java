package com.dal.cabby.rides;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.pojo.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisplayRidesDatabase implements IDisplayRidesDatabase {
    private IPersistence iPersistence;

    public DisplayRidesDatabase() throws SQLException {
        iPersistence = DBHelper.getInstance();
    }

    /**
     * This method gets the ride details from the database
     * Parameters:
     *   startDate - start date
     *   endDate - end date
     *   userID - id of the user
     *   userType - type of user (Customer or Driver)
     * Returns:
     *   list of completed rides between start date and end date
     */
    public List<String> getRidesFromDb(String startDate, String endDate, int userID, UserType userType) throws SQLException {
        List<String> listOfRides = new ArrayList<>();
        listOfRides.add("Ride Details ->");
        String query = String.format("select \n" +
            "bookings.booking_id,\n" +
            "bookings.source,\n" +
            "bookings.destination,\n" +
            "trips.trip_amount\n" +
            "from bookings inner join trips\n" +
            "on bookings.booking_id = trips.booking_id\n" +
            "where cast(trips.created_at as date) between '%s' and '%s' \n" +
            "and trips.%s = %d\n" +
            "order by trips.booking_id;", startDate, endDate, getColumnName(userType), userID);
        ResultSet result = iPersistence.executeSelectQuery(query);
        while (result.next()) {
            String bookingID = result.getString("booking_id");
            String pickupLocation = result.getString("source");
            String dropLocation = result.getString("destination");
            double rideAmount = result.getDouble("trip_amount");
            String rideDetail = "BookingID: " + bookingID + ", Pickup: " + pickupLocation +
                ", Destination: " + dropLocation + ", Price: " + rideAmount +
                ", Status: Completed";
            listOfRides.add(rideDetail);
        }
        if (listOfRides.size() == 1) {
            listOfRides.add(("No rides to display"));
        }
        return listOfRides;
    }

    /**
     * method to get the column name for user category
     */
    private String getColumnName(UserType userType) {
        if (userType == UserType.DRIVER) {
            return "driver_id";
        } else if (userType == UserType.CUSTOMER) {
            return "cust_id";
        } else {
            throw new RuntimeException("Usertype invalid: " + userType);
        }
    }
}
