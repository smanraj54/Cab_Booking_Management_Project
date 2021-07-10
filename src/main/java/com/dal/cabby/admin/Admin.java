package com.dal.cabby.admin;

import com.dal.cabby.pojo.Profile;
import com.dal.cabby.util.Common;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Admin {
    AdminHelper adminHelper;

    public Admin() throws SQLException {
        adminHelper = new AdminHelper();
        adminPage1();
    }

    private void adminPage1() throws SQLException {
        int input = Common.page1Options();

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
        System.out.println("Login successfull");
        page2();
    }

    private void register() {
        System.out.println("Welcome to Admin registration page");
    }

    private void forgotPassword() {
        System.out.println("Welcome to Admin forgot password page");
        System.out.println("Feature not implemented yet.");
    }

    private void page2() throws SQLException {
        while (true) {
            System.out.println("1. Approve Drivers");
            System.out.println("2. Approve Customers");
            System.out.println("3. Deregister drivers");
            System.out.println("4. Deregister customers");
            System.out.println("5. Logout");
            int input = getInput();
            switch (input) {
                case 1:
                    approveDriverAccounts();
                    break;
                case 2:
                    approveCustomerAccounts();
                    break;
                case 3:
                    deRegisterCustomer();
                    break;
                case 4:
                    deRegisterDriver();
                    break;
                case 5:
                    logout();
                    return;
                default:
                    System.out.println("Invalid input entered");
                    return;
            }
        }
    }

    private void logout() {
        System.out.println("Admin logged out");
    }

    private int getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    private void approveDriverAccounts() throws SQLException {
        System.out.println("List of drivers whose account is not yet approved:");
        List<Profile> profileList = adminHelper.listOfDriversToBeApproved();
        for (Profile p : profileList) {
            System.out.printf("DriverId: %d, Driver Name: %s\n", p.getId(), p.getName());
        }
        System.out.println("Enter the driver_id which you want to approve:");
        int driver_id = getInput();
        ApproveProfiles approveProfiles = new ApproveProfiles();
        approveProfiles.approveProfile(driver_id, AdminHelper.driverProfile);
        System.out.printf("Driver with id: %d is approved in the system\n", driver_id);
    }

    private void approveCustomerAccounts() throws SQLException {
        System.out.println("List of customers whose account is not approved:");
        List<Profile> profileList = adminHelper.listOfCustomersToBeApproved();
        for (Profile p : profileList) {
            System.out.printf("CustomerId: %d, Customer Name: %s\n", p.getId(), p.getName());
        }
        System.out.println("Enter the customer_id which you want to approve:");
        int cust_id = getInput();
        ApproveProfiles approveProfiles = new ApproveProfiles();
        approveProfiles.approveProfile(cust_id, AdminHelper.customerProfile);
        System.out.printf("Customer with id: %d is approved in the system\n", cust_id);
    }

    private void deRegisterCustomer() {
        System.out.println("Enter the customer id:");
        int cust_id = getInput();
        DeRegisterProfiles deRegisterProfile = new DeRegisterProfiles();
        deRegisterProfile.deRegisterCustomer(cust_id);
        System.out.printf("Customer with id: %d is de-registered in the system\n", cust_id);
    }

    private void deRegisterDriver() {
        System.out.println("Enter the driver id:");
        int driver_id = getInput();
        DeRegisterProfiles deRegisterProfile = new DeRegisterProfiles();
        deRegisterProfile.deRegisterCustomer(driver_id);
        System.out.printf("Driver with id: %d is de-registered in the system\n", driver_id);
    }
}