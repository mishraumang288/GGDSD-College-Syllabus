package com.example.syllabusagain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syllabusagain.adapters.CourseAdapter;
import com.example.syllabusagain.models.Download;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class years extends AppCompatActivity {

    ImageView back;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView semCycle;
    ArrayList<Download> downloadList = new ArrayList<>();
    TextView offText;
    ImageView icon_off, info;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);
        back = findViewById(R.id.back_Btn_sem);
        semCycle = findViewById(R.id.SemCycle);
        offText = findViewById(R.id.off_text);
        icon_off = findViewById(R.id.icon_off);
        info = findViewById(R.id.info);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        semCycle.setLayoutManager(gridLayoutManager);
        CourseAdapter courseAdapter = new CourseAdapter(this, downloadList, this);
        semCycle.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();
        showLoading();
        String courseName = getIntent().getStringExtra("COURSE_NAME");
        DatabaseReference myRef = database.getReference("syllabus").child(courseName);
        @SuppressLint("HardwareIds")
        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DatabaseReference myRefOnline = database.getReference("Device");
        myRefOnline.child(id).setValue("Online_V2");
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingDialog ratingDialog = new RatingDialog(years.this);
                ratingDialog.rateThis();
            }
        });

        if (isConnected()) {

        } else {
            hideLoading();
            showConnectionLost();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection Alert")
                    .setMessage("GO to Setting ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            //Creating dialog box
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshotList : snapshot.getChildren()) {
                        downloadList.add(new Download(snapshotList.child("courseName").getValue().toString(), snapshotList.child("semNumber").getValue().toString(), snapshotList.child("pdfLink").getValue().toString()));
                        courseAdapter.notifyDataSetChanged();
                    }
                }
                hideLoading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideLoading();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    public void showConnectionLost() {
        icon_off.setVisibility(View.VISIBLE);
        offText.setVisibility(View.VISIBLE);
        semCycle.setVisibility(View.GONE);
    }

    public void showLoading() {
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Getting Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIcon(R.drawable.ic_cloud_on);
        progressDialog.show();
    }

    public void hideLoading() {
        progressDialog.dismiss();
    }
}