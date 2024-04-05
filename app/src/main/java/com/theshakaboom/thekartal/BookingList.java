package com.theshakaboom.thekartal;

public class BookingList {

    String bookingId, startKm, cancelNote, payment, status, startDate, returnDate, vehicleId, userId;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getStartKm() {
        return startKm;
    }

    public void setStartKm(String startKm) {
        this.startKm = startKm;
    }

    public String getCancelNote() {
        return cancelNote;
    }

    public void setCancelNote(String cancelNote) {
        this.cancelNote = cancelNote;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BookingList(String bookingId, String startKm, String cancelNote, String payment, String status, String startDate, String returnDate, String vehicleId, String userId) {
        this.bookingId = bookingId;
        this.startKm = startKm;
        this.cancelNote = cancelNote;
        this.payment = payment;
        this.status = status;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.vehicleId = vehicleId;
        this.userId = userId;
    }
}
