package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DataNodeTest {
    private String user = "manraj";
    private String name = "Manraj Singh";
    private String email = "mn697903@dal.ca";
    private String password = "mnau123";
    private UserType userType = UserType.ADMIN;

    @Test
    void getUser() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        assertTrue(dataNode.getUser().equals(user), "Getting Username from DataNode failed with wrong value");
    }

    @Test
    void setUser() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        dataNode.setUser("newUser");
        assertTrue(dataNode.getUser().equals("newUser"), "Setting Username value in DataNode failed");
    }

    @Test
    void getName() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        assertTrue(dataNode.getName().equals(name), "Getting Name from DataNode failed with wrong value");

    }

    @Test
    void getUserType() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        assertTrue(dataNode.getUserType().equals(UserType.ADMIN), "Getting UserType from DataNode failed with wrong value");
    }

    @Test
    void setName() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        dataNode.setName("newName");
        assertTrue(dataNode.getName().equals("newName"), "Setting Name value in DataNode failed");
    }

    @Test
    void getEmail() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        assertTrue(dataNode.getEmail().equals(email), "Getting Email from DataNode failed with wrong value");
    }

    @Test
    void setEmail() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        dataNode.setEmail("newemail@gmail.com");
        assertTrue(dataNode.getEmail().equals("newemail@gmail.com"), "Setting Email value in DataNode failed");
    }

    @Test
    void getPassword() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        assertTrue(dataNode.getPassword().equals(password), "Getting Password from DataNode failed with wrong value");
    }

    @Test
    void setPassword() {
        DataNode dataNode = new DataNode(user, name, email, password, userType);
        dataNode.setPassword("newPassword123");
        assertTrue(dataNode.getPassword().equals("newPassword123"), "Setting Password value in DataNode failed");
    }
}