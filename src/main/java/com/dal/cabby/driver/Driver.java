package com.dal.cabby.driver;

import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.util.Common;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

public class Driver implements IDriver {
    DriverHelper driverHelper;
    private final Inputs inputs;
    private DriverTasks driverTasks;
    private DriverProfileManagement driverProfileManagement;
    private IPersistence IPersistence;

    public Driver(Inputs inputs, IPersistence IPersistence) throws SQLException, ParseException {
        this.inputs = inputs;
        this.IPersistence = IPersistence;
        intialiaze();
    }

    private void intialiaze() throws SQLException {
        driverHelper = new DriverHelper(IPersistence);
        driverTasks = new DriverTasks(driverHelper, inputs);
        driverProfileManagement = new DriverProfileManagement(driverHelper, inputs);
    }

    @Override
    public void performTasks() throws SQLException, ParseException, MessagingException, InterruptedException {
        profileManagementTasks();
    }

    @Override
    public void profileManagementTasks() throws SQLException, ParseException, MessagingException, InterruptedException {
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
            System.out.println("1. Logout");
            System.out.println("2. Start trip");
            System.out.println("3. View previous rides");
            System.out.println("4. View incomes");
            System.out.println("5. Rate customer for the trip");
            System.out.println("6. View your current rating");
            System.out.println("7. Buy Coupons");
            System.out.println("8. Cancel booking");
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    boolean isLogoutSuccessful = driverProfileManagement.logout();
                    if (isLogoutSuccessful) {
                        return;
                    }
                    break;
                case 2:
                    driverTasks.startTrip();
                    break;
                case 3:
                    driverTasks.viewRides();
                    break;
                case 4:
                    driverTasks.viewIncomes();
                    break;
                case 5:
                    driverTasks.rateCustomer();
                    break;
                case 6:
                    driverTasks.viewRatings();
                    break;
                case 7:
                    driverTasks.buyCoupons();
                    break;
                case 8:
                    System.out.println("Not implemented yet.");
                    break;
                default:
                    System.out.println("\nInvalid Input");
                    break;
            }
        }
    }
}