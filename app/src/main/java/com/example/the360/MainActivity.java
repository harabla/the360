package com.example.the360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button maxPuttsSelect;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String puttsToday;
    Integer toGoal, intPuttsToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance("https://the360-70adc-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();
        getUserProfile();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            fillInHeader(uid);
        }


        //list buttons




        Button login, register, logout;
        login = findViewById(R.id.Login);
        register = findViewById(R.id.Register);
        logout = findViewById(R.id.logout);

        //Putting
        Button maxPuttsSelect = findViewById(R.id.maxputts);
        Button jylySelect = findViewById(R.id.jyly);

        //Driving
        Button accuracySelect = findViewById(R.id.accuracy);
        Button distanceSelect = findViewById(R.id.distance);
        Button speedSelect = findViewById(R.id.speed);

        //Round stats
        Button itbSelect = findViewById(R.id.inthebag);
        Button roundsummarySelect = findViewById(R.id.roundsum);

        //What happens on button clicks

        // Max putts selected
        maxPuttsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,maxPuttsSettings.class);
                startActivity(intent);
            }
        });

        jylySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,JylySettings.class);
                startActivity(intent);
            }
        });

        accuracySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.midterm,Toast.LENGTH_SHORT).show();
            }
        });

        distanceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.midterm,Toast.LENGTH_SHORT).show();
            }
        });

        speedSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.midterm,Toast.LENGTH_SHORT).show();
            }
        });

        itbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.longterm,Toast.LENGTH_SHORT).show();
            }
        });

        roundsummarySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.longterm,Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.shortterm,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.shortterm,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });


}





    public void getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name, email;
            email = user.getEmail();
            name = user.getDisplayName();

            //TextView welcomeText = (TextView) findViewById(R.id.mainHeader);
            //welcomeText.setText("Welcome "+ email + name);

            //String uid = user.getUid();

            Button login = (Button) findViewById(R.id.Login);
            Button register = (Button) findViewById(R.id.Register);
            login.setVisibility(View.INVISIBLE);
            register.setVisibility(View.INVISIBLE);



        } else {
            Toast.makeText(this, "Why you not logged in brodda?", Toast.LENGTH_SHORT).show();
            Button logout = (Button) findViewById(R.id.logout);
            logout.setVisibility(View.INVISIBLE);
        }
    }

    public void signOut() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
        // [END auth_sign_out]
        Button login = (Button) findViewById(R.id.Login);
        Button register = (Button) findViewById(R.id.Register);
        Button logout = (Button) findViewById(R.id.logout);
        login.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
        logout.setVisibility(View.INVISIBLE);

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
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });



    }



}

// this is a change