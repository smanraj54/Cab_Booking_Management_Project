package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.util.Common;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

public class Admin implements IAdmin {
    private final Inputs inputs;
    private final IPersistence iPersistence;
    AdminHelper adminHelper;
    private AdminTasks adminTasks;
    private AdminProfileManagement adminProfileManagement;

    public Admin(Inputs inputs, IPersistence iPersistence) throws SQLException {
        this.inputs = inputs;
        this.iPersistence = iPersistence;
        initialize();
    }

    private void initialize() throws SQLException {
        adminHelper = new AdminHelper(iPersistence);
        adminTasks = new AdminTasks(adminHelper, inputs, iPersistence);
        adminProfileManagement = new AdminProfileManagement(adminHelper, inputs);
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
                    boolean isLoginSuccessful = adminProfileManagement.login();
                    if (isLoginSuccessful) {
                        System.out.println("Login successful");
                        performAdminTasks();
                    }
                    break;
                case 2:
                    boolean isRegistered = adminProfileManagement.register();
                    if (!isRegistered) {
                        System.out.println("Registration failed!");
                    }
                    break;
                case 3:
                    boolean recoveryStatus = adminProfileManagement.forgotPassword();
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
    public void performAdminTasks() throws SQLException {
        while (true) {
            System.out.println("1. Logout");
            System.out.println("2. Approve Drivers");
            System.out.println("3. Approve Customers");
            System.out.println("4. Deregister drivers");
            System.out.println("5. Deregister customers");

            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    boolean isLogoutSuccessful = adminProfileManagement.logout();
                    if (isLogoutSuccessful) {
                        return;
                    }
                    break;
                case 2:
                    adminTasks.approveDriverAccounts();
                    break;
                case 3:
                    adminTasks.approveCustomerAccounts();
                    break;
                case 4:
                    adminTasks.deRegisterDriver();
                    break;
                case 5:
                    adminTasks.deRegisterCustomer();
                    break;
                default:
                    System.out.println("Invalid input entered");
                    break;
            }
        }
    }
}