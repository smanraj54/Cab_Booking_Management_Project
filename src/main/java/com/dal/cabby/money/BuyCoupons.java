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

  /**
   * Constructor of class BuyCoupons
   */
  public BuyCoupons(Inputs inputs) throws SQLException {
    this.inputs = inputs;
    iPersistence = DBHelper.getInstance();
  }

  /**
   * This method will get the input from user and process the request
   * to buy coupons.
   * Parameters:
   *   userID - id of the user
   *   userType - user type (DRIVER or CUSTOMER)
   * Returns:
   *   return the output as a string
   */
  public String getCoupons(int userID, UserType userType) throws SQLException {
    requesterID = userID;
    requesterType = userType;
    return couponsPage();
  }

  /**
   * This method will display the available coupons and ask the user to
   * select the coupon they want to buy.
   */
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

  /**
   * This method will fetch the coupon details from the database
   */
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

  /**
   * This method will return the available user points
   */
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

  /**
   * This method will validate the input and process the request to
   * buy coupons.
   * Parameters:
   *   couponID - id of the coupon
   *   userPoints - points of the user
   * Returns:
   *   return the result of purchase request in string format
   */
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

  /**
   * This method will fetch the value of particular coupon.
   * Parameter:
   *   couponID - coupon id
   * Returns:
   *   return the value of the coupon
   */
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

  /**
   * This method will check if the coupon is valid.
   * Parameters:
   *   couponID - coupon id
   * Returns:
   *   returns true if coupon is valid else false
   */
  private boolean isCouponValid(int couponID) throws SQLException {
    int couponCount = 0;
    ResultSet result = iPersistence.executeSelectQuery("select count(*) id_count " +
        "from coupons where coupon_id = " + couponID + ";");
    while (result.next()) {
      couponCount = result.getInt("id_count");
    }
    return couponCount != 0;
  }

  /**
   * This method will update the database as per the purchase request.
   * Parameters:
   *   couponID - coupon id
   *   couponPoints - the value of coupon in points
   * Returns:
   *   returns the message after updating the database
   */
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
