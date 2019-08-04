package com.example.golan.train.Interfaces;

import com.example.golan.train.BL.RecyclerViewAdapter;
import com.example.golan.train.BL.User;
import com.example.golan.train.Fragments.RealRecommendation;

import org.json.JSONException;

import java.util.List;

public interface IMyService {
    void signUpNewUser(User user) throws JSONException;
    void registerUserToCourse(final RecyclerViewAdapter listener, String courseId, User user) throws JSONException;
    void deleteUserFromCourse(String courseId,String courseName, User user) throws JSONException;
    void addUserToWaitingList(String courseId, User user) throws JSONException;
    void RemoveUserFromWaitingList(String courseId, User user) throws JSONException;
    void rateCourse(String courseId, User user, int rate) throws JSONException;
    void writeHRList(String courseId, String userId, List<Integer> hrList) throws JSONException;

    void getRecommendation(final RealRecommendation listener, String user_id) throws JSONException, InterruptedException;
}
