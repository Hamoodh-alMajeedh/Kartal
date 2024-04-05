package com.theshakaboom.thekartal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.Objects;

public class ActivityDetailedActivity extends AppCompatActivity {


    TextView textvActivityDetailedStatusTitle;
    ImageView imgActivityDetailedVehicleimg;
    TextView textActivityDetailedVehicleNumber;
    TextView textActivityDetailedVehicleType;
    TextView textActivityDetailedVehicleModel;
    TextView textActivityDetailedVehicleMake;
    TextView textActivityDetailedVehicleTransmission;
    TextView textActivityDetailedVehicleFuel;
    TextView textActivityDetailedVehicleOwner;
    TextView txtvActivityDetailedRentDate;
    TextView txtvActivityDetailedCancelDate;
    TextView txtvActivityDetailedDistence;
    TextView txtvActivityDetailedFair;
    CardView cardActivityDetailedCancelFeedback;
    TextView textCardActivityCancelTitle;
    TextView textCardActivityCancelDescription;
    RatingBar ratingBar;
    Button btnActivityDetailedCancel;
    Button btnActivityDetailedStartRide;
    Button btnActivityDetailedEndRide;
    Button btnActivityDetailedFeedback;
    FloatingActionButton btnActivityDetailedFeedbackButton;
    View topBar;
    CardView cardRatings;
    DbHelper dbHelper;
    String bookId = "2", payment = "2", start_km, username, vehicle_id, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);

        dbHelper = new DbHelper(this);

        Intent intent = getIntent();

        if (intent.hasExtra("bookId")) {
            Log.d("Intent data", "positive");
            bookId = intent.getStringExtra("bookId");
        }
        else {
            Log.d("Intent data", "Negative");
        }

        // Initialize the views
        textvActivityDetailedStatusTitle = findViewById(R.id.TextvActivityDetailedStatusTitle);
        imgActivityDetailedVehicleimg = findViewById(R.id.ImgActivityDetailedVehicleimg);
        textActivityDetailedVehicleNumber = findViewById(R.id.TextActivityDetailedVehicleNumber);
        textActivityDetailedVehicleType = findViewById(R.id.TextActivityDetailedVehicleType);
        textActivityDetailedVehicleModel = findViewById(R.id.TextActivityDetailedVehicleModel);
        textActivityDetailedVehicleMake = findViewById(R.id.TextActivityDetailedVehicleMake);
        textActivityDetailedVehicleTransmission = findViewById(R.id.TextActivityDetailedVehicleTransmission);
        textActivityDetailedVehicleFuel = findViewById(R.id.TextActivityDetailedVehicleFuel);
        textActivityDetailedVehicleOwner = findViewById(R.id.TextActivityDetailedVehicleOwner);
        txtvActivityDetailedRentDate = findViewById(R.id.TxtvActivityDetailedRentDate);
        txtvActivityDetailedCancelDate = findViewById(R.id.TxtvActivityDetailedCancelDate);
        txtvActivityDetailedDistence = findViewById(R.id.TxtvActivityDetailedDistence);
        txtvActivityDetailedFair = findViewById(R.id.TxtvActivityDetailedFair);
        textCardActivityCancelTitle = findViewById(R.id.TextCardActivityCancelTitle);
        textCardActivityCancelDescription = findViewById(R.id.TextCardActivityCancelDescription);

        cardActivityDetailedCancelFeedback = findViewById(R.id.CardActivityDetailedCancelFeedback);
        cardRatings = findViewById(R.id.CardRatings);

        topBar = findViewById(R.id.top_bar);

        ratingBar = findViewById(R.id.ratingBar);

        btnActivityDetailedCancel = findViewById(R.id.BtnActivityDetailedCancel);
        btnActivityDetailedStartRide = findViewById(R.id.BtnActivityDetailedStartRide);
        btnActivityDetailedEndRide = findViewById(R.id.BtnActivityDetailedEndRide);
        btnActivityDetailedFeedback = findViewById(R.id.BtnActivityDetailedFeedback);

        btnActivityDetailedFeedbackButton = findViewById(R.id.BtnActivityDetailedFeedbackButton);

        username = dbHelper.getUsername();


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from bookings where booking_id = ?", new String[]{bookId});
        cursor.moveToFirst();

        start_km = cursor.getString(1);
        payment = cursor.getString(3);
        String status = cursor.getString(4);
        String start_date = cursor.getString(5);
        vehicle_id = cursor.getString(7);
        user_id  = cursor.getString(8);

        cursor.close();


        cursor = db.rawQuery("select * from vehicle where reg_no = ?", new String[]{vehicle_id});
        cursor.moveToFirst();

        String type = cursor.getString(1);
        String make = cursor.getString(2);
        String model = cursor.getString(3);
        String owner_id = cursor.getString(7);
        String transmission = cursor.getString(8);
        String fuel = cursor.getString(9);
        String car_image_front = cursor.getString(10);


        cursor.close();

        int ratings = 0;

        cursor = db.rawQuery("select * from rent_feedback where vehicle_id = ?", new String[]{vehicle_id});
        cursor.moveToFirst();
        try{
            while (cursor.moveToNext()){
            ratings = ratings + cursor.getInt(5);
            }
        }finally {
            cursor.close();
        }

        dbHelper.close();

        if (Objects.equals(status, "Ongoing")){
            topBar.setBackgroundColor(0xFFDDAD1B);
            btnActivityDetailedStartRide.setVisibility(View.GONE);
            btnActivityDetailedFeedback.setVisibility(View.GONE);

        } else if (Objects.equals(status, "Cancelled")) {
            topBar.setBackgroundColor(0xFFFF5555);
            btnActivityDetailedStartRide.setVisibility(View.GONE);
            btnActivityDetailedEndRide.setVisibility(View.GONE);
            btnActivityDetailedCancel.setVisibility(View.GONE);

        } else if (Objects.equals(status, "Completed")) {
            topBar.setBackgroundColor(0xFF4CAF50);
            btnActivityDetailedStartRide.setVisibility(View.GONE);
            btnActivityDetailedEndRide.setVisibility(View.GONE);
            btnActivityDetailedCancel.setVisibility(View.GONE);
            btnActivityDetailedFeedback.setVisibility(View.GONE);

        } else if (Objects.equals(status, "in Review")) {
            topBar.setBackgroundColor(0xFF0899FF);
            btnActivityDetailedEndRide.setVisibility(View.GONE);
            btnActivityDetailedFeedback.setVisibility(View.GONE);
        }
        // Set the new values for the views
        textvActivityDetailedStatusTitle.setText(status);

        textActivityDetailedVehicleNumber.setText(vehicle_id);
        textActivityDetailedVehicleType.setText(type);
        textActivityDetailedVehicleModel.setText(model);
        textActivityDetailedVehicleMake.setText(make);
        textActivityDetailedVehicleTransmission.setText(transmission);
        textActivityDetailedVehicleFuel.setText(fuel);
        textActivityDetailedVehicleOwner.setText(owner_id);
        txtvActivityDetailedRentDate.setText(start_date);
        txtvActivityDetailedCancelDate.setText("01/05/2023");
        txtvActivityDetailedDistence.setText("500 km");
        txtvActivityDetailedFair.setText(payment + " Rs");

        textCardActivityCancelTitle.setText("Cancel Reason");
        textCardActivityCancelDescription.setText("The car was not clean.");
        ratingBar.setRating(ratings);

        if (car_image_front != null) {
            File imageFiles = new File(car_image_front);
            if (imageFiles.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.getAbsolutePath());
                imgActivityDetailedVehicleimg.setImageBitmap(bitmap);
            }
        }


        // Set click listener for the cancel button
        btnActivityDetailedCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel button click
                showMessageCancelRide();
            }
        });
        btnActivityDetailedStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessageStartRide();
            }
        });
        btnActivityDetailedEndRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessageEndRide();
            }
        });
        // Set click listener for the feedback button
        btnActivityDetailedFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessageAddFeedback();
            }
        });

        cardRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDetailedActivity.this, ActivityVehicleFeedback.class);
                intent.putExtra("vehicleId", vehicle_id);
                startActivity(intent);
            }
        });
    }

    private void showMessageStartRide() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_start_ride, null);
        builder.setView(dialogView);

        final Button ok = dialogView.findViewById(R.id.ok);
        final Button cancel = dialogView.findViewById(R.id.cancel);
        final EditText StartKm = dialogView.findViewById(R.id.EdtCancelStartKM);

        AlertDialog dialog = builder.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sStartKM = StartKm.getText().toString();
                if (sStartKM.isEmpty()){
                    Toast.makeText(ActivityDetailedActivity.this,"Cancel Reason (*Required) ! ",Toast.LENGTH_LONG).show();
                    return;
                }
                dbHelper.startRent(Integer.parseInt(bookId),sStartKM,"Ongoing");
                Toast.makeText(ActivityDetailedActivity.this,"Ride Started ! ",Toast.LENGTH_LONG).show();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void showMessageEndRide() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_end_ride, null);
        builder.setView(dialogView);

        final Button ok = dialogView.findViewById(R.id.ok);
        final Button cancel = dialogView.findViewById(R.id.cancel);
        final EditText EndKm = dialogView.findViewById(R.id.EdtCancelEndKM);

        AlertDialog dialog = builder.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int Payment = 0;
                Payment = Integer.parseInt(payment);
                int endKm = Integer.parseInt(EndKm.getText().toString());
                if (endKm < 0){
                    Toast.makeText(ActivityDetailedActivity.this,"End KM (*Required) ! ",Toast.LENGTH_LONG).show();
                    return;
                }
                endKm = (endKm - Integer.parseInt(start_km));
                if (endKm > 200){
                    Payment = (endKm - 200) * 900;
                }

                dbHelper.endRent(Integer.parseInt(bookId),"Completed", String.valueOf(Payment));
                Toast.makeText(ActivityDetailedActivity.this,"Rent Completed ! ",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ActivityDetailedActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void showMessageCancelRide() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_cancel_ride, null);
        builder.setView(dialogView);

        final EditText CancelDetails = dialogView.findViewById(R.id.EdtCancelDetails);
        final Button cancel = dialogView.findViewById(R.id.cancel);


        AlertDialog dialog = builder.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String cancelDetails = CancelDetails.getText().toString();
                if (cancelDetails.isEmpty()){
                    Toast.makeText(ActivityDetailedActivity.this,"Cancel Reason (*Required) ! ",Toast.LENGTH_LONG).show();
                    return;
                }
                dbHelper.cancelRent(Integer.parseInt(bookId),cancelDetails,"Cancelled");
                Toast.makeText(ActivityDetailedActivity.this,"Ride Cancelled Successfully ! ",Toast.LENGTH_LONG).show();

            }
        });
    }
    private void showMessageAddFeedback() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_feedback_vehicle, null);
        builder.setView(dialogView);

        final Button ok = dialogView.findViewById(R.id.ok);
        final Button cancel = dialogView.findViewById(R.id.cancel);
        final EditText details = dialogView.findViewById(R.id.EdtVehicleRatingDetails);
        final RatingBar ratings = dialogView.findViewById(R.id.RatingProfileRatingbsr);

        AlertDialog dialog = builder.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Details = details.getText().toString();
                if (Details.isEmpty()){
                    Toast.makeText(ActivityDetailedActivity.this,"Cancel Reason (*Required) ! ",Toast.LENGTH_LONG).show();
                    return;
                }
                float Rating = ratings.getRating();
                if (username.equals(user_id)) {
                    dbHelper.addFeedback(Details, username, vehicle_id, bookId, (int) Rating);
                    Toast.makeText(ActivityDetailedActivity.this, "Ride Started ! ", Toast.LENGTH_LONG).show();
                } else {
                    dbHelper.addUserFeedback(Details, username, user_id, vehicle_id, bookId, (int) Rating);
                    Toast.makeText(ActivityDetailedActivity.this, "Ride Started ! ", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}