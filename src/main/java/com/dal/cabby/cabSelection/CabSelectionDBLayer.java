package com.dal.cabby.cabSelection;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.dbHelper.IPersistence;
import com.dal.cabby.io.Inputs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CabSelectionDBLayer {
    IPersistence iPersistence;
    Inputs inputs;
    CabSelection cabSelection;
    public List<CabSelectionDAO> cabDetails = new ArrayList<>();

    public CabSelectionDBLayer(Inputs inputs, CabSelection cabSelection){
        this.inputs=inputs;
        this.cabSelection = cabSelection;
        try {
            iPersistence= DBHelper.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<CabSelectionDAO> getAllNearbyCabs() throws SQLException {
        double sourceDistance = 0.0;
        String query = String.format("Select distanceFromOrigin from price_Calculation where sourceName='%s'", cabSelection.sourceLocation);
        ResultSet resultSet = iPersistence.executeSelectQuery(query);
        while (resultSet.next()) {
            sourceDistance = resultSet.getDouble("distanceFromOrigin");
        }

        double lowerRangeOfCabs = (sourceDistance - 5);
        double upperRangeOfCabs = (sourceDistance + 5);
        String query1 = String.format("Select cabName, cabId, cabDistanceFromOrigin, driver_id, routeTrafficDensity, " +
                        "cabSpeedOnRoute,driverGender from cabs where cabDistanceFromOrigin BETWEEN '%f' AND '%f'"
                , lowerRangeOfCabs, upperRangeOfCabs);

        ResultSet resultSet1 = iPersistence.executeSelectQuery(query1);

        while (resultSet1.next()) {
            CabSelectionDAO cabDetail = new CabSelectionDAO(resultSet1.getString("cabName"),
                    resultSet1.getInt("cabId"), resultSet1.getDouble("cabDistanceFromOrigin"),
                    resultSet1.getInt("driver_id"), resultSet1.getString("routeTrafficDensity"),
                    resultSet1.getInt("cabSpeedOnRoute"), resultSet1.getString("driverGender"));
            cabDetails.add(cabDetail);
        }

        System.out.println("Unfiltered List of nearby Cabs:");
        for (CabSelectionDAO cabDetail : cabDetails) {
            System.out.println(cabDetail.toString());
        }
        return cabDetails;
    }
}
