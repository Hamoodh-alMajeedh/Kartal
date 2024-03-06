package com.theshakaboom.thekartal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityTest extends AppCompatActivity {

    Button Login, UserRegister, OtpVarification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Login = findViewById(R.id.BtnTestLogin);
        UserRegister = findViewById(R.id.BtnTestUserRegister);
        OtpVarification = findViewById(R.id.BtnTestOtpVarification);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //replaceActivity(ActivityLogin.class);
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

    }
    private void replaceActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }
}