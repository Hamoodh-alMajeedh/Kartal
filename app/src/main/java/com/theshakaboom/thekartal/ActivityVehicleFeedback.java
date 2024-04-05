package com.theshakaboom.thekartal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityVehicleFeedback extends AppCompatActivity {

    ListView listView;
    DbHelper DB;
    AdopterVehicleFeedback adopter;
    ArrayList<RentFeedbackList> arrayList;
    String vehicleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vehicle_feedback);

        DB = new DbHelper(this);
        listView = findViewById(R.id.listVehicleFeedback);


        Intent intent = getIntent();

        if (intent.hasExtra("vehicleId")) {
            vehicleId = intent.getStringExtra("vehicleId");
            Log.d("Intent data", "positive" + vehicleId);
        }
        else {
            Log.d("Intent data", "Negative");
        }

        displaydata();
        filterDataStart();
    }
    private void filterDataStart() {
        ArrayList<RentFeedbackList> filteredList = new ArrayList<>();

        for (RentFeedbackList rentFeedbackList : arrayList) {
            if (rentFeedbackList.getVehicleId().toLowerCase().contains(vehicleId.toLowerCase())) {
                filteredList.add(rentFeedbackList);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void displaydata() {
        arrayList = DB.getRentFeedbackLists();
        adopter = new AdopterVehicleFeedback(this, arrayList);
        listView.setAdapter(adopter);
        adopter.notifyDataSetChanged();
    }
}