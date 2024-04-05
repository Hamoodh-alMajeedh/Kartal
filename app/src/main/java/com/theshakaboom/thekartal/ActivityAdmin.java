package com.theshakaboom.thekartal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityAdmin extends AppCompatActivity {
    CardView cardActivityAdminUsers, cardActivityAdminBike, cardActivityAdminTuktuk, cardActivityAdminCar, cardActivityAdminRentals, cardActivityAdminVan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        // Initialize CardView variables
        cardActivityAdminUsers = findViewById(R.id.cardActivityAdminUsers);
        cardActivityAdminBike = findViewById(R.id.cardActivityAdminBike);
        cardActivityAdminTuktuk = findViewById(R.id.cardActivityAdminTuktuk);
        cardActivityAdminCar = findViewById(R.id.cardActivityAdminCar);
        cardActivityAdminRentals = findViewById(R.id.cardActivityAdminRentals);
        cardActivityAdminVan = findViewById(R.id.cardActivityAdminVan);


        // Set OnClickListener for each CardView
        cardActivityAdminUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for cardActivityAdminUsers
                Intent intent = new Intent(ActivityAdmin.this, ActivityAllUsers.class);
                startActivity(intent);
            }
        });

        cardActivityAdminBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for cardActivityAdminBike
                Intent intent = new Intent(ActivityAdmin.this, ActivityVehicle.class);
                intent.putExtra("Type","Bike");
                startActivity(intent);
            }
        });

        cardActivityAdminTuktuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for cardActivityAdminTuktuk
                Intent intent = new Intent(ActivityAdmin.this, ActivityVehicle.class);
                intent.putExtra("Type","Tuktuk");
                startActivity(intent);
            }
        });

        cardActivityAdminCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for cardActivityAdminCar
                Intent intent = new Intent(ActivityAdmin.this, ActivityVehicle.class);
                intent.putExtra("Type","Car");
                startActivity(intent);
            }
        });
        cardActivityAdminVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for cardActivityAdminCar
                Intent intent = new Intent(ActivityAdmin.this, ActivityVehicle.class);
                intent.putExtra("Type","Van");
                startActivity(intent);
            }
        });
        cardActivityAdminRentals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for cardActivityAdminRentals
                Intent intent = new Intent(ActivityAdmin.this, ActivityAllRentals.class);
                intent.putExtra("Type","Van");
                startActivity(intent);
            }
        });
    }
}