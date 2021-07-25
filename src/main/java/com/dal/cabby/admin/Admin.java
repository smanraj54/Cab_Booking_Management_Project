package com.dal.cabby.admin;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.util.Common;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * This class is responsible for Admin portal. It will expose 2nd page
 * which consists of Login, Registration and Password Recovery.
 * After successful login, it will show the 3rd page which consists of Admin
 * tasks.
 */
public class Admin implements IAdmin {
    private final Inputs inputs;
    AdminDBLayer adminDBLayer;
    private AdminBusinessLayer adminBusinessLayer;

    public Admin(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        initialize();
    }

    /**
     * Initializes instance of dependent objects.
     *
     * @throws SQLException
     */
    private void initialize() throws SQLException {
        adminDBLayer = new AdminDBLayer();
        adminBusinessLayer = new AdminBusinessLayer(adminDBLayer, inputs);
    }

    @Override
    public void performTasks() throws SQLException, ParseException, MessagingException, InterruptedException {
        profileManagementTasks();
    }

    /**
     * This method show the options for Login, Registration and Password recovery.
     *
     * @throws SQLException
     * @throws MessagingException
     * @throws InterruptedException
     */
    @Override
    public void profileManagementTasks() throws SQLException, MessagingException, InterruptedException {
        while (true) {
            Common.page1Options();
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    boolean isLoginSuccessful = adminBusinessLayer.login();
                    if (isLoginSuccessful) {
                        System.out.println("Login successful");
                        performAdminTasks();
                    }
                    break;
                case 2:
                    boolean isRegistered = adminBusinessLayer.register();
                    if (!isRegistered) {
                        System.out.println("Registration failed!");
                    }
                    break;
                case 3:
                    boolean recoveryStatus = adminBusinessLayer.forgotPassword();
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

    /**
     * After successful login, Admin will enter the 3rd page which consists
     * of 4 tasks: Approve drivers, Approve Customers, Deactivate drivers and
     * Deactivate customers.
     *
     * @throws SQLException
     */
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
                    boolean isLogoutSuccessful = adminBusinessLayer.logout();
                    if (isLogoutSuccessful) {
                        return;
                    }
                    break;
                case 2:
                    adminBusinessLayer.approveDriverAccounts();
                    break;
                case 3:
                    adminBusinessLayer.approveCustomerAccounts();
                    break;
                case 4:
                    adminBusinessLayer.deRegisterDriver();
                    break;
                case 5:
                    adminBusinessLayer.deRegisterCustomer();
                    break;
                default:
                    System.out.println("Invalid input entered");
                    break;
            }
        }
    }
}