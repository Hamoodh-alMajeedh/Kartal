package com.theshakaboom.thekartal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DbName = "kartaldata.db";

    public DbHelper(@Nullable Context context) {
        super(context, DbName, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username TEXT primary key, firstname TEXT, lastname TEXT, phone TEXT, email TEXT, address TEXT, dob date, gender text, password TEXT, profileimg TEXT, drivinglfront TEXT, drivinglback TEXT)");
        db.execSQL("create table tempusers(status TEXT, username TEXT primary key, firstname TEXT, lastname TEXT, phone TEXT, email TEXT, address TEXT, dob date, gender text, password TEXT, profileimg TEXT, drivinglfront TEXT, drivinglback TEXT)");
        db.execSQL("create table login(username TEXT primary key, userlevel INTEGER)");
        db.execSQL("create table vehicle(reg_no TEXT primary key, type TEXT, make TEXT, model TEXT, year INTEGER, license_pic TEXT, status TEXT, username TEXT, transmission TEXT, fuel TEXT, car_image_front TEXT, car_image_back TEXT, car_image_right TEXT, car_image_left TEXT)");
        db.execSQL("create table bookings(booking_id INTEGER primary key AUTOINCREMENT, start_km TEXT, cancel_note TEXT, payment TEXT, status TEXT, start_date TEXT, return_date TEXT, vehicle_id TEXT, user_id TEXT, foreign key(vehicle_id) references vehicle(reg_no), foreign key(user_id) references users(username))");
        db.execSQL("create table rent_feedback(feedback_id INTEGER primary key, details TEXT, user_id TEXT, vehicle_id TEXT, booking_id TEXT, rating INTEGER, foreign key(user_id) references users(username), foreign key(vehicle_id) references vehicle(reg_no), foreign key(booking_id) references bookings(booking_id))");
        db.execSQL("create table passenger_feedback(feedback_id INTEGER primary key, details TEXT, owner_id TEXT, user_id TEXT, booking_id TEXT, rating INTEGER, foreign key(owner_id) references owner(_id), foreign key(user_id) references users(username), foreign key(booking_id) references bookings(booking_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists tempusers");
        db.execSQL("drop table if exists login");
        db.execSQL("drop table if exists vehicle");
        db.execSQL("drop table if exists bookings");
        db.execSQL("drop table if exists rent_feedback");
        db.execSQL("drop table if exists passenger_feedback");
    }

    public boolean registerNewUser(String username, String FName, String LName,String phone, String email,  String address, String date, String gender, String password, String profileimg, String drivinglfront, String drivinglback){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("firstname",FName);
        contentValues.put("lastname",LName);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        contentValues.put("address",address);
        contentValues.put("dob",date);
        contentValues.put("gender",gender);
        contentValues.put("password", password);
        contentValues.put("profileimg",profileimg);
        contentValues.put("drivinglfront",drivinglfront);
        contentValues.put("drivinglback",drivinglback);
        long result = db.insert("users",null,contentValues);
        db.close();
        if (result ==  -1) return false;
        else return true;
    }
    public boolean createTempUser(String status, String username, String FName, String LName,String phone, String email,  String address, String gender, String password, String profileimg, String drivinglfront, String drivinglback){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        contentValues.put("username", username);
        contentValues.put("firstname",FName);
        contentValues.put("lastname",LName);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        contentValues.put("address",address);
        contentValues.put("gender",gender);
        contentValues.put("password", password);
        contentValues.put("profileimg",profileimg);
        contentValues.put("drivinglfront",drivinglfront);
        contentValues.put("drivinglback",drivinglback);
        long result = db.insert("tempusers",null,contentValues);
        if (result ==  -1) return false;
        else return true;
    }

    public boolean updateUser(String username, String FName, String LName,String phone, String email,  String address, String gender, String password, String profileimg, String drivinglfront, String drivinglback){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("firstname",FName);
        contentValues.put("lastname",LName);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        contentValues.put("address",address);
        contentValues.put("gender",gender);
        contentValues.put("password", password);
        contentValues.put("profileimg",profileimg);
        contentValues.put("drivinglfront",drivinglfront);
        contentValues.put("drivinglback",drivinglback);
        Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[]{String.valueOf(username)});
        if (cursor.getCount() > 0) {
            long result = db.update("users", contentValues, "username = ?", new String[]{String.valueOf(username)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public boolean checkUsername(String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ?", new String[]{Username});
        if (cursor.moveToFirst() && cursor.getCount() > 0)
            return true;
        else return false;
    }
    public boolean deleteUser(String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[]{Username});
        if (cursor.getCount() > 0) {
            long result = db.delete("users","username = ?", new String[]{Username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public boolean loginNewUser(String Username,int Userlevel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",Username);
        contentValues.put("userlevel", Userlevel);
        long result = db.insert("login",null,contentValues);
        Log.d("Login new user ","table assigned");
        if (result ==  -1) return false;
        else return true;
    }

    public boolean mainLogin(String username, String password) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase();
             Cursor cursorUser = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password})) {

            if (cursorUser.moveToFirst() && cursorUser.getCount() > 0) {
                int userUsernameIndex = cursorUser.getColumnIndex("username");
                if (userUsernameIndex >= 0) {
                    String userUsername = cursorUser.getString(userUsernameIndex);
                    if (loginNewUser(userUsername, 0)) {
                        result = true;
                    }
                }
            } else if (username.equals("Admin") && password.equals("admin")) {
                if (loginNewUser(username, 1)) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean addVehicle(String regNo, String type, String make, String model, int year, String licensePic, String status, String transmission, String fuel, String car_image_front, String car_image_back, String car_image_right, String car_image_left, String username) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("reg_no",regNo);
            values.put("type", type);
            values.put("make", make);
            values.put("model", model);
            values.put("year", year);
            values.put("license_pic", licensePic);
            values.put("status", status);
            values.put("transmission", transmission);
            values.put("fuel", fuel);
            values.put("username", username);
            values.put("car_image_front", car_image_front);
            values.put("car_image_back", car_image_back);
            values.put("car_image_right", car_image_right);
            values.put("car_image_left", car_image_left);

            long newRowId = db.insert("vehicle", null, values);
            if (newRowId != -1) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public boolean updateVehicle(String regNo, String type, String make, String model, int year, String licensePic, String status, String transmission, String fuel, String car_image_front, String car_image_back, String car_image_right, String car_image_left, String username) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("type", type);
            values.put("make", make);
            values.put("model", model);
            values.put("year", year);
            values.put("license_pic", licensePic);
            values.put("status", status);
            values.put("transmission", transmission);
            values.put("fuel", fuel);
            values.put("username", username);
            values.put("car_image_front", car_image_front);
            values.put("car_image_back", car_image_back);
            values.put("car_image_right", car_image_right);
            values.put("car_image_left", car_image_left);

            int rowsAffected = db.update("vehicle", values, "reg_no = ?", new String[]{regNo});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteVehicle(String regNo) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int rowsAffected = db.delete("vehicle", "reg_no = ?", new String[]{regNo});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addBooking(String startDate, String returnDate, String vehicleRegNo, String userId, String payment, String status) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("start_date", startDate);
            values.put("return_date", returnDate);
            values.put("vehicle_id", vehicleRegNo);
            values.put("user_id", userId);
            values.put("payment", payment);
            values.put("status", status);

            long newRowId = db.insert("bookings", null, values);
            if (newRowId != -1) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean updateBooking(int bookingId, String startDate, String returnDate, String vehicleRegNo, int userId) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("start_date", startDate);
            values.put("return_date", returnDate);
            values.put("vehicle_id", vehicleRegNo);
            values.put("user_id", userId);

            int rowsAffected = db.update("bookings", values, "booking_id = ?", new String[]{String.valueOf(bookingId)});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean startRent(int bookingId, String startKM, String status) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("start_km", startKM);
            values.put("status", status);

            int rowsAffected = db.update("bookings", values, "booking_id = ?", new String[]{String.valueOf(bookingId)});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean cancelRent(int bookingId, String cancelNote, String status) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("status", status);
            values.put("cancel_note", cancelNote);

            int rowsAffected = db.update("bookings", values, "booking_id = ?", new String[]{String.valueOf(bookingId)});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean endRent(int bookingId, String status, String payment) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("status", status);
            values.put("payment", payment);

            int rowsAffected = db.update("bookings", values, "booking_id = ?", new String[]{String.valueOf(bookingId)});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean deleteBooking(int bookingId) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int rowsAffected = db.delete("bookings", "booking_id = ?", new String[]{String.valueOf(bookingId)});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addFeedback(String details, String userId, String vehicleRegNo, String bookingId, int rating) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("details", details);
            values.put("user_id", userId);
            values.put("vehicle_id", vehicleRegNo);
            values.put("booking_id", bookingId);
            values.put("rating", rating);

            long newRowId = db.insert("rent_feedback", null, values);
            if (newRowId != -1) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addUserFeedback(String details, String ownerId, String userId, String vehicleRegNo, String bookingId, int rating) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("details", details);
            values.put("owner_id", ownerId);
            values.put("user_id", userId);
            values.put("vehicle_id", vehicleRegNo);
            values.put("booking_id", bookingId);
            values.put("rating", rating);

            long newRowId = db.insert("rent_feedback", null, values);
            if (newRowId != -1) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean updateFeedback(int feedbackId, String details, String userId, String vehicleRegNo, String bookingId, int rating) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("details", details);
            values.put("user_id", userId);
            values.put("vehicle_id", vehicleRegNo);
            values.put("booking_id", bookingId);
            values.put("rating", rating);

            int rowsAffected = db.update("rent_feedback", values, "feedback_id = ?", new String[]{String.valueOf(feedbackId)});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean deleteFeedback(int feedbackId) {
        boolean result = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int rowsAffected = db.delete("rent_feedback", "feedback_id = ?", new String[]{String.valueOf(feedbackId)});
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<VehicleList> getVehicleListData(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<VehicleList> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM vehicle", null);
        try{
            while (cursor.moveToNext()){
                String reg_no = cursor.getString(0);
                String type = cursor.getString(1);
                String make = cursor.getString(2);
                String model = cursor.getString(3);
                int year = cursor.getInt(4);
                String license_pic = cursor.getString(5);
                String status = cursor.getString(6);
                String owner_id = cursor.getString(7);
                String transmission = cursor.getString(8);
                String fuel = cursor.getString(9);
                String car_image_front = cursor.getString(10);
                String car_image_back = cursor.getString(11);
                String car_image_right = cursor.getString(12);
                String car_image_left = cursor.getString(13);

                VehicleList vehicleList = new VehicleList(reg_no, type, make, model, year, license_pic, status, owner_id, transmission,fuel,car_image_front,car_image_back,car_image_right,car_image_left);
                arrayList.add(vehicleList);

            }
        }finally {
            cursor.close();
        }
        return arrayList;
    }
    public ArrayList<BookingList> getBookingListData(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<BookingList> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM bookings", null);
        try{
            while (cursor.moveToNext()){
                String booking_id = cursor.getString(0);
                String start_km = cursor.getString(1);
                String cancel_note = cursor.getString(2);
                String payment = cursor.getString(3);
                String status = cursor.getString(4);
                String start_date = cursor.getString(5);
                String return_date = cursor.getString(6);
                String vehicle_id = cursor.getString(7);
                String user_id  = cursor.getString(8);

                BookingList bookingList = new BookingList(booking_id,start_km,cancel_note,payment,status,start_date,return_date,vehicle_id,user_id);
                arrayList.add(bookingList);

            }
        }finally {
            cursor.close();
        }
        return arrayList;
    }
    public ArrayList<UserList> getUserListData(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UserList> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        try{
            while (cursor.moveToNext()){
                String username = cursor.getString(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String phone = cursor.getString(3);
                String email = cursor.getString(4);
                String address = cursor.getString(5);
                String dob = cursor.getString(6);
                String gender = cursor.getString(7);
                String password  = cursor.getString(8);
                String profileImg = cursor.getString(9);
                String drivingLFront = cursor.getString(10);
                String drivingLBack = cursor.getString(11);

                UserList userList = new UserList(username,firstName,lastName,phone,email,address,dob,gender,password,profileImg,drivingLFront,drivingLBack);
                arrayList.add(userList);

            }
        }finally {
            cursor.close();
        }
        return arrayList;
    }

    public ArrayList<PassengerFeedbackList> getPassengerFeedbackListData(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PassengerFeedbackList> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM passenger_feedback", null);
        try{
            while (cursor.moveToNext()){
                String feedbackId = cursor.getString(0);
                String details = cursor.getString(1);
                String ownerId = cursor.getString(2);
                String userId = cursor.getString(3);
                String bookingId = cursor.getString(4);
                int rating = cursor.getInt(5);

                PassengerFeedbackList passengerFeedbackList = new PassengerFeedbackList(feedbackId, details, ownerId, userId, bookingId, rating);
                arrayList.add(passengerFeedbackList);

            }
        }finally {
            cursor.close();
        }
        return arrayList;
    }
    public ArrayList<RentFeedbackList> getRentFeedbackLists(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RentFeedbackList> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM rent_feedback", null);
        try{
            while (cursor.moveToNext()){
                String feedbackId = cursor.getString(0);
                String details = cursor.getString(1);
                String userId = cursor.getString(2);
                String vehicle_id = cursor.getString(3);
                String bookingId = cursor.getString(4);
                int rating = cursor.getInt(5);

                RentFeedbackList rentFeedbackList = new RentFeedbackList(feedbackId, details, userId, vehicle_id, bookingId, rating);
                arrayList.add(rentFeedbackList);

            }
        }finally {
            cursor.close();
        }
        return arrayList;
    }

    public String getUsername() {
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        Cursor cursor = db.query("login", columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("username");
            if (columnIndex != -1) {
                result = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        db.close();
        return result;
    }

    public int loginState (){
        int result = 3;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from login",null);
        if (cursor.moveToFirst() && cursor.getCount() > 0){
            result = Integer.parseInt(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return  result;
    }

    public void Logout() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("login", null, null);
        db.close();
    }
}