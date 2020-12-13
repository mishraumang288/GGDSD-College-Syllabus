package com.example.syllabusagain.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.syllabusagain.PdfView;
import com.example.syllabusagain.R;

import com.example.syllabusagain.models.Download;
import com.google.android.material.card.MaterialCardView;



import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private Context context;
    public List<Download> DownloadList;
    Activity activity;


    public CourseAdapter(Context context, List<Download> DownloadList, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.DownloadList = DownloadList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_years, parent, false);
        return new CourseViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        String semNumbers = DownloadList.get(position).getSemNumber();
        holder.semNumber.setText("Sem " + semNumbers);

        switch (semNumbers) {
            case "1":
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_1));
                break;
            case "2":
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_2));
                break;
            case "3":
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_3));
                break;
            case "4":
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_4));
                break;
            case "5":
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_5));
                break;
            case "6":
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_6));
                break;
            default:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_background));
        }


        holder.semButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pdfIntent = new Intent(context, PdfView.class);
                pdfIntent.putExtra("LINK", DownloadList.get(position).getPdfLink());
                pdfIntent.putExtra("TITLE", DownloadList.get(position).getCourseName() + " Sem - " + DownloadList.get(position).getSemNumber());
                context.startActivity(pdfIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DownloadList.size();
    }


    class CourseViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView semButton;
        ImageView icon;
        TextView semNumber;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            semNumber = itemView.findViewById(R.id.item_sem_number);
            semButton = itemView.findViewById(R.id.sem);
            icon = itemView.findViewById(R.id.item_icon);
        }
    }

}
