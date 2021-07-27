package com.dal.cabby.cabPrice;

import com.dal.cabby.cabSelection.CabSelection;
import com.dal.cabby.cabSelection.ICabSelection;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PriceWithAmenitiesTests {

    @Test
    void microAndMiniWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(1).add("Dartmouth").add("Winnipeg").add(1).add(2).add(3).add(2);
        ICabSelection cabSelection = new CabSelection(inputs);
        Booking booking= cabSelection.preferredCab(1,20);
        double expectedPrice=91.29;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }
    @Test
    void primeSedanWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Toronto").add("Yarmouth").add(1).add(1).add(3).add(3);
        ICabSelection cabSelection = new CabSelection(inputs);
        Booking booking= cabSelection.preferredCab(1,20);
        double expectedPrice=547.31;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSUVWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(3).add("Halifax").add("Sydney").add(1).add(2).add(3).add(2);
        ICabSelection cabSelection = new CabSelection(inputs);
        Booking booking= cabSelection.preferredCab(1,20);
        double expectedPrice=90.66;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }
    @Test
    void luxuryClassWithAmenitiesTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(4).add("Sydney").add("Kentville").add(1).add(2).add(3).add(3);
        ICabSelection cabSelection = new CabSelection(inputs);
        Booking booking= cabSelection.preferredCab(1,20);
        double expectedPrice=460.25;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }
}
