package com.theshakaboom.thekartal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ActivityVehicle extends AppCompatActivity {

    ListView vehicleListview;
    EditText vehicleSearchView;
    TextView PageTitle;
    DbHelper DB;
    AdepterVehicls adopter;
    ArrayList<VehicleList> arrayList;
    String type = "All";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vehicle);

        //Intent intent = getIntent();
        DB = new DbHelper(this);
        vehicleListview = findViewById(R.id.ListVehicleResults);
        vehicleSearchView = findViewById(R.id.vehicle_search);

        Intent intent = getIntent();

        if (intent.hasExtra("Type")) {
            type = intent.getStringExtra("Type");
            Log.d("Intent data", "positive" + type);
        }
        else {
            Log.d("Intent data", "Negative");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.BtmActivities) {
                startActivity(new Intent(getApplicationContext(), ActivityMyActivities.class));
                overridePendingTransition(R.anim.slide_in_anim, R.anim.slide_out_anim);
                finish();
                return true;
            } else if (item.getItemId() == R.id.BtmHome) {
                startActivity(new Intent(getApplicationContext(), ActivityHome.class));
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

        vehicleSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (type.equals("All")){
                    filterDataAll(charSequence.toString());
                }else {
                    filterData(charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        Log.d("Filter Change After", "DisplayData");
        displaydata();
        if (!type.equals("All")) {
            filterDataStart();
        }
    }
    private void filterData(String query) {
        ArrayList<VehicleList> filteredList = new ArrayList<>();

        for (VehicleList vhl : arrayList) {
            if ((vhl.getMake().toLowerCase().contains(query.toLowerCase()) || vhl.getModel().toLowerCase().contains(query.toLowerCase())) && vhl.getType().toLowerCase().contains(type.toLowerCase())) {
                filteredList.add(vhl);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void filterDataAll(String query) {
        ArrayList<VehicleList> filteredList = new ArrayList<>();

        for (VehicleList vhl : arrayList) {
            if ((vhl.getMake().toLowerCase().contains(query.toLowerCase()) || vhl.getModel().toLowerCase().contains(query.toLowerCase()))) {
                filteredList.add(vhl);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void filterDataStart() {
        ArrayList<VehicleList> filteredList = new ArrayList<>();

        for (VehicleList vhl : arrayList) {
            if (vhl.getType().toLowerCase().contains(type.toLowerCase())) {
                filteredList.add(vhl);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void displaydata() {
        arrayList = DB.getVehicleListData();
        adopter = new AdepterVehicls(this, arrayList);
        vehicleListview.setAdapter(adopter);
        adopter.notifyDataSetChanged();
    }
}