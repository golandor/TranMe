package com.example.golan.train.Interfaces;

import com.example.golan.train.BL.User;

import org.json.JSONException;

public interface IMyService {
    void registerUserToCourse(String courseId, User user) throws JSONException;
    void deleteUserFromCourse(String courseId, String userId) throws JSONException;
    void addUserToWaitingList(String courseId, User user) throws JSONException;
    void RemoveUserFromWaitingList(String courseId, User user) throws JSONException;
    void rateCourse(String courseId, String userId, int rate) throws JSONException;
    boolean isFull(String courseId);
    boolean isOnWaitingList(String courseId);
    boolean isUserRegistered(String courseId, String userId) throws JSONException;
    boolean isDatePassed();

}
