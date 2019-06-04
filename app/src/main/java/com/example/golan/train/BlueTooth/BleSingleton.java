package com.example.golan.train.BlueTooth;

import android.content.Context;

import com.example.golan.train.BL.MySingelton;

import java.util.ArrayList;
import java.util.List;

public class BleSingleton {
    private String userId;
    private String courseId;
    private List<Integer> hrlist;
    private static BleSingleton mInstance;


    private BleSingleton() {
        this.hrlist = new ArrayList<Integer>();
    }
    public static synchronized BleSingleton getInstance(){
        if(mInstance==null){
            mInstance = new BleSingleton();
        }
        return mInstance;
    }

    public static BleSingleton getmInstance() {
        return mInstance;
    }

    public static void setmInstance(BleSingleton mInstance) {
        BleSingleton.mInstance = mInstance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<Integer> getHrlist() {
        return hrlist;
    }

    public void setHrlist(List<Integer> hrlist) {
        this.hrlist = hrlist;
    }

    @Override
    public String toString() {
        return "BleSingleton{" +
                "userId='" + userId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", hrlist=" + hrlist +
                '}';
    }

}
