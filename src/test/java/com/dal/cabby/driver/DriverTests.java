package com.dal.cabby.driver;

import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.UUID;

import static com.dal.cabby.util.Constants.*;

public class DriverTests {

    @Test
    void testExit() {
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
        }
    }

    @Test
    void testRegistration() {
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
        }
    }

    @Test
    void testLogin() {
        System.out.println("Testing login flow for Driver");
        PredefinedInputs inputs = new PredefinedInputs();
        String name = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = String.format("%s@gmail.com", name);
        inputs.add(REGISTRATION).add(name).add(email).add(userName).add(password)
                .add(password).add(LOGIN).add(userName).add(password).add(LOGOUT).add("y").add(EXIT);

        Driver driver;
        try {
            driver = new Driver(inputs);
            driver.performTasks();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            Assertions.fail(throwables.getMessage());
        }
    }
}