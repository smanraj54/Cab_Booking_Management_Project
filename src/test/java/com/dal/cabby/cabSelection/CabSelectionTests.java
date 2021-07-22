package com.dal.cabby.cabSelection;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class CabSelectionTests {
    @Test
    void microAndMiniNormalBookingTest() throws SQLException {
        PredefinedInputs inputs=new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Dartmouth").add(1).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=88.725;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void microAndMiniRideSharingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Sydney").add(1).add(1).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=63.315;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void microAndMiniWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Toronto").add(1).add(2).add(3).add(2);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=441.22999999999996;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

}
