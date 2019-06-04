package com.example.golan.train.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.golan.train.Activities.MainActivity;
import com.example.golan.train.BlueTooth.DeviceScanActivity;
import com.example.golan.train.R;
import com.google.firebase.auth.FirebaseAuth;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class MyZone_Fragment extends Fragment {

    // private Button logOutBtn;
    private FirebaseAuth firebaseAuth;
    private Switch aSwitch;
    private TextView switchTextView;

    View view;

    public MyZone_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_zone_, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        aSwitch = view.findViewById(R.id.switch1);

        //  setlogOutBtn();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    aSwitch.setChecked(false);
                    startActivity(new Intent(getContext(), DeviceScanActivity.class));
                }
            }
        });
        return view;
    }

    private void startAct() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
