package com.example.syllabusagain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syllabusagain.adapters.CourseNameAdapter;
import com.example.syllabusagain.models.CourseNameModel;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;


public class MainActivity2 extends AppCompatActivity {
    MaterialCardView homeBtn, suggest_new;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Device");
    RecyclerView CourseCycle;
    public static final String DATABASE_PATH_DOWNLOADS = "syllabus";
    DatabaseReference mSyllabus_ref = database.getReference(DATABASE_PATH_DOWNLOADS);
    ArrayList<CourseNameModel> courseList = new ArrayList<>();
    ProgressDialog progressDialog;
    TextView offText;
    ImageView icon_off, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        @SuppressLint("HardwareIds")
        String id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        suggest_new = findViewById(R.id.suggestion_btn);
        CourseCycle = findViewById(R.id.Course_cycle);
        offText = findViewById(R.id.off_text);
        icon_off = findViewById(R.id.icon_off);
        info = findViewById(R.id.info);

        showLoading();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        CourseCycle.setLayoutManager(linearLayoutManager);
        CourseNameAdapter courseNameAdapter = new CourseNameAdapter(this, courseList, this);
        CourseCycle.setAdapter(courseNameAdapter);
        myRef.child(id).setValue("Online");


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


        mSyllabus_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String key = ds.getKey();
                        courseList.add(new CourseNameModel(key));
                        courseNameAdapter.notifyDataSetChanged();
                    }
                }
                hideLoading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideLoading();
            }
        });


        suggest_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuggestionDialogue suggestionDialogue = new SuggestionDialogue(MainActivity2.this);
                suggestionDialogue.SuggestThis();
            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingDialog ratingDialog = new RatingDialog(MainActivity2.this);
                ratingDialog.rateThis();
            }
        });


        myRef.child(id).onDisconnect().setValue("offline");
    }

    public void showLoading() {
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Updating Courses");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIcon(R.drawable.ic_cloud_on);
        progressDialog.show();
    }

    public void hideLoading() {
        progressDialog.dismiss();
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
        suggest_new.setVisibility(View.GONE);
    }
}