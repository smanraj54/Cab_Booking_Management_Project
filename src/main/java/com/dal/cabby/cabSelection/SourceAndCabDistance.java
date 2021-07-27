package com.dal.cabby.cabSelection;

import java.sql.SQLException;

public class SourceAndCabDistance {

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
