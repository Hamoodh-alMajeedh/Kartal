package com.theshakaboom.thekartal;

public class UserList {
    String username;
    String firstName;
    String lastName;
    String phone;
    String email;
    String address;
    String dob;
    String gender;
    String password;
    String profileImg;
    String drivingLFront;
    String drivingLBack;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getDrivingLFront() {
        return drivingLFront;
    }

    public void setDrivingLFront(String drivingLFront) {
        this.drivingLFront = drivingLFront;
    }

    public String getDrivingLBack() {
        return drivingLBack;
    }

    public void setDrivingLBack(String drivingLBack) {
        this.drivingLBack = drivingLBack;
    }

    public UserList(String username, String firstName, String lastName, String phone, String email, String address, String dob, String gender, String password, String profileImg, String drivingLFront, String drivingLBack) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.password = password;
        this.profileImg = profileImg;
        this.drivingLFront = drivingLFront;
        this.drivingLBack = drivingLBack;
    }
}
