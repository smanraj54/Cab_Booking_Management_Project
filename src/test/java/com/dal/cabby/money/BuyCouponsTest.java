package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyCouponsTest {

  @Test
  void testBuyCoupon() throws SQLException {
    IPersistence persistence = DBHelper.getInstance();
    persistence.executeCreateOrUpdateQuery("update user_points set total_points = 100 " +
        "where user_id = 1 and user_type = 'CUSTOMER';");

    BuyCoupons buyCoupons = new BuyCoupons();
    assertEquals("\nCoupon added in your account successfully",
        buyCoupons.purchaseCoupon(101, 1, UserType.CUSTOMER),
        "Coupon is not added");
  }

  @Test
  void testBuyCouponLessPoints() throws SQLException {
    IPersistence persistence = DBHelper.getInstance();
    persistence.executeCreateOrUpdateQuery("update user_points set total_points = 100 " +
        "where user_id = 1 and user_type = 'CUSTOMER';");
    BuyCoupons buyCoupons = new BuyCoupons();
    assertEquals("\nYou don't have sufficient points to buy this coupon",
        buyCoupons.purchaseCoupon(103, 1, UserType.CUSTOMER),
        "Coupon is not added");
  }

  @Test
  void testBuyCouponInvalidID() throws SQLException {
    BuyCoupons buyCoupons = new BuyCoupons();
    assertEquals("\nInvalid coupon code",
        buyCoupons.purchaseCoupon(200, 1, UserType.CUSTOMER),
        "Coupon is not added");
  }
}
