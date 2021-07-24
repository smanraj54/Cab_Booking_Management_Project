package com.dal.cabby.pojo;

public class Booking {
    int bookingId;
    int customerId;
    int driverId;
    int cabId;
    String source, destination;
    String travelTime;
    double price;
    boolean isCancelled;
    boolean hasDriverCancelled;
    boolean hasCustomerCancelled;

    public Booking() {
    }

    public Booking(int booking_id, int customerId, int driverid, int cabId, String source, String destination, String travelTime, double price, boolean isCancelled) {
        this.bookingId = booking_id;
        this.customerId = customerId;
        this.driverId = driverid;
        this.cabId = cabId;
        this.source = source;
        this.destination = destination;
        this.travelTime = travelTime;
        this.price = price;
        this.isCancelled = isCancelled;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public boolean isHasDriverCancelled() {
        return hasDriverCancelled;
    }

    public void setHasDriverCancelled(boolean hasDriverCancelled) {
        this.hasDriverCancelled = hasDriverCancelled;
    }

    public boolean isHasCustomerCancelled() {
        return hasCustomerCancelled;
    }

    public void setHasCustomerCancelled(boolean hasCustomerCancelled) {
        this.hasCustomerCancelled = hasCustomerCancelled;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public void setCabId(int cabId) {
        this.cabId = cabId;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public void setPrice(double price) {
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
