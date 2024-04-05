package com.theshakaboom.thekartal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        int loginState = dbHelper.loginState();

        switch (loginState) {
            case 0: //base user
                replaceActivity(ActivityHome.class);
                break;

            case 1: // freelancer
                replaceActivity(ActivityAdmin.class);
                break;

            default:
                replaceActivity(ActivityLogin.class);
                break;
        }
    }
    private void replaceActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
        finish();  // Optional: finish the current activity if you want to navigate back
    }
}