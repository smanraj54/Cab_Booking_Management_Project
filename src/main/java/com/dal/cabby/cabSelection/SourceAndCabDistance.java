package com.dal.cabby.cabSelection;

import java.sql.SQLException;

public class SourceAndCabDistance {

    /*
        This method calculate distance between Source i.e, (location from where user is booking cab) and
        Nearby Cabs. It takes two parameters i.e, source location and names of nearby cabs.
     */
    public double calculateDistance(Double source,Double destination) throws SQLException {
        double distance=0.0;
        if(source > 0 && destination > 0) {
            if (destination < source) {
                distance = source - destination;
            } else {
                distance = destination - source;
            }
        }
        else if (source < 0 && destination < 0) {
            if (destination < source) {
                distance = source - destination;
            } else {
                distance = destination - source;
            }
        }
        else if (source < 0 && destination > 0) {
            distance = destination-source;
        }
        else if (source > 0 && destination < 0) {
            distance = source - destination;
        }
        return (Math.round(distance*100.0)/100.0);
    }
}
