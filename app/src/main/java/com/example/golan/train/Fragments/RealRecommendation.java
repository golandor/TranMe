package com.example.golan.train.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.golan.train.Activities.MainActivity;
import com.example.golan.train.BL.MyService;
import com.example.golan.train.R;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;

import java.time.Duration;

public class RealRecommendation extends Fragment {

    private Button logOutBtn;
    private Button getRecommandation;
    private TextView courseNameText;
    private TextView trainerNameText;
    private TextView openingText;
    private MyService myService;
    private String user_id;
    private String userNumber;
    private ProgressBar progressBar;
    private FadingCircle fadingCircle;
    private View view;
    private FirebaseAuth firebaseAuth;
    private  DatabaseReference reference;


    public RealRecommendation() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_real_recommendation_, container, false);
        init();
        getUserNumber();

        setLogOutBtn();
        setGetRecommandationBtn();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void init(){
        myService = new MyService(getContext());
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        progressBar = view.findViewById(R.id.spinKit);
        fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);
        progressBar.setVisibility(View.INVISIBLE);
        logOutBtn = view.findViewById(R.id.logOut_id);
        getRecommandation = view.findViewById(R.id.getRecommendation_id);
        courseNameText = view.findViewById(R.id.courseNameOnRecommendationId);
        trainerNameText = view.findViewById(R.id.trainerNameOnRecommendationId);
        openingText = view.findViewById(R.id.openingRecommendationId);
        user_id = user.getUid();
    }

    private void getUserNumber() {
        reference = FirebaseDatabase.getInstance().getReference();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userNumber = dataSnapshot.child("Users").child(user_id).child("userNumber").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setLogOutBtn() {
           logOutBtn.setOnClickListener(view -> {
               firebaseAuth.signOut();
               startAct();
           });
    }
    private void startAct() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private void setGetRecommandationBtn(){
        getRecommandation.setOnClickListener(view->{
            try {
                progressBar.setVisibility(View.VISIBLE);
                myService.getRecommendation(this,userNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void onGetRecommendationFromMyService(String courseNameForRecommendation, String trainerNameForRecommendation) throws InterruptedException {
        progressBar.setVisibility(View.INVISIBLE);
        if(!(courseNameForRecommendation.equals("null") || trainerNameForRecommendation.equals("null"))) {
            openingText.setText("Considering your class rankings, the system recommendation specifically for you is:");
            courseNameText.setText("Course Name: " + courseNameForRecommendation);
            trainerNameText.setText("Trainer Name: " + trainerNameForRecommendation);
        }
        else{
            courseNameText.setText("Sorry, there is not enough information yet. Continue to rate courses " +
                    "and we'll have a recommendation for you soon!");
        }
    }
}
