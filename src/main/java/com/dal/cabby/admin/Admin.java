package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.util.Common;

import java.sql.SQLException;
import java.text.ParseException;

public class Admin implements IAdmin {
    AdminHelper adminHelper;
    private final Inputs inputs;
    private AdminTasks adminTasks;
    private AdminProfileManagement adminProfileManagement;

    public Admin(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        initialize();
    }

    private void initialize() throws SQLException {
        adminHelper = new AdminHelper();
        adminTasks = new AdminTasks(adminHelper, inputs);
        adminProfileManagement = new AdminProfileManagement(adminHelper, inputs);
    }

    @Override
    public void performTasks() throws SQLException, ParseException {
        profileManagementTasks();
    }

    @Override
    public void profileManagementTasks() throws SQLException, ParseException {
        while(true) {
            Common.page1Options();
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    boolean isLoginSuccessful = adminProfileManagement.login();
                    if (isLoginSuccessful) {
                        System.out.println("Login successful");
                        performAdminTasks();
                    }
                    break;
                case 2:
                    boolean isRegistered = adminProfileManagement.register();
                    if(!isRegistered) {
                        System.out.println("Registration failed!");
                    }
                    break;
                case 3:
                    boolean recoveryStatus = adminProfileManagement.forgotPassword();
                    if(recoveryStatus) {
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
    public void performAdminTasks() throws SQLException, ParseException {
        while (true) {
            System.out.println("1. Approve Drivers");
            System.out.println("2. Approve Customers");
            System.out.println("3. Deregister drivers");
            System.out.println("4. Deregister customers");
            System.out.println("5. Logout");
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    adminTasks.approveDriverAccounts();
                    break;
                case 2:
                    adminTasks.approveCustomerAccounts();
                    break;
                case 3:
                    adminTasks.deRegisterDriver();
                    break;
                case 4:
                    adminTasks.deRegisterCustomer();
                    break;
                case 5:
                    boolean isLogoutSuccessful = adminProfileManagement.logout();
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
}