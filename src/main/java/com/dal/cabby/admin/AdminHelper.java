package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.pojo.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
  This is a utility class for Admin class.
 */
public class AdminHelper {

    static String driverProfile = "driver";
    static String customerProfile = "customer";
    DBHelper dbHelper;

    AdminHelper() throws SQLException {
        dbHelper = new DBHelper();
        dbHelper.initialize();
    }

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

    List<Profile> listOfDriversToBeApproved() throws SQLException {
        String query = "select driver_id, name from driver where status = false order by driver_id desc";
        ResultSet resultSet = dbHelper.executeSelectQuery(query);
        List<Profile> profileList = new ArrayList<>();
        while (resultSet.next()) {
            int driverId = resultSet.getInt("driver_id");
            String driverName = resultSet.getString("name");
            profileList.add(new Profile(driverId, driverName));
        }
        return profileList;
    }

    List<Profile> listOfCustomersToBeApproved() throws SQLException {
        String query = "select cust_id, name from customer where status = false order by cust_id desc";
        ResultSet resultSet = dbHelper.executeSelectQuery(query);
        List<Profile> profileList = new ArrayList<>();
        while (resultSet.next()) {
            int custId = resultSet.getInt("cust_id");
            String custName = resultSet.getString("name");
            profileList.add(new Profile(custId, custName));
        }
        return profileList;
    }
}