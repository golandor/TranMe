package com.example.golan.train.BL;

import java.util.List;

public class GraphData {

    private String courseName;
    private String date;
    private int calories;
    private int HR_avg;
    private List<Integer> hrList;

    public GraphData() {
        super();
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHR_avg() {
        return HR_avg;
    }

    public void setHR_avg(int HR_avg) {
        this.HR_avg = HR_avg;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }


    public List<Integer> getHrList() {
        return hrList;
    }

    public void setHrList(List<Integer> hrList) {
        this.hrList = hrList;
    }

    @Override
    public String toString() {
        return "GraphData{" +
                "courseName='" + courseName + '\'' +
                ", date='" + date + '\'' +
                ", calories=" + calories +
                ", HR_avg=" + HR_avg +
                ", hrList=" + hrList +
                '}';
    }
}
