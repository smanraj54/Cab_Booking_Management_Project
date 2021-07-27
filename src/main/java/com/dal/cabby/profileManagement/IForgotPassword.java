package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

import javax.mail.MessagingException;

public interface IForgotPassword {

    /*
        This is the method used to update the password of a user in database
        after proper validation and authentication
     */
    boolean passwordUpdateProcess(UserType userType) throws InterruptedException, MessagingException;
}
