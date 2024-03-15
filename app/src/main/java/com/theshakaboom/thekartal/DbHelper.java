package com.theshakaboom.thekartal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

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
        db.execSQL("create table owner(_id INTEGER primary key, user_id INTEGER, vehicle_id INTEGER, foreign key(user_id) references users(username), foreign key(vehicle_id) references vehicle(_reg_no))");
        db.execSQL("create table vehicle(_reg_no TEXT primary key, type TEXT, make TEXT, model TEXT, year INTEGER, license_pic TEXT, status TEXT, owner_id INTEGER, foreign key(owner_id) references owner(_id))");
        db.execSQL("create table bookings(booking_id INTEGER primary key, start_date TEXT, return_date TEXT, vehicle_id INTEGER, user_id INTEGER, foreign key(vehicle_id) references vehicle(_reg_no), foreign key(user_id) references users(username))");
        db.execSQL("create table rent_feedback(feedback_id INTEGER primary key, details TEXT, user_id INTEGER, vehicle_id INTEGER, booking_id INTEGER, foreign key(user_id) references users(username), foreign key(vehicle_id) references vehicle(_reg_no), foreign key(booking_id) references bookings(booking_id))");
        db.execSQL("create table passenger_feedback(feedback_id INTEGER primary key, details TEXT, owner_id INTEGER, user_id INTEGER, booking_id INTEGER, foreign key(owner_id) references owner(_id), foreign key(user_id) references users(username), foreign key(booking_id) references bookings(booking_id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists tempusers");
        db.execSQL("drop table if exists login");
        db.execSQL("drop table if exists owner");
        db.execSQL("drop table if exists vehicle");
        db.execSQL("drop table if exists bookings");
        db.execSQL("drop table if exists rent_feedback");
        db.execSQL("drop table if exists passenger_feedback");
    }

    public boolean registerNewUser(String username, String FName, String LName,String phone, String email,  String address, String gender, String password, String profileimg, String drivinglfront, String drivinglback){
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
    public boolean addOwner(int userId, int vehicleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("vehicle_id", vehicleId);
        long result = db.insert("owner", null, contentValues);
        return result != -1;
    }
    public boolean mainLogin(String username, String password) {
        boolean result = false;

        try (SQLiteDatabase db = this.getWritableDatabase();
             Cursor cursorUser = db.rawQuery("SELECT * FROM  users WHERE username = ? AND password = ?", new String[]{username, password});
             Cursor cursorFreelancer = db.rawQuery("SELECT * FROM freelancer WHERE name = ? AND password = ?", new String[]{username, password})) {

            if (cursorUser.moveToFirst() && cursorUser.getCount() > 0) {
                int userUsernameIndex = cursorUser.getColumnIndex("username");

                if (userUsernameIndex >= 0) {
                    String userUsername = cursorUser.getString(userUsernameIndex);

                    if (loginNewUser(userUsername, 0)) {
                        result = true;
                    }
                }
            } else if (cursorFreelancer.moveToFirst() && cursorFreelancer.getCount() > 0) {
                int freelancerUsernameIndex = cursorFreelancer.getColumnIndex("name");

                if (freelancerUsernameIndex >= 0) {
                    String freelancerUsername = cursorFreelancer.getString(freelancerUsernameIndex);

                    if (loginNewUser(freelancerUsername, 1)) {
                        result = true;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
