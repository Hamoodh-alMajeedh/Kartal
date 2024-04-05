package com.theshakaboom.thekartal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentFeedback extends Fragment {

    ListView listView;
    DbHelper DB;
    AdopterUserFeedback adopter;
    ArrayList<PassengerFeedbackList> arrayList;
    String username = "All";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        DB = new DbHelper(getActivity());
        listView = view.findViewById(R.id.FragmentFeedbacksList);

        displaydata();
        filterDataStart();
        return view;
    }
    private void filterDataStart() {
        ArrayList<PassengerFeedbackList> filteredList = new ArrayList<>();

        for (PassengerFeedbackList passengerFeedbackList : arrayList) {
            if (passengerFeedbackList.getUserID().toLowerCase().contains(username.toLowerCase())) {
                filteredList.add(passengerFeedbackList);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void displaydata() {
        arrayList = DB.getPassengerFeedbackListData();
        adopter = new AdopterUserFeedback(getActivity(), arrayList);
        listView.setAdapter(adopter);
        adopter.notifyDataSetChanged();
    }
}