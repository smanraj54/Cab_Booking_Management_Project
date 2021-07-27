package com.dal.cabby.cabSelection;
import com.dal.cabby.cabPrice.CabPriceCalculator;
import com.dal.cabby.cabPrice.ICabPriceCalculator;
import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.Booking;
import java.sql.SQLException;

public class CabSelection implements  ICabSelection{
    CabSelectionDAO bestCab;
    private IPersistence iPersistence;
    private Inputs inputs;
    private ICabPriceCalculator cabPriceCalculator;
    private ICabSelectionWithGender cabSelectionWithGender;
    private ICabSelectionWithoutGender cabSelectionWithoutGender;
    public String sourceLocation, destinationLocation;

    public CabSelection(Inputs inputs) throws SQLException {
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
        CabSelection cabSelection = new CabSelection(new InputFromUser());
        cabSelection.preferredCab(1, 20);
    }

    /*
        This method serves as presentation layer for Cab Selection feature. This method takes Cab preference,source and
        destination locations from user to book Cab. It also gives an option to book a cab based on gender of driver.
     */
    @Override
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
        Booking booking = new Booking(-1, custId, bestCab.driver_Id, -1, sourceLocation,
                                        destinationLocation, "", price, false);
        return booking;
    }
}

