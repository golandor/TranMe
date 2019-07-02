package com.example.golan.train.Interfaces;

import com.example.golan.train.BL.RecyclerViewAdapter;
import com.example.golan.train.BL.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public interface IMyService {
    void signUpNewUser(User user) throws JSONException;
    void registerUserToCourse(final RecyclerViewAdapter listener, String courseId, User user) throws JSONException;
    void deleteUserFromCourse(String courseId, User user) throws JSONException;
    void addUserToWaitingList(String courseId, User user) throws JSONException;
    void RemoveUserFromWaitingList(String courseId, User user) throws JSONException;
    void rateCourse(String courseId, User user, int rate) throws JSONException;
    void writeHRList(String courseId, String userId, List<Integer> hrList) throws JSONException;
    void isFull(final RecyclerViewAdapter listener, String courseId) throws JSONException;
    void isOnWaitingList(final RecyclerViewAdapter listener, String courseId, User user) throws JSONException;
    void setCurrentNumOfUsersRegisteredToCourse(String courseId,int CurrentNumOfUsers) throws JSONException;
    void isUserRegistered(RecyclerViewAdapter listener, String courseId, User user) throws JSONException;
    void isDatePassed(final RecyclerViewAdapter listener, String courseId) throws JSONException;

}
