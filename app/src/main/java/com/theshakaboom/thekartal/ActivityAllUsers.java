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

public class ActivityAllUsers extends AppCompatActivity {
    ListView userListView;
    EditText userSearchView;
    DbHelper DB;
    AdopterUsers adopter;
    ArrayList<UserList> arrayList;
    String type = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_users);

        DB = new DbHelper(this);
        userSearchView = findViewById(R.id.EdtActivityAllUsersSearch);
        userListView = findViewById(R.id.ListActivityAllUsersList);

        userSearchView.addTextChangedListener(new TextWatcher() {
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
        ArrayList<UserList> filteredList = new ArrayList<>();

        for (UserList userList : arrayList) {
            if ((userList.getUsername().toLowerCase().contains(query.toLowerCase())) || (userList.getFirstName().toLowerCase().contains(query.toLowerCase())) || (userList.getLastName().toLowerCase().contains(query.toLowerCase()))) {
                filteredList.add(userList);
            }
        }
        Log.d("FilteredData", filteredList.toString()); // Log the filtered data
        adopter.filterList(filteredList);
    }
    private void displaydata() {
        arrayList = DB.getUserListData();
        adopter = new AdopterUsers(this, arrayList);
        userListView.setAdapter(adopter);
        adopter.notifyDataSetChanged();
    }
}