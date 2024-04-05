package com.theshakaboom.thekartal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class FragmentMyVehicleList extends Fragment {
    ListView vehicleListView;
    DbHelper DB;
    AdepterVehicls adopter;
    ArrayList<VehicleList> arrayList;
    String username = "testers";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_vehicle_list, container, false);

        DB = new DbHelper(getActivity());
        vehicleListView = view.findViewById(R.id.FragmentMyVehicleVehicleList);

        displaydata();
        filterDataStart();

        return view;
    }
    private void filterDataStart() {
        ArrayList<VehicleList> filteredList = new ArrayList<>();

        for (VehicleList vehicleList : arrayList) {
            if (vehicleList.getOwnerId().toLowerCase().contains(username.toLowerCase())) {
                filteredList.add(vehicleList);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void displaydata() {
        arrayList = DB.getVehicleListData();
        adopter = new AdepterVehicls(getActivity(), arrayList);
        vehicleListView.setAdapter(adopter);
        adopter.notifyDataSetChanged();
    }
}