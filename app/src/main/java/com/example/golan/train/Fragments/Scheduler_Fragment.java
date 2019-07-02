package com.example.golan.train.Fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.golan.train.BL.Course;
import com.example.golan.train.BL.GraphData;
import com.example.golan.train.BL.MyService;
import com.example.golan.train.BL.RecyclerViewAdapter;
import com.example.golan.train.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Scheduler_Fragment extends Fragment {

    View view;
    private MaterialCalendarView materialCalendarView;
    private DatabaseReference courseRef;
    private RecyclerView courseRecyclerView;
    private ArrayList<Course> list;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager mLayoutManager;


    public Scheduler_Fragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_scheduler_, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        initRecyclerViewAndFireBaseRef();
        initCalender();

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction().addToBackStack(null);// when push the back btn we go back to the previous fragment
        ft.replace(R.id.placeholder, fragment);
        ft.commit();
    }

    public void initRecyclerViewAndFireBaseRef(){
        courseRecyclerView = view.findViewById(R.id.recyclerViewOnScheduler);
        mLayoutManager = new LinearLayoutManager(getContext());
        courseRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
//        list = new ArrayList<>();
        courseRef = FirebaseDatabase.getInstance().getReference().child(getString(R.string.courses));
        adapter = new RecyclerViewAdapter(getContext(), list);
        courseRecyclerView.setAdapter(adapter);
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // if we want to start the query we should only put this line on comment
              //  courseRef.addListenerForSingleValueEvent(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setQuery(String myDate) {
        Query query = courseRef.orderByChild("date").equalTo(myDate);
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    final ValueEventListener valueEventListener = new ValueEventListener() {
        //        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Course course = dataSnapshot1.getValue(Course.class);
                    list.add(course);
                }
                // sorting the list of courses by time
                Collections.sort(list, (s1, s2) -> s2.getTime().compareTo(s1.getTime()));
            } else {
                list.clear();
            }
            adapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
    }

    public void initCalender() {
        materialCalendarView = view.findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = mdFormat.format(calendar.getTime());

        materialCalendarView.setDateSelected(calendar.getTime(),true);
        materialCalendarView.setDynamicHeightEnabled(true);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 1, 1))
                .setMaximumDate(CalendarDay.from(2050, 12, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int m = date.getMonth();
                String day = String.valueOf(date.getDay());
                if(date.getDay() <10){
                    day = 0 + day;
                }
                m++;
                String month = String.valueOf(m);
                if(m<10){
                     month = 0+month;
                }
                String myDate = day + "-" + month + "-" + date.getYear();
                setQuery(myDate);
            }
        });
        setQuery(strDate);
    }



}
