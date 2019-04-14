package com.example.golan.train.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.golan.train.BL.Course;
import com.example.golan.train.Interfaces.fromRecyclerViewAdapter;
import com.example.golan.train.R;

public class Course_Details_Fragment extends Fragment  {


    private TextView courseName_on_Details;
    private TextView coachName_on_Details;
    private TextView time_on_Details;
    private TextView courseLocation_on_Details;
    private TextView details_on_Details;
    private Button courseStatusBtn_on_Details;
    private Course course;


    public Course_Details_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course__details_, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
