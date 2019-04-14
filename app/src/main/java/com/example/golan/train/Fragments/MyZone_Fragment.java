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
import android.widget.Toast;

import com.example.golan.train.Activities.MainActivity;
import com.example.golan.train.R;
import com.google.firebase.auth.FirebaseAuth;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class MyZone_Fragment extends Fragment {

   // private Button logOutBtn;
    private FirebaseAuth firebaseAuth;
    private int rate;
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
      //  setlogOutBtn();


        SmileRating smileRating =  view.findViewById(R.id.smile_rating);
       // @BaseRating.Smiley int smiley = smileRating.getSelectedSmile();
        smileRating.setTextSelectedColor(Color.GREEN);

        smileRating.setNameForSmile(BaseRating.TERRIBLE, "Too Easy!");
        smileRating.setNameForSmile(BaseRating.BAD, "Easy");
        smileRating.setNameForSmile(BaseRating.OKAY, "Medium");
        smileRating.setNameForSmile(BaseRating.GOOD, "Hard");
        smileRating.setNameForSmile(BaseRating.GREAT, "Very Hard!");

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                switch (smiley) {

                    case SmileRating.TERRIBLE:
                        Toast.makeText(getContext(),"" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.BAD:
                        Toast.makeText(getContext(),"" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        Toast.makeText(getContext(),"" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        Toast.makeText(getContext(),"" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        Toast.makeText(getContext(),"" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

//        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
//            @Override
//            public void onRatingSelected(int level, boolean reselected) {
//                // level is from 1 to 5 (0 when none selected)
//                // reselected is false when user selects different smiley that previously selected one
//                // true when the same smiley is selected.
//                // Except if it first time, then the value will be false.
//
//                //Toast.makeText(getContext(),"Selected Rating" + level, Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

   // private void setlogOutBtn() {
    //    logOutBtn =  view.findViewById(R.id.logOutBtn);
     //   logOutBtn.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View view) {
      //          firebaseAuth.signOut();
      //          startAct();
      //      }
    //    });
    //}

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
