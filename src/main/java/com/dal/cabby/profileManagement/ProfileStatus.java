package com.dal.cabby.profileManagement;

import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.pojo.UserType;

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

    public void approveDriver(int driver_id) throws SQLException {
        updateStatus(UserType.DRIVER, true, driver_id);
    }

    public void approveCustomer(int driver_id) throws SQLException {
        updateStatus(UserType.CUSTOMER, true, driver_id);
    }

    public void deactivateDriver(int driver_id) throws SQLException {
        updateStatus(UserType.DRIVER, false, driver_id);
    }

    public void deactivateCustomer(int driver_id) throws SQLException {
        updateStatus(UserType.CUSTOMER, false, driver_id);
    }

    private void updateStatus(UserType userType, boolean newStatus, int id) throws SQLException {
        String query = getQuery(userType, id, newStatus);
        iPersistence.executeCreateOrUpdateQuery(query);
    }

    private String getQuery(UserType userType, int id, boolean status) {
        if (userType == UserType.DRIVER) {
            return String.format("update driver set status=%b where driver_id=%d", status, id);
        } else if (userType == UserType.CUSTOMER) {
            return String.format("update customer set status=%b where cust_id=%d", status, id);
        } else {
            throw new RuntimeException("Invalid profile type: " + userType.toString());
        }
    }
}
