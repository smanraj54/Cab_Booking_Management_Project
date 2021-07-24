package com.dal.cabby.cabSelection;

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
         /* a. Total distance = 22 KM (Price for distance= 84.5)
            b. No extra charge on Micro and Mini Cab category
            c. 5% extra charge for Urban area as Halifax comes in Urban area = 4.23
            d. Ride is not during office hours so no extra charge for this parameters */
        double expectedPrice=88.73;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void microAndMiniRideSharingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Sydney").add(1).add(1).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 17 KM (Price for distance = 67)
           b. No extra charge on Micro and Mini Cab category
           c. Ride is not during office hours so no extra charge for this parameter.
           d. 5% extra charge for Urban area as Halifax comes in Urban area = 3.35
           e. 10% of discount on sharing ride with 1 co-passenger = 7.03 */
        double expectedPrice=63.32;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }


    @Test
    void microAndMiniWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Toronto").add(1).add(2).add(3).add(2);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 113 KM (Price for distance= 403)
           b. No extra charge on Micro and Mini Cab category
           c. Ride is not during office hours so no extra charge for this parameter.
           d. 5% extra charge for Urban area as Halifax comes in Urban area = 20.15
           e. Extra charge for availing both amenities = 18.08 */
        double expectedPrice=441.23;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanNormalBookingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Sydney").add("Dartmouth").add(1).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 39KM (Price for distance= 144)
           b. 10% extra charge for Prime Sedan Cab type  => (10% of 144)= 14.4
           c. 5% extra charge for Urban area as Sydney comes in Urban area=7.92
           d. Ride is not during office hours so no extra charge for this parameters */
        double expectedPrice=166.32;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanRideSharingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Dartmouth").add("Toronto").add(1).add(2).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 91KM (Price for distance= 326)
           b. Cab type is prime Sedan so 10% extra charge => (10% of 326)= 32.6
           c. Ride is not during office hours so no extra charge for this parameter.
           d. Source area is Rural so no extra charge for this parameter.
           e. 10% of discount on sharing ride with 1 co-passenger = 35.86 */
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
           c. Ride is not during office hours so no extra charge for this parameter.
           d. Source area is Rural so no extra charge for this parameter.
           e. Extra charge for availing both amenities = 3.56 */
        double expectedPrice=58.01;
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
           c. 5% extra charge for Urban area as Toronto comes in Urban area = 25.19
           d. Ride is not during office hours so no extra charge for this parameters */
        double expectedPrice=528.94;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSUVRideSharingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(3).add("Sydney").add("BedFord").add(1).add(2).add(2).add(2);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 26KM (Price for distance= 98.5)
           b. Cab type is prime SUV so 25% extra charge => (25% of 98.5 )= 24.63
           c. 5% extra charge for Urban area as Sydney comes in Urban area = 6.16
           d. Ride is not during office hours so no extra charge for this parameters
           e. 15% discount on sharing ride with 2 co-passengers = 19.39 */
        double expectedPrice=109.89;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSUVWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(3).add("Halifax").add("Winnipeg").add(1).add(2).add(3).add(2);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 45KM (Price for distance= 165)
           b. Cab type is prime SUV so 25% extra charge => (25% of 165 )= 41.25
           c. 5% extra charge for Urban area as Halifax comes in Urban area =10.31
           d. Ride is not during office hours so no extra charge for this parameters
           e. Extra charge for availing Wifi service during ride = 7.2 */
        double expectedPrice=223.76;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void luxuryClassNormalBookingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(4).add("Toronto").add("Montreal").add(1).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 338 KM (Price for distance= 1190.5)
           b. Cab type is LUXURY Class so 40% extra charge => (40% of 1190.5 )= 476.2
           c. 5% extra charge for Urban area as Toronto comes in Urban area = 83.34
           d. Ride is not during office hours so no extra charge for this parameters
        */
        double expectedPrice=1750.04;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void luxuryClassRideSharingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(4).add("Halifax").add("Winnipeg").add(1).add(2).add(2).add(2);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 45KM (Price for distance= 165)
           b. Cab type is LUXURY Class so 40% extra charge => (40% of 165 )= 66
           c. 5% extra charge for Urban area as Halifax comes in Urban area = 11.55
           d. Ride is not during office hours so no extra charge for this parameters
           e. 15% discount on sharing ride with 2 co-passengers = 36.38 */
        double expectedPrice=206.17;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void luxuryClassWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(4).add("Dartmouth").add("Kentville").add(1).add(2).add(3).add(3);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        /* a. Total distance = 121 KM (Price for distance= 431)
           b. Cab type is LUXURY Class so 40% extra charge => (40% of 431 )= 172.4
           c. No extra charge for Rural area as Dartmouth comes in rural area.
           d. Ride is not during office hours so no extra charge for this parameters
           e. Extra charge for availing Wifi service during ride = 34.57 */
        double expectedPrice=637.97;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

}
