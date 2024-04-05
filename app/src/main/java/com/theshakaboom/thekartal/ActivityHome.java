package com.theshakaboom.thekartal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityHome extends AppCompatActivity {

    ImageView carImage, vanImage, tuktukImage, bikeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.BtmHome);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.BtmHome) {
                return true;
            } else if (item.getItemId() == R.id.BtmActivities) {
                startActivity(new Intent(getApplicationContext(), ActivityMyActivities.class));
                overridePendingTransition(R.anim.slide_in_anim, R.anim.slide_out_anim);
                finish();
                return true;
            } else if (item.getItemId() == R.id.BtmVehicle) {
                startActivity(new Intent(getApplicationContext(), ActivityMyVehicle.class));
                overridePendingTransition(R.anim.slide_in_anim, R.anim.slide_out_anim);
                finish();
                return true;
            } else if (item.getItemId() == R.id.BtmProfile) {
                startActivity(new Intent(getApplicationContext(), ActivityMyProfile.class));
                overridePendingTransition(R.anim.slide_in_anim, R.anim.slide_out_anim);
                return true;
            }
            return false;
        });

        bikeImage = findViewById(R.id.ImgHomeBike);
        carImage = findViewById(R.id.ImgHomeCar);
        vanImage = findViewById(R.id.ImgHomeVan);
        tuktukImage = findViewById(R.id.ImgHomeTuktuk);

        bikeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityVehicle.class);
                intent.putExtra("Type","Bike");
                startActivity(intent);
            }
        });
        carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityVehicle.class);
                intent.putExtra("Type","Car");
                startActivity(intent);
            }
        });
        tuktukImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityVehicle.class);
                intent.putExtra("Type","Tuktuk");
                startActivity(intent);
            }
        });
        vanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityVehicle.class);
                intent.putExtra("Type","Van");
                startActivity(intent);
            }
        });
    }
}