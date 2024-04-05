package com.theshakaboom.thekartal;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import android.widget.DatePicker;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


public class ActivityVehicleRegister extends AppCompatActivity {
    Button register;
    EditText registerNumber, model, make, year;
    ImageView vehicleFront, vehicleBack, vehicleRight, vehicleLeft, license, insurance;
    CheckBox TermsCheck;
    TextView viewTerms;
    byte[] imageInByte;
    ImageView imageView;
    String  imageRename = "null.jpg", imagePath, ivehicleFront, ivehicleBack, ivehicleRight, ivehicleLeft, ilicense, iinsurance, VehicleType, EEmissionType, TransmissionType, username, RegisterNumber, baseImagePath = "/data/user/0/com.theshakaboom.thekartal/files/";
    Bitmap selectedImageBitmap;
    DbHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vehicle_register);

        register = findViewById(R.id.BtnVehicleRegisterSubmit);

        // Initialize EditText fields
        registerNumber = findViewById(R.id.EdtxtVehicleRegisterRegisterNumber);
        model = findViewById(R.id.EdtxtVehicleRegisterModel);
        make = findViewById(R.id.EdtxtVehicleRegisterMake);
        year = findViewById(R.id.EdtxtVehicleRegisterYear);

        // Initialize ImageView fields
        vehicleFront = findViewById(R.id.ImgVehicleRegisterVehicleFront);
        vehicleBack = findViewById(R.id.ImgVehicleRegisterVehicleBack);
        vehicleRight = findViewById(R.id.ImgVehicleRegisterVehicleRight);
        vehicleLeft = findViewById(R.id.ImgVehicleRegisterVehicleLeft);
        license = findViewById(R.id.ImgVehicleRegisterVehicleLicense);
        insurance = findViewById(R.id.ImgVehicleRegisterVehicleInsurance);

        // Initialize CheckBox
        TermsCheck = findViewById(R.id.CbxVehicleRegisterTerms);

        // Initialize TextView
        viewTerms = findViewById(R.id.TextVehicleRegisterTerms);

        registerNumber.findViewById(R.id.EdtxtVehicleRegisterRegisterNumber);

        RegisterNumber = registerNumber.getText().toString();

        // Set up Spinners
        Spinner vehicleType = findViewById(R.id.dropdownVehicleType);
        Spinner transmissionType = findViewById(R.id.dropdownTransmissionType);
        Spinner emissionType = findViewById(R.id.dropdownEmissionType);

        String[] arrayVehicle = new String[] {
                "Bike", "Tuktuk", "Car", "Van"
        };
        ArrayAdapter<String> adapterVehicle = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayVehicle);
        adapterVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleType.setAdapter(adapterVehicle);

        String[] arrayTransmission = new String[] {
                "Auto", "Manual", "Kryptonic"
        };
        ArrayAdapter<String> adapterTransmission = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayTransmission);
        adapterTransmission.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transmissionType.setAdapter(adapterTransmission);

        String[] arrayEmission = new String[] {
                "Petrol", "Diesel", "Electric"
        };
        ArrayAdapter<String> adapterEmission = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayEmission);
        adapterEmission.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emissionType.setAdapter(adapterEmission);

        // Initialize other variables
        dbHelper = new DbHelper(this);

        //username = dbHelper.getUsername();
        username = dbHelper.getUsername();
        vehicleFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNumber = registerNumber.getText().toString();
                if (RegisterNumber.isEmpty()){
                    Toast.makeText(ActivityVehicleRegister.this,"Please Enter Your vehicle Register number before upload any images",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean pick = true;
                imageRename = username + "_" + RegisterNumber + "_vehicleFront.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleFront);
                        pickImage();
                        ivehicleFront = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleFront);
                        pickImage();
                        ivehicleFront = imagePath;
                    }
                }
            }
        });

        vehicleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNumber = registerNumber.getText().toString();
                if (RegisterNumber.isEmpty()){
                    Toast.makeText(ActivityVehicleRegister.this,"Please Enter Your vehicle Register number before upload any images",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean pick = true;
                imageRename = username + "_" + RegisterNumber + "_vehicleBack.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleBack);
                        pickImage();
                        ivehicleBack = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleBack);
                        pickImage();
                        ivehicleBack = imagePath;
                    }
                }
            }
        });

        vehicleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNumber = registerNumber.getText().toString();
                if (RegisterNumber.isEmpty()){
                    Toast.makeText(ActivityVehicleRegister.this,"Please Enter Your vehicle Register number before upload any images",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean pick = true;
                imageRename = username + "_" + RegisterNumber + "_vehicleRight.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleRight);
                        pickImage();
                        ivehicleRight = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleRight);
                        pickImage();
                        ivehicleRight = imagePath;
                    }
                }
            }
        });

        vehicleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNumber = registerNumber.getText().toString();
                if (RegisterNumber.isEmpty()){
                    Toast.makeText(ActivityVehicleRegister.this,"Please Enter Your vehicle Register number before upload any images",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean pick = true;
                imageRename = username + "_" + RegisterNumber + "_vehicleLeft.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleLeft);
                        pickImage();
                        ivehicleLeft = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleLeft);
                        pickImage();
                        ivehicleLeft = imagePath;
                    }
                }
            }
        });

        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNumber = registerNumber.getText().toString();
                if (RegisterNumber.isEmpty()){
                    Toast.makeText(ActivityVehicleRegister.this,"Please Enter Your vehicle Register number before upload any images",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean pick = true;
                imageRename = username + "_" + RegisterNumber + "_license.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleLicense);
                        pickImage();
                        ilicense = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleLicense);
                        pickImage();
                        ilicense = imagePath;
                    }
                }
            }
        });

        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNumber = registerNumber.getText().toString();
                if (RegisterNumber.isEmpty()){
                    Toast.makeText(ActivityVehicleRegister.this,"Please Enter Your vehicle Register number before upload any images",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean pick = true;
                imageRename = username + "_" + RegisterNumber + "_insurance.jpg";
                if (pick == true) {
                    if (!checkCamaraPermission() && !checkStoragePermission()) {
                        requestCamaraPermission();
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleInsurance);
                        pickImage();
                        iinsurance = imagePath;
                    }
                } else {
                    if (!checkStoragePermission()){
                        requestStoragePermision();
                    } else {
                        imageView = findViewById(R.id.ImgVehicleRegisterVehicleInsurance);
                        pickImage();
                        iinsurance = imagePath;
                    }
                }
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransmissionType = transmissionType.getSelectedItem().toString();
                EEmissionType = emissionType.getSelectedItem().toString();
                VehicleType = vehicleType.getSelectedItem().toString();

                registerUser();
            }
        });

    }
    public void registerUser(){

        registerNumber.findViewById(R.id.EdtxtVehicleRegisterRegisterNumber);
        model.findViewById(R.id.EdtxtVehicleRegisterModel);
        make.findViewById(R.id.EdtxtVehicleRegisterMake);
        year.findViewById(R.id.EdtxtVehicleRegisterYear);

        String RegisterNumber = registerNumber.getText().toString();
        String Model = model.getText().toString();
        String Make = make.getText().toString();
        String YEAR = year.getText().toString();
        int Year = 0;



        if (RegisterNumber.isEmpty() || Model.isEmpty() || Make.isEmpty() || YEAR.isEmpty()){

            Toast.makeText(ActivityVehicleRegister.this,"Please Fill All the Fields ! ",Toast.LENGTH_LONG).show();
        }
        else if(!TermsCheck.isChecked()){
            
        }else         {
            Year = Integer.parseInt(String.valueOf(YEAR));
            boolean registerSuccess = dbHelper.addVehicle(RegisterNumber,VehicleType,Make,Model,Year,baseImagePath + username + "_" + RegisterNumber + "_license.jpg","available",TransmissionType,EEmissionType,baseImagePath + username + "_" + RegisterNumber + "_vehicleFront.jpg",baseImagePath + username + "_" + RegisterNumber + "_vehicleBack.jpg",baseImagePath + username + "_" + RegisterNumber + "_vehicleRight.jpg",baseImagePath + username + "_" + RegisterNumber + "_vehicleLeft.jpg", username );
            if (registerSuccess)
                Toast.makeText(ActivityVehicleRegister.this,"Vehicle Register Successfully ! ",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ActivityVehicleRegister.this,"Vehicle Register Failed ! ",Toast.LENGTH_LONG).show();
        }

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
/*
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Example: Disable specific dates (e.g., "07-03-2018")
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String disabledDate = "07-03-2018";
        Date date = null;
        try {
            date = sdf.parse(disabledDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar disabledCalendar = dateToCalendar(date);

        List<Calendar> disabledDates = new ArrayList<>();
        disabledDates.add(disabledCalendar);
        Calendar[] disabledDaysArray = disabledDates.toArray(new Calendar[disabledDates.size()]);
        dpd.setDisabledDays(disabledDaysArray);

        dpd.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String formattedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        // Update your UI with the selected date (e.g., set it to a TextView)
        // textview.setText(formattedDate);
    }


 */
}