package com.dal.cabby.cabSelection;

import com.dal.cabby.cabPrice.CabPriceCalculator;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Booking;
import java.sql.SQLException;

public class CabSelectionService {
    CabSelectionDAO bestCab;
    private IPersistence iPersistence;
    private Inputs inputs;
    private CabPriceCalculator cabPriceCalculator;
    private CabSelectionWithGender cabSelectionWithGender;
    private CabSelectionWithoutGender cabSelectionWithoutGender;
    public String sourceLocation, destinationLocation;

    public CabSelectionService(Inputs inputs) throws SQLException {
        this.inputs = inputs;
        cabPriceCalculator = new CabPriceCalculator(inputs);
        try {
            iPersistence = DBHelper.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cabSelectionWithGender=new CabSelectionWithGender(inputs,this);
        cabSelectionWithoutGender=new CabSelectionWithoutGender(inputs,this);
    }

    public static void main(String[] args) throws SQLException {
        CabSelectionService cabSelectionService = new CabSelectionService(new InputFromUser());
        cabSelectionService.preferredCab(1, 20);
    }

    public Booking preferredCab(int custId, double hour) throws SQLException {
        System.out.println("Enter your cab preference.");
        System.out.println("1. Micro or Mini");
        System.out.println("2. Prime Sedan");
        System.out.println("3. Prime SUV");
        System.out.println("4. Luxury Class");
        int cabType = inputs.getIntegerInput();
        System.out.println("Enter Source location");
        sourceLocation = inputs.getStringInput();
        System.out.println("Enter Destination location");
        destinationLocation = inputs.getStringInput();

        System.out.println("Do you want to book cab based on Gender of Cab Driver??");
        System.out.println("1. YES ");
        System.out.println("2. NO ");
        int input = inputs.getIntegerInput();
        switch (input) {
            case 1:
                bestCab = cabSelectionWithGender.withGenderPreference();
                break;
            case 2:
                bestCab = cabSelectionWithoutGender.withoutGenderPreference();
        }
        double price = cabPriceCalculator.priceCalculation(sourceLocation, destinationLocation, cabType, hour);
        Booking booking = new Booking(-1, custId, bestCab.driver_Id, -1, sourceLocation, destinationLocation, "", price, false);
        return booking;
    }
}

