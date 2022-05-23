package com.example.the360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Randomputts extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Integer puttDistance;
    String location, windSpeed, windFrontBehind, windLeftRight;
    TextView puttLocationHeader, windSpeedHeader, windFrontBackHeader, windLeftRightHeader, puttingDistanceHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomputts);

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

        //list buttons

        Button mpNewRound = findViewById(R.id.mpNewRound);
        Button mpExit = findViewById(R.id.mpExit);

        EditText score = findViewById(R.id.maxPuttsmade);
        EditText misses = findViewById(R.id.maxPuttsMissed);
        EditText attempts = findViewById(R.id.maxPuttsAttempts);

        ToggleButton toggleLocation = findViewById(R.id.toggleLocation);
        ToggleButton toggleWind = findViewById(R.id.toggleWind);
        ToggleButton toggleStance = findViewById(R.id.toggleStance);
        ToggleButton toggleDistance = findViewById(R.id.toggleDistance);
        ToggleButton toggleTarget = findViewById(R.id.toggleTarget);

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

        TextView headerScore = findViewById(R.id.scoreText);
        TextView headerMisses = findViewById(R.id.missesText);
        TextView attemptHeader = findViewById(R.id.attemptsText);

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


        toggleLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    radioGroupLocation.setVisibility(View.VISIBLE);
                    puttLocationHeader.setVisibility(View.VISIBLE);
                } else {
                    radioGroupLocation.setVisibility(View.INVISIBLE);
                    puttLocationHeader.setVisibility(View.INVISIBLE);
                }
            }
        });

        toggleWind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    radioGroupWindSelectWindSpeed.setVisibility(View.VISIBLE);
                    windSpeedHeader.setVisibility(View.VISIBLE);
                } else {
                    radioGroupWindSelectWindSpeed.setVisibility(View.INVISIBLE);
                    windSpeedHeader.setVisibility(View.INVISIBLE);

                    radioGroupWindSelectFrontBack.setVisibility(View.INVISIBLE);
                    radioGroupWindSelectLeftRight.setVisibility(View.INVISIBLE);

                    windFrontBackHeader.setVisibility(View.INVISIBLE);
                    windLeftRightHeader.setVisibility(View.INVISIBLE);
                }
            }
        });

        toggleDistance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    radioGroupMaxPuttsDistance.setVisibility(View.VISIBLE);
                    puttingDistanceHeader.setVisibility(View.VISIBLE);
                } else {
                    radioGroupMaxPuttsDistance.setVisibility(View.INVISIBLE);
                    puttingDistanceHeader.setVisibility(View.INVISIBLE);
                }
            }
        });

        toggleTarget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {



                    TableRow.LayoutParams hideParams = new TableRow.LayoutParams(
                            0,
                            100,
                            0);

                    TableRow.LayoutParams visibleParams = new TableRow.LayoutParams(
                            0,
                            100,
                            1);


                    headerScore.setVisibility(View.INVISIBLE);
                    headerScore.setLayoutParams(hideParams);
                    score.setVisibility(View.INVISIBLE);
                    score.setLayoutParams(hideParams);
                    attemptHeader.setVisibility(View.VISIBLE);
                    attemptHeader.setLayoutParams(visibleParams);
                    attempts.setVisibility(View.VISIBLE);
                    attempts.setLayoutParams(visibleParams);
                    headerMisses.setVisibility(View.VISIBLE);
                    headerMisses.setLayoutParams(visibleParams);
                    misses.setVisibility(View.VISIBLE);
                    misses.setLayoutParams(visibleParams);




                } else {


                    TableRow.LayoutParams hideParams = new TableRow.LayoutParams(
                            0,
                            100,
                            0);

                    TableRow.LayoutParams visibleParams = new TableRow.LayoutParams(
                            0,
                            100,
                            1);

                    headerScore.setVisibility(View.VISIBLE);
                    headerScore.setLayoutParams(visibleParams);
                    score.setVisibility(View.VISIBLE);
                    score.setLayoutParams(visibleParams);
                    attemptHeader.setVisibility(View.INVISIBLE);
                    attemptHeader.setLayoutParams(hideParams);
                    attempts.setVisibility(View.INVISIBLE);
                    attempts.setLayoutParams(hideParams);
                    headerMisses.setVisibility(View.INVISIBLE);
                    headerMisses.setLayoutParams(hideParams);
                    misses.setVisibility(View.INVISIBLE);
                    misses.setLayoutParams(hideParams);

                }
            }
        });

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
                        radioGroupWindSelectFrontBack.setVisibility(View.VISIBLE);
                        radioGroupWindSelectLeftRight.setVisibility(View.VISIBLE);

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



    }
}