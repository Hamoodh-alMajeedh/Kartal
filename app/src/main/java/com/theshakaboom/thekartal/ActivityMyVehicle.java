package com.theshakaboom.thekartal;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class ActivityMyVehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.BtmVehicle);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.BtmVehicle) {
                return true;
            } else if (item.getItemId() == R.id.BtmActivities) {
                startActivity(new Intent(getApplicationContext(), ActivityMyActivities.class));
                overridePendingTransition(R.anim.slide_in_anim, R.anim.slide_out_anim);
                finish();
                return true;
            } else if (item.getItemId() == R.id.BtmHome) {
                startActivity(new Intent(getApplicationContext(), ActivityHome.class));
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.TbarMyVehicleTab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viwpMyVehicleViewPager);

        tabLayout.setupWithViewPager(viewPager);

        AdapterMyActivity adapterMyActivity = new AdapterMyActivity(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapterMyActivity.addFragment(new FragmentMyVehicleList(),"Vehicle");
        adapterMyActivity.addFragment(new FragmentMyVehicleActivity(),"Activities");
        viewPager.setAdapter(adapterMyActivity);
    }
}