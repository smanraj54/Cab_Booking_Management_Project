package com.dal.cabby.driver;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.PredefinedInputs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.UUID;

import static com.dal.cabby.util.Constants.*;

public class DriverTests {
    IPersistence iPersistence;

    @Test
    void testExit() throws SQLException {
        iPersistence = DBHelper.getInstance();
        System.out.println("Testing simple exit flow for driver");
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(EXIT);
        Driver driver;
        try {
            driver = new Driver(inputs);
            driver.performTasks();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            Assertions.fail(throwables.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRegistration() throws SQLException {
        iPersistence = DBHelper.getInstance();
        System.out.println("Testing resgitration flow for Driver");
        PredefinedInputs inputs = new PredefinedInputs();
        String name = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = String.format("%s@gmail.com", name);
        inputs.add(REGISTRATION).add(name).add(email).add(userName).add(password).add(password).add(EXIT);

        Driver driver;
        try {
            driver = new Driver(inputs);
            driver.performTasks();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            Assertions.fail(throwables.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLogin() throws SQLException {
        iPersistence = DBHelper.getInstance();
        System.out.println("Testing login flow for Driver");
        PredefinedInputs inputs = new PredefinedInputs();
        String name = UUID.randomUUID().toString();
        String userName = "driver1";
        String password = "driver1@123";
        String email = String.format("%s@gmail.com", name);
        inputs.add(LOGIN).add(userName).add(password)
                .add(DRIVER_LOGOUT).add("y").add(EXIT);

        Driver driver;
        try {
            driver = new Driver(inputs);
            driver.performTasks();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            Assertions.fail(throwables.getMessage());
        } catch (MessagingException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}