package com.dal.cabby.util;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DateOperations {

  IPersistence iPersistence;

  public DateOperations() throws SQLException {
    iPersistence = DBHelper.getInstance();
  }

  // method for date validation
  public boolean validateDate(String date) {
    if (date != null && date.length() == 10 && date.indexOf("/") == 2 && date.lastIndexOf("/") == 5) {
      String[] splitDate = date.split("/");
      String day = splitDate[0];
      String month = splitDate[1];
      String year = splitDate[2];
      return !day.equals("00") && !month.equals("00") && !year.equals("0000");
    }
    return false;
  }

  // method to get the date in required format
  public String getFormattedDate(String inputDate) {
    String[] splitDate = inputDate.split("/");
    String day = splitDate[0];
    String month = splitDate[1];
    String year = splitDate[2];
    return (year + "-" + month + "-" + day);
  }

  // method to get the last day of month
  public String getLastDay(String inputDate) throws SQLException {
    String date = "";
    String query = String.format("select last_day('%s') as last_date", inputDate);
    ResultSet result = iPersistence.executeSelectQuery(query);
    while (result.next()) {
      date = result.getString("last_date");
    }
    return date;
  }

  // method to get the difference between two dates
  public int getDateDifference(String startDate, String endDate) throws SQLException {
    int dateDifference = 0;
    String query = String.format("select datediff('%s','%s') as date_difference", endDate, startDate);
    ResultSet result = iPersistence.executeSelectQuery(query);
    while (result.next()) {
      dateDifference = result.getInt("date_difference");
    }
    return dateDifference;
  }
}
