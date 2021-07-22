package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

public class DataNode {

    private String user;
    private String name;
    private String email;
    private String password;
    private UserType userType;

    public DataNode(String user, String name, String email, String password, UserType userType) {
        
        this.user = user;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getUser() {

        return user;
    }

    public void setUser(String user) {

        this.user = user;
    }

    public String getName() {

        return name;
    }

    public UserType getUserType() {

        return userType;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
}
