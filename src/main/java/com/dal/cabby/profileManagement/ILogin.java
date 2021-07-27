package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

public interface ILogin {

    /*
        This method is used to attempt the login and generate a login request
        input as the userType
     */
    boolean attemptLogin(UserType userType) throws InterruptedException;
}
