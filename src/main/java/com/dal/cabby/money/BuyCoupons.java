package com.dal.cabby.money;

import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;

/**
 * This class will provide the options to the user to buy coupons using
 * earned points.
 */
public class BuyCoupons {
    private IDBCouponOperation couponOperation;

    /**
     * Constructor of class BuyCoupons
     */
    public BuyCoupons() throws SQLException {
        couponOperation = new DBCouponOperations();
    }

    /**
     * This method will validate the input and process the request to
     * buy coupons.
     * Parameters:
     *   couponID - id of the coupon
     *   userID - id of the user
     *   userType - type of the user (CUSTOMER or DRIVER)
     * Returns:
     *   return the result of purchase request in string format
     */
    public String purchaseCoupon(int couponId, int userID, UserType userType) throws SQLException {
        int userPoints = couponOperation.checkUserPoints(userID, userType);
        int couponPoints = couponOperation.getCouponValue(couponId);
        if (!couponOperation.isCouponValid(couponId)) {
            return "\nInvalid coupon code";
        } else if (couponPoints > userPoints) {
            return "\nYou don't have sufficient points to buy this coupon";
        } else {
            return couponOperation.beginTransaction(couponId, couponPoints, userID, userType);
        }
    }
}
