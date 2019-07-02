package com.example.golan.train.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.golan.train.BL.Course;
import com.example.golan.train.BL.GraphData;
import com.example.golan.train.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recommendation_Fragment extends Fragment {

    final int NUM_OF_DAYS_TO_BARCHART = 7;
    final int NUM_OF_DAYS_TO_PIECHART = 30;
    final int NUM_OF_DAYS_TO_LINECHART = 4;

    private PieChart pieChart;
    private LineChart lineChart;
    private BarChart barChart;
    private ArrayList<Course> qList;
    private ArrayList<GraphData> barChartList;
    private ArrayList<GraphData> pieChartList;
    private ArrayList<GraphData> lineChartList;
    private ArrayList<GraphData> myList;
    private DatabaseReference myGraphRef;
    private String user_id;
    private FirebaseAuth firebaseAuth;
    private View view;

    public Recommendation_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommendation_, container, false);
        initFireBase();
        qList = new ArrayList<>();
        myList = new ArrayList<>();
        barChartList = new ArrayList<>();
        pieChartList = new ArrayList<>();
        lineChartList = new ArrayList<>();
        getCoursesList();

        return view;
    }

    public void initFireBase() {
        firebaseAuth = FirebaseAuth.getInstance();
        myGraphRef = FirebaseDatabase.getInstance().getReference("Graphs");

        if (firebaseAuth.getCurrentUser() != null) {

        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user_id = user.getUid();
    }

    public void getCoursesList() {
        myGraphRef.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    myList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()
                    ) {
                        GraphData graphData = dataSnapshot1.getValue(GraphData.class);
                        myList.add(graphData);
                    }
                } else {
                    myList.clear();
                }

                try {
                    initPieChart();
                    initLineChart();
                    initBarChart();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initLineChart() {
        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();
        ArrayList<ILineDataSet> datasets = new ArrayList<>();
        LineDataSet lineDataSet;
        lineChart = view.findViewById(R.id.line_chart_id);

        lineChartList = setChartDays(NUM_OF_DAYS_TO_LINECHART);
        Collections.reverse(lineChartList);

        for (int i = 0; i < lineChartList.size(); i++) {
            lineDataSet = new LineDataSet(dataValuesWithHrList(i), lineChartList.get(i).getCourseName());
            lineDataSets.add(lineDataSet);
            datasets.add(lineDataSet);
            lineDataSet.setDrawValues(false);
            lineDataSet.setDrawCircles(false);
        }
        setColorsToLineCharts(lineDataSets);

        LineData data = new LineData(datasets);

        lineChart.setData(data);
        lineChart.invalidate();
        lineChart.setDescription(null);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getXAxis().setEnabled(false);

        lineChart.animateY(2000);
        lineChart.getLegend().setEnabled(false);

        YAxis rightAxis = lineChart.getAxisRight();

        Legend legend = lineChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setEnabled(true);


    }
    private void setColorsToLineCharts(ArrayList<LineDataSet> datasets) {
        int i = 0;
        for (LineDataSet ds : datasets) {
            if (i <5) {
            ds.setColor(ColorTemplate.JOYFUL_COLORS[i]);
            i++;
            }
            else{
                ds.setColor(Color.WHITE);
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<GraphData> setChartDays(int days){
        return (ArrayList<GraphData>) myList.stream()
                        .sorted(Comparator.comparing(GraphData::getDate).reversed())
                        .limit(days)
                        .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initBarChart() {
        barChart = view.findViewById(R.id.bar_chart_id);
        Description description = new Description();
        description.setTextSize(15);
        description.setTextColor(Color.WHITE);

        setData();
        barChart.setFitBars(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData() {
        ArrayList<BarEntry> yVals = new ArrayList<>();
        barChartList = setChartDays(NUM_OF_DAYS_TO_BARCHART);
        Collections.reverse(barChartList);

        String[] myDays = new String[barChartList.size()];

        for (int i = 0; i < barChartList.size(); i++) {
            yVals.add(new BarEntry(i, barChartList.get(i).getCalories()));
        }
        for (int i = 0; i < myDays.length; i++) {
            myDays[i] = barChartList.get(i).getDate().split("-")[0] + "." + barChartList.get(i).getDate().split("-")[1];
        }

        BarDataSet set = new BarDataSet(yVals, "");
        set.setColors(ColorTemplate.JOYFUL_COLORS);
        set.setDrawValues(true);
        BarData data = new BarData(set);

        set.setValueTextColor(Color.WHITE);
        barChart.setData(data);
        barChart.invalidate();
        barChart.animateY(2000);

        barChart.setDescription(null);
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.getLegend().setEnabled(false);

        barChart.getAxisLeft().setTextColor(Color.WHITE);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(myDays));
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void getCoursesListForBarChart() {
        Query query = myGraphRef.child(user_id);
        System.out.println("before");
        query.addListenerForSingleValueEvent(sortByDate);
        System.out.println("after");
    }

    ValueEventListener sortByDate = new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            System.out.println("in");
            if (dataSnapshot.exists()) {
                barChartList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()
                ) {
                    GraphData graphData = dataSnapshot1.getValue(GraphData.class);
                    barChartList.add(graphData);
                    System.out.println("Data: " + graphData.toString());
                }
            }
            else{
                barChartList.clear();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initPieChart() throws ParseException {
        pieChart = view.findViewById(R.id.pie_chart_id);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);

        pieChartList = setChartDays(NUM_OF_DAYS_TO_PIECHART);
        Collections.reverse(pieChartList);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        Map<String, Integer> coursesNameAndCount = new HashMap();
        for (int i = 0; i < pieChartList.size(); i++) {

            // if key do not exists, put 1 as value, otherwise sum 1 to the value linked to key.
            coursesNameAndCount.merge(pieChartList.get(i).getCourseName(), 1, Integer::sum);
        }

        for ( String key : coursesNameAndCount.keySet() ) {
            yValues.add(new PieEntry(coursesNameAndCount.get(key),key));
        }

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues,"Courses");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS
        );
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

    }

    private ArrayList<Entry> dataValuesWithHrList(int j){

        ArrayList<Entry> dataVals = new ArrayList<>();
        for (int i = 0; i < lineChartList.get(j).getHrList().size(); i++) {
            dataVals.add(new Entry(i,lineChartList.get(j).getHrList().get(i)));
        }
        return dataVals;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
