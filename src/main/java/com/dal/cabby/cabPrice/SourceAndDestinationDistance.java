package com.dal.cabby.cabPrice;

import java.sql.SQLException;

public class SourceAndDestinationDistance {

    /*
        This method calculate distance between Source and Destination place. It takes two parameters
        i.e, source location and destination location as input.
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
