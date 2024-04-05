package com.theshakaboom.thekartal;

import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class ActivityMyProfile extends AppCompatActivity {

    TextView username, email, phone, firstName, lastName, dob, address, gender;
    ImageView profilePic;
    String Username;
    DbHelper dbHelper;
    CardView update, delete, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_profile);

        dbHelper = new DbHelper(this);

        Intent intent = getIntent();

        if (intent.hasExtra("username")) {
            Log.d("Intent data", "positive");
            Username = intent.getStringExtra("username");
        }
        else {
            Log.d("Intent data", "Negative");
            Username = dbHelper.getUsername();
        }

        username = findViewById(R.id.TxtProfileUsername);
        email = findViewById(R.id.TxtProfileEmail);
        phone = findViewById(R.id.TxtProfilePhone);
        firstName = findViewById(R.id.TxtProfileFirstName);
        lastName = findViewById(R.id.TxtProfileLastName);
        dob = findViewById(R.id.TxtProfileDob);
        address = findViewById(R.id.TxtProfileAddress);
        gender = findViewById(R.id.TxtProfileGender);

        update = findViewById(R.id.CardProfileAccount);
        delete = findViewById(R.id.CardProfileDelete);
        logout = findViewById(R.id.CardProfileLogout);



        profilePic = findViewById(R.id.ImgProfileProfile);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from users where username = ?", new String[]{Username});
        cursor.moveToFirst();

        String FirstName = cursor.getString(1);
        String LastName = cursor.getString(2);
        String Phone = cursor.getString(3);
        String Email = cursor.getString(4);
        String Address = cursor.getString(5);
        String Dob = cursor.getString(6);
        String Gender = cursor.getString(7);
        String profileImg = cursor.getString(9);

        username.setText(Username);
        firstName.setText(FirstName);
        lastName.setText(LastName);
        phone.setText(Phone);
        email.setText(Email);
        address.setText(Address);
        dob.setText(Dob);
        gender.setText(Gender);

        if (profileImg != null) {
            File imageFiles = new File(profileImg);
            if (imageFiles.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.getAbsolutePath());
                profilePic.setImageBitmap(bitmap);
            }
        }
        cursor.close();
        dbHelper.close();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMyProfile.this, ActivityUserRegister.class);
                intent.putExtra("username", Username);
                startActivity(intent);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbHelper.deleteUser(Username);
                showMessageDelete();

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessageLogout();

            }
        });
    }
    private void showMessageLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.message_logout, null);
        builder.setView(dialogView);

        final Button ok = dialogView.findViewById(R.id.ok);
        final Button cancel = dialogView.findViewById(R.id.cancel);

        AlertDialog dialog = builder.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.Logout();
                Toast.makeText(ActivityMyProfile.this,"Logged out ! ",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ActivityMyProfile.this, MainActivity.class);
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
    private void showMessageDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_layout, null);
        builder.setView(dialogView);

        final Button ok = dialogView.findViewById(R.id.ok);
        final Button cancel = dialogView.findViewById(R.id.cancel);

        AlertDialog dialog = builder.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteUser(Username);
                if (dbHelper.loginState() == 0){
                    dbHelper.Logout();
                }
                Toast.makeText(ActivityMyProfile.this,"User Deleted ! ",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ActivityMyProfile.this, MainActivity.class);
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
}