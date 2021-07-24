package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Profile;
import com.dal.cabby.profileManagement.ProfileStatus;

import java.sql.SQLException;
import java.util.List;

class AdminTasks {
    private final AdminHelper adminHelper;
    private final Inputs inputs;
    private IPersistence iPersistence;
    ProfileStatus profileStatus;

    public AdminTasks(AdminHelper adminHelper, Inputs inputs, IPersistence iPersistence) throws SQLException {
        this.adminHelper = adminHelper;
        this.inputs = inputs;
        this.iPersistence = iPersistence;
        profileStatus = new ProfileStatus();
    }

    void approveDriverAccounts() throws SQLException {
        List<Profile> profileList = adminHelper.listOfDriversToBeApproved();
        if (profileList.size() == 0) {
            System.out.println("There is no driver in the system whose account is pending.");
            return;
        }
        System.out.println("List of drivers whose account is not yet approved:");
        for (Profile p : profileList) {
            System.out.printf("DriverId: %d, Driver Name: %s\n", p.getId(), p.getName());
        }
        System.out.println("Enter the driver_id which you want to approve:");
        int driver_id = inputs.getIntegerInput();
        profileStatus.approveDriver(driver_id);
        System.out.printf("Driver with id: %d is approved in the system\n", driver_id);
    }

    void approveCustomerAccounts() throws SQLException {
        List<Profile> profileList = adminHelper.listOfCustomersToBeApproved();
        if (profileList.size() == 0) {
            System.out.println("There is no customer in the system whose account is pending.");
            return;
        }
        System.out.println("List of customers whose account is not approved:");
        for (Profile p : profileList) {
            System.out.printf("CustomerId: %d, Customer Name: %s\n", p.getId(), p.getName());
        }

        System.out.println("Enter the customer_id which you want to approve:");
        int cust_id = inputs.getIntegerInput();
        profileStatus.approveDriver(cust_id);
        System.out.printf("Customer with id: %d is approved in the system\n", cust_id);
    }

    void deRegisterCustomer() throws SQLException {
        System.out.println("Enter the customer id:");
        int cust_id = inputs.getIntegerInput();
        profileStatus.deactivateCustomer(cust_id);
        System.out.printf("Customer with id: %d is de-registered in the system\n", cust_id);
    }

    void deRegisterDriver() throws SQLException {
        System.out.println("Enter the driver id:");
        int driver_id = inputs.getIntegerInput();
        profileStatus.deactivateDriver(driver_id);
        System.out.printf("Driver with id: %d is de-registered in the system\n", driver_id);
    }
}
