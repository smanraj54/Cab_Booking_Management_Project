package com.dal.cabby.cabSelection;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

public class CabWithoutGenderPreferenceTests {
    @Test
    void microAndMiniNormalBookingWithoutGenderTest() throws SQLException {
        PredefinedInputs inputs=new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Dartmouth").add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=88.73;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void microAndMiniRideSharingWithoutGenderTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Sydney").add(2).add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=63.32;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void microAndMiniWithAmenitiesWithoutGenderTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Toronto").add(2).add(3).add(2);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=441.23;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanNormalBookingWithoutGenderTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Sydney").add("Dartmouth").add(2).add(1);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=166.32;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanRideSharingWithoutGenderTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Dartmouth").add("Toronto").add(2).add(2).add(2);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=304.81;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanWithAmenitiesWithoutGenderTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Yarmouth").add("Halifax").add(2).add(3).add(3);
        CabSelectionService cabSelectionService = new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=58.01;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

}
