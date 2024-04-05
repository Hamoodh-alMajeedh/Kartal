package com.theshakaboom.thekartal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentMyVehicleActivity extends Fragment {

    ListView bookingListview;
    DbHelper DB;
    AdopterActivity adopter;
    ArrayList<BookingList> arrayList;
    String username = "testers";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rentals, container, false);

        DB = new DbHelper(getActivity());
        bookingListview = view.findViewById(R.id.FragmentRentalsList);

        displaydata();
        filterDataStart();

        return view;
    }
    private void filterDataStart() {
        ArrayList<BookingList> filteredList = new ArrayList<>();

        for (BookingList bookingList : arrayList) {
            if (bookingList.getVehicleId().toLowerCase().contains(username.toLowerCase())) {
                filteredList.add(bookingList);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void displaydata() {
        arrayList = DB.getBookingListData();
        adopter = new AdopterActivity(getActivity(), arrayList);
        bookingListview.setAdapter(adopter);
        adopter.notifyDataSetChanged();
    }
}