package com.dal.cabby.profileManagement;

/**
 * This class will maintain the id and name of the current logged
 * in users. At the time of successful login, we will set the ID and
 * name of the logged in profile.
 */
public class LoggedInProfile {
    // The id of logged in user. The user can be customer, driver or admin.
    static int loggedInId;
    static String loggedInName;

    public static int getLoggedInId() {
        return loggedInId;
    }

    public static void setLoggedInId(int id) {
        loggedInId = id;
    }

    public static String getLoggedInName() {
        return loggedInName;
    }

    public static void setLoggedInName(String loggedInName) {
        LoggedInProfile.loggedInName = loggedInName;
    }
}