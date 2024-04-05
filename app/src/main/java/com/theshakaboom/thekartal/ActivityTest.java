package com.theshakaboom.thekartal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityTest extends AppCompatActivity {

    Button Login, UserRegister, OtpVarification, Home, MyActivities, vehicleRegister, activityVehicle, vehicleDetails, admin, main, logout;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Login = findViewById(R.id.BtnTestLogin);
        UserRegister = findViewById(R.id.BtnTestUserRegister);
        OtpVarification = findViewById(R.id.BtnTestOtpVarification);
        Home  = findViewById(R.id.BtnTestHome);
        MyActivities  = findViewById(R.id.BtnTestMyActivities);
        vehicleRegister  = findViewById(R.id.BtnTestVehicleRegister);
        activityVehicle = findViewById(R.id.BtnTestVehicle);
        vehicleDetails = findViewById(R.id.BtnTestVehicleDetails);
        admin = findViewById(R.id.BtnTestVehicleAdmin);
        main = findViewById(R.id.BtnTestVehicleMain);
        logout = findViewById(R.id.BtnTestVehicleLogout);

        dbHelper = new DbHelper(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(ActivityLogin.class);
            }
        });

        UserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(ActivityUserRegister.class);
            }
        });

        OtpVarification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(ActivityOtpVarification.class);
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(ActivityHome.class);
            }
        });

        MyActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(ActivityMyActivities.class);
            }
        });

        vehicleRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(ActivityVehicleRegister.class);
            }
        });
        activityVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTest.this, ActivityVehicle.class);
                intent.putExtra("Type","All");
                startActivity(intent);
            }
        });
        vehicleDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(ActivityVehicleDetails.class);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(ActivityAdmin.class);
            }
        });


        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(MainActivity.class);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.Logout();
                Toast.makeText(ActivityTest.this,"Login details deleted",Toast.LENGTH_LONG).show();
            }
        });


    }
    private void replaceActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }
}