package com.dal.cabby.profileManagement;

import com.dal.cabby.dbHelper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Operations {

    private String query = "Select * from Registration where username = <username>";

    public boolean dbUserNameValidation(String userName){

        return dbContainsUserName(userName);

    }

    public boolean dbContainsUserName(String userName){

        boolean foundUser = false;
        String value = getValueFromDB(userName, "username");
        if(value==null){
            return false;
        }
        if(userName.equals(value)){
           foundUser =  true;
        }
        return foundUser;
    }

    public String getPassword(String userName){
        String password = getValueFromDB(userName, "password");

        return password;
    }

    public boolean validatePassword(String userName, String password){
        String dbPassword = getPassword(userName);
        if(dbPassword == null){
            return false;
        }
        if(dbPassword.equals(password)){
            return true;
        }
        return false;
    }

    private String getValueFromDB(String userName, String keywordSearch ){
        String query = this.query.replace("<username>", userName);
        String value = null;
        DBHelper dbHelper = new DBHelper();
        try {
            dbHelper.initialize();
            ResultSet resultSet = dbHelper.executeSelectQuery(query);
            while(resultSet.next()){
                value = resultSet.getString(keywordSearch);
            }
            dbHelper.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return value;
    }

}


