package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.DBHelper;

import java.sql.SQLException;

/*
  This is a utility class for Admin class.
 */
public class AdminHelper {

    static String driverProfile = "driver";
    static String customerProfile = "customer";

    boolean updateStatus(String profileType, boolean newStatus, int id) throws SQLException {
        DBHelper dbHelper = new DBHelper();
        String query = getQuery(profileType, id, newStatus);
        try {
            dbHelper.initialize();
            dbHelper.executeCreateOrUpdateQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            dbHelper.close();
        }
        return true;
    }

    String getQuery(String profileType, int id, boolean status) {
        if (profileType.equals("driver")) {
            return String.format("update driver set status=%b where driver_id=%d", status, id);
        } else if (profileType.equals("customer")) {
            return String.format("update customer set status=%b where cust_id=%d", status, id);
        } else {
            throw new RuntimeException("Invalid profile type: " + profileType);
        }
    }
}
