package com.dal.cabby.driver;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.util.Common;

import java.sql.SQLException;
import java.text.ParseException;

public class Driver implements IDriver {
    DriverHelper driverHelper;
    private final Inputs inputs;
    private DriverTasks driverTasks;
    private DriverProfileManagement driverProfileManagement;

    public Driver(Inputs inputs) throws SQLException, ParseException {
        this.inputs = inputs;
        intialiaze();
    }

    private void intialiaze() throws SQLException {
        driverHelper = new DriverHelper();
        driverTasks = new DriverTasks(driverHelper, inputs);
        driverProfileManagement = new DriverProfileManagement(driverHelper, inputs);
    }

    @Override
    public void performTasks() throws SQLException, ParseException {
        profileManagementTasks();
    }

    @Override
    public void profileManagementTasks() throws SQLException, ParseException {
        while (true) {
            Common.page1Options();
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    boolean isLoginSuccessful = driverProfileManagement.login();
                    if (isLoginSuccessful) {
                        System.out.println("Login successful");
                        performDriverTasks();
                    }
                    break;
                case 2:
                    boolean isRegistered = driverProfileManagement.register();
                    if (!isRegistered) {
                        System.out.println("Registration failed!");
                    }
                    break;
                case 3:
                    boolean recoveryStatus = driverProfileManagement.forgotPassword();
                    if (recoveryStatus) {
                        System.out.println("Password reset successful. Please login with new credentials");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input: " + input);
            }
        }
    }

    @Override
    public void performDriverTasks() throws SQLException, ParseException {
        while (true) {
            System.out.println("1. Start trip");
            System.out.println("2. View previous rides");
            System.out.println("3. View incomes");
            System.out.println("4. Rate customer for the trip:");
            System.out.println("5. Logout");
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    driverTasks.startTrip();
                    break;
                case 2:
                    driverTasks.viewIncomes();
                    break;
                case 3:
                    driverTasks.viewRides();
                    break;
                case 4:
                    driverTasks.rateCustomer();
                    break;
                case 5:
                    boolean isLogoutSuccessful = driverProfileManagement.logout();
                    if (isLogoutSuccessful) {
                        return;
                    }
                    break;
            }
        }
    }
}