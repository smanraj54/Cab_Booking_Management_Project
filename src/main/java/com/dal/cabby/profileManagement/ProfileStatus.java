package com.dal.cabby.profileManagement;

import com.dal.cabby.dbHelper.IPersistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileStatus {
    private IPersistence iPersistence;

    public ProfileStatus(IPersistence iPersistence) {
        this.iPersistence = iPersistence;
    }

    public boolean isDriverAproved(int driver_id) throws SQLException {
        String query = String.format("select status from driver where driver_id=%d", driver_id);
        ResultSet resultSet = iPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            return resultSet.getBoolean("status");
        }
        throw new RuntimeException(String.format("Driver with id: %s not found", driver_id));
    }

    public boolean isCustomerApproved(int custmoerId) throws SQLException {
        String query = String.format("select status from customer where cust_id=%d", custmoerId);
        ResultSet resultSet = iPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            return resultSet.getBoolean("status");
        }
        throw new RuntimeException(String.format("Customer with id: %s not found", custmoerId));
    }
}
