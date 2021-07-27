package com.dal.cabby.profileManagement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInput {

    /*
        Validating the email using regex for the proper format
     */
    public boolean validateEmail(String email) {

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern VALIDATE_EMAIL_ADDRESS = Pattern.compile(regex,
                Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = VALIDATE_EMAIL_ADDRESS.matcher(email);

        return emailMatcher.find();

    }

    /*
        Validating the confirm password with the value if input password
     */
    public boolean validateConfirmPassword(String password,
                                           String confirmPassword) {

        return confirmPassword.equals(password);
    }

}
