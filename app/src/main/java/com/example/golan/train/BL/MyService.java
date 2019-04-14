package com.example.golan.train.BL;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.golan.train.Interfaces.IMyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MyService implements IMyService {

    private RequestQueue mQueue;
    private String type;
    private boolean resultFromServer;

    private Context context;
    private String url = "http://192.168.0.164:8080/trainme/activity";

    private JSONObject myUser;
    private JSONObject myJson;
    private JSONObject moreAttributes;


    public MyService(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(this.context);
    }

    @Override
    public void registerUserToCourse() {

    }

    @Override
    public void deleteUserFromCourse() {

    }

    @Override
    public void addUserToWaitingList(String courseId, User user) throws JSONException {
        myUser= new JSONObject();
        myJson= new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "JoinToWaitingList";

        myUser.put("fullName",user.getFullName());
        myUser.put("height",user.getHeight());
        myUser.put("gender",user.getGender());
        myUser.put("userId",user.getUser_id());
        myUser.put("weigh",user.getWeigh());

        moreAttributes.put("courseId", courseId);
        moreAttributes.put("user", myUser);

        myJson.put("type", type);
        //myJson.put("", type);

        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, myJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error in volley use");
                System.out.println(error.toString());

            }
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override
    public void removeUserToWaitingList(String courseId, String userId) throws JSONException {
        myJson= new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "removeUserToWaitingList";

        myJson.put("type", type);

        moreAttributes.put("courseId", courseId);
        moreAttributes.put("userId", userId);


    }


    @Override
    public void rateCourse() {

    }

    @Override
    public boolean isFull(String courseId) {
        return false;
    }

    @Override
    public boolean isOnWaitingList(String courseId) {

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    System.out.println("type: " + response.getString("type"));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("Something went wrong");
//                error.printStackTrace();
//            }
//        });
//        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
//
//
        return false;
    }

    @Override
    public boolean isUserRegistered() {
        return false;
    }

    @Override
    public boolean isDatePassed() {
        return false;
    }
//save the context recievied via constructor in a local variable

}