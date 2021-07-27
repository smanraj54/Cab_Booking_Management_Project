package com.dal.cabby.cabSelection;
import java.sql.SQLException;

/*
    This Interface serves as business logic layer. It provides all the nearby cabs from source location.
 */
public interface ICabSelectionWithoutGender {
    /*
         This method pass names of all nearby cabs to DB Layer to calculate distance between Source Location and
         Cab location which will be used as one of the parameter two parameters (2nd one is Traffic Density) in
         Fetching optimal cab.
     */
    CabSelectionDAO withoutGenderPreference() throws SQLException;
}
