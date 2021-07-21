package com.dal.cabby.pojo;

public class Booking {
    int bookingId;
    int customerId;
    int driverId;
    int cabId;
    String source, destination;
    String travelTime;
    double price;

    public Booking(int booking_id, int customerId, int driverid, int cabId, String source, String destination, String travelTime, double price) {
        this.bookingId = booking_id;
        this.customerId = customerId;
        this.driverId = driverid;
        this.cabId = cabId;
        this.source = source;
        this.destination = destination;
        this.travelTime = travelTime;
        this.price = price;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getDriverId() {
        return driverId;
    }

    public int getCabId() {
        return cabId;
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

    public double getPrice() {
        return price;
    }
}
