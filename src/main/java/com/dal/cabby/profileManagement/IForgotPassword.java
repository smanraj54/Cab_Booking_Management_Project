package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

import javax.mail.MessagingException;

public interface IForgotPassword {
    boolean passwordUpdateProcess(UserType userType) throws InterruptedException, MessagingException;
}
