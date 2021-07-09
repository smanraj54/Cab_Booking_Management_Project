package com.dal.cabby.pojo;

public class Bookings {
    int bookingId;
    int customerId;
    String source, destination;
    String travelTime;

    public Bookings(int booking_id, int customerId, String source, String destination, String travelTime) {
        this.bookingId = booking_id;
        this.customerId = customerId;
        this.source = source;
        this.destination = destination;
        this.travelTime = travelTime;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getTravelTime() {
        return travelTime;
    }
}
