package com.example.golan.train.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;

import com.example.golan.train.BL.ViewPagerAdapter;
import com.example.golan.train.Fragments.MyZone_Fragment;
import com.example.golan.train.Fragments.RealRecommendation;
import com.example.golan.train.Fragments.Recommendation_Fragment;
import com.example.golan.train.Fragments.Scheduler_Fragment;
import com.example.golan.train.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.invoke.MethodHandles;

public class MainScreenActivity extends AppCompatActivity {

    private BottomNavigationView mNavBar;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_test);

        setNavBarAndAdapter();
        initFireBase();


    }
    public void initFireBase(){
        firebaseAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user_id = user.getUid();



    }

    private void setNavBarAndAdapter() {
        mNavBar = findViewById(R.id.btmNav);
        viewPager = (ViewPager)findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Add the fragments

        adapter.addFragment(new Scheduler_Fragment(),"");
        adapter.addFragment(new Recommendation_Fragment(),"");
        adapter.addFragment(new MyZone_Fragment(),"");
        adapter.addFragment(new RealRecommendation(),"");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager.setAdapter(adapter);

        mNavBar.setOnNavigationItemSelectedListener(menuItem -> {

            switch(menuItem.getItemId()) {
                case R.id.main:
                    viewPager.setCurrentItem(0);
                    menuItem.setChecked(true);
                    break;
                case R.id.ideas:
                    viewPager.setCurrentItem(1);
                    menuItem.setChecked(true);
                    break;
                case R.id.myZone:
                    viewPager.setCurrentItem(2);
                    menuItem.setChecked(true);
                    break;
                case R.id.recommendations:
                    viewPager.setCurrentItem(3);
                    menuItem.setChecked(true);
                    break;
            }
            return false;
        });
    }


}
