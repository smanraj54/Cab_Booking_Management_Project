package com.dal.cabby.admin;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Profile;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;
import com.dal.cabby.util.Common;

import java.sql.SQLException;
import java.util.List;

public class Admin {
    AdminHelper adminHelper;
    Inputs inputs;

    public Admin(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        adminHelper = new AdminHelper();
        adminPage1();
    }

    private void adminPage1() throws SQLException {
        Common.page1Options();
        int input = inputs.getIntegerInput();

        switch (input) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                forgotPassword();
                break;
            default:
                System.out.println("Invalid input: " + input);
                return;
        }
    }

    private void login() throws SQLException {
        System.out.println("Welcome to Admin login page");
        Login login = new Login(inputs);
        if (login.attemptLogin(UserType.ADMIN)) {
            System.out.println("Login successful");
            System.out.printf("LoggedID: %d, LoggedIn name: %s\n",
                    LoggedInProfile.getLoggedInId(), LoggedInProfile.getLoggedInName());
        } else {
            return;
        }
        page2();
    }

    private void register() {
        System.out.println("Welcome to Admin registration page");
        Registration registration = new Registration(inputs);
        registration.registerUser(UserType.ADMIN);
    }

    private void forgotPassword() {
        System.out.println("Welcome to Admin forgot password page");
        ForgotPassword forgotPassword = new ForgotPassword(inputs);
        forgotPassword.passwordUpdateProcess(UserType.ADMIN);
    }

    private void page2() throws SQLException {
        while (true) {
            System.out.println("1. Approve Drivers");
            System.out.println("2. Approve Customers");
            System.out.println("3. Deregister drivers");
            System.out.println("4. Deregister customers");
            System.out.println("5. Logout");
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    approveDriverAccounts();
                    break;
                case 2:
                    approveCustomerAccounts();
                    break;
                case 3:
                    deRegisterDriver();
                    break;
                case 4:
                    deRegisterCustomer();
                    break;
                case 5:
                    boolean isLogoutSuccessful = logout();
                    if (isLogoutSuccessful) {
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid input entered");
                    break;
            }
        }
    }

    private boolean logout() {
        return new Logout(inputs).logout();
    }

    private void approveDriverAccounts() throws SQLException {
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
        ApproveProfiles approveProfiles = new ApproveProfiles();
        approveProfiles.approveProfile(driver_id, AdminHelper.driverProfile);
        System.out.printf("Driver with id: %d is approved in the system\n", driver_id);
    }

    private void approveCustomerAccounts() throws SQLException {
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
        ApproveProfiles approveProfiles = new ApproveProfiles();
        approveProfiles.approveProfile(cust_id, AdminHelper.customerProfile);
        System.out.printf("Customer with id: %d is approved in the system\n", cust_id);
    }

    private void deRegisterCustomer() {
        System.out.println("Enter the customer id:");
        int cust_id = inputs.getIntegerInput();
        DeRegisterProfiles deRegisterProfile = new DeRegisterProfiles();
        deRegisterProfile.deRegisterCustomer(cust_id);
        System.out.printf("Customer with id: %d is de-registered in the system\n", cust_id);
    }

    private void deRegisterDriver() {
        System.out.println("Enter the driver id:");
        int driver_id = inputs.getIntegerInput();
        DeRegisterProfiles deRegisterProfile = new DeRegisterProfiles();
        deRegisterProfile.deRegisterDriver(driver_id);
        System.out.printf("Driver with id: %d is de-registered in the system\n", driver_id);
    }
}