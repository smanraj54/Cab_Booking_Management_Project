package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

public interface ILogin {
    boolean attemptLogin(UserType userType) throws InterruptedException;
}
