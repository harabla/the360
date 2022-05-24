package com.example.the360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class PuttingActivity extends AppCompatActivity {

    FloatingActionButton puttingSelectionTree, puttingSelectionTreeMP, puttingSelectionTreeRP, puttingSelectionTreeJyly;

    Animation rotate_open, rotate_close, from_bottom, to_bottom;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String puttsToday;
    Integer toGoal, intPuttsToday;

    boolean isFABOpen;

    RecyclerView scoreRecyclerView;
    puttingScoreAdapter puttingScoreAdapter;
    ArrayList<puttingScore> puttingScoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putting);

        getSupportActionBar().hide();

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        firebaseDatabase = FirebaseDatabase.getInstance("https://the360-70adc-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();


        getUserProfile();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            fillInHeader(uid);
        }


        //load animations
        rotate_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_open_anim);
        rotate_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_close_anim);
        from_bottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom_anim);
        to_bottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom_anim);

        //new buttons
        puttingSelectionTree = (FloatingActionButton) findViewById(R.id.puttingSelectionTree);

        // Putting
        Button maxPuttsSelect = findViewById(R.id.maxputts);
        Button jylySelect = findViewById(R.id.jyly);
        Button randomPuttsSelect = findViewById(R.id.randomPutts);

        maxPuttsSelect.setVisibility(View.INVISIBLE);
        jylySelect.setVisibility(View.INVISIBLE);
        randomPuttsSelect.setVisibility(View.INVISIBLE);

        // Nav buttons

        Button navMain = findViewById(R.id.navMain);
        Button navPutting = findViewById(R.id.navPutting);
        Button navDriving = findViewById(R.id.navDriving);
        Button navAnalysis = findViewById(R.id.navAnalysis);

        //recycler view
        scoreRecyclerView = findViewById(R.id.puttingRecyclerView);
        scoreRecyclerView.setHasFixedSize(true);
        scoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        puttingScoreList = new ArrayList<>();
        puttingScoreAdapter = new puttingScoreAdapter(this,puttingScoreList);
        scoreRecyclerView.setAdapter(puttingScoreAdapter);

        databaseReference.child("Max Putts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    puttingScore puttingScore = dataSnapshot.getValue(puttingScore.class);
                    puttingScoreList.add(puttingScore);

                }
                puttingScoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //tree onclicklisteners
        isFABOpen = false;

        puttingSelectionTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen) {
                    jylySelect.startAnimation(from_bottom);
                    maxPuttsSelect.startAnimation(from_bottom);
                    randomPuttsSelect.startAnimation(from_bottom);
                    isFABOpen=true;

                } else {
                    jylySelect.startAnimation(to_bottom);
                    maxPuttsSelect.startAnimation(to_bottom);
                    randomPuttsSelect.startAnimation(to_bottom);
                    isFABOpen=false;
                }

            }
        });




        // Max putts selected
        maxPuttsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuttingActivity.this,maxPuttsSettings.class);
                startActivity(intent);
            }
        });

        randomPuttsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuttingActivity.this,Randomputts.class);
                startActivity(intent);
            }
        });

        jylySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuttingActivity.this,JylySettings.class);
                startActivity(intent);
            }
        });

        // navigation buttons

        navMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuttingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        navPutting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        navDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PuttingActivity.this, DrivingActivity.class);
                startActivity(intent);

            }
        });

        navAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PuttingActivity.this, AnalysisActivity.class);
                startActivity(intent);

            }
        });

    }

    public void fillInHeader(String uid){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                TextView totalTries, leftTillTodaysGoal, daysSinceLast, totalPutts;

                totalTries = findViewById(R.id.totalTries);
                leftTillTodaysGoal = findViewById(R.id.leftTillTodaysGoal);
                daysSinceLast = findViewById(R.id.daysSinceLast);
                totalPutts = findViewById(R.id.totalPutts);

                ///total tries first
                Map<String, Object> totalsData = (Map<String, Object>) snapshot.child("totals").child(uid).getValue();
                String putts = totalsData.get("total putts").toString();
                totalPutts.setText(putts);

                //days since last
                Map<String, Object> userData = (Map<String, Object>) snapshot.child("user Data").child(uid).getValue();
                String lastTimePlayedTime = userData.get("Last time played").toString();
                Long time = System.currentTimeMillis()/1000;

                long longLastTimeplayed = Long.parseLong(lastTimePlayedTime);
                long diff = time - longLastTimeplayed;
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = (hours / 24);


                String strDays = String.valueOf(days);
                int intDays = (int) days;

                if (intDays < 1) {
                    daysSinceLast.setText("Today");
                } else {
                    daysSinceLast.setText(strDays + " days ago");
                }

                //to the 100t putts this year
                final Calendar today = Calendar.getInstance();

                Integer daysLeft = 365 - today.get(Calendar.DAY_OF_YEAR);
                String strDaysLeft = Integer.toString(daysLeft);

                final Calendar todayFilter = Calendar.getInstance();
                String yearToday = Integer.toString(todayFilter.get(Calendar.YEAR));
                String monthToday = Integer.toString(today.get(Calendar.MONTH));
                String dayToday = Integer.toString(today.get(Calendar.DATE));

                String todaysDateRightFormat = dayToday + "-" + monthToday + "-" + yearToday;


                // databaseReference.child("totals").child(uid).child("putt totals per day").child(todaysDateRightFormat).setValue(ServerValue.increment(0));

                Map<String, Object> totalsDataDates = (Map<String, Object>) snapshot.child("totals").child(uid).child("putt totals per day").getValue();



                if (totalsDataDates.containsKey(todaysDateRightFormat)) {
                    puttsToday = totalsDataDates.get(todaysDateRightFormat).toString();

                    intPuttsToday = Integer.valueOf(puttsToday);
                } else {
                    intPuttsToday = 0;
                }

                int intPutts = Integer.parseInt(putts);
                toGoal = ((100000 - intPutts) / daysLeft) - intPuttsToday;

                String strToGoal = Integer.toString(toGoal);

                leftTillTodaysGoal.setText(strToGoal);

                totalTries.setText(strDaysLeft);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PuttingActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name, email;
            email = user.getEmail();
            name = user.getDisplayName();

        } else {
            Toast.makeText(this, "Why you not logged in brodda?", Toast.LENGTH_SHORT).show();
            Button logout = (Button) findViewById(R.id.logout);
            logout.setVisibility(View.INVISIBLE);
        }
    }


}