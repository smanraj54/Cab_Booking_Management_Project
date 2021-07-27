package com.dal.cabby.money;

import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;
import java.util.List;

/**
 * This class is used to display the coupon details to the user and
 * get the input from the user to purchase the coupons.
 */
public class BuyCouponsPage {
    private Inputs inputs;
    private BuyCoupons buyCoupons;
    private CouponDetails coupons;

    public BuyCouponsPage() throws SQLException {
        inputs = new InputFromUser();
        buyCoupons = new BuyCoupons();
        coupons = new CouponDetails();
    }

    /**
     * This method will display the available coupons and ask the user to
     * select the coupon they want to buy.
     */
    public void couponsPage(int userID, UserType userType) throws SQLException {
        System.out.println("\nBelow are the available coupons: ");
        System.out.println("\nCouponID" + ", " + "CouponName" + ", " +
            "CouponValue" + ", " + "PriceInPoint");
        List<String> couponsList = coupons.getCoupons();
        for (String coupon : couponsList) {
            System.out.println(coupon);
        }
        System.out.print("\nDo you want to buy any coupon (y/n): ");
        String selection = inputs.getStringInput();
        if (selection.equalsIgnoreCase("y")) {
            System.out.print("\nPlease enter the coupon id: ");
            int couponID = inputs.getIntegerInput();
            System.out.println(buyCoupons.purchaseCoupon(couponID, userID, userType));
        } else {
            System.out.println("\nThanks for visiting the buy coupons page");
        }
    }
}
