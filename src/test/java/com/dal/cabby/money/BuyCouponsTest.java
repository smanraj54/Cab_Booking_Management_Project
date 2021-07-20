package com.dal.cabby.money;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class BuyCouponsTest {

  @Test
  void test() throws SQLException {
    Inputs inputs = new PredefinedInputs();
    BuyCoupons buyCoupons = new BuyCoupons(inputs);
    buyCoupons.selectCoupons(1, UserType.CUSTOMER);
  }
}
