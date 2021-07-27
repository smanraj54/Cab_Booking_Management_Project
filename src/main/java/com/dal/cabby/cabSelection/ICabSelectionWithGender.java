package com.dal.cabby.cabSelection;

import java.sql.SQLException;

/*
    This Interface serves as business logic layer as it provides cabs based on Gender preference.
 */
public interface ICabSelectionWithGender {
    /*
        This method pass names of all nearby cabs of a specific gender chosen by customer to DB Layer
        to calculate distance between Source Location and Cab location which will be used as one of the
        two parameters (2nd one is Traffic Density) in Fetching optimal cab.
    */
    CabSelectionDAO withGenderPreference() throws SQLException;
}
