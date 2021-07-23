package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class will provide the options to the user to buy coupons using
 * earned points.
 */
public class BuyCoupons {
  IPersistence IPersistence;
  int requesterID;
  UserType requesterType;
  Inputs inputs;

  public BuyCoupons(Inputs inputs) throws SQLException {
    this.inputs = inputs;
    IPersistence = DBHelper.getInstance();
  }

  public void getCoupons(int userID, UserType userType) throws SQLException {
    requesterID = userID;
    requesterType = userType;
    couponsPage();
  }

  private void couponsPage() throws SQLException {
    System.out.println("\nBelow are the available coupons: ");
    System.out.println("\nCouponID" + ", " + "CouponName" + ", " +
        "CouponValue" + ", " + "PriceInPoint");
    displayCoupons();
    System.out.print("\nDo you want to buy any coupon (y/n): ");
    String selection = inputs.getStringInput();
    if (selection.equalsIgnoreCase("y")) {
      System.out.print("\nPlease enter the coupon id: ");
      int id = inputs.getIntegerInput();
      int points = checkUserPoints();
      purchaseCoupon(id, points);
    } else {
      return;
    }
  }

  private void displayCoupons() throws SQLException {
    String query = "select coupon_id, coupon_name, coupon_value," +
        "price_in_points from coupons;";
    ResultSet result = IPersistence.executeSelectQuery(query);
    while (result.next()) {
      int couponID = result.getInt("coupon_id");
      String couponName = result.getString("coupon_name");
      double couponValue = result.getDouble("coupon_value");
      int couponPoints = result.getInt("price_in_points");
      System.out.println(couponID + ", " + couponName + ", $" +
          couponValue + ", " + couponPoints);
    }
  }

  private int checkUserPoints() throws SQLException {
    String query = String.format("select total_points \n" +
        "from user_points \n" +
        "where user_id = %d and upper(user_type) = '%s';",
        requesterID, requesterType);
    ResultSet resultSet = IPersistence.executeSelectQuery(query);
    int points = 0;
    while (resultSet.next()) {
      points = resultSet.getInt("total_points");
    }
    return points;
  }

  private void purchaseCoupon(int couponId, int userPoints) throws SQLException {
    int couponPoints = getCouponValue(couponId);
    if (couponPoints > userPoints) {
      System.out.println("\nYou don't have sufficient points to buy this coupon");
    } else {
      beginTransaction(couponId, couponPoints);
    }
  }

  private int getCouponValue(int couponID) throws SQLException {
    String query = String.format("select price_in_points \n" +
        "from coupons\n" +
        "where coupon_id = %d;", couponID);
    ResultSet result = IPersistence.executeSelectQuery(query);
    int points = 0;
    while (result.next()) {
      points = result.getInt("price_in_points");
    }
    return points;
  }

  private void beginTransaction(int couponID, int couponPoints) throws SQLException {

    // query to start transaction
    String query1 = "start transaction;";

    // query to update user points
    String query2 = String.format("update user_points \n" +
        "set total_points = total_points - %d \n" +
        "where user_id = %d and upper(user_type) = '%s';",
        couponPoints, requesterID, requesterType);

    // query to add the details of purchased coupon in database table
    String query3 = String.format("insert into user_coupons(user_id, user_type, coupon_id) " +
        "values (%d, '%s', %d);", requesterID, requesterType, couponID);

    // query to commit the transaction
    String query4 = "commit;";

    // executing queries in order
    IPersistence.executeCreateOrUpdateQuery(query1);
    IPersistence.executeCreateOrUpdateQuery(query2);
    IPersistence.executeCreateOrUpdateQuery(query3);
    IPersistence.executeCreateOrUpdateQuery(query4);
    System.out.println("\nCoupon added in your account successfully");
  }
}
