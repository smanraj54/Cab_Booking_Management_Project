package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.IPersistence;

import java.sql.SQLException;

public class ApproveProfiles {
    private IPersistence IPersistence;

    public ApproveProfiles(IPersistence IPersistence) {
        this.IPersistence = IPersistence;
    }

    /*
        Driver will be by default deactivated at the time of first time registration.
        Admin will have to approve the driver after checking all the information driver has given
        at the time of registration.

        This function will accept the driver_id and it will the update the status field.
     */
    public boolean approveProfile(int id, String profileType) {
        try {
            AdminHelper adminHelper = new AdminHelper(IPersistence);
            adminHelper.updateStatus(profileType, true, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
