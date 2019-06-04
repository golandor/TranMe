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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.Format;
import java.util.ArrayList;
import java.util.Random;

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


        initPieChart();
        initLineChart();
        initBarChart();


        return view;
    }

    private ArrayList<Entry> dataValues1(){

        ArrayList<Entry> dataVals = new ArrayList<>();

        dataVals.add(new Entry(0,70));
        dataVals.add(new Entry(1,74));
        dataVals.add(new Entry(2,78));
        dataVals.add(new Entry(3,72));
        dataVals.add(new Entry(4,80));
        dataVals.add(new Entry(5,84));
        dataVals.add(new Entry(6,84));
        dataVals.add(new Entry(7,88));
        dataVals.add(new Entry(8,90));
        dataVals.add(new Entry(10,94));
        dataVals.add(new Entry(11,94));
        dataVals.add(new Entry(12,89));
        dataVals.add(new Entry(13,87));
        dataVals.add(new Entry(14,91));
        dataVals.add(new Entry(15,99));
        dataVals.add(new Entry(16,120));
        dataVals.add(new Entry(17,130));
        dataVals.add(new Entry(18,125));
        dataVals.add(new Entry(19,140));
        dataVals.add(new Entry(20,120));
        dataVals.add(new Entry(21,99));
        dataVals.add(new Entry(22,90));
        dataVals.add(new Entry(23,110));
        dataVals.add(new Entry(24,100));
        dataVals.add(new Entry(25,96));
        dataVals.add(new Entry(26,94));
        dataVals.add(new Entry(27,94));
        dataVals.add(new Entry(28,92));
        dataVals.add(new Entry(29,91));
        dataVals.add(new Entry(30,80));

        return dataVals;
    }
    private ArrayList<Entry> dataValues2(){

        ArrayList<Entry> dataVals = new ArrayList<>();

        dataVals.add(new Entry(0,70));
        dataVals.add(new Entry(1,80));
        dataVals.add(new Entry(2,78));
        dataVals.add(new Entry(3,76));
        dataVals.add(new Entry(4,80));
        dataVals.add(new Entry(5,83));
        dataVals.add(new Entry(6,84));
        dataVals.add(new Entry(7,88));
        dataVals.add(new Entry(8,96));
        dataVals.add(new Entry(10,74));
        dataVals.add(new Entry(11,94));
        dataVals.add(new Entry(12,89));
        dataVals.add(new Entry(13,87));
        dataVals.add(new Entry(14,91));
        dataVals.add(new Entry(15,99));
        dataVals.add(new Entry(16,140));
        dataVals.add(new Entry(17,80));
        dataVals.add(new Entry(18,83));
        dataVals.add(new Entry(19,85));
        dataVals.add(new Entry(20,90));
        dataVals.add(new Entry(21,99));
        dataVals.add(new Entry(22,90));
        dataVals.add(new Entry(23,100));
        dataVals.add(new Entry(24,100));
        dataVals.add(new Entry(25,92));
        dataVals.add(new Entry(26,91));
        dataVals.add(new Entry(27,91));
        dataVals.add(new Entry(28,97));
        dataVals.add(new Entry(29,91));
        dataVals.add(new Entry(30,70));

        return dataVals;
    } private ArrayList<Entry> dataValues3(){

        ArrayList<Entry> dataVals = new ArrayList<>();

        dataVals.add(new Entry(0,70));
        dataVals.add(new Entry(1,80));
        dataVals.add(new Entry(2,78));
        dataVals.add(new Entry(3,76));
        dataVals.add(new Entry(4,80));
        dataVals.add(new Entry(5,83));
        dataVals.add(new Entry(6,84));
        dataVals.add(new Entry(7,88));
        dataVals.add(new Entry(8,96));
        dataVals.add(new Entry(10,90));
        dataVals.add(new Entry(11,110));
        dataVals.add(new Entry(12,110));
        dataVals.add(new Entry(13,110));
        dataVals.add(new Entry(14,91));
        dataVals.add(new Entry(15,99));
        dataVals.add(new Entry(16,140));
        dataVals.add(new Entry(17,80));
        dataVals.add(new Entry(18,83));
        dataVals.add(new Entry(19,85));
        dataVals.add(new Entry(20,90));
        dataVals.add(new Entry(21,99));
        dataVals.add(new Entry(22,90));
        dataVals.add(new Entry(23,100));
        dataVals.add(new Entry(24,100));
        dataVals.add(new Entry(25,92));
        dataVals.add(new Entry(26,91));
        dataVals.add(new Entry(27,91));
        dataVals.add(new Entry(28,97));
        dataVals.add(new Entry(29,91));
        dataVals.add(new Entry(30,70));

        return dataVals;
    }
    private void initLineChart(){
        lineChart = view.findViewById(R.id.line_chart_id);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(),"Spinning");
        LineDataSet lineDataSet2 = new LineDataSet(dataValues2(),"Pilates");
        LineDataSet lineDataSet3 = new LineDataSet(dataValues3(),"Boxing");
        LineDataSet lineDataSet4 = new LineDataSet(dataValues1(),"sdfsd");
        LineDataSet lineDataSet5 = new LineDataSet(dataValues2(),"122");
        LineDataSet lineDataSet6 = new LineDataSet(dataValues3(),"456");

        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);
        lineDataSets.add(lineDataSet3);
        lineDataSets.add(lineDataSet4);
        lineDataSets.add(lineDataSet5);
        lineDataSets.add(lineDataSet6);

        setColorsToLineCharts(lineDataSets);
        ArrayList<ILineDataSet> datasets = new ArrayList<>();


        datasets.add(lineDataSet1);

        lineDataSet1.setDrawValues(false);

        datasets.add(lineDataSet2);
        datasets.add(lineDataSet3);
        datasets.add(lineDataSet4);
        datasets.add(lineDataSet5);
        datasets.add(lineDataSet6);

        lineDataSet2.setDrawValues(false);

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
        int i = 5;
        for (LineDataSet ds : datasets) {
            i--;
            if (i < 0) {
                ds.setColor(Color.WHITE);
            }else
            ds.setColor(ColorTemplate.JOYFUL_COLORS[i]);
        }
    }

    private void initBarChart(){
        barChart=view.findViewById(R.id.bar_chart_id);
        Description description = new Description();
        description.setText("bar here");
        description.setTextSize(15);
        description.setTextColor(Color.WHITE);
        // lineChart.setDescription(description);

        setData(7);
        barChart.setFitBars(true);


    }

    private void setData(int days) {
        ArrayList<BarEntry> yVals = new ArrayList<>();
        for (int i = 0; i < days ; i++) {
            int value = (int) (Math.random()*300 + 300);
            yVals.add(new BarEntry(i,value));
        }

        String[] myDays = new String[]{"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

        BarDataSet set = new BarDataSet(yVals,"");
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
        xAxis.setLabelRotationAngle(45f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(myDays));
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);


        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void initPieChart(){
        pieChart = view.findViewById(R.id.pie_chart_id);
        // pieChart.setUsePercentValues(true);
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
