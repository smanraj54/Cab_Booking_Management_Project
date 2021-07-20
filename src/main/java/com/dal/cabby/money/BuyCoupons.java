package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import java.sql.ResultSet;
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

  public void selectCoupons(int userID, UserType userType) throws SQLException {
    requesterID = userID;
    requesterType = userType;
    couponsPage();
  }

  private void couponsPage() throws SQLException {
    System.out.println("\nBelow are the available coupons: ");
    System.out.println("\nCouponID" + ", " + "CouponName" + ", " +
        "CouponValue" + ", " + "PriceInPoint");
    displayCoupons();
    System.out.println("\nDo you want to buy any coupon (y/n): ");
    String selection = inputs.getStringInput();
    if (selection.equalsIgnoreCase("y")) {
      System.out.println("\nPlease enter the coupon id: ");
      int id = inputs.getIntegerInput();
    } else {
      return;
    }
  }

  private void displayCoupons() throws SQLException {
    String query = "select coupon_id, coupon_name, coupon_value," +
        "price_in_points from coupons;";
    ResultSet result = dbHelper.executeSelectQuery(query);
    while (result.next()) {
      int couponID = result.getInt("coupon_id");
      String couponName = result.getString("coupon_name");
      double couponValue = result.getDouble("coupon_value");
      int couponPoints = result.getInt("price_in_points");
      System.out.println(couponID + ", " + couponName + ", $" +
          couponValue + ", " + couponPoints);
    }
  }
}
