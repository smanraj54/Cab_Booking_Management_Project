package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;

import java.sql.SQLException;

/**
 * This class will provide the options to the user to buy coupons using
 * earned points.
 */
public class BuyCoupons {
  DBHelper dbHelper;
  int requesterID;
  UserType requesterType;
  Inputs inputs;

  public BuyCoupons(Inputs inputs) throws SQLException {
    this.inputs = inputs;
    dbHelper = new DBHelper();
    dbHelper.initialize();
  }

  public void selectCoupons(int userID, UserType userType) {
    requesterID = userID;
    requesterType = userType;
    couponsPage();
  }

  private void couponsPage() {
    System.out.println("\nBelow are the available coupons: ");
    System.out.println("\nCouponID" + "\t-\t" + "CouponName" + "\t-\t" + "CouponValue" + "\t-\t" + "PriceInPoint");
  }

  public static void main(String[] args) throws SQLException {
    Inputs inputs = new PredefinedInputs();
    BuyCoupons coupons = new BuyCoupons(inputs);
    coupons.selectCoupons(1, UserType.CUSTOMER);
  }
}
