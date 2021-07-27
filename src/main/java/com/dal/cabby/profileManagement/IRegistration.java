package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

public interface IRegistration {

    /*
        This method is used to register new user and update the dataNode which
        is further used in db operation to write into the db
     */
    boolean registerUser(UserType userType);

    /*
        this method get the password from the user and validate it with the
        input confirm password value
     */
    String getPassword(ValidateInput validateInput);
}
