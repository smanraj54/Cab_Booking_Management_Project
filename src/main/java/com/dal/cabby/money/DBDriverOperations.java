package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to send query related to the driver earnings to the database
 * and get the appropriate result.
 */
public class DBDriverOperations implements IDBDriverOperations {
    private IPersistence iPersistence;
    private ICommissionCalculation commission;

    public DBDriverOperations() throws SQLException {
        iPersistence = DBHelper.getInstance();
        commission = new CommissionCalculation();
    }

    /**
     * This method will check the earning details from database
     * Parameters:
     *   driverID - id of the driver
     *   date - date for which earning is being calculated
     * Returns:
     *   earning on that particular date
     */
    public double earningOnDate(int driverID, String date) throws SQLException {
        int totalRides = 0;
        double travelDistance = 0.0;
        double travelTime = 0.0;
        double amountOfRides = 0.0;
        String query = String.format("select \n" +
            "count(trip_id) as total_rides,\n" +
            "sum(distance_covered) as total_distance_covered,\n" +
            "sum(timestampdiff(second,trip_start_time,trip_end_time))/3600 as total_travel_time,\n" +
            "sum(trip_amount) as total_amount\n" +
            "from trips \n" +
            "where driver_id = %d and cast(created_at as date) = '%s'\n" +
            "group by driver_id", driverID, date);
        ResultSet output = iPersistence.executeSelectQuery(query);
        while (output.next()) {
            totalRides = output.getInt("total_rides");
            travelDistance = output.getDouble("total_distance_covered");
            travelTime = output.getDouble("total_travel_time");
            amountOfRides = output.getDouble("total_amount");
        }
        // getting commission percentage
        int commissionPercentage = commission.getCommissionPercentage(totalRides, travelDistance, travelTime);
        return (amountOfRides - ((amountOfRides * commissionPercentage) / 100));
    }
}
