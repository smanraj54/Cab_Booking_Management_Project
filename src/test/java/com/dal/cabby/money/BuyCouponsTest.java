package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyCouponsTest {

  @Test
  void test() throws SQLException {
    IPersistence persistence = DBHelper.getInstance();
    persistence.executeCreateOrUpdateQuery("update user_points set total_points = 100 " +
        "where user_id = 1 and user_type = 'CUSTOMER';");
    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add("y").add(101);
    BuyCoupons buyCoupons = new BuyCoupons(inputs);
    assertEquals("\nCoupon added in your account successfully",
        buyCoupons.getCoupons(1, UserType.CUSTOMER),
        "Coupon is not added");
  }
}
