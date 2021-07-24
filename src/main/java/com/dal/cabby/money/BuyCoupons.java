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
  IPersistence iPersistence;
  int requesterID;
  UserType requesterType;
  Inputs inputs;

  public BuyCoupons(Inputs inputs) throws SQLException {
    this.inputs = inputs;
    iPersistence = DBHelper.getInstance();
  }

  public String getCoupons(int userID, UserType userType) throws SQLException {
    requesterID = userID;
    requesterType = userType;
    return couponsPage();
  }

  private String couponsPage() throws SQLException {
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
      return purchaseCoupon(id, points);
    } else {
      return "Thanks for visiting the buy coupons page";
    }
  }

  private void displayCoupons() throws SQLException {
    String query = "select coupon_id, coupon_name, coupon_value," +
        "price_in_points from coupons;";
    ResultSet result = iPersistence.executeSelectQuery(query);
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
    ResultSet resultSet = iPersistence.executeSelectQuery(query);
    int points = 0;
    while (resultSet.next()) {
      points = resultSet.getInt("total_points");
    }
    return points;
  }

  private String purchaseCoupon(int couponId, int userPoints) throws SQLException {
    int couponPoints = getCouponValue(couponId);
    if (!isCouponValid(couponId)) {
      return "\nInvalid coupon code";
    } else if (couponPoints > userPoints) {
      return "\nYou don't have sufficient points to buy this coupon";
    } else {
    return beginTransaction(couponId, couponPoints);
    }
  }

  private int getCouponValue(int couponID) throws SQLException {
    String query = String.format("select price_in_points \n" +
        "from coupons\n" +
        "where coupon_id = %d;", couponID);
    ResultSet result = iPersistence.executeSelectQuery(query);
    int points = 0;
    while (result.next()) {
      points = result.getInt("price_in_points");
    }
    return points;
  }

  private boolean isCouponValid(int couponID) throws SQLException {
    int couponCount = 0;
    ResultSet result = iPersistence.executeSelectQuery("select count(*) id_count " +
        "from coupons where coupon_id = " + couponID + ";");
    while (result.next()) {
      couponCount = result.getInt("id_count");
    }
    return couponCount != 0;
  }

  private String beginTransaction(int couponID, int couponPoints) throws SQLException {

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
    iPersistence.executeCreateOrUpdateQuery(query1);
    iPersistence.executeCreateOrUpdateQuery(query2);
    iPersistence.executeCreateOrUpdateQuery(query3);
    iPersistence.executeCreateOrUpdateQuery(query4);
    return "\nCoupon added in your account successfully";
  }
}
