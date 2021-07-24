package com.dal.cabby.profileManagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggedInProfileTest {

    private int loggedInId = 1;
    private String loggedInName = "LoggedInName";
    @Test
    void getLoggedInId() {

        LoggedInProfile.setLoggedInId(loggedInId);

        assertTrue(LoggedInProfile.getLoggedInId() == loggedInId, "LoggedIn ID fetched wrong from LoggedInProfile");
    }

    @Test
    void setLoggedInId() {
        LoggedInProfile.setLoggedInId(loggedInId);

        assertTrue(LoggedInProfile.getLoggedInId() == loggedInId, "LoggedIn ID set wrong from LoggedInProfile");
    }

    @Test
    void getLoggedInName() {
        LoggedInProfile.setLoggedInName(loggedInName);

        assertTrue(LoggedInProfile.getLoggedInName().equals(loggedInName), "LoggedIn Name fetched wrong from LoggedInProfile");
    }

    @Test
    void setLoggedInName() {
        LoggedInProfile.setLoggedInName(loggedInName);

        assertTrue(LoggedInProfile.getLoggedInName().equals(loggedInName), "LoggedIn Name set wrong from LoggedInProfile");
    }
}