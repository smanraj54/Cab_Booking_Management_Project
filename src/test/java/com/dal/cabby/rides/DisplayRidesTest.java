package com.dal.cabby.rides;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisplayRidesTest {

  IPersistence iPersistence = DBHelper.getInstance();

  public DisplayRidesTest() throws SQLException {
  }

  @Test
  void testDailyDriverRides() throws SQLException {

    // cleaning data for testing
    iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (101,102);");
    iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (101,102);");

    // inserting data for testing in booking table
    iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
        "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
        "(101, '2020-07-24 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto'), " +
        "(102, '2020-07-24 20:00:00', 2, 2, 2, 80.5, 'Winnipeg', 'Montreal');");

    // inserting data for testing in trips table
    iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
        "booking_id, trip_amount, distance_covered, created_at) values " +
        "(101, 1, 1, 101, 150, 1600, '2020-07-24 20:00:00'), " +
        "(102, 2, 2, 102, 80.5, 200, '2020-07-24 20:00:00');");

    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(1).add("24/07/2020");
    DisplayRides rides = new DisplayRides(inputs);

    List<String> expected = new ArrayList<>();
    expected.add("Ride Details ->");
    expected.add("BookingID: 101, Pickup: Halifax, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");

    assertEquals(Collections.singletonList(expected),
        Collections.singletonList(rides.getRides(UserType.DRIVER, 1)),
        "Daily Rides returned for driver are not correct");
  }

  @Test
  void testMonthlyDriverRides() throws SQLException {

    // cleaning data for testing
    iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (101,102,103,104);");
    iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (101,102,103,104);");

    // inserting data for testing in booking table
    iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
        "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
        "(101, '2020-07-01 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto'), " +
        "(102, '2020-07-10 20:00:00', 1, 2, 2, 150, 'Winnipeg', 'Toronto'), " +
        "(103, '2020-07-20 20:00:00', 1, 3, 3, 150, 'Montreal', 'Toronto'), " +
        "(104, '2020-07-31 20:00:00', 1, 4, 4, 80.5, 'Winnipeg', 'Montreal');");

    // inserting data for testing in trips table
    iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
        "booking_id, trip_amount, distance_covered, created_at) values " +
        "(101, 1, 1, 101, 150, 1600, '2020-07-01 20:00:00'), " +
        "(102, 1, 2, 102, 150, 1600, '2020-07-10 20:00:00'), " +
        "(103, 1, 3, 103, 150, 1600, '2020-07-20 20:00:00'), " +
        "(104, 1, 4, 104, 80.5, 200, '2020-07-31 20:00:00');");

    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(2).add("07/2020");  // user inputs
    DisplayRides rides = new DisplayRides(inputs);

    List<String> expected = new ArrayList<>();
    expected.add("Ride Details ->");
    expected.add("BookingID: 101, Pickup: Halifax, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 102, Pickup: Winnipeg, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 103, Pickup: Montreal, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 104, Pickup: Winnipeg, Destination: Montreal, " +
        "Price: 80.5, Status: Completed");

    assertEquals(Collections.singletonList(expected),
        Collections.singletonList(rides.getRides(UserType.DRIVER, 1)),
        "Monthly Rides returned for driver are not correct");
  }

  @Test
  void testSpecificPeriodDriverRides() throws SQLException {

    // cleaning data for testing
    iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (101,102,103,104);");
    iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (101,102,103,104);");

    // inserting data for testing in booking table
    iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
        "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
        "(101, '2020-01-01 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto'), " +
        "(102, '2020-03-10 20:00:00', 1, 2, 2, 150, 'Winnipeg', 'Toronto'), " +
        "(103, '2020-04-20 20:00:00', 1, 3, 3, 150, 'Montreal', 'Toronto'), " +
        "(104, '2020-07-31 20:00:00', 1, 4, 4, 80.5, 'Winnipeg', 'Montreal');");

    // inserting data for testing in trips table
    iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
        "booking_id, trip_amount, distance_covered, created_at) values " +
        "(101, 1, 1, 101, 150, 1600, '2020-01-01 20:00:00'), " +
        "(102, 1, 2, 102, 150, 1600, '2020-02-10 20:00:00'), " +
        "(103, 1, 3, 103, 150, 1600, '2020-04-20 20:00:00'), " +
        "(104, 1, 4, 104, 80.5, 200, '2020-07-31 20:00:00');");

    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(3).add("01/01/2020").add("31/07/2020");  // user inputs
    DisplayRides rides = new DisplayRides(inputs);

    List<String> expected = new ArrayList<>();
    expected.add("Ride Details ->");
    expected.add("BookingID: 101, Pickup: Halifax, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 102, Pickup: Winnipeg, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 103, Pickup: Montreal, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 104, Pickup: Winnipeg, Destination: Montreal, " +
        "Price: 80.5, Status: Completed");

    assertEquals(Collections.singletonList(expected),
        Collections.singletonList(rides.getRides(UserType.DRIVER, 1)),
        "Rides returned between specific period for driver are not correct");
  }

  @Test
  void invalidDateTest() throws SQLException {
    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(1).add("00/07/2020");
    DisplayRides rides = new DisplayRides(inputs);

    assertEquals(Collections.singletonList("Invalid Input"),
        rides.getRides(UserType.DRIVER, 1),
        "Issue in detecting invalid date");
  }

  @Test
  void invalidMonthTest() throws SQLException {
    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(1).add("01/00/2020");
    DisplayRides rides = new DisplayRides(inputs);

    assertEquals(Collections.singletonList("Invalid Input"),
        rides.getRides(UserType.DRIVER, 1),
        "Issue in detecting invalid date");
  }

  @Test
  void invalidYearTest() throws SQLException {
    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(1).add("01/07/0000");
    DisplayRides rides = new DisplayRides(inputs);

    assertEquals(Collections.singletonList("Invalid Input"),
        rides.getRides(UserType.DRIVER, 1),
        "Issue in detecting invalid date");
  }

  @Test
  void testDailyCustomerRides() throws SQLException {

    // cleaning data for testing
    iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (101,102);");
    iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (101,102);");

    // inserting data for testing in booking table
    iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
        "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
        "(101, '2020-07-24 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto'), " +
        "(102, '2020-07-24 20:00:00', 2, 2, 2, 80.5, 'Winnipeg', 'Montreal');");

    // inserting data for testing in trips table
    iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
        "booking_id, trip_amount, distance_covered, created_at) values " +
        "(101, 1, 1, 101, 150, 1600, '2020-07-24 20:00:00'), " +
        "(102, 2, 2, 102, 80.5, 200, '2020-07-24 20:00:00');");

    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(1).add("24/07/2020");
    DisplayRides rides = new DisplayRides(inputs);

    List<String> expected = new ArrayList<>();
    expected.add("Ride Details ->");
    expected.add("BookingID: 101, Pickup: Halifax, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");

    assertEquals(Collections.singletonList(expected),
        Collections.singletonList(rides.getRides(UserType.CUSTOMER, 1)),
        "Daily Rides returned for customer are not correct");
  }

  @Test
  void testMonthlyCustomerRides() throws SQLException {

    // cleaning data for testing
    iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (101,102,103,104);");
    iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (101,102,103,104);");

    // inserting data for testing in booking table
    iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
        "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
        "(101, '2020-07-01 20:00:00', 1, 1, 1, 150, 'Halifax', 'Toronto'), " +
        "(102, '2020-07-10 20:00:00', 2, 1, 2, 150, 'Winnipeg', 'Toronto'), " +
        "(103, '2020-07-20 20:00:00', 3, 1, 3, 150, 'Montreal', 'Toronto'), " +
        "(104, '2020-07-31 20:00:00', 4, 1, 4, 80.5, 'Winnipeg', 'Montreal');");

    // inserting data for testing in trips table
    iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
        "booking_id, trip_amount, distance_covered, created_at) values " +
        "(101, 1, 1, 101, 150, 1600, '2020-07-01 20:00:00'), " +
        "(102, 2, 1, 102, 150, 1600, '2020-07-10 20:00:00'), " +
        "(103, 3, 1, 103, 150, 1600, '2020-07-20 20:00:00'), " +
        "(104, 4, 1, 104, 80.5, 200, '2020-07-31 20:00:00');");

    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(2).add("07/2020");  // user inputs
    DisplayRides rides = new DisplayRides(inputs);

    List<String> expected = new ArrayList<>();
    expected.add("Ride Details ->");
    expected.add("BookingID: 101, Pickup: Halifax, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 102, Pickup: Winnipeg, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 103, Pickup: Montreal, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 104, Pickup: Winnipeg, Destination: Montreal, " +
        "Price: 80.5, Status: Completed");

    assertEquals(Collections.singletonList(expected),
        Collections.singletonList(rides.getRides(UserType.CUSTOMER, 1)),
        "Monthly Rides returned for customer are not correct");
  }

  @Test
  void testSpecificPeriodCustomerRides() throws SQLException {

    // cleaning data for testing
    iPersistence.executeCreateOrUpdateQuery("delete from trips where trip_id in (101,102,103,104);");
    iPersistence.executeCreateOrUpdateQuery("delete from bookings where booking_id in (101,102,103,104);");

    // inserting data for testing in booking table
    iPersistence.executeCreateOrUpdateQuery("insert into bookings(booking_id, " +
        "created_at, driver_id, cust_id, cab_id, estimated_price, source, destination) values " +
        "(101, '2020-01-01 20:00:00', 1, 2, 1, 150, 'Halifax', 'Toronto'), " +
        "(102, '2020-03-10 20:00:00', 2, 2, 2, 150, 'Winnipeg', 'Toronto'), " +
        "(103, '2020-04-20 20:00:00', 3, 2, 3, 150, 'Montreal', 'Toronto'), " +
        "(104, '2020-07-31 20:00:00', 4, 2, 4, 80.5, 'Winnipeg', 'Montreal');");

    // inserting data for testing in trips table
    iPersistence.executeCreateOrUpdateQuery("insert into trips(trip_id, driver_id, cust_id, " +
        "booking_id, trip_amount, distance_covered, created_at) values " +
        "(101, 1, 2, 101, 150, 1600, '2020-01-01 20:00:00'), " +
        "(102, 2, 2, 102, 150, 1600, '2020-02-10 20:00:00'), " +
        "(103, 3, 2, 103, 150, 1600, '2020-04-20 20:00:00'), " +
        "(104, 4, 2, 104, 80.5, 200, '2020-07-31 20:00:00');");

    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(3).add("01/01/2020").add("31/07/2020");  // user inputs
    DisplayRides rides = new DisplayRides(inputs);

    List<String> expected = new ArrayList<>();
    expected.add("Ride Details ->");
    expected.add("BookingID: 101, Pickup: Halifax, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 102, Pickup: Winnipeg, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 103, Pickup: Montreal, Destination: Toronto, " +
        "Price: 150.0, Status: Completed");
    expected.add("BookingID: 104, Pickup: Winnipeg, Destination: Montreal, " +
        "Price: 80.5, Status: Completed");

    assertEquals(Collections.singletonList(expected),
        Collections.singletonList(rides.getRides(UserType.CUSTOMER, 2)),
        "Rides returned between specific period for customer are not correct");
  }

  @Test
  void testWhenNoRides() throws SQLException {
    PredefinedInputs inputs = new PredefinedInputs();
    inputs.add(1).add("01/07/2000");
    DisplayRides rides = new DisplayRides(inputs);

    List<String> expected = new ArrayList<>();
    expected.add("Ride Details ->");
    expected.add("No rides to display");

    assertEquals(Collections.singletonList(expected),
        Collections.singletonList(rides.getRides(UserType.DRIVER, 1)),
        "Incorrect output when there is no ride to display");
  }
}
