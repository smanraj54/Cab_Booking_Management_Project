package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

public interface IRegistration {
    boolean registerUser(UserType userType);

    String getPassword(ValidateInput validateInput);
}
