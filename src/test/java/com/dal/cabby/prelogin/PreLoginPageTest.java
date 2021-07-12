package com.dal.cabby.prelogin;

import com.dal.cabby.io.PredefinedInputs;
import org.junit.jupiter.api.Test;

public class PreLoginPageTest {

    @Test
    void testRegistrationFlowOfAdmin() {

    }

    @Test
    void testLoginFlowOfAdmin() {

    }

    @Test
    void testRegsitrationFlowOfDriver() {

    }

    @Test
    void testLoginFlowOfDriver() {

    }

    @Test
    void testRegsitrationFlowOfCustomer() {

    }

    @Test
    void testLoginFlowOfCustomer() {

    }

    @Test
    void testStart() {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add(1).add("devraj").add("devraj123").add(5).add("yes");
        PreLoginPage preLoginPage = new PreLoginPage(inputs);
        preLoginPage.start();
    }
}