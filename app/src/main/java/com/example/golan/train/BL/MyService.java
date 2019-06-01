package com.example.golan.train.BL;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.golan.train.Interfaces.IMyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyService implements IMyService {

    private RequestQueue mQueue;
    private String type;
    private String resultFromServer;

    private Context context;
    private String url = "http://192.168.1.21:8080/trainme/activity";

    private JSONObject myUser;
    private JSONObject myJson;
    private JSONObject moreAttributes;


    public MyService(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(this.context);
    }

    @Override                                /**    DONE     **/
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

    @Override                                /**    DONE     **/
    public void RemoveUserFromWaitingList(String courseId, User user) throws JSONException {
        myJson= new JSONObject();
        myUser= new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "RemoveUserFromWaitingList";

        myUser.put("fullName",user.getFullName());
        myUser.put("height",user.getHeight());
        myUser.put("gender",user.getGender());
        myUser.put("userId",user.getUser_id());
        myUser.put("weigh",user.getWeigh());

        moreAttributes.put("courseId", courseId);
        moreAttributes.put("user", myUser);

        myJson.put("type", type);

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
        MySingelton.getInstance(this.context).addToRequesrtQueue(jsonObjectRequest);

    }

    @Override                                /**    DONE     **/
    public void rateCourse(String courseId, String userId, int rate) {

        System.out.println("rate is: "+ rate);
//        myJson= new JSONObject();
//        moreAttributes = new JSONObject();
//
//        this.type = "RateCourse";
//
//        moreAttributes.put("courseId", courseId);
//        moreAttributes.put("userId", userId);
//        moreAttributes.put("rate", rate);
//
//        myJson.put("type",type);
//
//        myJson.put("moreAttributes", moreAttributes);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, myJson,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("Response: " + response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("Error in volley use");
//                System.out.println(error.toString());
//
//            }
//        });
//        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override                                /**    DONE     **/
    public void registerUserToCourse(String courseId, User user) throws JSONException {
//        myUser= new JSONObject();
//        myJson= new JSONObject();
//        moreAttributes = new JSONObject();
//
//        this.type = "AddUserToCourse";
//
//        myUser.put("fullName",user.getFullName());
//        myUser.put("height",user.getHeight());
//        myUser.put("gender",user.getGender());
//        myUser.put("userId",user.getUser_id());
//        myUser.put("weigh",user.getWeigh());
//
//        moreAttributes.put("courseId", courseId);
//        moreAttributes.put("user", myUser);
//
//        myJson.put("type", type);
//
//        myJson.put("moreAttributes", moreAttributes);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, myJson,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("Response: " + response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("Error in volley use");
//                System.out.println(error.toString());
//
//            }
//        });
//        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }
    @Override

    public void deleteUserFromCourse(String courseId, String userId) throws JSONException {
        myJson= new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "RemoveUserFromCourse";

        moreAttributes.put("courseId", courseId);
        moreAttributes.put("userId", userId);

        myJson.put("type", type);

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
                System.out.println(error.toString());
            }
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
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


        return false;
    }

    @Override
    public boolean isUserRegistered(String courseId, String userId) throws JSONException {
    //synchronized (this){
        return false;
    //}


//        myJson= new JSONObject();
//        moreAttributes = new JSONObject();
//
//
//        this.type = "IsUserRegisteredToCourse";
//
//        moreAttributes.put("courseId", courseId);
//        moreAttributes.put("userId", userId);
//
//        myJson.put("type", this.type);
//        myJson.put("moreAttributes", moreAttributes);
//
//        RequestFuture<JSONObject> future = RequestFuture.newFuture();
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, myJson,future,future);

//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(jsonObjectRequest);

//        try {
//            System.out.println("Just checking");
//            MySingelton.getInstance(this.context).addToRequesrtQueue(jsonObjectRequest);
//
//
//                    JSONObject response;
//
//            response = future.get(10,TimeUnit.SECONDS);
//            System.out.println("Response: " + response.toString());
//            myJson = response.getJSONObject("moreAttributes");
//            System.out.println("moreAttributes: " + moreAttributes.toString());
//            resultFromServer = myJson.getString("registered");
//            System.out.println("resultFromServer: " + resultFromServer);
//
//
//        } catch (InterruptedException e) {
//            System.out.println("Error in volley InterruptedException");
//            System.out.println(e.toString());
//        } catch (ExecutionException e) {
//            System.out.println("Error in volley ExecutionException");
//            System.out.println(e.toString());
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//
//
//         System.out.println("before return: " + resultFromServer);
        //return true; //resultFromServer.equals("true");
    }
//
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("Response: " + response.toString());
//                        try {
//                            moreAttributes = response.getJSONObject("moreAttributes");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            resultFromServer =  moreAttributes.get("registered").toString();
//                            System.out.println("res: " + resultFromServer);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {

//
//            }
//        });

    @Override
    public boolean isDatePassed() {
        return false;
    }
//save the context recievied via constructor in a local variable

}