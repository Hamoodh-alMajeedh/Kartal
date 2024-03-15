package com.theshakaboom.thekartal;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityUserRegister extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private RadioGroup genderRadioGroup;
    private EditText dobEditText;
    private ImageView profileImageView;
    private ImageView drivingLFrontImageView;
    private ImageView drivingLBackImageView;
    private Button registerButton;
    private LinearLayout DateOfBirth;
    DbHelper dbHelper;
    Calendar DobDateCalendar;
    String DobDate, imagePath, SprofileImageView, SdrivingLFrontImageView, SdrivingLBackImageView, imageRename = "null.jpg", emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Bitmap selectedImageBitmap;
    byte[] imageInByte;
    ImageView imageView;
    private ActivityResultLauncher<Intent> getContentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        firstNameEditText = findViewById(R.id.EdtUserRegisterFirstname);
        lastNameEditText = findViewById(R.id.EdtUserRegisterLastname);
        phoneEditText = findViewById(R.id.EdtUserRegisterPhone);
        emailEditText = findViewById(R.id.EdtUserRegisterEmail);
        addressEditText = findViewById(R.id.EdtUserRegisterAddress);
        usernameEditText = findViewById(R.id.EdtUserRegisterUsername);
        passwordEditText = findViewById(R.id.EdtUserRegisterPassword);
        confirmPasswordEditText = findViewById(R.id.EdtUserRegisterConfirmpassword);
        genderRadioGroup = findViewById(R.id.radioGroup);
        profileImageView = findViewById(R.id.imgUserRegisterUserprofile);
        imageView = findViewById(R.id.imgUserRegisterUserprofile);
        drivingLFrontImageView = findViewById(R.id.imgUserRegisterDrivingLFront);
        drivingLBackImageView = findViewById(R.id.imgUserRegisterDrivingLBack);
        registerButton = findViewById(R.id.BtnUserRegisterRegister);
        DateOfBirth = findViewById(R.id.LinierUserRegisterDob);

        dbHelper = new DbHelper(this);
        DobDateCalendar = Calendar.getInstance();

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDobDatePickerDialog();
            }
        });


        // Set onClickListener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input and register the user
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                String gender = "";
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.RadioUserRegisterMale) {
                    gender = "Male";
                } else if (selectedId == R.id.RadioUserRegisterFemale) {
                    gender = "Female";
                }

                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty() ||
                        address.isEmpty() || username.isEmpty() || password.isEmpty() ||
                        confirmPassword.isEmpty() || gender.isEmpty() || DobDate.isEmpty()){
                    Toast.makeText(ActivityUserRegister.this,"Please Fill All the Fields ! ",Toast.LENGTH_LONG).show();
                } else {
                    if (password.equals(confirmPassword)){
                       /* if (dbHelper.checkUsername(username)){
                            Toast.makeText(ActivityUserRegister.this,"User Already Exist Try another Name !",Toast.LENGTH_LONG).show();
                            return;

                        }
                        */
                        if (!email.matches(emailPattern)){
                            Toast.makeText(ActivityUserRegister.this,"Enter valid Email !",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (!android.util.Patterns.PHONE.matcher(phone).matches()){
                            Toast.makeText(ActivityUserRegister.this,"Enter valid Phone Number  !",Toast.LENGTH_LONG).show();
                            return;
                        }

                        boolean registerSuccess = dbHelper.registerNewUser(username, firstName, lastName, phone, email, address, gender, password, SprofileImageView, SdrivingLFrontImageView, SdrivingLBackImageView);
                        if (registerSuccess)
                            Toast.makeText(ActivityUserRegister.this,"User Register Successfully ",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ActivityUserRegister.this,"User Register Failed ",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(ActivityUserRegister.this,"Password and confirm password didn't match",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean pick = true;
                imageRename = "profile_image.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.imgUserRegisterUserprofile);
                        pickImage();
                         SprofileImageView = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.imgUserRegisterUserprofile);
                        pickImage();
                        SprofileImageView = imagePath;
                    }
                }
            }
        });
        drivingLFrontImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean pick = true;
                imageRename = "driving_l_front_image.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.imgUserRegisterDrivingLFront);
                        pickImage();
                        SdrivingLFrontImageView = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.imgUserRegisterDrivingLFront);
                        pickImage();
                        SdrivingLFrontImageView = imagePath;
                    }
                }
            }
        });

        drivingLBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean pick = true;
                imageRename = "driving_l_back_image.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.imgUserRegisterDrivingLBack);
                        pickImage();
                        SdrivingLBackImageView = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.imgUserRegisterDrivingLBack);
                        pickImage();
                        SdrivingLBackImageView = imagePath;
                    }
                }
            }
        });
    }
    private void pickImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) { Uri selectedImageUri = data.getData();
                        try {
                            selectedImageBitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImageUri);
                            imagePath = saveImageToInternalStorage(selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(selectedImageBitmap);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        imageInByte = baos.toByteArray();
                    }
                }
            });

    private String saveImageToInternalStorage(Bitmap bitmap) {
        File imageFile = new File(getFilesDir(), imageRename);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            return imageFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void showDobDatePickerDialog() {
        DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DobDateCalendar.set(Calendar.YEAR, year);
                DobDateCalendar.set(Calendar.MONTH, monthOfYear);
                DobDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Calculate the number of days between the start and end dates


                // Format the start date and end date as needed
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                DobDate = dateFormat.format(DobDateCalendar.getTime());
                Log.d("Date picker", DobDate);
                // Use daysBetween, finalStartDate, and finalEndDate as needed
            }
        };

        DatePickerDialog DatePickerDialog = new DatePickerDialog(
                this,
                endDateListener,
                DobDateCalendar.get(Calendar.YEAR),
                DobDateCalendar.get(Calendar.MONTH),
                DobDateCalendar.get(Calendar.DAY_OF_MONTH)
        );

        DatePickerDialog.setTitle("Select Date of Birth");
        DatePickerDialog.show();
    }

    private void requestStoragePermision() {
        requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCamaraPermission() {
        requestPermissions(new String[] {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkCamaraPermission() {

        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED;
    }

}