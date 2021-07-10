package com.dal.cabby.profileManagement;

import com.dal.cabby.dbHelper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Operations {

    private String queryUser = "Select * from Registration where username = '<input>'";
    private String queryEmail = "Select * from Registration where email = '<input>'";

    public boolean dbUserNameValidation(String userName){

        return dbContainsUserName(userName);

    }

    public boolean dbContainsUserName(String userName){

        boolean foundUser = false;
        String value = getValueFromDB(userName, "username", queryUser);
        if(value==null){
            return false;
        }
        if(userName.equals(value)){
           foundUser =  true;
        }
        return foundUser;
    }

    public boolean dbContainsEmail(String email){
        boolean foundUser = false;
        String value = getEmailValue(email, "email", queryEmail);
        if(value==null){
            return false;
        }
        if(email.equals(value)){
            foundUser =  true;
        }
        return foundUser;
    }


    private String getValueFromDB(String userName, String keywordSearch, String query ){
        query = query.replace("<input>", userName);
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

    public String getEmailValue(String email, String keywordSearch, String query){
        query = query.replace("<input>", email);
        String emailValue = null;
        DBHelper dbHelper = new DBHelper();
        try {
            dbHelper.initialize();
            ResultSet resultSet = dbHelper.executeSelectQuery(query);
            while(resultSet.next()){
                emailValue = resultSet.getString(keywordSearch);
            }
            dbHelper.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return emailValue;
    }

    public void entryRegistration(DataNode dataNode){
        DBHelper dbHelper = getDBInstance();
        String query = "insert into registration (username, name, password, email, usertype) value ('"+dataNode.getUser()+"', '"+dataNode.getName()+"', '"+dataNode.getPassword()+"', '"+dataNode.getEmail()+"', '"+dataNode.getUserType()+"')";
        try {
            dbHelper.executeCreateOrUpdateQuery(query);
            dbHelper.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private DBHelper getDBInstance(){
        DBHelper dbHelper = new DBHelper();
        try {
            dbHelper.initialize();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return dbHelper;
    }

    public boolean validateLoginUser(String userName, String password, String userType){

        boolean userNameLogin = dbContainsUserName(userName);
        boolean emailLogin = dbContainsEmail(userName);
        boolean passwordValidate = false;
        boolean userTypeValidate = false;

        if(userNameLogin){
            passwordValidate = validateKeyword(userName, "password", password, queryUser);
            userTypeValidate = validateKeyword(userName, "usertype", userType, queryUser);
        }
        else if(emailLogin){
            passwordValidate = validateKeyword(userName, "password", password, queryEmail);
            userTypeValidate = validateKeyword(userName, "usertype", userType, queryEmail);
        }

        return (passwordValidate && userTypeValidate);
    }

    private boolean validateKeyword(String userName, String keyword, String keywordValue, String query){
        String value = getValueFromDB(userName, keyword, query);
        if(value!=null && value.equals(keywordValue)){
            return true;
        }
        return false;
    }

    public String fetchEmailForAuthentication(String user){
        String email = null;

        boolean isEmail = false;
        isEmail = dbContainsEmail(user);
        if(!isEmail){
            if(!dbContainsUserName(user)){
                return null;
            }
            email = getValueFromDB(user, "email", queryUser);
        }
        else{
            email = user;
        }
        return email;
    }

    public void updateEmailPassword(String email, String newPassword){
        DBHelper dbHelper = new DBHelper();
        String query = "UPDATE Registration set password = '"+newPassword+"'where email = '"+email+"'";
        try {
            dbHelper.initialize();
            dbHelper.executeCreateOrUpdateQuery(query);
            dbHelper.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }





}


