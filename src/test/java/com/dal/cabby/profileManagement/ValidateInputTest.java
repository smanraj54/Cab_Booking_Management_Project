package com.dal.cabby.profileManagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidateInputTest {

    private String correctEmail = "sorrydonthaveany@gmail.com";
    private String wrongEmail = "sorrydonthaveany @gmail.com";
    private String password = "newPassword@123";

    @Test
    void validateCorrectEmail() {
        ValidateInput validateInput = new ValidateInput();
        assertTrue(validateInput.validateEmail(correctEmail), "Validation of email failed with Correct Email validated false");
    }

    @Test
    void validateWrongEmail() {
        ValidateInput validateInput = new ValidateInput();
        assertFalse(validateInput.validateEmail(wrongEmail), "Validation of email failed with Wrong Email validated True");
    }

    @Test
    void validateConfirmPassword() {
        ValidateInput validateInput = new ValidateInput();
        assertTrue(validateInput.validateConfirmPassword(password, password), "Password Validation failed with matching passwords");
    }
}