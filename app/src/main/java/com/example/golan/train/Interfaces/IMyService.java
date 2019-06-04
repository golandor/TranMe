package com.example.golan.train.Interfaces;

import com.example.golan.train.BL.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public interface IMyService {
    void registerUserToCourse(String courseId, User user) throws JSONException;
    void deleteUserFromCourse(String courseId, User user) throws JSONException;
    void addUserToWaitingList(String courseId, User user) throws JSONException;
    void RemoveUserFromWaitingList(String courseId, User user) throws JSONException;
    void rateCourse(String courseId, User user, int rate) throws JSONException;
    void writeHRList(String courseId, String userId, List<Integer> hrList) throws JSONException;
    boolean isFull(String courseId);
    boolean isOnWaitingList(String courseId);
    boolean isUserRegistered(String courseId, User user) throws JSONException;
    boolean isDatePassed();

}
