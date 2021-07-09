package com.dal.cabby.admin;

import java.sql.SQLException;

public class ApproveProfiles {

    public static void main(String[] args) {
        ApproveProfiles a = new ApproveProfiles();
        a.approveProfile(1, AdminHelper.customerProfile);
    }

    /*
        Driver will be by default deactivated at the time of first time registration.
        Admin will have to approve the driver after checking all the information driver has given
        at the time of registration.

        This function will accept the driver_id and it will the update the status field.
     */
    public boolean approveProfile(int id, String profileType) {
        try {
            AdminHelper adminHelper = new AdminHelper();
            adminHelper.updateStatus(profileType, true, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
