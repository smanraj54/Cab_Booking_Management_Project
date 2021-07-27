package com.dal.cabby.money;

import java.sql.SQLException;
import java.util.List;

/**
 * This class is used to get the coupon details from the database
 * and return it as List.
 */
public class CouponDetails {
    private IDBCouponOperation couponOperation;

    public CouponDetails() throws SQLException {
        couponOperation =new DBCouponOperations();
    }

    /**
     * This method will fetch the coupon details from the database
     * Returns:
     *   List of coupon details
     */
    public List<String> getCoupons() throws SQLException {
        return couponOperation.showCoupons();
    }
}
