package com.dal.cabby.profileManagement;

public class DB_Operations {

    public boolean dbUserNameValidation(String userName){

        //DB check for username
        return true;
    }

    public boolean dbContainsUserName(String userName){

        // Validate username exists or not
        return false;
    }

    public String getPassword(String userName){
        // getPassword form db
        return "";
    }

    public boolean validatePassword(String userName, String password){

        //return password.equals(getPassword(userName));

        return true;
    }

}


