package com.theshakaboom.thekartal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class ActivityLogin extends AppCompatActivity {
    Button Login;
    EditText Username,Password;
    String username, password;
    DbHelper db;
    TextView Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Login = findViewById(R.id.BtnLoginLogin);
        Register= findViewById(R.id.TxvLoginRegister);
        Username = findViewById(R.id.EdtLoginUsername);
        Password =findViewById(R.id.EdtLoginPassword);
        db = new DbHelper(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = Username.getText().toString();
                password = Password.getText().toString();

                Log.d("Activity Login Username",username);
                Log.d("Activity Login Password",password);
                if (!username.isEmpty() || password.isEmpty()){
                    if (!db.mainLogin(username, password)){
                        Toast.makeText(ActivityLogin.this,"Username And Password didn't match",Toast.LENGTH_LONG).show();
                        Username.setText("");
                        Password.setText("");
                    } else {
                        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(ActivityLogin.this,"Please fill all the fields",Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}