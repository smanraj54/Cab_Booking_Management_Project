package com.dal.cabby.driver;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.profileManagement.LoggedInProfile;
import com.dal.cabby.profileManagement.ProfileStatus;
import com.dal.cabby.util.Common;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.dal.cabby.util.ConsolePrinter.printErrorMsg;
import static com.dal.cabby.util.ConsolePrinter.printSuccessMsg;

/**
 * This class implements IDriver interface and implements the
 * presentation layer of the Driver user.
 */
public class Driver implements IDriver {
    private final Inputs inputs;
    private DriverBusinessLayer driverBusinessLayer;
    private ProfileStatus profileStatus;

    public Driver(Inputs inputs) throws SQLException, ParseException {
        this.inputs = inputs;
        intialize();
    }

    /**
     * Initialize the instance of the dependent objects.
     *
     * @throws SQLException
     */
    private void intialize() throws SQLException {
        driverBusinessLayer = new DriverBusinessLayer(inputs);
        profileStatus = new ProfileStatus();
    }

    @Override
    public void performTasks() throws SQLException, ParseException, MessagingException, InterruptedException {
        profileManagementTasks();
    }

    /**
     * This method implements presentation layer for Login, Registration and Password recovery.
     *
     * @throws SQLException
     * @throws ParseException
     * @throws MessagingException
     * @throws InterruptedException
     */
    @Override
    public void profileManagementTasks() throws SQLException, ParseException, MessagingException, InterruptedException {
        while (true) {
            Common.page1Options();
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    boolean isLoginSuccessful = driverBusinessLayer.login();
                    if (isLoginSuccessful) {
                        printSuccessMsg("Login successful");
                        if (!profileStatus.isDriverAproved(LoggedInProfile.getLoggedInId())) {
                            printErrorMsg("You are in deactivated state rigth now. " +
                                    "Please contact Fincare customer care: fincare@dal.ca");
                            return;
                        }
                        performDriverTasks();
                    }
                    break;
                case 2:
                    boolean isRegistered = driverBusinessLayer.register();
                    if (!isRegistered) {
                        System.out.println("Registration failed!");
                    }
                    break;
                case 3:
                    boolean recoveryStatus = driverBusinessLayer.forgotPassword();
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
     * This method implements the presentation layer for the Driver tasks
     * which he will be able to perform once he do successfull login.
     *
     * @throws SQLException
     * @throws ParseException
     */
    @Override
    public void performDriverTasks() throws SQLException, ParseException {
        while (true) {
            System.out.println("\n1. Logout");
            System.out.println("2. Start trip");
            System.out.println("3. View previous rides");
            System.out.println("4. View incomes");
            System.out.println("5. Rate customer for the trip");
            System.out.println("6. View your current rating");
            System.out.println("7. Buy Coupons");
            System.out.println("8. Cancel booking");
            System.out.println("9. View upcoming trips");
            int input = inputs.getIntegerInput();
            switch (input) {
                case 1:
                    boolean isLogoutSuccessful = driverBusinessLayer.logout();
                    if (isLogoutSuccessful) {
                        return;
                    }
                    break;
                case 2:
                    driverBusinessLayer.startTrip();
                    break;
                case 3:
                    driverBusinessLayer.viewRides();
                    break;
                case 4:
                    driverBusinessLayer.viewIncomes();
                    break;
                case 5:
                    driverBusinessLayer.rateCustomer();
                    break;
                case 6:
                    driverBusinessLayer.viewRatings();
                    break;
                case 7:
                    driverBusinessLayer.buyCoupons();
                    break;
                case 8:
                    driverBusinessLayer.cancelBooking();
                    break;
                case 9:
                    driverBusinessLayer.viewUpcomingTrip();
                    break;
                default:
                    System.out.println("\nInvalid Input");
                    break;
            }
        }
    }
}