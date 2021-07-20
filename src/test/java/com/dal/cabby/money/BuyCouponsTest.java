package com.dal.cabby.money;

import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class BuyCouponsTest {

  @Test
  void test() throws SQLException {
    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add("y").add(101);
    BuyCoupons buyCoupons = new BuyCoupons(inputs);
    buyCoupons.selectCoupons(1, UserType.CUSTOMER);
  }
}
