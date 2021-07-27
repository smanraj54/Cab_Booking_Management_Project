package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

/*
    DataNode of all the details of a user used for registering and login
    The dataNode object is used to store the registration details which are used
    later by the DBOperations class to make an entry in the database
*/

public class DataNode {

    private String user;
    private String name;
    private String email;
    private String password;
    private final UserType userType;

    public DataNode(String user, String name, String email, String password,
                    UserType userType) {

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

        if (user == null) {
            throw new NullPointerException();
        }

        this.user = user;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        if (name == null) {
            throw new NullPointerException();
        }

        this.name = name;
    }

    public UserType getUserType() {

        return userType;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        if (email == null) {
            throw new NullPointerException();
        }

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        if (password == null) {
            throw new NullPointerException();
        }

        this.password = password;
    }
}
