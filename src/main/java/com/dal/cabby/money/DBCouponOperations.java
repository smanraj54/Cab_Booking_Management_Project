package com.dal.cabby.money;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.pojo.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to send query related to the coupons to the database
 * and get the appropriate result.
 */
public class DBCouponOperations implements IDBCouponOperation {
    private IPersistence iPersistence;

    public DBCouponOperations() throws SQLException {
        iPersistence = DBHelper.getInstance();
    }

    /**
     * This method will fetch the coupon details from the database
     * Returns:
     *   List of coupons
     */
    public List<String> showCoupons() throws SQLException {
        List<String> couponsList = new ArrayList<>();
        String query = "select coupon_id, coupon_name, coupon_value," +
            "price_in_points from coupons;";
        ResultSet result = iPersistence.executeSelectQuery(query);
        while (result.next()) {
            int couponID = result.getInt("coupon_id");
            String couponName = result.getString("coupon_name");
            double couponValue = result.getDouble("coupon_value");
            int couponPoints = result.getInt("price_in_points");
            String coupons = (couponID + ", " + couponName + ", $" +
                couponValue + ", " + couponPoints);
            couponsList.add(coupons);
        }
        return couponsList;
    }

    /**
     * This method check the available points for the user
     * Returns:
     *   points in integer
     */
    public int checkUserPoints(int userID, UserType userType) throws SQLException {
        String query = String.format("select total_points \n" +
                "from user_points \n" +
                "where user_id = %d and upper(user_type) = '%s';",
            userID, userType);
        ResultSet resultSet = iPersistence.executeSelectQuery(query);
        int points = 0;
        while (resultSet.next()) {
            points = resultSet.getInt("total_points");
        }
        return points;
    }

    /**
     * This method will fetch the value of particular coupon.
     * Parameter:
     *   couponID - coupon id
     * Returns:
     *   return the value of the coupon
     */
    public int getCouponValue(int couponID) throws SQLException {
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
    public boolean isCouponValid(int couponID) throws SQLException {
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
     *   userID - id of the user
     *   userType - type of the user (CUSTOMER or DRIVER)
     * Returns:
     *   returns the message after updating the database
     */
    public String beginTransaction(int couponID, int couponPoints, int userID, UserType userType) throws SQLException {

        // query to start transaction
        String query1 = "start transaction;";

        // query to update user points
        String query2 = String.format("update user_points \n" +
                "set total_points = total_points - %d \n" +
                "where user_id = %d and upper(user_type) = '%s';",
            couponPoints, userID, userType);

        // query to add the details of purchased coupon in database table
        String query3 = String.format("insert into user_coupons(user_id, user_type, coupon_id) " +
            "values (%d, '%s', %d);", userID, userType, couponID);

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
