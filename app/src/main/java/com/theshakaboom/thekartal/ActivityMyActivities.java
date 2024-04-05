package com.theshakaboom.thekartal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class ActivityMyActivities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activities);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.BtmActivities);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.BtmActivities) {
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.TbarMyActivitiesTab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viwpMyActivitiesViewPager);

        tabLayout.setupWithViewPager(viewPager);

        AdapterMyActivity adapterMyActivity = new AdapterMyActivity(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapterMyActivity.addFragment(new FragmentRentals(),"Rentals");
        adapterMyActivity.addFragment(new FragmentFeedback(),"Feedback");
        viewPager.setAdapter(adapterMyActivity);
    }
}