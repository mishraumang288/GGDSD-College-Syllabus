package com.example.syllabusagain;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smileyrating.SmileyRating;

public class RatingDialog {
    private Activity activity;
    private AlertDialog dialog;
    RelativeLayout sent;
    Button send, share, close;
    EditText username, feedback;
    LinearLayout feedBox, main;
    ImageView gitUtsav, gitUmang, gmailUtsav, gmailUmang, LinkedInUtsav, LinkedInUmang, InstaUtsav, InstaUmang;
    TextView utsav_web;
    int rating;

    public RatingDialog(Activity myActivity) {
        activity = myActivity;
    }

    public void rateThis() {
        ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Please wait...");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference("feedbacks");


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.ratingdialog, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();

        main = dialog.findViewById(R.id.mainlay);
        sent = dialog.findViewById(R.id.feedSent);
        SmileyRating smileRating = (SmileyRating) dialog.findViewById(R.id.smile_rating);
        feedBox = dialog.findViewById(R.id.feedbackBox);
        send = dialog.findViewById(R.id.sendfeedback);
        username = dialog.findViewById(R.id.UserName);
        feedback = dialog.findViewById(R.id.UserFeedBack);
        close = dialog.findViewById(R.id.closefeedback);
        share = dialog.findViewById(R.id.share);


        gitUtsav = dialog.findViewById(R.id.github_utsav);
        gitUmang = dialog.findViewById(R.id.github_umang);
        gmailUtsav = dialog.findViewById(R.id.gmail_utsav);
        gmailUmang = dialog.findViewById(R.id.gmail_umang);
        LinkedInUtsav = dialog.findViewById(R.id.linkedIn_utsav);
        LinkedInUmang = dialog.findViewById(R.id.linkedIn_umang);
        InstaUtsav = dialog.findViewById(R.id.insta_utsav);
        InstaUmang = dialog.findViewById(R.id.insta_umang);
        utsav_web = dialog.findViewById(R.id.utsav_web);

        utsav_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gitIntent = new Intent(activity, WebScreen.class);
                gitIntent.putExtra("passingURL", "https://utsav-dev.netlify.app/");
                gitIntent.putExtra("passingTitle", "Portfolio");
                activity.startActivity(gitIntent);
            }
        });
        gitUtsav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gitIntent = new Intent(activity, WebScreen.class);
                gitIntent.putExtra("passingURL", "https://github.com/utsav-devadiga");
                gitIntent.putExtra("passingTitle", "Github");
                activity.startActivity(gitIntent);

            }
        });
        gitUmang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gitIntent = new Intent(activity, WebScreen.class);
                gitIntent.putExtra("passingURL", "https://github.com/mishraumang288");
                gitIntent.putExtra("passingTitle", "Github");
                activity.startActivity(gitIntent);
            }
        });
        gmailUtsav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"utsavdevadiga10@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Syllabus App");
                activity.startActivity(Intent.createChooser(intent, "Send mail..."));
            }
        });
        gmailUmang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mishraumang288@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Syllabus App");
                activity.startActivity(Intent.createChooser(intent, "Send mail..."));
            }
        });
        LinkedInUtsav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gitIntent = new Intent(activity, WebScreen.class);
                gitIntent.putExtra("passingURL", "https://www.linkedin.com/in/utsav-devadiga-a6b143179");
                gitIntent.putExtra("passingTitle", "LinkedIn");
                activity.startActivity(gitIntent);
            }
        });
        LinkedInUmang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gitIntent = new Intent(activity, WebScreen.class);
                gitIntent.putExtra("passingURL", "https://www.linkedin.com/in/umang-mishra-435003161");
                gitIntent.putExtra("passingTitle", "LinkedIn");
                activity.startActivity(gitIntent);
            }
        });
        InstaUtsav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gitIntent = new Intent(activity, WebScreen.class);
                gitIntent.putExtra("passingURL", "https://www.instagram.com/utsav_devadiga/?hl=en");
                gitIntent.putExtra("passingTitle", "Instagram");
                activity.startActivity(gitIntent);
            }
        });
        InstaUmang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gitIntent = new Intent(activity, WebScreen.class);
                gitIntent.putExtra("passingURL", "https://www.instagram.com/umang_miishra/");
                gitIntent.putExtra("passingTitle", "Instagram");
                activity.startActivity(gitIntent);
            }
        });


        main.setVisibility(View.VISIBLE);

        smileRating.setTitle(SmileyRating.Type.TERRIBLE, "Worst!");
        smileRating.setTitle(SmileyRating.Type.BAD, "Okayish");
        smileRating.setTitle(SmileyRating.Type.OKAY, "very Good!");
        smileRating.setTitle(SmileyRating.Type.GOOD, "Awesome!");
        smileRating.setTitle(SmileyRating.Type.GREAT, "Loved It!");


        smileRating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                rating = type.getRating();
                feedBox.setVisibility(View.VISIBLE);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(username.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(feedback.getWindowToken(), 0);
                pd.show();
                String name = username.getText().toString().trim();
                if (name.isEmpty()) {
                    pd.dismiss();
                    Toast.makeText(activity, "How Can I recognise you? \nPlease write your name ", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference myRef = db.child(name);
                    myRef.setValue(name);
                    DatabaseReference myfeed = myRef.child("feedback");
                    String feed = feedback.getText().toString().trim();
                    if (feed.isEmpty()) {
                        feed = "nothing";
                    }
                    myfeed.setValue(feed);
                    DatabaseReference myrate = database.getReference("feedbacks").child(name).child("rating");
                    myrate.setValue(rating).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            main.setVisibility(View.GONE);
                            sent.setVisibility(View.VISIBLE);
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("HardwareIds")
                String id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
                DatabaseReference myRef = database.getReference("Device").child(id);
                myRef.setValue("Share");

                DatabaseReference db = database.getReference("link");

                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String link = snapshot.getValue().toString();
                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                            String shareMessage = "\nLet me recommend you this application\n\n";
                            shareMessage = shareMessage + link;
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            activity.startActivity(Intent.createChooser(shareIntent, "choose one"));
                        } catch (Exception e) {
                            //e.toString();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }


}
