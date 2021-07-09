package com.dal.cabby.profileManagement;

public class DataNode {

    private String user = null;
    private String name = null;
    private String email = null;
    private String password = null;
    private String userType = null;

    public DataNode(String user, String name, String email, String password, String userType) {
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
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
