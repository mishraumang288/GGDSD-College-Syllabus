package com.example.syllabusagain;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SuggestionDialogue {
    private Activity activity;
    private AlertDialog dialog;
    Button send, close;
    RelativeLayout sent;
    LinearLayout feedBox;
    EditText username, suggestions_text;


    public SuggestionDialogue(Activity myActivity) {
        activity = myActivity;
    }

    public void SuggestThis() {
        ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Please wait...");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference("suggestion");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.sugestion_dialogue, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
        send = dialog.findViewById(R.id.sendSuggestion);
        username = dialog.findViewById(R.id.UserName);
        suggestions_text = dialog.findViewById(R.id.Usersuggestion);
        feedBox = dialog.findViewById(R.id.feedbackBox);
        sent = dialog.findViewById(R.id.feedSent);
        close = dialog.findViewById(R.id.closefeedback);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(username.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(suggestions_text.getWindowToken(), 0);
                pd.show();
                String name = username.getText().toString().trim();
                if (name.isEmpty()) {
                    pd.dismiss();
                    Toast.makeText(activity, "How Can I recognise you? \nPlease write your name ", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference myRef = db.child(name);
                    myRef.setValue(name);
                    DatabaseReference myfeed = myRef.child("suggestion");
                    String feed = suggestions_text.getText().toString().trim();
                    if (feed.isEmpty()) {
                        feed = "nothing";
                    }
                    myfeed.setValue(feed);
                    DatabaseReference myrate = database.getReference("suggestion").child(name).child("suggestion");
                    myrate.setValue(feed).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            sent.setVisibility(View.VISIBLE);
                            feedBox.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
