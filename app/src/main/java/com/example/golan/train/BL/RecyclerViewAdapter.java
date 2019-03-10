package com.example.golan.train.BL;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.golan.train.R;

import java.util.List;

public class RecyclrerViewAdapter extends RecyclerView.Adapter<RecyclrerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Course> mCourseList;

    public RecyclrerViewAdapter(Context mContext, List<Course> mCourseList) {
        this.mContext = mContext;
        this.mCourseList = mCourseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.course_on_scheduler,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_courseName.setText(mCourseList.get(i).getCourseName());
        myViewHolder.tv_coachName.setText(mCourseList.get(i).getCoachName());
        myViewHolder.tv_time.setText(mCourseList.get(i).getTime());
        myViewHolder.courseStatusBtn.setText(mCourseList.get(i).getCourseStatusBtn());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_courseName;
        private TextView tv_coachName;
        private TextView tv_time;
        private Button courseStatusBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_courseName = (TextView)itemView.findViewById(R.id.courseNameIdOnScheduler);
            tv_coachName = (TextView)itemView.findViewById(R.id.coachNameIdOnScheduler);
            tv_time = (TextView)itemView.findViewById(R.id.timeIdOnScheduler);
            courseStatusBtn = (Button)itemView.findViewById(R.id.courseStatusIdOnScheduler);


        }
    }
}