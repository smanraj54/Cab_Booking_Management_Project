package com.dal.cabby.cabPrice;

import com.dal.cabby.cabSelection.CabSelection;
import com.dal.cabby.cabSelection.ICabSelection;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PriceNormalBookingTests {
    @Test
    void microAndMiniNormalBookingTest() throws SQLException {
        PredefinedInputs inputs=new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Dartmouth").add(1).add(2).add(1);
        ICabSelection cabSelection = new CabSelection(inputs);
        Booking booking= cabSelection.preferredCab(1,20);
        double expectedPrice=88.73;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSedanNormalBookingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(2).add("Sydney").add("Dartmouth").add(1).add(2).add(1);
        ICabSelection cabSelection = new CabSelection(inputs);
        Booking booking= cabSelection.preferredCab(1,20);
        double expectedPrice=166.32;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void primeSUVNormalBookingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(3).add("Toronto").add("Halifax").add(1).add(1).add(1);
        ICabSelection cabSelection = new CabSelection(inputs);
        Booking booking= cabSelection.preferredCab(1,20);
        double expectedPrice=528.94;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

    @Test
    void luxuryClassNormalBookingTest() throws SQLException {
        PredefinedInputs inputs = new PredefinedInputs();
        inputs.add(4).add("Toronto").add("Montreal").add(1).add(2).add(1);
        ICabSelection cabSelection = new CabSelection(inputs);
        Booking booking= cabSelection.preferredCab(1,20);
        double expectedPrice=1750.04;
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }
}
