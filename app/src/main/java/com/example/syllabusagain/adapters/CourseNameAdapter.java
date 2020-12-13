package com.example.syllabusagain.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syllabusagain.R;
import com.example.syllabusagain.models.CourseNameModel;
import com.example.syllabusagain.models.Download;
import com.example.syllabusagain.years;
import com.google.android.material.card.MaterialCardView;

import java.time.Year;
import java.util.List;


public class CourseNameAdapter extends RecyclerView.Adapter<CourseNameAdapter.CourseNameViewHolder> {

    private Context context;
    public List<CourseNameModel> DownloadList;
    Activity activity;


    public CourseNameAdapter(Context context, List<CourseNameModel> DownloadList, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.DownloadList = DownloadList;
    }

    @NonNull
    @Override
    public CourseNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseNameViewHolder holder, int position) {
        holder.CourseName.setText(DownloadList.get(position).getCourseName());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent semIntent = new Intent(context, years.class);
                semIntent.putExtra("COURSE_NAME", DownloadList.get(position).getCourseName());
                context.startActivity(semIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return DownloadList.size();
    }


    class CourseNameViewHolder extends RecyclerView.ViewHolder {

        TextView CourseName;
        MaterialCardView button;


        public CourseNameViewHolder(@NonNull View itemView) {
            super(itemView);
            CourseName = itemView.findViewById(R.id.course_item_name_text);
            button = itemView.findViewById(R.id.item_btn);
        }
    }

}
