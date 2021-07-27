package com.dal.cabby.cabSelection;
import com.dal.cabby.pojo.Booking;
import java.sql.SQLException;

/*
    This interface serves as presentation layer for Cab Selection feature.
 */
public interface ICabSelection {
    /*
        This method takes Cab preference, source and destination locations from user to book Cab. It also gives an
        option to book a cab based on gender of driver.
     */
    Booking preferredCab(int custId, double hour) throws SQLException;
}
