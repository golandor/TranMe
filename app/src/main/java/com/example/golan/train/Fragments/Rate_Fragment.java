package com.example.golan.train.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.golan.train.R;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class Rate_Fragment extends Fragment {

    private SmileRating smileRating;
    private Button rateBtn;
    private int rateLevel = -1;

    public Rate_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rate_, container, false);
        smileRating =  v.findViewById(R.id.smile_rating);
        rateBtn = v.findViewById(R.id.rate_done);
        setRateBtnClicked();
        System.out.println("in onCreateView");
        rate();

        return v;
    }

    private void setRateBtnClicked(){
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("you rated: " + rateLevel);
            }
        });
    }

    private void rate() {

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
                        Toast.makeText(getContext(), "" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.BAD:
                        Toast.makeText(getContext(), "" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        Toast.makeText(getContext(), "" + smiley, Toast.LENGTH_SHORT).show();
                        System.out.println("I rated: " + smiley);

                        break;
                    case SmileRating.GOOD:
                        Toast.makeText(getContext(), "" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        Toast.makeText(getContext(), "" + smiley, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

                Toast.makeText(getContext(),"Selected Rating" + level, Toast.LENGTH_SHORT).show();

                rateLevel = level;
            }
        });

    }


}
