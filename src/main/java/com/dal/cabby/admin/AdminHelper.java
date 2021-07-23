package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.pojo.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
  This is a utility class for Admin class.
 */
class AdminHelper {
    static String driverProfile = "driver";
    static String customerProfile = "customer";
    IPersistence IPersistence;

    AdminHelper(IPersistence IPersistence) throws SQLException {
        this.IPersistence = IPersistence;
    }

    List<Profile> listOfDriversToBeApproved() throws SQLException {
        String query = "select driver_id, name from driver where status = false order by driver_id desc";
        ResultSet resultSet = IPersistence.executeSelectQuery(query);
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
        ResultSet resultSet = IPersistence.executeSelectQuery(query);
        List<Profile> profileList = new ArrayList<>();
        while (resultSet.next()) {
            int custId = resultSet.getInt("cust_id");
            String custName = resultSet.getString("name");
            profileList.add(new Profile(custId, custName));
        }
        return profileList;
    }
}
