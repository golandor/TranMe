package com.example.golan.train.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.golan.train.BL.ViewPagerAdapter;
import com.example.golan.train.Fragments.MyZone_Fragment;
import com.example.golan.train.Fragments.Profile_Fragment;
import com.example.golan.train.Fragments.Recommendation_Fragment;
import com.example.golan.train.Fragments.Scheduler_Fragment;
import com.example.golan.train.R;

public class BottomTest extends AppCompatActivity {

    private BottomNavigationView mNavBar;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_test);

        mNavBar = findViewById(R.id.btmNav);
        viewPager = (ViewPager)findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Add the fragments

        adapter.addFragment(new Profile_Fragment(),"");
        adapter.addFragment(new Scheduler_Fragment(),"");
        adapter.addFragment(new MyZone_Fragment(),"");
        adapter.addFragment(new Recommendation_Fragment(),"");

        viewPager.setAdapter(adapter);

        mNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()) {
                    case R.id.profile:
                        viewPager.setCurrentItem(0);
                        menuItem.setChecked(true);
                        break;
                    case R.id.main:
                        viewPager.setCurrentItem(1);
                        menuItem.setChecked(true);
                        break;
                    case R.id.myZone:
                        viewPager.setCurrentItem(2);
                        menuItem.setChecked(true);
                        break;
                    case R.id.ideas:
                        viewPager.setCurrentItem(3);
                        menuItem.setChecked(true);
                        break;
                }
                return false;
            }
        });
    }
}
