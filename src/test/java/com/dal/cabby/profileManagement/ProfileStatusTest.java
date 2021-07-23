package com.dal.cabby.profileManagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProfileStatusTest {

    @Test
    void isDriverAproved() throws SQLException {
        ProfileStatus profileStatus = new ProfileStatus();
        profileStatus.deactivateDriver(1);
        Assertions.assertFalse(profileStatus.isDriverAproved(1), "isDriverAproved() returned wrong result");
        profileStatus.approveDriver(1);
    }

    @Test
    void isCustomerApproved() throws SQLException {
        ProfileStatus profileStatus = new ProfileStatus();
        profileStatus.deactivateCustomer(1);
        Assertions.assertFalse(profileStatus.isCustomerApproved(1), "isCustomerApproved() returned wrong result");
        profileStatus.approveDriver(1);
    }

    @Test
    void approveDriver() throws SQLException {
        ProfileStatus profileStatus = new ProfileStatus();
        profileStatus.deactivateDriver(1);
        Assertions.assertFalse(profileStatus.isDriverAproved(1), "isDriverAproved() returned wrong result");
        profileStatus.approveDriver(1);
        Assertions.assertTrue(profileStatus.isDriverAproved(1), "isDriverAproved() returned wrong result");
    }

    @Test
    void approveCustomer() throws SQLException {
        ProfileStatus profileStatus = new ProfileStatus();
        profileStatus.deactivateCustomer(1);
        Assertions.assertFalse(profileStatus.isCustomerApproved(1), "isCustomerApproved() returned wrong result");
        profileStatus.approveCustomer(1);
        Assertions.assertTrue(profileStatus.isCustomerApproved(1), "isCustomerApproved() returned wrong result");
    }

    @Test
    void deactivateDriver() throws SQLException {
        ProfileStatus profileStatus = new ProfileStatus();
        profileStatus.deactivateDriver(1);
        Assertions.assertFalse(profileStatus.isDriverAproved(1), "isDriverAproved() returned wrong result");
        profileStatus.approveDriver(1);
        Assertions.assertTrue(profileStatus.isDriverAproved(1), "isDriverAproved() returned wrong result");
    }

    @Test
    void deactivateCustomer() throws SQLException {
        ProfileStatus profileStatus = new ProfileStatus();
        profileStatus.deactivateCustomer(1);
        Assertions.assertFalse(profileStatus.isCustomerApproved(1), "isCustomerApproved() returned wrong result");
        profileStatus.approveCustomer(1);
        Assertions.assertTrue(profileStatus.isCustomerApproved(1), "isCustomerApproved() returned wrong result");
    }
}