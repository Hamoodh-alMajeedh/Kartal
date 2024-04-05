package com.theshakaboom.thekartal;

public class VehicleList {

    String regNo;
    String type;
    String make;
    String model;
    int year;
    String license_pic;
    String status;
    String ownerId;
    String transmission;
    String fuel;
    String carImageFront;
    String carImageBack;
    String carImageRight;
    String carImageLeft;




    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public String getLicense_pic() {
        return license_pic;
    }

    public void setLicense_pic(String license_pic) {
        this.license_pic = license_pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
    public String getCarImageFront() {
        return carImageFront;
    }

    public void setCarImageFront(String carImageFront) {
        this.carImageFront = carImageFront;
    }

    public String getCarImageBack() {
        return carImageBack;
    }

    public void setCarImageBack(String carImageBack) {
        this.carImageBack = carImageBack;
    }

    public String getCarImageRight() {
        return carImageRight;
    }

    public void setCarImageRight(String carImageRight) {
        this.carImageRight = carImageRight;
    }

    public String getCarImageLeft() {
        return carImageLeft;
    }

    public void setCarImageLeft(String carImageLeft) {
        this.carImageLeft = carImageLeft;
    }


    public VehicleList( String regNo, String type, String make, String model, int year, String license_pic, String status, String ownerId, String transmission, String fuel, String carImageFront, String carImageBack, String carImageRight, String carImageLeft) {
        this.regNo = regNo;
        this.type = type;
        this.make = make;
        this.model = model;
        this.year = year;
        this.license_pic = license_pic;
        this.status = status;
        this.ownerId = ownerId;
        this.transmission = transmission;
        this.fuel = fuel;
        this.carImageFront = carImageFront;
        this.carImageBack = carImageBack;
        this.carImageRight = carImageRight;
        this.carImageLeft = carImageLeft;
    }


}
