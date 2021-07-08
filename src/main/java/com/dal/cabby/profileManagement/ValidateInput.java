package com.dal.cabby.profileManagement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInput {

    public boolean validateEmail(String email){
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
        Pattern VALIDATE_EMAIL_ADDRESS = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = VALIDATE_EMAIL_ADDRESS.matcher(email);
        return emailMatcher.find();

    }

    public boolean validateConfirmPassword(String password, String confirmPassword){
        return confirmPassword.equals(password);
    }

}
