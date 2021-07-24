package com.dal.cabby.profileManagement;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.pojo.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOperations implements IDBOperations {

    UserType userType;
    private final String queryUser = "Select * from %s where username = '%s'";
    private final String queryEmail = "Select * from %s where email = '%s'";

    DBOperations(UserType userType) {

        this.userType = userType;
    }

    @Override
    public boolean dbUserNameValidation(String userName) {

        return dbContainsUserName(userName, userType);
    }

    @Override
    public boolean dbContainsUserName(String userName, UserType userType) {

        boolean foundUser = false;
        String value = getValueFromDB(userName, "username", userType, queryUser);
        if (value == null) {
            return false;
        }
        if (userName.equals(value)) {
            foundUser = true;
        }

        return foundUser;
    }

    @Override
    public boolean dbContainsEmail(String email, UserType userType) {

        boolean foundUser = false;
        String value = getEmailValue(email, "email", userType, queryEmail);
        if (value == null) {
            return false;
        }
        if (email.equals(value)) {
            foundUser = true;
        }

        return foundUser;
    }

    private String getValueFromDB(String userName, String columnName, UserType userType, String query) {

        String tableName = getTableName(userType);
        query = String.format(query, tableName, userName);
        String value = null;
        IPersistence iPersistence;
        try {
            iPersistence = DBHelper.getInstance();
            ResultSet resultSet = iPersistence.executeSelectQuery(query);
            while (resultSet.next()) {
                value = resultSet.getString(columnName);
                String idColumnName = getIDColumnName(userType);
                int id = resultSet.getInt(idColumnName);
                String loggedInName = resultSet.getString("name");
                LoggedInProfile.setLoggedInId(id);
                LoggedInProfile.setLoggedInName(loggedInName);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return value;
    }

    @Override
    public String getEmailValue(String email, String keywordSearch, UserType userType, String query) {

        String tableName = getTableName(userType);
        query = String.format(query, tableName, email);
        String emailValue = null;
        IPersistence iPersistence;
        try {
            iPersistence = DBHelper.getInstance();
            ResultSet resultSet = iPersistence.executeSelectQuery(query);
            while (resultSet.next()) {
                emailValue = resultSet.getString(keywordSearch);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return emailValue;
    }

    @Override
    public void entryRegistration(DataNode dataNode) {

        IPersistence iPersistence = getDBInstance();
        String tableName = getTableName(dataNode.getUserType());
        String query = String.format("insert into %s (username, name, email, password) value ('%s','%s', '%s', '%s')", tableName, dataNode.getUser(), dataNode.getName(), dataNode.getEmail(), dataNode.getPassword());
        try {
            iPersistence.executeCreateOrUpdateQuery(query);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private IPersistence getDBInstance() {
        IPersistence iPersistence = null;
        try {
            iPersistence = DBHelper.getInstance();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return iPersistence;
    }

    @Override
    public boolean validateLoginUser(String userNameOrEmail, String password, UserType userType) {

        boolean userNameLogin = dbContainsUserName(userNameOrEmail, userType);
        boolean emailLogin = dbContainsEmail(userNameOrEmail, userType);
        boolean passwordValidate = false;
        boolean userTypeValidate = false;

        if (userNameLogin) {
            passwordValidate = validateKeyword(userNameOrEmail, "password", password, userType, queryUser);
            userTypeValidate = validateKeyword(userNameOrEmail, "username", userNameOrEmail, userType, queryUser);
        } else if (emailLogin) {
            passwordValidate = validateKeyword(userNameOrEmail, "password", password, userType, queryEmail);
            userTypeValidate = validateKeyword(userNameOrEmail, "email", userNameOrEmail, userType, queryEmail);
        }

        return (passwordValidate && userTypeValidate);
    }

    private boolean validateKeyword(String userNameOrEmail, String keyword, String keywordValue, UserType userType, String query) {

        String value = getValueFromDB(userNameOrEmail, keyword, userType, query);
        return value != null && value.equals(keywordValue);
    }

    @Override
    public String fetchEmailForAuthentication(String user, UserType userType) {

        String email = null;
        boolean isEmail = false;
        isEmail = dbContainsEmail(user, userType);
        if (!isEmail) {
            if (!dbContainsUserName(user, userType)) {
                return null;
            }
            email = getValueFromDB(user, "email", userType, queryUser);
        } else {
            email = user;
        }

        return email;
    }

    @Override
    public void updateEmailPassword(String email, String newPassword, UserType userType) {

        IPersistence iPersistence = null;
        String tableName = getTableName(userType);
        String query = String.format("UPDATE %s set password = '%s'where email = '%s'", tableName, newPassword, email);
        try {
            iPersistence = DBHelper.getInstance();
            iPersistence.executeCreateOrUpdateQuery(query);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    @Override
    public String getTableName(UserType userType) {

        if (userType == UserType.ADMIN) {
            return "cabby_admin";
        } else if (userType == UserType.DRIVER) {
            return "driver";
        } else if (userType == UserType.CUSTOMER) {
            return "customer";
        } else {
            throw new RuntimeException("Usertype invalid: " + userType);
        }
    }

    @Override
    public String getIDColumnName(UserType userType) {

        if (userType == UserType.ADMIN) {
            return "admin_id";
        } else if (userType == UserType.DRIVER) {
            return "driver_id";
        } else if (userType == UserType.CUSTOMER) {
            return "cust_id";
        } else {
            throw new RuntimeException("Usertype invalid: " + userType);
        }
    }
}