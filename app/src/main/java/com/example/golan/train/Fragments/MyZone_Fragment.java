package com.example.golan.train.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.golan.train.Activities.MainActivity;
import com.example.golan.train.BL.Course;
import com.example.golan.train.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MyZone_Fragment extends Fragment {
    View view;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference userCourseRef;
    private ListView myCourseListView;
    private ArrayList<Course> myCourseList;
    private String user_id;
    private String currentDate;
    private Calendar calendar;
    private SimpleDateFormat mdFormat;
    private CostomAdapter costomAdapter = new CostomAdapter();

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
        init();
        setQuery(currentDate);
        setMyAdapter();
        return view;
    }



    private void setMyAdapter() {
        myCourseListView.setAdapter(costomAdapter);
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user_id = user.getUid();
        calendar = Calendar.getInstance();
        mdFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = mdFormat.format(calendar.getTime());
        myCourseListView = view.findViewById(R.id.listViewOnMyZone);
        myCourseList = new ArrayList<>();
        userCourseRef = FirebaseDatabase.getInstance().getReference().child("UserCourses");

    }

    private void setQuery(String myDate) {

        Query query = userCourseRef.child(user_id).orderByChild("date")
                .startAt(myDate);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    final ValueEventListener valueEventListener = new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                myCourseList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Course course = dataSnapshot1.getValue(Course.class);
                    myCourseList.add(course);
                }
                // sorting the list of courses by date and time

                myCourseList.sort(Comparator.comparing(Course::getDate).thenComparing(Course::getTime));

            } else {
                myCourseList.clear();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    class CostomAdapter extends BaseAdapter {

        TextView courseName;
        TextView coachName;
        TextView time;
        TextView date;
        @Override
        public int getCount() {
            return myCourseList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.course_on_my_zone,null);
            courseName = view1.findViewById(R.id.courseNameIdOnMyZone);
            coachName = view1.findViewById(R.id.coachNameIdOnMyZone);
            time = view1.findViewById(R.id.timeIdOnOnMyZone);
            date = view1.findViewById(R.id.dateIdOnOnMyZone);

            courseName.setText(myCourseList.get(position).getCourseName());
            coachName.setText(myCourseList.get(position).getTrainerName());
            time.setText(myCourseList.get(position).getTime());
            date.setText(myCourseList.get(position).getDate());
            return view1;
        }
    }

}

