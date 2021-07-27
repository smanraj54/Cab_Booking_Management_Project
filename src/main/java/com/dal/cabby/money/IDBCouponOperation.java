package com.dal.cabby.money;

import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;
import java.util.List;

public interface IDBCouponOperation {

    /**
     * This method will fetch the coupon details from the database
     */
    List<String> showCoupons() throws SQLException;

    /**
     * This method will return the available user points
     */
    int checkUserPoints(int userID, UserType userType) throws SQLException;

    /**
     * This method will fetch the value of particular coupon.
     * Parameter:
     *   couponID - coupon id
     * Returns:
     *   return the value of the coupon
     */
    int getCouponValue(int couponID) throws SQLException;

    /**
     * This method will check if the coupon is valid.
     * Parameters:
     *   couponID - coupon id
     * Returns:
     *   returns true if coupon is valid else false
     */
    boolean isCouponValid(int couponID) throws SQLException;

    /**
     * This method will update the database as per the purchase request.
     * Parameters:
     *   couponID - coupon id
     *   couponPoints - the value of coupon in points
     *   userID - id of the user
     *   userType - type of the user (CUSTOMER or DRIVER)
     * Returns:
     *   returns the message after updating the database
     */
    String beginTransaction(int couponID, int couponPoints, int userID, UserType userType) throws SQLException;
}
