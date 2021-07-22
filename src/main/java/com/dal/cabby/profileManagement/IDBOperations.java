package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

public interface IDBOperations {
    boolean dbUserNameValidation(String userName);

    boolean dbContainsUserName(String userName, UserType userType);

    boolean dbContainsEmail(String email, UserType userType);

    String getEmailValue(String email, String keywordSearch, UserType userType, String query);

    void entryRegistration(DataNode dataNode);

    boolean validateLoginUser(String userNameOrEmail, String password, UserType userType);

    String fetchEmailForAuthentication(String user, UserType userType);

    void updateEmailPassword(String email, String newPassword, UserType userType);

    String getTableName(UserType userType);

    String getIDColumnName(UserType userType);
}
