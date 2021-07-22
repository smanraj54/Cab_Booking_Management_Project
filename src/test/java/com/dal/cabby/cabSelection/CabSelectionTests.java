package com.dal.cabby.cabSelection;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class CabSelectionTests {
    @Test
    void microAndMiniCategoryTest() throws SQLException {
        PredefinedInputs inputs=new PredefinedInputs();
        inputs.add(1).add("Halifax").add("Dartmouth").add(1).add(2).add(1);
        CabSelectionService cabSelectionService=new CabSelectionService(inputs);
        Booking booking=cabSelectionService.preferredCab(1,20);
        double expectedPrice=84.5;
        //double actualPrice=cabSelectionService.preferredCab();
        Assertions.assertEquals(expectedPrice,booking.getPrice(),"Error in calculating right price");
    }

}
