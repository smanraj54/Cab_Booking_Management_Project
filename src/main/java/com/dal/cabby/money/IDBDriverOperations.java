package com.dal.cabby.money;

import java.sql.SQLException;

/**
 * This interface has the method to get earning from database
 */
public interface IDBDriverOperations {

    /**
     * This method will check the earning details from database
     * Parameters:
     *   driverID - id of the driver
     *   date - date for which earning is being calculated
     * Returns:
     *   earning on that particular date
     */
    public double earningOnDate(int driverID, String date) throws SQLException;
}
