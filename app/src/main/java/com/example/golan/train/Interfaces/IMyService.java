package com.example.golan.train.Interfaces;

import com.example.golan.train.BL.User;

import org.json.JSONException;

public interface IMyService {
    void registerUserToCourse();
    void deleteUserFromCourse();
    void addUserToWaitingList(String courseId, User user) throws JSONException;
    void RemoveUserFromWaitingList(String courseId, User user) throws JSONException;
    void rateCourse();
    boolean isFull(String courseId);
    boolean isOnWaitingList(String courseId);
    boolean isUserRegistered();
    boolean isDatePassed();



}
