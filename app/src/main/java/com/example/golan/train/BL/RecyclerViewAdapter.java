package com.example.golan.train.BL;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.golan.train.Activities.MainActivity;
import com.example.golan.train.BlueTooth.DeviceScanActivity;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.provider.Settings.System.getString;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private RecyclerViewAdapter mAdapter = this;
    private Context mcContext;
    private ArrayList<Course> mCourseList;
    private Dialog myDialog;
    private String finalCourseStatus;
    private int currentNumOfUsersInCourse;
    private int maxNumberOfPeopleInCourse;
    private FirebaseAuth firebaseAuth;
    private String user_id;
    private String token;
    private DatabaseReference myRef;
    private DatabaseReference courseRegisterRef;
    private String course_id;
    private Button dialog_course_status;
    private Button startTrainingBtn;
    private static TextView course_status_on_scheduler;
    private User user;
    private MyViewHolder vHolder;
    private Calendar calendar;
    private SimpleDateFormat currentTimeToString,mdFormat;
    private MyService myService;
    private SmileRating smileRating;


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

    private void set_dialog_course_status(final MyViewHolder vHolder, final String course_id) {
        myRef.child(mcContext.getString(R.string.courses)).child(course_id).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               if i want to use this 2 lines i should make the eventListener on myRef
//               final User myUser;
//               myUser = getUserByDataSnapshot(dataSnapshot);

               try {
                   if(isCurrentDate(dataSnapshot)&&(isUserRegistered(dataSnapshot))){
                           startTrainingBtn.setVisibility(View.VISIBLE);
                   }else{
                       startTrainingBtn.setVisibility(View.INVISIBLE);
                   }
                   if(!isDatePassed(dataSnapshot)){
                       if(!isFull(dataSnapshot)){
                           if(!isUserRegistered(dataSnapshot)){
                               if(dataSnapshot.child("waitingList").exists()) {
                                   if(isOnWaitingList(dataSnapshot)){
                                       finalCourseStatus = (currentNumOfUsersInCourse) + "/" + (maxNumberOfPeopleInCourse);
                                   }else{
                                       finalCourseStatus = "Course Is Full";
                                   }
                               }else {
                                   finalCourseStatus = (currentNumOfUsersInCourse) + "/" + (maxNumberOfPeopleInCourse);
                               }
                           }else{  // isUserRegistered() is TRUE
                               finalCourseStatus = mcContext.getString(R.string.cancel_regisration);
                           }
                       }else{  //  isFull() is TRUE
                           if(!isUserRegistered(dataSnapshot)){
                               if(!isOnWaitingList(dataSnapshot)){
                                   if(dataSnapshot.child("waitingList").exists()) {
                                       finalCourseStatus = "Course Is Full";
                                   }else { // waiting list is not exist
                                       finalCourseStatus = mcContext.getString(R.string.waitingList);
                                   }
                               }else{  // isOnWaitingList is TRUE
                                   int numOfUserOnWaitingList = Integer.parseInt(dataSnapshot.child(mcContext.getString(R.string.waitingListOnFB)).child(user_id)
                                           .child("position").getValue().toString());
                                   finalCourseStatus = " Your place is: " + String.valueOf(numOfUserOnWaitingList);
                               }
                           }else{  // isUserRegistered() is TRUE
                               finalCourseStatus = mcContext.getString(R.string.cancel_regisration);
                           }
                       }
                   }else{  //  isDatePassed() is TRUE
                       if(!isUserRegistered(dataSnapshot)){
                           finalCourseStatus = "";
                       }else{  // isUserRegistered() is TRUE
                           finalCourseStatus = mcContext.getString(R.string.rateCourse);
                       }
                   }
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               dialog_course_status.setText(finalCourseStatus);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    private void setSmileRatingOn(final String course_id, final User user) {
        smileRating.setVisibility(View.VISIBLE);
        smileRating.setTextSelectedColor(Color.GREEN);

        smileRating.setNameForSmile(BaseRating.TERRIBLE, "TERRIBLE");
        smileRating.setNameForSmile(BaseRating.BAD, "BAD");
        smileRating.setNameForSmile(BaseRating.OKAY, "OKAY");
        smileRating.setNameForSmile(BaseRating.GOOD, "GOOD");
        smileRating.setNameForSmile(BaseRating.GREAT, "GREAT");

        smileRating.setOnSmileySelectionListener((smiley, reselected) -> {
            // reselected is false when user selects different smiley that previously selected one
            // true when the same smiley is selected.
            // Except if it first time, then the value will be false.
            switch (smiley) {

                case SmileRating.TERRIBLE:
                    break;
                case SmileRating.BAD:
                    break;
                case SmileRating.OKAY:
                    break;
                case SmileRating.GOOD:
                    break;
                case SmileRating.GREAT:
                    break;
            }
        });

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                try {
                    myService.rateCourse(course_id,user,level);
                    myDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                dialog_course_description.setMovementMethod(new ScrollingMovementMethod());

                TextView dialog_course_name2 = myDialog.findViewById(R.id.courseNameOnCourseDetails2);
                final TextView dialog_coach_name = myDialog.findViewById(R.id.coachNameIdOnDetails);
                TextView dialog_course_time = myDialog.findViewById(R.id.timeIdOnDetails);
                dialog_course_status = myDialog.findViewById(R.id.courseStatusIdOnDetails);
                startTrainingBtn = myDialog.findViewById(R.id.startTraining);
                smileRating = myDialog.findViewById(R.id.rate);

                //TODO
                // startTrainingBtn setVisibility only at the right time

                startTrainingBtn.setVisibility(View.INVISIBLE);
                smileRating.setVisibility(View.INVISIBLE);

                dialog_course_location.setText(mCourseList.get(vHolder.getAdapterPosition()).getCourseLocation());
                dialog_course_name.setText(mCourseList.get(vHolder.getAdapterPosition()).getCourseName());
                dialog_course_location.setText(mCourseList.get(vHolder.getAdapterPosition()).getCourseLocation());
                dialog_course_description.setText(mCourseList.get(vHolder.getAdapterPosition()).getDescription());
                dialog_course_name2.setText(mCourseList.get(vHolder.getAdapterPosition()).getCourseName());
                dialog_coach_name.setText(mCourseList.get(vHolder.getAdapterPosition()).getTrainerName());
                dialog_course_time.setText(mCourseList.get(vHolder.getAdapterPosition()).getTime());

                course_id = mCourseList.get(vHolder.getAdapterPosition()).getCourseId();
                set_dialog_course_status(vHolder,course_id);
                setStartTrainingBtn(course_id);


                dialog_course_status.setOnClickListener(v1 ->
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final User user1 = getUserByDataSnapshot(dataSnapshot);
                                DataSnapshot snap = dataSnapshot.child(mcContext.getString(R.string.courses)).child(course_id);
                                try {
                                    if(!isDatePassed(snap)){
                                        if(!isFull(snap)){
                                            if(!isUserRegistered(snap)){
                                                if(snap.child("waitingList").exists()) {
                                                        // do nothing
                                                }else{
                                                    myService.registerUserToCourse(mAdapter, course_id, user1);
                                                    currentNumOfUsersInCourse = Integer.parseInt(snap.child("currentNumOfUsersInCourse").getValue().toString());
                                                    myDialog.hide();
                                                }
                                            }else{  // isUserRegistered() is TRUE
                                                if(snap.child("waitingList").exists()) {

                                                }else{
                                                    myService.deleteUserFromCourse(course_id, mCourseList.get(vHolder.getAdapterPosition()).getCourseName(), user1);
                                                    currentNumOfUsersInCourse = Integer.parseInt(snap.child("currentNumOfUsersInCourse").getValue().toString());
                                                    myDialog.hide();
                                                }
                                            }
                                        }else{  //  isFull() is TRUE
                                            if(!isUserRegistered(snap)){
                                                if(!isOnWaitingList(snap)){
                                                    if(!snap.child("waitingList").exists()) {
                                                        myService.addUserToWaitingList(course_id, user1);
                                                        myDialog.hide();
                                                    }
                                                }else{  // isOnWaitingList is TRUE
                                                }
                                            }else{  // isUserRegistered() is TRUE
                                                myService.deleteUserFromCourse(course_id, mCourseList.get(vHolder.getAdapterPosition()).getCourseName(), user1);
                                                currentNumOfUsersInCourse = Integer.parseInt(snap.child("currentNumOfUsersInCourse").getValue().toString());
                                                myDialog.hide();
                                            }
                                        }
                                    }else{  //  isDatePassed() is TRUE
                                        if(isUserRegistered(snap)){
                                            setSmileRatingOn(course_id,user1);
                                        }
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        }));
                myDialog.show();
            }
        });
    }

    private void setStartTrainingBtn(String CourseId){
        startTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcContext,DeviceScanActivity.class);
                intent.putExtra("courseId",course_id);
                intent.putExtra("userId",user_id);

                mcContext.startActivity(intent);
            }
        });
    }

    private boolean isFull(DataSnapshot dataSnapshot){
          currentNumOfUsersInCourse = Integer.parseInt(dataSnapshot.child("currentNumOfUsersInCourse").getValue().toString());
          maxNumberOfPeopleInCourse = Integer.parseInt(dataSnapshot.child("maxNumOfUsersInCourse").getValue().toString());
          if (currentNumOfUsersInCourse == maxNumberOfPeopleInCourse) {
              return true;
          }
        return false;
    }
    private boolean isOnWaitingList(DataSnapshot dataSnapshot){

        if(dataSnapshot.child(mcContext.getString(R.string.waitingListOnFB)).child(user_id).exists()) {
            return true;
        }
        return false;
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

    private boolean isCurrentDate(DataSnapshot dataSnapshot) throws ParseException {
        calendar = Calendar.getInstance();
        mdFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = mdFormat.format(calendar.getTime());
        String courseDate = dataSnapshot.child("date").getValue().toString();
        Date current_Date = mdFormat.parse(currentDate);
        Date course_Date = mdFormat.parse(courseDate);

        if (course_Date.compareTo(current_Date) == 0) {
            return true;
        }
        return false;
    }

    public void onIsUserRegister(boolean isRegistered) {
        Log.d(TAG, "onIsUserRegister: " + isRegistered);
    }
    public void onIsDatePassed(boolean isDatePassed) {
        Log.d(TAG, "onIsDatePassed: " + isDatePassed);
    }
    public void onIsFull(boolean isFull) {
        Log.d(TAG, "onIsFull: " + isFull);
    }

    public void onIsOnWaitingList(boolean isOnWaitingList) {
        Log.d(TAG, "onIsOnWaitingList: " + isOnWaitingList);
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
        int age = Integer.valueOf(dataSnapshot.child(mcContext.getString(R.string.users)).child(user_id).child("age").getValue().toString());
        double weigh = Double.valueOf(dataSnapshot.child(mcContext.getString(R.string.users)).child(user_id).child("weigh").getValue().toString());
        long userNumber = Long.parseLong(dataSnapshot.child(mcContext.getString(R.string.users)).child(user_id).child("userNumber").getValue().toString());
        user =  new User(fullName,user_id,gender,token,weigh,age,userNumber);
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
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        token = task.getResult().getToken();
//                        System.out.println("TOken: " + token);
                    }
                });
    }


}