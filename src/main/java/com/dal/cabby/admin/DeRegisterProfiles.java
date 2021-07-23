package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.IPersistence;

import java.sql.SQLException;

/*
   This class will implementation of Driver and Customer deregistraion.
   Based on poor performance or bad behavior of driver and customer, admin can
   deregister such profiles.
*/
public class DeRegisterProfiles {

    private IPersistence IPersistence;
    DeRegisterProfiles(IPersistence IPersistence) {
        this.IPersistence = IPersistence;
    }
    // Deactivate the customer profile.
    public boolean deRegisterCustomer(int cust_id) {
        try {
            AdminHelper adminHelper = new AdminHelper(IPersistence);
            adminHelper.updateStatus(AdminHelper.customerProfile, false, cust_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Deactivate the driver profile.
    public boolean deRegisterDriver(int driver_id) {
        try {
            AdminHelper adminHelper = new AdminHelper(IPersistence);
            adminHelper.updateStatus(AdminHelper.driverProfile, false, driver_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
