package com.theshakaboom.thekartal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityAllRentals extends AppCompatActivity {
    ListView rentalListview;
    EditText rentalSearchView;
    DbHelper DB;
    AdopterActivity adopter;
    ArrayList<BookingList> arrayList;
    String type = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_rentals);

        DB = new DbHelper(this);
        rentalSearchView = findViewById(R.id.EdtActivityAllRentalsSearch);
        rentalListview = findViewById(R.id.ListActivityAllRentalsList);

        rentalSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    filterData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        Log.d("Filter Change After", "DisplayData");
        displaydata();
    }
    private void filterData(String query) {
        ArrayList<BookingList> filteredList = new ArrayList<>();

        for (BookingList bookingList : arrayList) {
            if ((bookingList.getVehicleId().toLowerCase().contains(query.toLowerCase())) || (bookingList.getStatus().toLowerCase().contains(query.toLowerCase())) || (bookingList.getUserId().toLowerCase().contains(query.toLowerCase()))) {
                filteredList.add(bookingList);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void displaydata() {
        arrayList = DB.getBookingListData();
        adopter = new AdopterActivity(this, arrayList);
        rentalListview.setAdapter(adopter);
        adopter.notifyDataSetChanged();
    }
}