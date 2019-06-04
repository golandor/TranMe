package com.example.golan.train.BL;

import android.content.Context;
import android.os.AsyncTask;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyService implements IMyService {

    private RequestQueue mQueue;
    private String type;
    private String stringResultFromServer;
    private boolean boolResultFromServer;

    private Context context;
    private String url = "http://192.168.1.21:8080/trainme/activity";

    private JSONObject myUser;
    private JSONObject myJson;
    private JSONObject myCourse;
    private JSONObject moreAttributes;
    private CountDownLatch countDownLatch;



    public MyService(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(this.context);
        this.countDownLatch=new CountDownLatch(1);
    }


    @Override                                /**    DONE     **/
    public void addUserToWaitingList(String courseId, User user) throws JSONException {

        myUser= new JSONObject();
        myJson= new JSONObject();
        myCourse = new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "JoinToWaitingList";

        myCourse.put("courseId",courseId);

        myUser.put("fullName",user.getFullName());
        myUser.put("height",user.getHeight());
        myUser.put("gender",user.getGender());
        myUser.put("userId",user.getUser_id());
        myUser.put("weigh",user.getWeigh());

        moreAttributes.put("courseEntity", myCourse);
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

        myUser= new JSONObject();
        myJson= new JSONObject();
        myCourse = new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "RemoveUserFromWaitingList";

        myCourse.put("courseId",courseId);

        myUser.put("userId",user.getUser_id());

        moreAttributes.put("courseEntity", myCourse);
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
    public void rateCourse(String courseId, User user, int rate) throws JSONException {
        myUser= new JSONObject();
        myJson= new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();

        this.type = "RateCourse";

        myCourse.put("courseId",courseId);

        myUser.put("userId",user.getUser_id());

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);
        moreAttributes.put("rate", rate);

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
    public void registerUserToCourse(String courseId, User user) throws JSONException {

        myUser= new JSONObject();
        myJson= new JSONObject();
        myCourse = new JSONObject();
        moreAttributes = new JSONObject();

        this.type = "AddUserToCourse";

        myCourse.put("courseId",courseId);

        myUser.put("fullName",user.getFullName());
        myUser.put("height",user.getHeight());
        myUser.put("gender",user.getGender());
        myUser.put("userId",user.getUser_id());
        myUser.put("weigh",user.getWeigh());

        moreAttributes.put("courseEntity", myCourse);
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
    public void deleteUserFromCourse(String courseId, User user) throws JSONException {
        myUser= new JSONObject();
        myJson= new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();

        this.type = "RemoveUserFromCourse";

        myCourse.put("courseId",courseId);
        myUser.put("userId",user.getUser_id());

        moreAttributes.put("courseEntity", myCourse);
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
                System.out.println(error.toString());
            }
        });
        MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
    }

    @Override                                /**    DONE     **/
    public void writeHRList(String courseId, String userId, List<Integer> hrList) throws JSONException {
        myUser= new JSONObject();
        myJson= new JSONObject();
        myCourse = new JSONObject();

        moreAttributes = new JSONObject();
        JSONArray hrJsonArray=new JSONArray();
        for (int hr: hrList){
            hrJsonArray.put(hr);
        }

        this.type = "WriteHR";

        myCourse.put("courseId",courseId);

        myUser.put("userId",userId);

        moreAttributes.put("courseEntity", myCourse);
        moreAttributes.put("user", myUser);
        moreAttributes.put("hrlist", hrJsonArray);

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
    public boolean isUserRegistered(String courseId, User user) throws JSONException {
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

      // Thread thread=new Thread() {
        //   @Override
           //public void run() {
               JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, myJson,
                       new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {
                               try {
                                   boolResultFromServer = response.getJSONObject("moreAttributes").getBoolean("registerdToCourse");
                                   System.out.println("Response: " + boolResultFromServer);

                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }

                               //  countDownLatch.countDown();
                           }
                       }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       System.out.println("Error in volley use");
                       System.out.println("Error: " + error.toString());
                      // countDownLatch.countDown();

                   }
               });
               System.out.println("before mySingelton");
               MySingelton.getInstance(context).addToRequesrtQueue(jsonObjectRequest);
       
        //countDownLatch.countDown();
         //  }
     //  };

       // try {
            //thread.start();
            //thread.join();
            //Thread.currentThread().wait(5000);
            //Thread.currentThread().join();
            //thread.join();
//            System.out.println(countDownLatch.getCount());
            //countDownLatch.await();
            return boolResultFromServer;
       // } catch (InterruptedException e) {
           // e.printStackTrace();
          //  return boolResultFromServer;
       // }

    }

    @Override
    public boolean isDatePassed() {
        return false;
    }
//save the context recievied via constructor in a local variable

}

// class TestVolly{
//    private JSONObject myUser;
//    private JSONObject myJson;
//    private JSONObject myCourse;
//    private JSONObject moreAttributes;
//    private String url = "http://192.168.1.21:8080/trainme/activity";
//    private String type;
//
//    public JSONObject fetchModules(Context ctx) throws JSONException {
//        JSONObject response = null;
//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//
//        myUser = new JSONObject();
//        myJson = new JSONObject();
//        myCourse = new JSONObject();
//
//        moreAttributes = new JSONObject();
//
//        this.type = "IsUserRegisteredToCourse";
//
//        myCourse.put("courseId", "10:18:17:911");
//        myUser.put("userId", "zB98ExOolaPIuPqLrZav6YLBoOt2");
//
//        moreAttributes.put("courseEntity", myCourse);
//        moreAttributes.put("user", myUser);
//
//        myJson.put("type", type);
//        myJson.put("moreAttributes", moreAttributes);
//
//
//        RequestFuture<JSONObject> future = RequestFuture.newFuture();
//        JsonObjectRequest request = new JsonObjectRequest(url,myJson,future,future);
//        requestQueue.add(request);
//
//
//        try {
//            response = future.get(3, TimeUnit.SECONDS); // Blocks for at most 10 seconds.
//        } catch (InterruptedException e) {
//            System.out.println("interrupted");
//        } catch (ExecutionException e) {
//            System.out.println("execution");
//
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//        if(response!=null) {
//            System.out.println(response.getJSONObject("moreAttributes").getBoolean("registerdToCourse"));
//        }
//        else{
//            System.out.println("Response is null");
//        }
//        return response;
//    }
//}
//
//class MyVolleyAsyncTask extends AsyncTask<String,String, JSONObject> {
//
//    private Context ctx;
//
//    public MyVolleyAsyncTask(Context hostContext)
//    {
//        ctx = hostContext;
//    }
//
//
//
//    @Override
//    protected JSONObject doInBackground(String... params) {
//
//        // Method runs on a separate thread, make all the network calls you need
//        TestVolly tester = new TestVolly();
//
//        try {
//            return tester.fetchModules(ctx);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    @Override
//    protected void onPostExecute(JSONObject result)
//    {
//        //System.out.println("RESULT: " + result.toString());
//        // runs on the UI thread
//        // do something with the result
//    }
//}