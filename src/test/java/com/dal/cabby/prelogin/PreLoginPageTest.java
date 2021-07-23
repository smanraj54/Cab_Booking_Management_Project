package com.dal.cabby.prelogin;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.PredefinedInputs;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static com.dal.cabby.util.Constants.*;

public class PreLoginPageTest {
    IPersistence IPersistence;
    @Test
    void testFlowOfAdmin() throws SQLException {
        IPersistence = DBHelper.getInstance();
        System.out.println("Testing complete flow for Admin");
        PredefinedInputs inputs = new PredefinedInputs();
        String name = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = String.format("%s@gmail.com", name);
        inputs.add(ADMIN).add(REGISTRATION).add(name).add(email).add(userName).add(password).add(password)
                .add(LOGIN).add(userName).add(password).add(ADMIN_LOGOUT).add("y").add(EXIT);

        PreLoginPage preLoginPage;
        preLoginPage = new PreLoginPage(inputs, IPersistence);
        preLoginPage.start();
    }

    @Test
    void testFlowOfDriver() {
        System.out.println("Testing complete flow for Driver");
        PredefinedInputs inputs = new PredefinedInputs();
        String name = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = String.format("%s@gmail.com", name);
        inputs.add(DRIVER).add(REGISTRATION).add(name).add(email).add(userName).add(password).add(password)
                .add(LOGIN).add(userName).add(password).add(DRIVER_LOGOUT).add("y").add(EXIT);

        PreLoginPage preLoginPage;
        preLoginPage = new PreLoginPage(inputs, IPersistence);
        preLoginPage.start();
    }

    @Test
    void testFlowOfCustomer() {
        System.out.println("Testing complete flow for Customer");
        PredefinedInputs inputs = new PredefinedInputs();
        String name = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = String.format("%s@gmail.com", name);
        inputs.add(DRIVER).add(REGISTRATION).add(name).add(email).add(userName).add(password).add(password)
                .add(LOGIN).add(userName).add(password).add(CUSTOMER_LOGOUT).add("y").add(EXIT);

        PreLoginPage preLoginPage;
        preLoginPage = new PreLoginPage(inputs, IPersistence);
        preLoginPage.start();
    }
}