package com.theshakaboom.thekartal;

public class PassengerFeedbackList {
    String feedbackID;
    String details;
    String userID;
    String booking;
    String ownerId ;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    public PassengerFeedbackList(String feedbackID, String details, String ownerId, String userID, String booking, int rating) {
        this.feedbackID = feedbackID;
        this.details = details;
        this.userID = userID;
        this.ownerId = ownerId;
        this.booking = booking;
        this.rating = rating;
    }
}
