package com.example.the360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class mpStatistics extends AppCompatActivity {


    DatabaseReference databaseReference, totalAttemptedDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp_statistics);

        TextView mpScores10header = findViewById(R.id.mpScores10header);
        TextView mpScores8header = findViewById(R.id.mpScores8header);
        TextView mpScores7header = findViewById(R.id.mpScores7header);
        TextView mpScores6header = findViewById(R.id.mpScores6header);
        TextView mpScores5header = findViewById(R.id.mpScores5header);
        TextView mpScores4header = findViewById(R.id.mpScores4header);

        EditText mpScores10target = findViewById(R.id.mpScores10target);
        EditText mpScores9target = findViewById(R.id.mpScores9target);
        EditText mpScores8target = findViewById(R.id.mpScores8target);
        EditText mpScores7target = findViewById(R.id.mpScores7target);
        EditText mpScores6target = findViewById(R.id.mpScores6target);
        EditText mpScores5target = findViewById(R.id.mpScores5target);
        EditText mpScores4target = findViewById(R.id.mpScores4target);

        TextView mpScores10current = findViewById(R.id.mpScores10current);
        TextView mpScores9current = findViewById(R.id.mpScores9current);
        TextView mpScores8current = findViewById(R.id.mpScores8current);
        TextView mpScores7current = findViewById(R.id.mpScores7current);
        TextView mpScores6current = findViewById(R.id.mpScores6current);
        TextView mpScores5current = findViewById(R.id.mpScores5current);
        TextView mpScores4current = findViewById(R.id.mpScores4current);

        TextView mpScores10cumulative = findViewById(R.id.mpScores10cumulative);
        TextView mpScores9cumulative = findViewById(R.id.mpScores9cumulative);
        TextView mpScores8cumulative = findViewById(R.id.mpScores8cumulative);
        TextView mpScores7cumulative = findViewById(R.id.mpScores7cumulative);
        TextView mpScores6cumulative = findViewById(R.id.mpScores6cumulative);
        TextView mpScores5cumulative = findViewById(R.id.mpScores5cumulative);
        TextView mpScores4cumulative = findViewById(R.id.mpScores4cumulative);

        TextView mpScores10attempted = findViewById(R.id.mpScores10attempted);
        TextView mpScores9attempted = findViewById(R.id.mpScores9attempted);
        TextView mpScores8attempted = findViewById(R.id.mpScores8attempted);
        TextView mpScores7attempted = findViewById(R.id.mpScores7attempted);
        TextView mpScores6attempted = findViewById(R.id.mpScores6attempted);
        TextView mpScores5attempted = findViewById(R.id.mpScores5attempted);
        TextView mpScores4attempted = findViewById(R.id.mpScores4attempted);

        //lets start populating fields


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance("https://the360-70adc-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        totalAttemptedDB = FirebaseDatabase.getInstance("https://the360-70adc-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Max Putts");
        DatabaseReference totalAttemptedDBchild = totalAttemptedDB.child(uid);

        //easiest = ie. attempted putts

        totalAttemptedDBchild.addValueEventListener(attemptedPutts);


        //goal values
        getGoalValues("4m");
        getGoalValues("5m");
        getGoalValues("6m");
        getGoalValues("7m");

        mpScores4target.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newValue4m = mpScores4target.getText().toString();
                databaseReference.child("Max Putt Targets").child(uid).child("4m").setValue(newValue4m);
            }
        });

        mpScores5target.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newValue5m = mpScores5target.getText().toString();
                databaseReference.child("Max Putt Targets").child(uid).child("5m").setValue(newValue5m);
            }
        });

        mpScores6target.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newValue6m = mpScores6target.getText().toString();
                databaseReference.child("Max Putt Targets").child(uid).child("5m").setValue(newValue6m);
            }
        });

        mpScores7target.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newValue7m = mpScores7target.getText().toString();
                databaseReference.child("Max Putt Targets").child(uid).child("7m").setValue(newValue7m);
            }
        });






    }

    ValueEventListener attemptedPutts = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            int total4m = 0;
            int total5m = 0;
            int total6m = 0;
            int total7m = 0;
            int total8m = 0;
            int total9m = 0;
            int total10m = 0;

            int total4mattempted = 0;
            int total5mattempted = 0;
            int total6mattempted = 0;
            int total7mattempted = 0;
            int total8mattempted = 0;
            int total9mattempted = 0;
            int total10mattempted = 0;

            int cumulative5m = 0;
            int cumulative6m = 0;
            int cumulative7m = 0;
            int cumulative8m = 0;
            int cumulative9m = 0;
            int cumulative10m = 0;

            int cumulative5mattempted = 0;
            int cumulative6mattempted = 0;
            int cumulative7mattempted = 0;
            int cumulative8mattempted = 0;
            int cumulative9mattempted = 0;
            int cumulative10mattempted = 0;



            for(DataSnapshot ds : snapshot.getChildren()) {
                String strtotal4m = ds.child("4m").getValue(String.class);
                if(strtotal4m != null){
                    int intTotal4m = Integer.parseInt(strtotal4m);
                    total4m += intTotal4m;

                    String strtotalattempted4m = ds.child("4m attempted").getValue(String.class);
                    int intTotal4mattempted = Integer.parseInt(strtotalattempted4m);
                    total4mattempted += intTotal4mattempted;

                    cumulative5m += intTotal4m;
                    cumulative6m += intTotal4m;
                    cumulative7m += intTotal4m;
                    cumulative8m += intTotal4m;
                    cumulative9m += intTotal4m;
                    cumulative10m += intTotal4m;

                    cumulative5mattempted += intTotal4mattempted;
                    cumulative6mattempted += intTotal4mattempted;
                    cumulative7mattempted += intTotal4mattempted;
                    cumulative8mattempted += intTotal4mattempted;
                    cumulative9mattempted += intTotal4mattempted;
                    cumulative10mattempted += intTotal4mattempted;

                }

                String strtotal5m = ds.child("5m").getValue(String.class);
                if (strtotal5m != null) {
                    int intTotal5m = Integer.parseInt(strtotal5m);
                    total5m += intTotal5m;

                    String strtotalattempted5m = ds.child("5m attempted").getValue(String.class);
                    int intTotal5mattempted = Integer.parseInt(strtotalattempted5m);
                    total5mattempted += intTotal5mattempted;

                    cumulative5m += intTotal5m;
                    cumulative6m += intTotal5m;
                    cumulative7m += intTotal5m;
                    cumulative8m += intTotal5m;
                    cumulative9m += intTotal5m;
                    cumulative10m += intTotal5m;

                    cumulative5mattempted += intTotal5mattempted;
                    cumulative6mattempted += intTotal5mattempted;
                    cumulative7mattempted += intTotal5mattempted;
                    cumulative8mattempted += intTotal5mattempted;
                    cumulative9mattempted += intTotal5mattempted;
                    cumulative10mattempted += intTotal5mattempted;
                }

                String strtotal6m = ds.child("6m").getValue(String.class);
                if (strtotal6m != null) {
                    int intTotal6m = Integer.parseInt(strtotal6m);
                    total6m += intTotal6m;

                    String strtotalattempted6m = ds.child("6m attempted").getValue(String.class);
                    int intTotal6mattempted = Integer.parseInt(strtotalattempted6m);
                    total6mattempted += intTotal6mattempted;


                    cumulative6m += intTotal6m;
                    cumulative7m += intTotal6m;
                    cumulative8m += intTotal6m;
                    cumulative9m += intTotal6m;
                    cumulative10m += intTotal6m;


                    cumulative6mattempted += intTotal6mattempted;
                    cumulative7mattempted += intTotal6mattempted;
                    cumulative8mattempted += intTotal6mattempted;
                    cumulative9mattempted += intTotal6mattempted;
                    cumulative10mattempted += intTotal6mattempted;
                }


                String strtotal7m = ds.child("7m").getValue(String.class);
                if (strtotal7m != null) {
                    int intTotal7m = Integer.parseInt(strtotal7m);
                    total7m += intTotal7m;

                    String strtotalattempted7m = ds.child("7m attempted").getValue(String.class);
                    int intTotal7mattempted = Integer.parseInt(strtotalattempted7m);
                    total7mattempted += intTotal7mattempted;


                    cumulative7m += intTotal7m;
                    cumulative8m += intTotal7m;
                    cumulative9m += intTotal7m;
                    cumulative10m += intTotal7m;



                    cumulative7mattempted += intTotal7mattempted;
                    cumulative8mattempted += intTotal7mattempted;
                    cumulative9mattempted += intTotal7mattempted;
                    cumulative10mattempted += intTotal7mattempted;
                }


            }

            if (total4m != 0){
                String strtotal4mattempted = String.valueOf(total4mattempted);
                TextView mpScores4attempted = findViewById(R.id.mpScores4attempted);
                mpScores4attempted.setText(strtotal4mattempted);

                double doubleTotal4m = Double.valueOf(total4m);
                double doubleTotal4mattempted = Double.valueOf(total4mattempted);

                double current4mpercentage = ((doubleTotal4m / doubleTotal4mattempted))*100;
                int intcurrent4mpercentage = (int) current4mpercentage;
                String strcurrent4mpercentage = String.valueOf(intcurrent4mpercentage);
                TextView mpScores4mCurrent = findViewById(R.id.mpScores4current);
                mpScores4mCurrent.setText(strcurrent4mpercentage + "%");
            }

            if (total5m != 0){
                String strtotal5mattempted = String.valueOf(total5mattempted);
                TextView mpScores5attempted = findViewById(R.id.mpScores5attempted);
                mpScores5attempted.setText(strtotal5mattempted);

                double doubleTotal5m = Double.valueOf(total5m);
                double doubleTotal5mattempted = Double.valueOf(total5mattempted);

                double current5mpercentage = ((doubleTotal5m / doubleTotal5mattempted)) *100;
                int intcurrent5mpercentage = (int) current5mpercentage;
                String strcurrent5mpercentage = String.valueOf(intcurrent5mpercentage);
                TextView mpScores5mCurrent = findViewById(R.id.mpScores5current);
                mpScores5mCurrent.setText(strcurrent5mpercentage + "%");
            }

            if (total6m != 0){
                String strtotal6attempted = String.valueOf(total6mattempted);
                TextView mpScores6attempted = findViewById(R.id.mpScores6attempted);
                mpScores6attempted.setText(strtotal6attempted);

                double doubleTotal6m = Double.valueOf(total6m);
                double doubleTotal6mattempted = Double.valueOf(total6mattempted);

                double current6mpercentage = ((doubleTotal6m / doubleTotal6mattempted)) *100;
                int intcurrent6mpercentage = (int) current6mpercentage;
                String strcurrent6mpercentage = String.valueOf(intcurrent6mpercentage);
                TextView mpScores6mCurrent = findViewById(R.id.mpScores6current);
                mpScores6mCurrent.setText(strcurrent6mpercentage + "%");
            }

            if (total7m != 0){
                String strtotal7attempted = String.valueOf(total7mattempted);
                TextView mpScores7attempted = findViewById(R.id.mpScores7attempted);
                mpScores7attempted.setText(strtotal7attempted);

                double doubleTotal7m = Double.valueOf(total7m);
                double doubleTotal7mattempted = Double.valueOf(total7mattempted);

                double current7mpercentage = ((doubleTotal7m / doubleTotal7mattempted)) * 100;
                int intcurrent7mpercentage = (int) current7mpercentage;
                String strcurrent7mpercentage = String.valueOf(intcurrent7mpercentage);
                TextView mpScores7mCurrent = findViewById(R.id.mpScores7current);
                mpScores7mCurrent.setText(strcurrent7mpercentage + "%");
            }

            if (cumulative5mattempted != 0) {
                double doublecumulative5mattempted = Double.valueOf(cumulative5mattempted);
                double doublecumulative5m = Double.valueOf(cumulative5m);
                double cumulative5mpercentage = (doublecumulative5m / doublecumulative5mattempted) *100;
                int intcumulative5mpercentage = (int) cumulative5mpercentage;

                TextView cumulative5mpercentageTextView = findViewById(R.id.mpScores5cumulative);
                cumulative5mpercentageTextView.setText(intcumulative5mpercentage + "%");
            }

            if (cumulative6mattempted != 0) {
                double doublecumulative6mattempted = Double.valueOf(cumulative6mattempted);
                double doublecumulative6m = Double.valueOf(cumulative6m);
                double cumulative6mpercentage = (doublecumulative6m / doublecumulative6mattempted) *100;
                int intcumulative6mpercentage = (int) cumulative6mpercentage;

                TextView cumulative6mpercentageTextView = findViewById(R.id.mpScores6cumulative);
                cumulative6mpercentageTextView.setText(intcumulative6mpercentage + "%");
            }

            if (cumulative7mattempted != 0) {
                double doublecumulative7mattempted = Double.valueOf(cumulative7mattempted);
                double doublecumulative7m = Double.valueOf(cumulative7m);
                double cumulative7mpercentage = (doublecumulative7m / doublecumulative7mattempted) *100;
                int intcumulative7mpercentage = (int) cumulative7mpercentage;

                TextView cumulative7mpercentageTextView = findViewById(R.id.mpScores7cumulative);
                cumulative7mpercentageTextView.setText(intcumulative7mpercentage + "%");
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    public void getGoalValues(String distance){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            String goal;
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                Map<String, Object> totalsData = (Map<String, Object>) snapshot.child("Max Putt Targets").child(uid).getValue();
                goal = totalsData.get(distance).toString();


                if (distance.equals("4m")) {
                    EditText mpScores4target = findViewById(R.id.mpScores4target);
                    mpScores4target.setText("99");
                }

                if (distance == "5m") {
                    EditText mpScores5target = findViewById(R.id.mpScores5target);
                    mpScores5target.setText(goal);
                }

                if (distance == "6m") {
                    EditText mpScores6target = findViewById(R.id.mpScores6target);
                    mpScores6target.setText(goal);
                }

                if (distance == "7m") {
                    EditText mpScores7target = findViewById(R.id.mpScores7target);
                    mpScores7target.setText(goal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }

}