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
        /*
          a. Total distance = 39KM (Price for distance= 144)
          b. 10% extra charge for Prime Sedan Cab type  => (10% of 144)= 14.4
          c. 5% extra charge for Urban area as Sydney comes in Urban area
          d. Ride is not during office hours so no extra charge for this parameters
         */
        double expectedPrice=166.32;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanRideSharingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Dartmouth").add("Toronto").add(1).add(2).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /*
          a. Total distance = 91KM (Price for distance= 326)
          b. Cab type is prime Sedan so 10% extra charge => (10% of 326)= 32.6
          c. Ride is not during office hours and Source area is also Rural so no extra charge for these parameters
          d. 10% of discount on total amount, upon sharing ride with co passenger
         */
        double expectedPrice=322.74;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Yarmouth").add("Halifax").add(1).add(1).add(3).add(3);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 12KM (Price for distance= 49.5)
           b. Cab type is prime Sedan so 10% extra charge => (10% of 49.5)= 4.95
           c. Ride is not during office hours and Source area is also Rural so no extra charge for these parameters
           d. Extra charge for availing both amenities = 3.5555555555555554
        */
        double expectedPrice=58.00555555555556;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSUVNormalBookingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(3).add("Toronto").add("Halifax").add(1).add(1).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 113KM (Price for distance= 403)
           b. Cab type is prime SUV so 25% extra charge => (25% of 403 )= 100.75
           c. 5% extra charge for Urban area as Toronto comes in Urban area
           d. Ride is not during office hours so no extra charge for this parameters
        */
        double expectedPrice=528.9375;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }
}
