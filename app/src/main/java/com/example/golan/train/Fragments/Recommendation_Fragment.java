package com.example.golan.train.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.golan.train.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.Format;
import java.util.ArrayList;

public class Recommendation_Fragment extends Fragment {

    private PieChart pieChart;
    private LineChart lineChart;
    private BarChart barChart;

    private View view;
    public Recommendation_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommendation_, container, false);


        float yValues [] = {5,4,6,3,7,2,8};
        String xValues [] = {"one","two","three","four","five","six"};
        initPieChart();
        initLineChart(yValues,xValues);
        initBarChart(yValues,xValues);


        return view;
    }

    private void initLineChart(float[] yValues, String[] xValues){
        lineChart=view.findViewById(R.id.line_chart_id);
        Description description = new Description();
        description.setText("Line here");
        description.setTextSize(15);
        description.setTextColor(Color.WHITE);
       // lineChart.setDescription(description);

        ArrayList<Entry>yData = new ArrayList<>();
        for(int i=0;i<yValues.length;i++){
            yData.add(new Entry(yValues[i],i));
        }
        ArrayList<String>xData = new ArrayList<>();
        for(int i=0;i<xValues.length;i++){
            xData.add(xValues[i]);
        }
        LineDataSet lineDataSet = new LineDataSet(yData,"myTutorial");
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);



        LineData lineData = new LineData(lineDataSet);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);


        lineData.setValueTextSize(10f);
        lineData.setValueTextColor(Color.WHITE);
        //lineDataSet.setValueTextColor(Color.GREEN);
        lineChart.setGridBackgroundColor(Color.GREEN);

        lineChart.setData(lineData);
       // barChart.invalidate();

    }

    private void initBarChart(float[] yValues, String[] xValues){
        barChart=view.findViewById(R.id.bar_chart_id);
        Description description = new Description();
        description.setText("bar here");
        description.setTextSize(15);
        description.setTextColor(Color.WHITE);
        // lineChart.setDescription(description);

        ArrayList<BarEntry>yData = new ArrayList<>();
        for(int i=0;i<yValues.length;i++){
            yData.add(new BarEntry(yValues[i],i));
        }
        ArrayList<String>xData = new ArrayList<>();
        for(int i=0;i<xValues.length;i++){
            xData.add(xValues[i]);
        }
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        BarDataSet barDataSet = new BarDataSet(yData,"");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData barData = new BarData(barDataSet);


        barData.setValueTextSize(7f);
        barData.setValueTextColor(Color.WHITE);
        //lineDataSet.setValueTextColor(Color.GREEN);

        barChart.setData(barData);
        barChart.invalidate();
    }


    private void initPieChart(){
        pieChart = view.findViewById(R.id.pie_chart_id);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);


        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(25,"Spinning"));
        yValues.add(new PieEntry(33,"Yoga"));
        yValues.add(new PieEntry(7,"Pilatice"));
        yValues.add(new PieEntry(35,"Stations"));

        Description description = new Description();
        description.setText("Courses Token this month");
        description.setTextSize(15);
        description.setTextColor(Color.WHITE);
       // pieChart.setDescription(description);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues,"countries");
        //    dataSet.setF;
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        PieData data = new PieData(dataSet);
        //data.val
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
