package com.dal.cabby.prelogin;

import com.dal.cabby.admin.Admin;
import com.dal.cabby.customer.Customer;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.driver.Driver;
import com.dal.cabby.io.Inputs;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

// Prelogin page when user visit the Cabby app
public class PreLoginPage {
    Inputs inputs;
    IPersistence iPersistence;

    public PreLoginPage(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        this.iPersistence = DBHelper.getInstance();
    }

    /* Starting point of Prelogin page. */
    public void start() {
        try {
            welcomeMessage();
            page1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Show welcome message when user vigit Cabby app page */
    private void welcomeMessage() {
        System.out.println("***** Cabby App: A one stop app for your cab booking *****\n");
    }

    private void page1() throws SQLException, ParseException, MessagingException, InterruptedException {
        System.out.println("Are you: \n1: Admin\n2: Driver\n3: Customer");
        int input = inputs.getIntegerInput();

        switch (input) {
            case 1:
                Admin admin = new Admin(inputs, iPersistence);
                admin.performTasks();
                break;
            case 2:
                Driver driver = new Driver(inputs);
                driver.performTasks();
                break;
            case 3:
                Customer customer = new Customer(inputs);
                customer.performTasks();
                break;
            default:
                System.out.println("Invalid input: " + input);
                return;
        }
    }
}
