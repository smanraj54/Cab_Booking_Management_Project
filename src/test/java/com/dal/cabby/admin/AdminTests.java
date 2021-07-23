package com.dal.cabby.admin;

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

public class AdminTests {
    IPersistence iPersistence;
    @Test
    void testExit() throws SQLException {
        iPersistence = DBHelper.getInstance();
        System.out.println("Testing simple exit flow for Admin");
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(EXIT);
        Admin admin;
        try {
            admin = new Admin(inputs, iPersistence);
            admin.performTasks();
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
        System.out.println("Testing resgitration flow for Admin");
        PredefinedInputs inputs = new PredefinedInputs();
        String name = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = String.format("%s@gmail.com", name);
        inputs.add(REGISTRATION).add(name).add(email).add(userName).add(password).add(password).add(EXIT);

        Admin admin;
        try {
            admin = new Admin(inputs, iPersistence);
            admin.performTasks();
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
        System.out.println("Testing login flow for Admin");
        PredefinedInputs inputs = new PredefinedInputs();
        String name = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = String.format("%s@gmail.com", name);
        inputs.add(REGISTRATION).add(name).add(email).add(userName).add(password)
                .add(password).add(LOGIN).add(userName).add(password).add(ADMIN_LOGOUT).add("y").add(EXIT);

        Admin admin;
        try {
            admin = new Admin(inputs, iPersistence);
            admin.performTasks();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            Assertions.fail(throwables.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
