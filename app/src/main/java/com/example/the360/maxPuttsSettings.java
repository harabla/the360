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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.w3c.dom.Document;

import java.util.HashMap;

public class maxPuttsSettings extends AppCompatActivity {

    DatabaseReference databaseReference;
    Integer puttDistance;
    String location, windSpeed, windFrontBehind, windLeftRight;
    TextView puttLocationHeader, windSpeedHeader, windFrontBackHeader, windLeftRightHeader, puttingDistanceHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_putts_settings);


        //list buttons

        Button mpNewRound = findViewById(R.id.mpNewRound);
        Button mpSaveExit = findViewById(R.id.mpFinishExit);
        Button mpExit = findViewById(R.id.mpExit);

        EditText score = findViewById(R.id.maxPuttsmade);

        puttLocationHeader = findViewById(R.id.puttLocationText);
        windSpeedHeader = findViewById(R.id.windSpeedText);
        windFrontBackHeader = findViewById(R.id.windFrontBackText);
        windLeftRightHeader = findViewById(R.id.windLeftRightText);
        puttingDistanceHeader = findViewById(R.id.puttingDistanceText);

        RadioGroup radioGroupLocation = (RadioGroup) findViewById(R.id.puttLocation);
        RadioGroup radioGroupWindSelectWindSpeed = (RadioGroup) findViewById(R.id.windSpeed);
        RadioGroup radioGroupWindSelectFrontBack = (RadioGroup) findViewById(R.id.windDirectionFrontBack);
        RadioGroup radioGroupWindSelectLeftRight = (RadioGroup) findViewById(R.id.windDirectionLeftRight);
        RadioGroup radioGroupMaxPuttsDistance = (RadioGroup) findViewById(R.id.maxPuttsDistance);
        RadioButton radioButtonWindFrontBackNone = (RadioButton) findViewById(R.id.mpWindFrontBackNone);
        RadioButton radioButtonWindLeftRightNone = (RadioButton) findViewById(R.id.mpWindLeftRightNone);
        RadioButton radioButtonWindSpeedNone = (RadioButton) findViewById(R.id.mpWindNone);
        RadioButton radioButtonLocationIndoors = (RadioButton) findViewById(R.id.mpIndoors);

        //Default values
        radioButtonWindFrontBackNone.setChecked(true);
        windFrontBehind = "None";
        radioButtonWindLeftRightNone.setChecked(true);
        windLeftRight = "None";
        radioButtonWindSpeedNone.setChecked(true);
        windSpeed = "None";
        radioButtonLocationIndoors.setChecked(true);
        location = "Indoors";


        //lets hide the radiogroups we dont need initially

        radioGroupWindSelectFrontBack.setVisibility(View.INVISIBLE);
        radioGroupWindSelectLeftRight.setVisibility(View.INVISIBLE);
        radioGroupWindSelectWindSpeed.setVisibility(View.INVISIBLE);
        windSpeedHeader.setVisibility(View.INVISIBLE);
        windFrontBackHeader.setVisibility(View.INVISIBLE);
        windLeftRightHeader.setVisibility(View.INVISIBLE);



        //lets give default checked states for radio buttons

        /// Time to make life difficult -- firebase
        databaseReference = FirebaseDatabase.getInstance("https://the360-70adc-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        //lets assign values to the different selections


        //What do those buttons do

        //MP new round - should save data to firebase & clear putt count - other settings to stay the same
        mpNewRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                String stscore = score.getText().toString();
                int finalscore = Integer.parseInt(stscore);

                Long time = System.currentTimeMillis()/1000;
                String ts = time.toString();

                HashMap<String , Object> map = new HashMap<>();
                map.put("Score", finalscore);
                map.put("Putts total", finalscore+1);
                map.put("Distance", puttDistance);
                map.put("Wind Speed", windSpeed);
                map.put("Location", location);
                map.put("Wind Direction FrontBack", windFrontBehind);
                map.put("Wind Direction LeftRight", windLeftRight);

                databaseReference.child("Max Putts").child(uid).child(ts).updateChildren(map);


                databaseReference.child("totals").child(uid).child("total putts").setValue(ServerValue.increment(finalscore+1));
                score.getText().clear();



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
                          windSpeedHeader.setVisibility(View.INVISIBLE);
                          windFrontBackHeader.setVisibility(View.INVISIBLE);
                          windLeftRightHeader.setVisibility(View.INVISIBLE);

                          radioButtonWindFrontBackNone.setChecked(true);
                          radioButtonWindLeftRightNone.setChecked(true);
                          radioButtonWindSpeedNone.setChecked(true);

                          location = "indoors";


                          break;
                    case 1: // outdoors
                                       
                         Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                         radioGroupWindSelectWindSpeed.setVisibility(View.VISIBLE);
                        windSpeedHeader.setVisibility(View.VISIBLE);

                         location = "Outdoors";
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

                        windFrontBackHeader.setVisibility(View.INVISIBLE);
                        windLeftRightHeader.setVisibility(View.INVISIBLE);


                        radioButtonWindFrontBackNone.setChecked(true);
                        radioButtonWindLeftRightNone.setChecked(true);

                        windSpeed = "none";

                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        radioGroupWindSelectFrontBack.setVisibility(View.VISIBLE);
                        radioGroupWindSelectLeftRight.setVisibility(View.VISIBLE);
                        windFrontBackHeader.setVisibility(View.VISIBLE);
                        windLeftRightHeader.setVisibility(View.VISIBLE);
                        windSpeed = "Low";
                        break;

                    case 2:
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        windFrontBackHeader.setVisibility(View.VISIBLE);
                        windLeftRightHeader.setVisibility(View.VISIBLE);

                        windFrontBackHeader.setVisibility(View.VISIBLE);
                        windLeftRightHeader.setVisibility(View.VISIBLE);
                        windSpeed = "Medium";
                        break;

                    case 3:
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        radioGroupWindSelectFrontBack.setVisibility(View.VISIBLE);
                        radioGroupWindSelectLeftRight.setVisibility(View.VISIBLE);

                        windFrontBackHeader.setVisibility(View.VISIBLE);
                        windLeftRightHeader.setVisibility(View.VISIBLE);
                        windSpeed = "High";
                        break;
                }
            }
        });


        //Lets give puttind distance some values
        radioGroupMaxPuttsDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checked = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(checked);

                switch (index) {
                    case 0:
                        puttDistance = 4;
                        break;
                    case 1:
                        puttDistance = 5;
                        break;
                    case 2:
                        puttDistance = 6;
                        break;
                    case 3:
                        puttDistance = 7;
                        break;
                    case 4:
                        puttDistance = 8;
                        break;
                    case 5:
                        puttDistance = 9;
                        break;
                    case 6:
                        puttDistance = 10;
                        break;
                }
            }
        });


        radioGroupWindSelectFrontBack.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checked = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(checked);

                switch (index) {
                    case 0:
                        windFrontBehind = "Front";
                        break;
                    case 1:
                        windFrontBehind = "None";
                        break;
                    case 2:
                        windFrontBehind = "Back";
                        break;
                }
            }
        });

        radioGroupWindSelectLeftRight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checked = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(checked);

                switch (index) {
                    case 0:
                        windLeftRight = "Left";
                        break;
                    case 1:
                        windLeftRight = "None";
                        break;
                    case 2:
                        windLeftRight = "Right";
                        break;
                }
            }
        });

        //public getRecord(String distance) {


        //}






    }
}