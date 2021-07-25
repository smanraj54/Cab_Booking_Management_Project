package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Profile;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.profileManagement.*;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.List;

class AdminBusinessLayer {
    private final AdminDBLayer adminDBLayer;
    private final Inputs inputs;
    ProfileStatus profileStatus;

    public AdminBusinessLayer(AdminDBLayer adminDBLayer, Inputs inputs) throws SQLException {
        this.adminDBLayer = adminDBLayer;
        this.inputs = inputs;
        profileStatus = new ProfileStatus();
    }

    void approveDriverAccounts() throws SQLException {
        List<Profile> profileList = adminDBLayer.listOfDriversToBeApproved();
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
        List<Profile> profileList = adminDBLayer.listOfCustomersToBeApproved();
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

    boolean login() throws InterruptedException {
        System.out.println("Welcome to Admin login page");
        ILogin ILogin = new Login(inputs);
        if (ILogin.attemptLogin(UserType.ADMIN)) {
            System.out.println("Login successful");
            System.out.printf("LoggedID: %d, LoggedIn name: %s\n",
                    LoggedInProfile.getLoggedInId(), LoggedInProfile.getLoggedInName());
            return true;
        } else {
            return false;
        }
    }

    boolean register() {
        System.out.println("Welcome to Admin registration page");
        IRegistration IRegistration = new Registration(inputs);
        return IRegistration.registerUser(UserType.ADMIN);
    }

    boolean forgotPassword() throws MessagingException, InterruptedException {
        System.out.println("Welcome to Admin forgot password page");
        IForgotPassword IForgotPassword = new ForgotPassword(inputs);
        return IForgotPassword.passwordUpdateProcess(UserType.ADMIN);
    }

    boolean logout() {
        return new Logout(inputs).logout();
    }
}
