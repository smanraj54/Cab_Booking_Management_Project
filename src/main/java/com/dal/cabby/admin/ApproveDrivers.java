package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.DBHelper;

import java.sql.SQLException;

public class ApproveDrivers {

    /*
        Driver will be by default deactivated at the time of first time registration.
        Admin will have to approve the driver after checking all the information driver has given
        at the time of registration.

        This function will accept the driver_id and it will the update the status field.
     */
    public boolean approveDriver(int driver_id) {
        try {
            AdminHelper adminHelper = new AdminHelper();
            adminHelper.updateStatus(AdminHelper.driverProfile, true, driver_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ApproveDrivers a = new ApproveDrivers();
        a.approveDriver(1);
    }
}
