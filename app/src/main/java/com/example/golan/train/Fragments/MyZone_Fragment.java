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

    private Button logOutBtn;
    private FirebaseAuth firebaseAuth;

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
        logOutBtn = view.findViewById(R.id.logOut_id);

        setLogOutBtn();

        return view;
    }

    private void setLogOutBtn() {
           logOutBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  firebaseAuth.signOut();
                  startAct();
              }
            });
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
