package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

public interface IDBOperations {
    // Username validate from the table
    boolean dbUserNameValidation(String userName);

    // Username validate from the table
    boolean dbContainsUserName(String userName, UserType userType);

    //Email value is validated from the database
    boolean dbContainsEmail(String email, UserType userType);

    // Fetching Email Value from database
    String getEmailValue(String email, String keywordSearch, UserType userType, String query);

    // Registering a new database entry using datanode object
    void entryRegistration(DataNode dataNode);

    // Validating the login User by fetching data and comparing from database
    boolean validateLoginUser(String userNameOrEmail, String password, UserType userType);

    // fetching email from database against the username
    String fetchEmailForAuthentication(String user, UserType userType);

    // updating the password in db while forgot password scenario
    void updateEmailPassword(String email, String newPassword, UserType userType);

    /* this method is called to fetch the User table name from the database for
     *  storing data fetched from the registration page
     * */
    String getTableName(UserType userType);

    /*
        This method return the ID column name from the userType.
     */
    String getIDColumnName(UserType userType);
}
