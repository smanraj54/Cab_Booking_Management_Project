package com.dal.cabby.cabSelection;
import com.dal.cabby.cabPrice.CabPriceCalculator;

import java.sql.SQLException;
import java.util.Scanner;

public class CabSelection {
    static Scanner ss=new Scanner(System.in);
    CabPriceCalculator cabPriceCalculator=new CabPriceCalculator();

    public void preferredCab() throws SQLException {
        System.out.println("Enter your Cab preference");
        System.out.println("1. Micro and Mini");
        System.out.println("2. Prime Sedan");
        System.out.println("3. Prime SUV");
        System.out.println("4. Luxury Class");
        int input=ss.nextInt();
        switch (input){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        cabPriceCalculator.priceCalculation("Halifax","Montreal",false,"urban",true, input);
    }
}

