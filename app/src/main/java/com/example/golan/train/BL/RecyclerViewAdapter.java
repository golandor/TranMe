package com.example.golan.train.BL;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.golan.train.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mcContext;
    private ArrayList<Course> mCourseList;
    private Dialog myDialog;
    private String finalCourseStatus;
    private int currentNumOfUsersInCourse;
    private int maxNumberOfPeopleInCourse;
    private FirebaseAuth firebaseAuth;
    private String user_id;
    private DatabaseReference myRef;
    private DatabaseReference courseRegisterRef;
    private String course_id;
    private Button dialog_course_status;
    private static TextView course_status_on_scheduler;
    private User user;
    private MyViewHolder vHolder;
    private Calendar calendar;
    private SimpleDateFormat currentTimeToString,mdFormat;

    private MyService myService;

    public RecyclerViewAdapter(Context mcContext, ArrayList<Course> mCourseList){
        this.mcContext = mcContext;
        this.mCourseList = mCourseList;
    }

    // here is the dialog screen
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mcContext).inflate(R.layout.course_on_scheduler, viewGroup, false);
        vHolder = new MyViewHolder(v);
        initFireBase();
        myService = new MyService(mcContext);
        course_status_on_scheduler = v.findViewById(R.id.courseStatusIdOnScheduler);

        setDialog(vHolder);

        return vHolder;
    }

    private void setTimeCheck() {
        currentTimeToString = new SimpleDateFormat("HH:mm:ss");
        String saveCurrentTimeToString = currentTimeToString.format(calendar.getTime());
    }


    private void set_dialog_course_status(final MyViewHolder vHolder, final String course_id){
        myRef.child(mcContext.getString(R.string.courses)).child(course_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                        if(dataSnapshot.child("registered").child(user_id).exists()) {
                        try {
                          //  System.out.println("before");
                            //myService.rateCourse(course_id,user_id,3);

                            if(myService.isUserRegistered(course_id,user_id)) {
                                System.out.println("after");
                                // The user is registered to this course
                               // finalCourseStatus =mcContext.getString(R.string.cancel_regisration);
                            }
                            else{
                                if(isFull(dataSnapshot)){
                                    if(!isOnWaitingList(dataSnapshot)) {
                                        // join to waiting list
                                        finalCourseStatus = mcContext.getString(R.string.waitingList);
                                    }
                                    else{
                                        try {
                                            int numOfUserOnWaitingList = Integer.parseInt(dataSnapshot.child(mcContext.getString(R.string.waitingListOnFB)).child(user_id)
                                                    .child("position").getValue().toString());
                                            finalCourseStatus = " Your place is: " + String.valueOf(numOfUserOnWaitingList);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                else{
                                    // The course is not full
                                    //  System.out.println("here: " +  String.valueOf(currentNumOfUsersInCourse) + "/" + String.valueOf(maxNumberOfPeopleInCourse));
                                    finalCourseStatus = String.valueOf(currentNumOfUsersInCourse) + "/" + String.valueOf(maxNumberOfPeopleInCourse);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // if date is passed
                        try {
                            if(isDatePassed(dataSnapshot)){
                                if(dataSnapshot.child("registered").child(user_id).exists()) {
                                    // The user is registered to this course
                                    finalCourseStatus = "Rate this course";
                                }
                                else{
                                    finalCourseStatus ="";
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        finalCourseStatus = mcContext.getString(R.string.waitingList);

                        dialog_course_status.setText(finalCourseStatus);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setDialog(final MyViewHolder vHolder){

        myDialog = new Dialog(mcContext);
        myDialog.setContentView(R.layout.fragment_course__details_);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.course_on_scheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //init attributes

                TextView dialog_course_name = myDialog.findViewById(R.id.courseNameOnCourseDetails);
                TextView dialog_course_location = myDialog.findViewById(R.id.courseLocationOnCourseDetails);
                TextView dialog_course_description = myDialog.findViewById(R.id.descriptionOnCourseDetails);
                TextView dialog_course_name2 = myDialog.findViewById(R.id.courseNameOnCourseDetails2);
                final TextView dialog_coach_name = myDialog.findViewById(R.id.coachNameIdOnDetails);
                TextView dialog_course_time = myDialog.findViewById(R.id.timeIdOnDetails);
                dialog_course_status = myDialog.findViewById(R.id.courseStatusIdOnDetails);

                // taking the data from the vHolder for now // TODO: 02/01/2019 take from firebase
                mCourseList.get(vHolder.getAdapterPosition()).setCourseLocation("Room 18");
                // mCourseList.get(vHolder.getAdapterPosition()).setCourseDescription("Spinning is a brand of indoor bicycles and indoor cycling instruction classes" +
                //      " distributed and licensed by the American health and fitness company Mad Dog Athletics.");
                dialog_course_name.setText(mCourseList.get(vHolder.getAdapterPosition()).getCourseName());
                dialog_course_location.setText(mCourseList.get(vHolder.getAdapterPosition()).getCourseLocation());
                // dialog_course_description.setText(mCourseList.get(vHolder.getAdapterPosition()).getCourseDescription());
                dialog_course_name2.setText(mCourseList.get(vHolder.getAdapterPosition()).getCourseName());
                dialog_coach_name.setText(mCourseList.get(vHolder.getAdapterPosition()).getTrainerName());
                dialog_course_time.setText(mCourseList.get(vHolder.getAdapterPosition()).getTime());

                course_id = mCourseList.get(vHolder.getAdapterPosition()).getCourseId();
                set_dialog_course_status(vHolder,course_id);

                dialog_course_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef.child(mcContext.getString(R.string.courses)).child(course_id)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        joinToWaitingList(vHolder, vHolder.getAdapterPosition());

                                       // isOnWaitingList(dataSnapshot);
                                        try {
                                            if (!isDatePassed(dataSnapshot)) {
                                                if ((!isFull(dataSnapshot)) && (!isUserRegistered(dataSnapshot))) {

                                                    saveRegisterDetails(vHolder.getAdapterPosition());
                                                } else if (isUserRegistered(dataSnapshot)) {

                                                    deleteUserFromCourse(vHolder, vHolder.getAdapterPosition());
                                                } else if ((isFull(dataSnapshot)) && (!isUserRegistered(dataSnapshot)) && (!isOnWaitingList(dataSnapshot))) {

                                                   // joinToWaitingList(vHolder, vHolder.getAdapterPosition());
                                                }
                                            }
                                            else{
                                                if(dataSnapshot.child("registered").child(user_id).exists()) {
                                                    // The user is registered to this course
                                                    rateCourse(dataSnapshot);
                                                }
                                                else{
                                                    //TODO make nothing when clicked
                                                }
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                });
                myDialog.show();
            }
        });
    }

    private void rateCourse(DataSnapshot dataSnapshot) {
        // myDialog.setContentView(R.layout.fragment_my_zone_);
        // myDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //    myDialog.hide();
        //  rateDialog.show();
    }

    private boolean isFull(DataSnapshot dataSnapshot){
        //TODO Check if it is ok!!
        //  boolean result = myService.isFull(dataSnapshot.child("courseId").getValue().toString());
        //  currentNumOfUsersInCourse = Integer.parseInt(dataSnapshot.child("courseStatusBtnText").getValue().toString());
        //  maxNumberOfPeopleInCourse = Integer.parseInt(dataSnapshot.child("maxNumOfUsersInCourse").getValue().toString());

        //  if (currentNumOfUsersInCourse == maxNumberOfPeopleInCourse) {
        //      return true;
        //  }
        return true;
    }
    private boolean isOnWaitingList(DataSnapshot dataSnapshot){
//        boolean result = myService.isOnWaitingList(dataSnapshot.child("courseId").getValue().toString());

        if(dataSnapshot.child(mcContext.getString(R.string.waitingListOnFB)).child(user_id).exists()) {
            return true;
        }
        return false;
//        return result;
    }
    private boolean isUserRegistered(DataSnapshot dataSnapshot){
        if(dataSnapshot.child("registered").child(user_id).exists()) {
            return true;
        }
        return false;
    }
    private boolean isDatePassed(DataSnapshot dataSnapshot) throws ParseException {
        calendar = Calendar.getInstance();
        mdFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = mdFormat.format(calendar.getTime());
        String courseDate = dataSnapshot.child("date").getValue().toString();
        Date current_Date = mdFormat.parse(currentDate);
        Date course_Date = mdFormat.parse(courseDate);

        if (course_Date.compareTo(current_Date) < 0) {
            return true;
        }
        return false;
    }

    private void joinToWaitingList(final MyViewHolder myViewHolder,final int position) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(!dataSnapshot.child(mcContext.getString(R.string.courses)).child(course_id)
//                                .child(mcContext.getString(R.string.waitingListOnFB)).child(user_id).exists()) {

                final User myUser = getUserByDataSnapshot(dataSnapshot);
                try {
                    System.out.println("CourseId: " + course_id + "user: " + myUser.getFullName());
                    //myService.addUserToWaitingList(course_id, myUser);
                    //myService.RemoveUserFromWaitingList(course_id, myUser);
                    //myService.registerUserToCourse(course_id, myUser);
                    myService.deleteUserFromCourse(course_id, user_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                            String pos = String.valueOf(dataSnapshot.child(mcContext.getString(R.string.courses)).child(course_id)
//                                    .child(mcContext.getString(R.string.waitingListOnFB)).getChildrenCount() + 1);
//
//                            HashMap eventMap = new HashMap();
//                            eventMap.put("user_id", myUser.getUser_id());
//                            eventMap.put("full_name", myUser.getFullName());
//                            eventMap.put("position",pos);
//
//                            courseRegisterRef.child(course_id).child(mcContext.getString(R.string.waitingListOnFB)).child(user_id)
//                                    .updateChildren(eventMap).addOnCompleteListener(new OnCompleteListener() {
//                                @Override
//                                public void onComplete(@NonNull Task task) {
//                                    Toast.makeText(mcContext,"In Waiting List",Toast.LENGTH_SHORT).show();
//                                }
//                            });
            }
            //                    }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myDialog.hide();
    }

    private void deleteUserFromCourse(final MyViewHolder myViewHolder,final int position) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentNumOfUsersInCourse =  Integer.parseInt(dataSnapshot
                            .child(mcContext.getString(R.string.courses)).child(course_id).child("currentNumOfUsersInCourse")
                            .getValue().toString());
                    currentNumOfUsersInCourse--;

                    myRef.child(mcContext.getString(R.string.courses)).child(course_id).child("currentNumOfUsersInCourse").setValue(String.valueOf(currentNumOfUsersInCourse));
                    myDialog.hide();
                    // update the list that the user was deleted
                    mCourseList.get(position).setCurrentNumOfUsersInCourse(String.valueOf(currentNumOfUsersInCourse));
                    dataSnapshot.child(mcContext.getString(R.string.courses)).child(course_id).child("registered")
                            .child(user_id).getRef().removeValue();
                    Toast.makeText(mcContext,"Cancel Registration ",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveRegisterDetails(final int position) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if(!dataSnapshot.child(mcContext.getString(R.string.courses)).child(course_id).child("registered").child(user_id).exists()){

                        User myUser = getUserByDataSnapshot(dataSnapshot);
                        HashMap eventMap = new HashMap();
                        eventMap.put("user_id", myUser.getUser_id());
                        eventMap.put("full_name", myUser.getFullName());

                        courseRegisterRef.child(course_id).child("registered").child(user_id)
                                .updateChildren(eventMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                courseRegisterRef.child(course_id).child("currentNumOfUsersInCourse").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        currentNumOfUsersInCourse = Integer.parseInt(dataSnapshot.getValue().toString());
                                        currentNumOfUsersInCourse++;
                                        courseRegisterRef.child(course_id).child("currentNumOfUsersInCourse").setValue(String.valueOf(currentNumOfUsersInCourse));

                                        // update the list that the user was registered
                                        mCourseList.get(position).setCurrentNumOfUsersInCourse(String.valueOf(currentNumOfUsersInCourse));
                                        Toast.makeText(mcContext,"Registered Successfully ",Toast.LENGTH_SHORT).show();
//                                            jsonParse();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myDialog.hide();
    }




    // here is the main scheduler screen
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.courseName_on_scheduler.setText(mCourseList.get(i).getCourseName());
        myViewHolder.coachName_on_scheduler.setText(mCourseList.get(i).getTrainerName());
        myViewHolder.time_on_scheduler.setText(mCourseList.get(i).getTime());
        currentNumOfUsersInCourse = Integer.parseInt(mCourseList.get(myViewHolder.getAdapterPosition()).getCurrentNumOfUsersInCourse());
        maxNumberOfPeopleInCourse = Integer.parseInt(mCourseList.get(myViewHolder.getAdapterPosition()).getMaxNumOfUsersInCourse());
        RecyclerViewAdapter.course_status_on_scheduler.setText(mcContext.getString(R.string.details));
    }



    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public User getUserByDataSnapshot(DataSnapshot dataSnapshot){
        String fullName = dataSnapshot.child(mcContext.getString(R.string.users)).child(user_id).child("fullName").getValue().toString();
        String gender = dataSnapshot.child(mcContext.getString(R.string.users)).child(user_id).child("gender").getValue().toString();
        double height = Double.valueOf(dataSnapshot.child(mcContext.getString(R.string.users)).child(user_id).child("height").getValue().toString());
        double weigh = Double.valueOf(dataSnapshot.child(mcContext.getString(R.string.users)).child(user_id).child("weigh").getValue().toString());
        user =  new User(fullName,user_id,gender,weigh,height);
        return user;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout course_on_scheduler;
        private TextView courseName_on_scheduler;
        private TextView coachName_on_scheduler;
        private TextView time_on_scheduler;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName_on_scheduler =  itemView.findViewById(R.id.courseNameIdOnScheduler);
            coachName_on_scheduler =  itemView.findViewById(R.id.coachNameIdOnScheduler);
            time_on_scheduler = itemView.findViewById(R.id.timeIdOnScheduler);
            course_on_scheduler = itemView.findViewById(R.id.course_on_scheduler_id);
        }
    }
    public void initFireBase(){
        firebaseAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference();
        if (firebaseAuth.getCurrentUser() != null) {

        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user_id = user.getUid();
        courseRegisterRef = FirebaseDatabase.getInstance().getReference(mcContext.getString(R.string.courses));
    }
}