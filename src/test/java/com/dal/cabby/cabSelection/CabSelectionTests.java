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

    @Test
    void primeSedanNormalBookingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Sydney").add("Dartmouth").add(1).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        // Total distance = 39KM (Price for distance= 144)
        // 10% extra charge for Prime Sedan Cab type  => (10% of 144)= 14.4
        // 5% extra charge for Urban area as Sydney comes in Urban area
        // Ride is not during office hours and Source area is also Rural so no extra charge for these parameters
        double expectedPrice=166.32;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanRideSharingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Dartmouth").add("Toronto").add(1).add(2).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        // Total distance = 91KM (Price for distance= 326)
        // Cab type is prime Sedan so 10% extra charge => (10% of 326)= 32.6
        // Ride is not during office hours and Source area is also Rural so no extra charge for these parameters
        // 10% of discount on total amount, upon sharing ride with co passenger
        double expectedPrice=322.74;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

}
