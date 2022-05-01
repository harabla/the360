package com.example.the360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class maxPuttsSettings extends AppCompatActivity {

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_putts_settings);


        //list buttons

        Button mpNewRound = findViewById(R.id.mpNewRound);
        Button mpSaveExit = findViewById(R.id.mpFinishExit);
        Button mpExit = findViewById(R.id.mpExit);

        EditText score = findViewById(R.id.maxPuttsmade);

        RadioGroup radioGroupLocation = (RadioGroup) findViewById(R.id.puttLocation);
        RadioGroup radioGroupWindSelectWindSpeed = (RadioGroup) findViewById(R.id.windSpeed);
        RadioGroup radioGroupWindSelectFrontBack = (RadioGroup) findViewById(R.id.windDirectionFrontBack);
        RadioGroup radioGroupWindSelectLeftRight = (RadioGroup) findViewById(R.id.windDirectionLeftRight);

        //lets hide the radiogroups we dont need initially

        radioGroupWindSelectFrontBack.setVisibility(View.INVISIBLE);
        radioGroupWindSelectLeftRight.setVisibility(View.INVISIBLE);
        radioGroupWindSelectWindSpeed.setVisibility(View.INVISIBLE);


        //lets give default checked states for radio buttons

        /// Time to make life difficult -- firebase
        databaseReference = FirebaseDatabase.getInstance("https://the360-70adc-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        //test user
        String user = "Harri";




        //What do those buttons do

        //MP new round - should save data to firebase & clear putt count - other settings to stay the same
        mpNewRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String stscore = score.getText().toString();
                int finalscore = Integer.parseInt(stscore);

                Long time = System.currentTimeMillis()/1000;
                String ts = time.toString();

                databaseReference.child("Max Putts").child(user).child(ts).setValue(String.valueOf(finalscore));


                Toast.makeText(getApplicationContext(),"This should save data to firebase & clear putt count - other settings to stay the same",Toast.LENGTH_SHORT).show();

            }

        });




        //MP save exit = should save data to firebase and exit to the front page
        mpSaveExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"This should save data to firebase and exit to the front page",Toast.LENGTH_SHORT).show();

                databaseReference.setValue("160").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        Log.i("succeeding", "success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        Log.i("failing", "failed");
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "wtf", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(maxPuttsSettings.this,MainActivity.class);
                startActivity(intent);

            }
        });

        //mp exit - should simply exit to the front page
        mpExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"This should simply exit to the front page",Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(maxPuttsSettings.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //wind only asked if outside
        radioGroupLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                View checked = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(checked);



                switch (index) {
                    case 0: //indoors
                          Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                          radioGroupWindSelectWindSpeed.setVisibility(View.INVISIBLE);
                          radioGroupWindSelectFrontBack.setVisibility(View.INVISIBLE);
                          radioGroupWindSelectLeftRight.setVisibility(View.INVISIBLE);

                          break;
                    case 1: // outdoors
                                       
                         Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                         radioGroupWindSelectWindSpeed.setVisibility(View.VISIBLE);
                         break;

               }
            }
        });

        //wind direction only if wind speed =! none
        radioGroupWindSelectWindSpeed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checked = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(checked);

                switch (index) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        radioGroupWindSelectFrontBack.setVisibility(View.INVISIBLE);
                        radioGroupWindSelectLeftRight.setVisibility(View.INVISIBLE);

                        break;
                    case 1:
                    case 2:
                    case 3:
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        radioGroupWindSelectFrontBack.setVisibility(View.VISIBLE);
                        radioGroupWindSelectLeftRight.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });



    }
}