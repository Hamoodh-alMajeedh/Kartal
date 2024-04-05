package com.theshakaboom.thekartal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityVehicleDetails extends AppCompatActivity {

    TextView headText, modelMake, registerNumber, type, year, transmission, fuel, owner, allFeedback, rentStart, rentEnd, totalFair, discount;
    Button bookNow;
    ImageView imgFront, imgBack, imRight, imgLeft;
    DbHelper dbHelper;
    Calendar startDateCalendar, endDateCalendar;
    String finalStartDate,finalEndDate, HeadText, Model, Make, RegisterNumber, Type, Year, Transmission, Fuel, Owner, ImgFront, ImgBack, ImRight, ImgLeft;
    int daysBetween, payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vehicle_details);

        Intent intent = getIntent();
        String VehicleID = intent.getStringExtra("ID");
        //String VehicleID ="abc1234";

        headText = findViewById(R.id.TxtvVehicleDetailsHeadText);
        modelMake = findViewById(R.id.TxtvVehicleDetailsModelMake);
        registerNumber = findViewById(R.id.TxtvVehicleDetailsRegisterNumber);
        type = findViewById(R.id.TxtvVehicleDetailsType);
        year = findViewById(R.id.TxtvVehicleDetailsYear);
        transmission = findViewById(R.id.TxtvVehicleDetailsTransmission);
        fuel = findViewById(R.id.TxtvVehicleDetailsFuel);
        owner = findViewById(R.id.TxtvVehicleDetailsOwner);

        allFeedback = findViewById(R.id.TxtvVehicleDetailsSeeAllFeedback);
        rentStart = findViewById(R.id.TxtvVehicleDetailsRentStart);
        rentEnd = findViewById(R.id.TxtvVehicleDetailsRentEnd);
        totalFair = findViewById(R.id.TxtvVehicleDetailsRentTotalFair);
        discount = findViewById(R.id.TxtvVehicleDetailsRentDiscount);

        bookNow = findViewById(R.id.BtnVehicleDetailsBookNow);

        imgFront = findViewById(R.id.ImgVehicleDetailsFront);
        imgBack = findViewById(R.id.ImgVehicleDetailsBack);
        imRight = findViewById(R.id.ImgVehicleDetailsRight);
        imgLeft = findViewById(R.id.ImgVehicleDetailsLeft);

        startDateCalendar = Calendar.getInstance();
        endDateCalendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        finalStartDate = dateFormat.format(startDateCalendar.getTime());

        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        finalEndDate = dateFormate.format(endDateCalendar.getTime());

        rentStart.setText(finalStartDate);
        rentEnd.setText(finalEndDate);

        dbHelper = new DbHelper(this);

        String Username = dbHelper.getUsername();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from vehicle where reg_no = ?", new String[]{VehicleID});
        cursor.moveToFirst();

        HeadText = cursor.getString(0);
        Model = cursor.getString(3);
        RegisterNumber = cursor.getString(0);
        Type = cursor.getString(1);
        Year = cursor.getString(4);
        Transmission = cursor.getString(8);
        Fuel = cursor.getString(9);
        Make = cursor.getString(2);
        Owner = cursor.getString(7);

        ImgFront = cursor.getString(10);
        ImgBack = cursor.getString(11);
        ImRight = cursor.getString(12);
        ImgLeft = cursor.getString(13);

        headText.setText(HeadText);
        registerNumber.setText(RegisterNumber);
        type.setText(Type);
        year.setText(Year);
        transmission.setText(Transmission);
        fuel.setText(Fuel);
        modelMake.setText(Make + " " + Model);
        owner.setText(Owner);


        if (ImgFront != null) {
            File imageFiles = new File(ImgFront);
            if (imageFiles.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.getAbsolutePath());
                imgFront.setImageBitmap(bitmap);
            }
        }
        if (ImgBack != null) {
            File imageFiles = new File(ImgBack);
            if (imageFiles.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.getAbsolutePath());
                imgBack.setImageBitmap(bitmap);
            }
        }
        if (ImRight != null) {
            File imageFiles = new File(ImRight);
            if (imageFiles.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.getAbsolutePath());
                imRight.setImageBitmap(bitmap);
            }
        }
        if (ImgLeft != null) {
            File imageFiles = new File(ImgLeft);
            if (imageFiles.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.getAbsolutePath());
                imgLeft.setImageBitmap(bitmap);
            }
        }
        cursor.close();
        dbHelper.close();

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeABooking();
            }
        });

        rentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog();
            }
        });
        rentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog();
            }
        });

    }

    private void makeABooking() {

        if (finalStartDate.isEmpty()||finalEndDate.isEmpty()){
            Toast.makeText(ActivityVehicleDetails.this,"Please Select start and end date !  ",Toast.LENGTH_LONG).show();
        } else {
            boolean register = dbHelper.addBooking(finalStartDate,finalEndDate, RegisterNumber,Owner, String.valueOf(payment),"Completed");
            if (register)
                Toast.makeText(ActivityVehicleDetails.this,"Booking placed Successfully ! ",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ActivityVehicleDetails.this,"Booking placed Failed ! ",Toast.LENGTH_LONG).show();
        }

    }

    private void showStartDatePickerDialog() {
        DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDateCalendar.set(Calendar.YEAR, year);
                startDateCalendar.set(Calendar.MONTH, monthOfYear);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                finalStartDate = dateFormat.format(startDateCalendar.getTime());
                rentStart = findViewById(R.id.TxtvVehicleDetailsRentStart);
                rentStart.setText(finalStartDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                startDateListener,
                startDateCalendar.get(Calendar.YEAR),
                startDateCalendar.get(Calendar.MONTH),
                startDateCalendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.setTitle("Select Start Date");
        datePickerDialog.show();
        return;
    }

    private void showEndDatePickerDialog() {
        DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, monthOfYear);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Format the start date and end date as needed
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                finalEndDate = dateFormat.format(endDateCalendar.getTime());
                rentEnd = findViewById(R.id.TxtvVehicleDetailsRentEnd);
                rentEnd.setText(finalEndDate);
                calculateDaysBetween();

            }
        };

        DatePickerDialog endDatePickerDialog = new DatePickerDialog(
                this,
                endDateListener,
                endDateCalendar.get(Calendar.YEAR),
                endDateCalendar.get(Calendar.MONTH),
                endDateCalendar.get(Calendar.DAY_OF_MONTH)
        );

        endDatePickerDialog.setTitle("Select End Date");
        endDatePickerDialog.show();
    }

    private void calculateDaysBetween() {
        // Calculate the difference in days
        daysBetween = (int)(startDateCalendar.getTimeInMillis() - endDateCalendar.getTimeInMillis()) / (24 * 60 * 60 * 1000);
        payment = 900 * daysBetween;
        totalFair.setText(payment + ".00 LKR");
    }

}