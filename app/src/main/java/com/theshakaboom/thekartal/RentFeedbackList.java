package com.theshakaboom.thekartal;

public class RentFeedbackList {
    String feedbackID, details, userID, vehicleId, booking ;
    int rating;

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public RentFeedbackList(String feedbackID, String details, String userID, String vehicleId, String booking, int rating) {
        this.feedbackID = feedbackID;
        this.details = details;
        this.userID = userID;
        this.vehicleId = vehicleId;
        this.booking = booking;
        this.rating = rating;
    }
}
