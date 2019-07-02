package com.example.golan.train.BL;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.golan.train.Interfaces.IMyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyService implements IMyService {

    private static final String TAG = "MyService";

    private RequestQueue mQueue;
    private String type;
    private String stringResultFromServer;
    private boolean boolResultFromServer;

    private Context context;
    //private String url = "http://192.168.1.21:8080/trainme/activity";
    private String url = "http://192.168.1.21:8080";
    private String activityUrl = url + "/trainme/activity";

    private JSONObject myUser;
    private JSONObject myJson;
    private JSONObject myCourse;
    private JSONObject moreAttributes;

    public MyService(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(this.context);
    }


    @Override
    /**    DONE     **/
    public void addUserToWaitingList(String courseId, User user) throws JSONException {

        myUser = new JSONObject();
        myJson = new JSONObject();
        myCourse = new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "JoinToWaitingList";

        myCourse.put("courseId", courseId);

        myUser.put("fullName", user.getFullName());
        myUser.put("age", user.getAge());
        myUser.put("gender", user.getGender());
        myUser.put("userId", user.getUser_id());
        myUser.put("weigh", user.getWeigh());

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Added To Waiting List",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something Went Wrong",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"On addUserToWaitingList" + error);

            }
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override
    /**    DONE     **/
    public void RemoveUserFromWaitingList(String courseId, User user) throws JSONException {

        myUser = new JSONObject();
        myJson = new JSONObject();
        myCourse = new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "RemoveUserFromWaitingList";

        myCourse.put("courseId", courseId);

        myUser.put("userId", user.getUser_id());

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
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

    @Override
    /**    DONE     **/
    public void rateCourse(String courseId, User user, int rate) throws JSONException {
        myUser = new JSONObject();
        myJson = new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();

        this.type = "RateCourse";

        myCourse.put("courseId", courseId);

        myUser.put("userId", user.getUser_id());

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);
        moreAttributes.put("rate", rate);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                response ->
                        Toast.makeText(context, "Thanks For Your Rate",Toast.LENGTH_SHORT).show(),
                error -> {
                    Toast.makeText(context, "Something Went Wrong",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"On rateCourse" + error);

                });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override
    /**    DONE     **/
    public synchronized void registerUserToCourse(final RecyclerViewAdapter listener, String courseId, User user) throws JSONException {

        myUser = new JSONObject();
        myJson = new JSONObject();
        myCourse = new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "AddUserToCourse";

        myCourse.put("courseId", courseId);

        myUser.put("fullName", user.getFullName());
        myUser.put("age", user.getAge());
        myUser.put("gender", user.getGender());
        myUser.put("userId", user.getUser_id());
        myUser.put("weigh", user.getWeigh());
        myUser.put("userNumber", user.getUserNumber());

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);
System.out.println("hwrersbgfxn hbgrfdymjn bjhbhbgvc");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                response -> {

                                //boolResultFromServer = response.getJSONObject("moreAttributes").getBoolean("onWaitingList");
                    //try {
                      //  listener.onRegisterUserToCourseFromMyService(true);
                 //   } catch (JSONException e) {
                 //       e.printStackTrace();
                //    }
                        }, error -> {
            System.out.println("Error in volley use");
            System.out.println("Error: " + error.toString());
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    /**    DONE     **/
    @Override
    public void setCurrentNumOfUsersRegisteredToCourse(String courseId,int CurrentNumOfUsers) throws JSONException {
        myJson = new JSONObject();
        myCourse = new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "SetCurrentNumOfUsersRegisteredToCourse";

        myCourse.put("courseId", courseId);
        myCourse.put("currentNumOfUsersInCourse", CurrentNumOfUsers);


        moreAttributes.put("courseEntity", myCourse);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                response -> {
                }, error -> Log.d(TAG, "setCurrentNumOfUsersRegisteredToCourse: " + error));
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);

    }

    @Override
    /**    DONE     **/
    public void deleteUserFromCourse(String courseId, User user) throws JSONException {
        myUser = new JSONObject();
        myJson = new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();

        this.type = "RemoveUserFromCourse";

        myCourse.put("courseId", courseId);
        myUser.put("userId", user.getUser_id());

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "you've been removed from the course",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something Went Wrong",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"On deleteUserFromCourse" + error);
            }
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override
    /**    DONE     **/
    public void writeHRList(String courseId, String userId, List<Integer> hrList) throws JSONException {
        myUser = new JSONObject();
        myJson = new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();
        JSONArray hrJsonArray = new JSONArray();
        for (int hr : hrList) {
            hrJsonArray.put(hr);
        }

        this.type = "WriteHR";

        myCourse.put("courseId", courseId);

        myUser.put("userId", userId);

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);
        moreAttributes.put("hrlist", hrJsonArray);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
    /**    DONE     **/
    public void signUpNewUser(User user) throws JSONException {
        String url2 = url + "/trainme/user";
        myUser = new JSONObject();

        myUser.put("fullName", user.getFullName());
        myUser.put("age", user.getAge());
        myUser.put("gender", user.getGender());
        myUser.put("userId", user.getUser_id());
        myUser.put("weigh", user.getWeigh());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url2, myUser,
                response -> System.out.println("Response: " + response.toString()),
                error -> {
                    System.out.println("Error in volley use - signUpNewUser");
                    System.out.println(error.toString());

                });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override
    /**    DONE     **/
    public void isOnWaitingList(final RecyclerViewAdapter listener, String courseId, User user) throws JSONException {
        myUser = new JSONObject();
        myJson = new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();

        this.type = "IsOnWaitingList";

        myCourse.put("courseId", courseId);
        myUser.put("userId", user.getUser_id());

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                response -> {
                    try {
                        boolResultFromServer = response.getJSONObject("moreAttributes").getBoolean("onWaitingList");
                        listener.onIsUserRegister(boolResultFromServer);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    System.out.println("Error in volley use");
                    System.out.println("Error: " + error.toString());
                });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override
    /**    DONE     **/
    public void isFull(final RecyclerViewAdapter listener, String courseId) throws JSONException {
        myJson = new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();

        this.type = "CheckCourseIsFull";

        myCourse.put("courseId", courseId);

        moreAttributes.put("courseEntity", myCourse);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolResultFromServer = response.getJSONObject("moreAttributes").getBoolean("full");
                            listener.onIsFull(boolResultFromServer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error in volley use");
                System.out.println("Error: " + error.toString());
            }
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override
    /**    DONE     **/
    public void isUserRegistered(final RecyclerViewAdapter listener, String courseId, User user) throws JSONException {
        myUser = new JSONObject();
        myJson = new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();

        this.type = "IsUserRegisteredToCourse";

        myCourse.put("courseId", courseId);
        myUser.put("userId", user.getUser_id());

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolResultFromServer = response.getJSONObject("moreAttributes").getBoolean("registerdToCourse");
                            listener.onIsUserRegister(boolResultFromServer);
                            //System.out.println("Response: " + boolResultFromServer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error in volley use");
                System.out.println("Error: " + error.toString());
            }
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    /**    DONE     **/
    @Override
    public void isDatePassed(final RecyclerViewAdapter listener, String courseId) throws JSONException {
        myJson = new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();

        this.type = "IsDatedPassed";

        myCourse.put("courseId", courseId);

        moreAttributes.put("courseEntity", myCourse);

        myJson.put("type", type);
        myJson.put("moreAttributes", moreAttributes);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, activityUrl, myJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolResultFromServer = response.getJSONObject("moreAttributes").getBoolean("datePassed");
                            listener.onIsDatePassed(boolResultFromServer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error in volley use");
                System.out.println("Error: " + error.toString());
            }
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }
}
